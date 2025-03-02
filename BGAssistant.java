import javax.swing.JOptionPane;

public class BGAssistant {

    public String p1_name;
    public String p2_name;
    public BGFrame frame;

    public void BG(Player p1, Player p2, BGFrame f) {
        
        frame = f;

        // GAME START
        JOptionPane.showMessageDialog(frame, "If you have mastered all the rules, \n" + 
        "press the OK button!", "Game Start", JOptionPane.INFORMATION_MESSAGE);

        // Initialize Player 1
        String p1_name = JOptionPane.showInputDialog("What should I call Player 1?");
        f.player_info[0][0].setText(p1_name);
        f.player_info[0][0].setEnabled(true);
        f.player_info[0][1].setEnabled(true);
        f.player_info[0][1].setText("10");
        f.player_info[0][1].setEnabled(true);
        f.input_board[0][0].setEnabled(true);
        f.input_board[0][1].setEnabled(true);

        // Initialize Player 2
        String p2_name = JOptionPane.showInputDialog("What should I call Player 2?");
        f.player_info[0][3].setText(p2_name);
        f.player_info[0][3].setEnabled(true);
        f.player_info[0][4].setText("10");
        f.player_info[0][4].setEnabled(true);
        
        // Set P1 turn
        f.player_info[0][2].setText("It's your turn!");
        f.player_info[0][2].setEnabled(true);
        
    }
    
}
