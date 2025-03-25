package guessingNumbers;

import javax.swing.*;
import java.util.*;

public class StageGame {
    private Player player;
    private Random random;
    private int[] ansNumber;
    private int stage;

    public StageGame(Player player) {
        this.player=player;
        this.random=new Random();
        this.stage=1;
    }

    private void generateTheNumber() {
        ansNumber=new int[4];
        Set<Integer> usedNumbers = new HashSet<>();
        for (int i=0;i<4;i++) {
            int num;
            do {
                num=random.nextInt(10);
            }
            while (usedNumbers.contains(num));
            ansNumber[i] = num;
            usedNumbers.add(num);
        }
    }

    private int[] checkGuess(int[] guess) {
        int[] result=new int[2];

        for (int i=0;i<4;i++) {
            if(guess[i]==ansNumber[i]) {
                result[0]++;
            }
            else if(containsDigit(ansNumber, guess[i])) {
                result[1]++;
            }
        }

        return result;
    }

    private boolean containsDigit(int[] array, int value) {
    	for(int i=0;i<array.length;i++) {
    	    if(array[i]==value) {
    	        return true;
    	    }
    	}
    	return false;
    }

    private void giveHint() {
        int index=random.nextInt(4);
        JOptionPane.showMessageDialog(null, "Hint: One of the digits is "+ansNumber[index]);
    }
    
    public void play() {
        int[] maxAttemptsPerStage={25,22,20,19,18,17,16,15,14,13,12,10};

        while (stage<=12) {
            generateTheNumber();
            int attempts=0;
            int maxAttempts=maxAttemptsPerStage[stage - 1];
            List <GameHistory.GuessResult> guessResults=new ArrayList<>();

            while(true) {
                String guessStr=JOptionPane.showInputDialog(player.getName()+", enter your guess for stage "+stage+" ("+(maxAttempts - attempts)+" attempts left):");
                if(guessStr==null) {
                    return;
                }
                if(guessStr.length() != 4||!guessStr.matches("\\d{4}")) {
                    JOptionPane.showMessageDialog(null, "Please enter a 4-digit number.");
                    continue;
                }

                int[] guess=new int[4];
                try {
                    for(int i=0;i<4;i++) {
                        guess[i]=guessStr.charAt(i)-'0';
                    }
                }
                catch(NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter only digits.");
                    continue;
                }

                attempts++;
                int[] result=checkGuess(guess);

                if(result[0]==4) {
                    JOptionPane.showMessageDialog(null, "Congratulations! You've passed stage "+stage+" in "+attempts+" attempts.");
                    StageRanking.record(player.getName(), stage);
                    GameHistory.record(player.getName(), "Stage Game", attempts, guessResults);
                    stage++;
                    break;
                }
                else {
                    JOptionPane.showMessageDialog(null, result[0] +"A"+result[1]+"B");
                }

                if(attempts==maxAttempts) {
                    JOptionPane.showMessageDialog(null, "You've failed stage "+stage+". Better luck next time!");
                    return;
                }

                if (attempts==30||attempts==40||attempts==45) {
                    int option=JOptionPane.showConfirmDialog(null, "Do you need a hint?", "Hint", JOptionPane.YES_NO_OPTION);
                    if (option==JOptionPane.YES_OPTION) {
                        giveHint();
                    }
                }
            }
        }

        JOptionPane.showMessageDialog(null, "Congratulations! You've completed all stages!");
    }

    
}
