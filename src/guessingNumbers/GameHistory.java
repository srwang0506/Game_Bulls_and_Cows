package guessingNumbers;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameHistory {
    private static List<HistoryRecord> history = new ArrayList<>();

    public static void record(String playerName, String mode, int attempts, List<GuessResult> guessResults) {
        history.add(new HistoryRecord(playerName, mode, attempts, guessResults));
    }

    public static void display() {
        StringBuilder stringBuilder1= new StringBuilder("Game History:\n\n");
        for(int i=0;i<history.size();i++) {
            HistoryRecord record=history.get(i);
            stringBuilder1.append("Player: ").append(record.playerName).append("\n");
            stringBuilder1.append("Mode: ").append(record.mode).append("\n");
            stringBuilder1.append("Attempts: ").append(record.attempts).append("\n");
            stringBuilder1.append("Guesses:\n");
            
            for(int j=0;j<record.guessResults.size();j++) {
                GuessResult result = record.guessResults.get(j);
                stringBuilder1.append("Guess: ")
                .append(result.guess)
                .append(" - Result: ")
                .append(result.result)
                .append("\n");
            }
            stringBuilder1.append("\n");
        }
        JOptionPane.showMessageDialog(null, stringBuilder1.toString(), "Game History", JOptionPane.INFORMATION_MESSAGE);
    }

    private static class HistoryRecord {
        String playerName;
        String mode;
        int attempts;
        List<GuessResult> guessResults;

        public HistoryRecord(String playerName, String mode, int attempts, List<GuessResult> guessResults) {
            this.playerName=playerName;
            this.mode=mode;
            this.attempts=attempts;
            this.guessResults=guessResults;
        }
    }

    public static class GuessResult {
        String guess;
        String result;

        public GuessResult(String guess, String result) {
            this.guess=guess;
            this.result=result;
        }
    }
}