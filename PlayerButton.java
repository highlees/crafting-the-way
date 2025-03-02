import java.awt.event.*;
import javax.swing.*;

public class PlayerButton extends JButton implements ActionListener {
    
    public PlayerButton(BGFrame f) {
        addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (this.getText().equals("")) {

            int x = this.getX();
            int y = this.getY();
            System.out.println("x's pos : " + x + " y's pos " + y);

        }
        
    }
    
}