package guessingNumbers;

import javax.swing.*;
import java.util.*;

public class DoublePlayerGame {
    private Player player1;
    private Player player2;
    private boolean allowDuplicates;
    private int[] ansNumber;

    public DoublePlayerGame(Player player1, Player player2, boolean allowDuplicates) {
        this.player1=player1;
        this.player2=player2;
        this.allowDuplicates=allowDuplicates;
    }

    private int[] checkGuess(int[] guess) {
        int[] result=new int[2];

        for(int i=0;i<4;i++) {
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
        int index=new Random().nextInt(4);
        JOptionPane.showMessageDialog(null, "Hint: One of the digits is "+ansNumber[index]);
    }
    
    public void play() {
        String ansStr=JOptionPane.showInputDialog(player1.getName() + ", enter a 4-digit number for " + player2.getName() + " to guess:");
        if(ansStr==null) {
            return;
        }
        if(ansStr.length()!=4||!ansStr.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(null, "Please enter a 4-digit number.");
            return;
        }

        ansNumber=new int[4];
        Set<Integer> usedNumbers=new HashSet<>();
        try {
            for(int i=0;i<4;i++) {
                int num=ansStr.charAt(i)-'0';
                if(!allowDuplicates&&usedNumbers.contains(num)) {
                    JOptionPane.showMessageDialog(null, "No duplicate digits allowed.");
                    return;
                }
                ansNumber[i]=num;
                usedNumbers.add(num);
            }
        }
        catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter only digits.");
            return;
        }

        int attempts=0;
        List <GameHistory.GuessResult> guessResults=new ArrayList<>();
        
        while (true) {
            String guessStr=JOptionPane.showInputDialog(player2.getName()+", enter your guess:");
            if (guessStr==null) {
                return;
            }
            if (guessStr.length()!=4||!guessStr.matches("\\d{4}")) {
                JOptionPane.showMessageDialog(null, "Please enter a 4-digit number.");
                continue;
            }

            int[] guess=new int[4];
            try {
                for(int i=0;i<4;i++) {
                    guess[i]=guessStr.charAt(i)-'0';
                }
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter only digits.");
                continue;
            }

            attempts++;
            int[] result=checkGuess(guess);

            if(result[0]==4) {
                JOptionPane.showMessageDialog(null, "Congratulations, "+player2.getName()+"! You've guessed the number in "+attempts+" attempts.");
                GameHistory.record(player2.getName(), "Double Player", attempts, guessResults);
                return;
            }
            else{
                JOptionPane.showMessageDialog(null, result[0]+"A"+result[1]+"B");
            }

            if(attempts==30||attempts==40||attempts==45) {
                int option=JOptionPane.showConfirmDialog(null, "Do you need a hint?", "Hint", JOptionPane.YES_NO_OPTION);
                if (option==JOptionPane.YES_OPTION) {
                    giveHint();
                }
            }
        }
    }

}
