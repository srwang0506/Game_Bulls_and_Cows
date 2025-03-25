package guessingNumbers;

import javax.swing.*;
import java.util.*;

public class LeaderBoard {
    private static Map<String, Integer> scores=new HashMap<>();

    public static void record(String playerName, int attempts) {
        scores.put(playerName, Math.min(scores.getOrDefault(playerName, Integer.MAX_VALUE), attempts));
    }

    public static void display() {
        StringBuilder stringBuilder1=new StringBuilder("LeaderBoard:\n");
        List<Map.Entry<String, Integer>> entryList=new ArrayList<>(scores.entrySet());
        for(int i=0;i<entryList.size();i++) {
            Map.Entry<String, Integer> entry=entryList.get(i);
            stringBuilder1.append(entry.getKey())
              .append(": ")
              .append(entry.getValue())
              .append(" attempts\n");
        }
        JOptionPane.showMessageDialog(null, stringBuilder1.toString(), "LeaderBoard", JOptionPane.INFORMATION_MESSAGE);
    }
}
