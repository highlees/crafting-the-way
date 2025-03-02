import java.awt.event.*;
import javax.swing.*;

public class InputButton extends JButton implements ActionListener {

    public BGFrame frame;
    public int P_row;
    public int P_col;
    private Inspector inspector;
    
    public InputButton(BGFrame f, Inspector ins) {
        frame = f;
        inspector = ins;
        addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e) {
        int x = this.getX();
        int y = this.getY();
        System.out.println("x's pos : " + x + " y's pos " + y);

        //------------------- USER INPUT BUTTON SETTING START -------------------

        // Deactivate Wall
        if (this.getText() == "P1 Pawn") {
            frame.deactivate_wall();
            // If P2 alternates mode
        	if (!(frame.p2_wall_set == 1));
        	else { /* frame.p2_wall_set == 1 */
        		frame.p2_wall_set -= 1;
            	int pos_r1 = inspector.getCurrentR(); // The row of the wall selected in the first
    			int pos_c1 = inspector.getCurrentC(); // The col of the wall selected in the first
    			BGButton first = frame.button_board[pos_r1][pos_c1]; // The first selected wall
    			first.setOpaque(false);
    			first.setBorderPainted(true);
                first.setBackground(null);
            }
            // User Select
            frame.UserSelect(this.getText());
        }

        if (this.getText() == "P2 Pawn") {
            frame.deactivate_wall();
            // If P2 alternates mode
        	if (!(frame.p2_wall_set == 1));
        	else { /* frame.p2_wall_set == 1 */
        		frame.p2_wall_set -= 1;
            	int pos_r1 = inspector.getCurrentR(); // The row of the wall selected in the first
    			int pos_c1 = inspector.getCurrentC(); // The col of the wall selected in the first
    			BGButton first = frame.button_board[pos_r1][pos_c1]; // The first selected wall
    			first.setOpaque(false);
    			first.setBorderPainted(true);
                first.setBackground(null);
            }
            // User Select
            frame.UserSelect(this.getText());
        }

        // Deactivate Pawn
        if (this.getText() == "P1 Wall") {
            frame.deactivate_pawn();
            // User Select
            frame.UserSelect(this.getText());
        }

        if (this.getText() == "P2 Wall") {
            frame.deactivate_pawn();
            // User Select
            frame.UserSelect(this.getText());
        }

        //------------------- USER INPUT BUTTON SETTING END -------------------

    }

}