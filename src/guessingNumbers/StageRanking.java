package guessingNumbers;

import javax.swing.*;
import java.util.*;

public class StageRanking {
    private static Map<String, Integer> stageRanking=new HashMap<>();

    public static void record(String playerName, int stage) {
        stageRanking.put(playerName, Math.max(stageRanking.getOrDefault(playerName, 0), stage));
    }

    public static void display() {
        StringBuilder stringBuilder1=new StringBuilder("Stage Ranking:\n");
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(stageRanking.entrySet());
        for (int i = 0; i < entryList.size(); i++) {
            Map.Entry<String, Integer> entry = entryList.get(i);
            stringBuilder1.append(entry.getKey())
              .append(": Stage ")
              .append(entry.getValue())
              .append("\n");
        }
        JOptionPane.showMessageDialog(null, stringBuilder1.toString(), "Stage Ranking", JOptionPane.INFORMATION_MESSAGE);
    }
}
