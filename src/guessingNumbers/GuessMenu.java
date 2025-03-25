package guessingNumbers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GuessMenu {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuessMenu::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame=new JFrame("Guessing Numbers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JLabel titleLabel=new JLabel("Guessing Numbers", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));

        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] buttonLabels={
                "Single Player",
                "Multiplayer",
                "Double Player",
                "Stage Mode",
                "View LeaderBoard",
                "View History",
                "View Stage Ranking",
                "View Rules",
                "Exit"
        };

        for (String label:buttonLabels) {
            JButton button=new JButton(label);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(new MenuButtonListener());
            panel.add(button);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        frame.getContentPane().add(BorderLayout.NORTH, titleLabel);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
    }
    
    public static void showSinglePlayerMenu() {
        String playerName=JOptionPane.showInputDialog("Enter your name:");
        if(playerName==null) {
            return;
        }

        Player player=new Player(playerName);
        int option=JOptionPane.showOptionDialog(null,
                "Choose number type:",
                "Number Type",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Allow Duplicates", "No Duplicates"},
                "Allow Duplicates");

        boolean allowDuplicates=(option==0);
        SinglePlayerGame game=new SinglePlayerGame(player, allowDuplicates);
        game.play();
    }

    public static void showMultiplayerMenu() {
        int playerCount;
        while(true) {
            String playerCountStr=JOptionPane.showInputDialog("Enter number of players (2-4):");
            if (playerCountStr==null) {
                return;
            }
            try {
                playerCount = Integer.parseInt(playerCountStr);
                if (playerCount >= 2 && playerCount <= 4) {
                    break;
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please enter a number between 2 and 4.");
                }
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.");
            }
        }

        boolean allowDuplicates=false;
        int option=JOptionPane.showOptionDialog(null,
                "Choose number type:",
                "Number Type",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Allow Duplicates", "No Duplicates"},
                "Allow Duplicates");
        allowDuplicates=(option==0);

        List<Player> players=new ArrayList<>();
        for(int i=1; i<=playerCount;i++) {
            String playerName=JOptionPane.showInputDialog("Enter name for Player "+i + ":");
            if(playerName==null) {
                return;
            }
            players.add(new Player(playerName));
        }

        MultiplayerGame game=new MultiplayerGame(players, allowDuplicates);
        game.play();
    }

    public static void showDoublePlayerMenu() {
        String playerName1=JOptionPane.showInputDialog("Enter name for Player 1:");
        if(playerName1==null) {
            return;
        }
        Player player1=new Player(playerName1);

        String playerName2=JOptionPane.showInputDialog("Enter name for Player 2:");
        if(playerName2==null) {
            return;
        }
        Player player2=new Player(playerName2);

        int option = JOptionPane.showOptionDialog(null,
                "Choose number type:",
                "Number Type",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Allow Duplicates", "No Duplicates"},
                "Allow Duplicates");

        boolean allowDuplicates=(option==0);
        DoublePlayerGame game=new DoublePlayerGame(player1, player2, allowDuplicates);
        game.play();
    }

    public static void showStageModeMenu() {
        String playerName=JOptionPane.showInputDialog("Enter your name:");
        if(playerName==null) {
            return;
        }
        Player player=new Player(playerName);
        StageGame game=new StageGame(player);
        game.play();
    }

    public static void showRules() {
    	String rules = "Guessing Numbers Game Rules:\n"
                +"1. Single Player: Try to guess the 4-digit number generated by the system.\n"
                +"2. Multiplayer: Multiple players take turns to guess the number. The one who guesses correctly first wins.\n"
                +"3. Double Player: Two players take turns to guess the number. The one who guesses correctly first wins.\n"
                +"4. Stage Mode: Progress through stages by guessing the number correctly in each stage.\n"
                +"5. Leaderboard: View the top players based on the number of attempts.\n"
                +"6. History: View your previous game records.\n"
                +"7. Stage Ranking: View the ranking of players based on stages completed.\n"
                +"Additional Rules:\n"
                +"1. You can choose whether to allow duplicate digits or not.\n"
                +"2. You will receive hints after 30, 40, and 45 incorrect attempts.\n"
                +"3. A hint will reveal one digit of the number (position not specified).\n";
        JOptionPane.showMessageDialog(null, rules, "Game Rules", JOptionPane.INFORMATION_MESSAGE);
    }

    private static class MenuButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command=e.getActionCommand();
            switch(command) {
                case "Single Player":
                    showSinglePlayerMenu();
                    break;
                case "Multiplayer":
                    showMultiplayerMenu();
                    break;
                case "Double Player":
                    showDoublePlayerMenu();
                    break;
                case "Stage Mode":
                    showStageModeMenu();
                    break;
                case "View LeaderBoard":
                    LeaderBoard.display();
                    break;
                case "View History":
                    GameHistory.display();
                    break;
                case "View Stage Ranking":
                    StageRanking.display();
                    break;
                case "View Rules":
                    showRules();
                    break;
                case "Exit":
                    System.exit(0);
                    break;
            }
        }
    }

    
}
