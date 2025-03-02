import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;

public class BGButton extends JButton implements ActionListener {

    public int UserSelectedButtonRow;
    public int UserSelectedButtonCol;
    public int P1WallRow[];
    public int P1WallCol[];
    public int P2WallRow[];
    public int P2WallCol[];
    private BGFrame frame;
    private Inspector ins;
    
    public BGButton(BGFrame f, Inspector ins) {
        frame = f;
        this.ins = ins;
        addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (this.getText().equals("")) {

            for (int row = 0; row < 17; row++) {
                for (int col = 0; col < 17; col++) {
                    if (frame.button_board[row][col].getX() == this.getX() && 
                    frame.button_board[row][col].getY() == this.getY()) {
                        UserSelectedButtonRow = row;
                        UserSelectedButtonCol = col;
                        System.out.println("this.row : " + UserSelectedButtonRow + 
                        " this.col : " + UserSelectedButtonCol);
                    }
                }
            }
            frame.UserRequestRow(UserSelectedButtonRow);
            frame.UserRequestCol(UserSelectedButtonCol);
            frame.UserRequest(this.getBackground());

            // ---------------------------------------- USER SELECTED SETTING START ----------------------------------------

            // -------------------------------------------- P1 START --------------------------------------------

            // When p1 wants to move Pawn...
            if (frame.UserSelected.equals("P1 Pawn") && 
            	frame.player_info[0][2].getText().equals("It's your turn!")) {
                // Check to see if it is in a moveable position
            	if (ins.pawnPossibility(UserSelectedButtonRow, UserSelectedButtonCol, 1) ||
                   	ins.pawnPossibilityDiagonal(UserSelectedButtonRow, UserSelectedButtonCol, 1) ||
                	ins.pawnPossibilityJump(UserSelectedButtonRow, UserSelectedButtonCol, 1)) {
	                if (this.getBackground() == frame.p2_color) {
	                    JOptionPane.showMessageDialog(frame,
	                     "Umm.. you can't move Pawn there.", 
	                     "Error Message", JOptionPane.WARNING_MESSAGE);
	                    this.getBackground();
	                    this.setBackground(this.getBackground());
	                }
	                else {
	                	ins.pawnMove(UserSelectedButtonRow, UserSelectedButtonCol, 1);
	                    // Paint existing P1 pawn normal color
	                    for (int row = 0; row < 17; row++) {
	                        for (int col = 0; col < 17; col++) {
	                            if (frame.button_board[row][col].getBackground() == frame.p1_color) {
	                                frame.button_board[row][col].setBackground(null);
	                            }
	                        }
	                    }
	                    // P1 Win
	                    if (this.getY() == 605) {
	                        JOptionPane.showMessageDialog(frame,
	                        "You Win, " + frame.player_info[0][0].getText() + "!", 
	                        "Congratulations", JOptionPane.INFORMATION_MESSAGE);
	                    this.getBackground();
	                    this.setBackground(this.getBackground());
	                    }
	                    // Move P1 Pawn
	                    this.setBackground(frame.p1_color);
	                    // Set P2 turn
	                    frame.player_info[0][2].setText("");
	                    frame.player_info[0][2].setEnabled(false);
	                    frame.player_info[0][5].setText("It's your turn!");
	                    frame.player_info[0][5].setEnabled(true);
	                    frame.input_board[0][0].setEnabled(false);
	                    frame.input_board[0][1].setEnabled(false);
	                    frame.input_board[0][2].setEnabled(true);
	                    frame.input_board[0][3].setEnabled(true);
	                    
	                    for (int row = 0; row < 17; row++) {
                        	for (int col = 0; col < 17; col++) {
                      			frame.button_board[row][col].setEnabled(false);
                      		}
                  		}
	                    
	                }
            	}
            	else {
            		JOptionPane.showMessageDialog(frame,
            				"Umm.. you can't move Pawn there!", 
            				"Error Message", JOptionPane.WARNING_MESSAGE);
            	}
            }

            // When p1 wants to build a wall...
            if (frame.UserSelected.equals("P1 Wall") && 
            frame.player_info[0][2].getText().equals("It's your turn!") && 
            frame.p1_wall_count != 0 &&
            ins.wallPossibility(UserSelectedButtonRow, UserSelectedButtonCol) ) {
            	
        		if (frame.p1_wall_set % 2 == 0) {
        			ins.setCurrentPos(UserSelectedButtonRow, UserSelectedButtonCol);
        			this.setOpaque(true);
	                this.setBorderPainted(false);
	                this.setBackground(Color.BLACK);
	                frame.p1_wall_set += 1;
        		}
        		else if (frame.p1_wall_set % 2 == 1) {
        			int pos_r1 = ins.getCurrentR(); // The row of the wall selected in the first
        			int pos_c1 = ins.getCurrentC(); // The row of the wall selected in the first
        			int pawn_r = ins.getCurrentLoc1()[0];
        			int pawn_c = ins.getCurrentLoc1()[1];
    				if (ins.wallPossibilityInspector(pos_r1, pos_c1, 
    					UserSelectedButtonRow, UserSelectedButtonCol) && 
    					ins.connectivityInspector(pawn_r, pawn_c, pos_r1, pos_c1, 
    							UserSelectedButtonRow, UserSelectedButtonCol)) {
	                	this.setOpaque(true);
	                	this.setBorderPainted(false);
	                	this.setBackground(Color.BLACK);
	                	frame.p1_wall_set += 1;
    				}
    				else {
    					frame.p1_wall_set -= 1;
    					BGButton first = frame.button_board[pos_r1][pos_c1]; // The first selected wall
            			first.setOpaque(false);
            			first.setBorderPainted(true);
    	                first.setBackground(null);
    				}
        		}
        		
                if (frame.p1_wall_set % 2 == 0 && frame.p1_wall_set == 2) {
                    // Fill Space between walls...
                    System.out.println("Fill space between walls...");
                    if (UserSelectedButtonRow % 2 != 0) { 
						// Vertical : Up-Down Connection	
                		int pos_c1 = ins.getCurrentC(); // The row of the wall selected in the first
                		System.out.println(pos_c1);
                		int dot_col = (pos_c1 + UserSelectedButtonCol) / 2; // Column of the point (small button) between the first wall and the second wall.
                		frame.button_board[UserSelectedButtonRow][dot_col].
                        setOpaque(true);
                        frame.button_board[UserSelectedButtonRow][dot_col].
                        setBorderPainted(false);
                        frame.button_board[UserSelectedButtonRow][dot_col].
                        setBackground(Color.BLACK);
                    	
                        frame.p1_wall_set -= 2;
                        
                        frame.p1_wall_count -= 1;
                        frame.player_info[0][1].setText(Integer.toString(frame.p1_wall_count));
                        // Set P2 turn
                        frame.player_info[0][2].setText("");
                        frame.player_info[0][2].setEnabled(false);
                        frame.player_info[0][5].setText("It's your turn!");
                        frame.player_info[0][5].setEnabled(true);
                        frame.input_board[0][0].setEnabled(false);
                        frame.input_board[0][1].setEnabled(false);
                        frame.input_board[0][2].setEnabled(true);
                        frame.input_board[0][3].setEnabled(true);
                        
                        for (int row = 0; row < 17; row++) {
                        	for (int col = 0; col < 17; col++) {
                      			frame.button_board[row][col].setEnabled(false);
                      		}
                  		}
                    }
                    if (UserSelectedButtonRow % 2 == 0) { // Horizontal : Left-Right Connection
                    	
                		int pos_r1 = ins.getCurrentR(); // The row of the wall selected in the first
                		int dot_row = (pos_r1 + UserSelectedButtonRow) / 2; // The row of the dot (small button) between the first wall and the second wall.
                		frame.button_board[dot_row][UserSelectedButtonCol].
                        setOpaque(true);
                        frame.button_board[dot_row][UserSelectedButtonCol].
                        setBorderPainted(false);
                        frame.button_board[dot_row][UserSelectedButtonCol].
                        setBackground(Color.BLACK);
                    	
                        frame.p1_wall_set -= 2;

                        frame.p1_wall_count -= 1;
                        frame.player_info[0][1].setText(Integer.toString(frame.p1_wall_count));
                        // Set P2 turn
                        frame.player_info[0][2].setText("");
                        frame.player_info[0][2].setEnabled(false);
                        frame.player_info[0][5].setText("It's your turn!");
                        frame.player_info[0][5].setEnabled(true);
                        frame.input_board[0][0].setEnabled(false);
                        frame.input_board[0][1].setEnabled(false);
                        frame.input_board[0][2].setEnabled(true);
                        frame.input_board[0][3].setEnabled(true);
                        
                        for (int row = 0; row < 17; row++) {
                        	for (int col = 0; col < 17; col++) {
                      			frame.button_board[row][col].setEnabled(false);
                      		}
                  		}
                    }
                }

            }

            // -------------------------------------------- P1 END --------------------------------------------
        
            // -------------------------------------------- P2 START --------------------------------------------

            // When p2 wants to move Pawn...
            if (frame.UserSelected.equals("P2 Pawn") && 
            	frame.player_info[0][5].getText().equals("It's your turn!")) {
                // Check to see if it is in a moveable position
            	if (ins.pawnPossibility(UserSelectedButtonRow, UserSelectedButtonCol, 2) ||
                   	ins.pawnPossibilityDiagonal(UserSelectedButtonRow, UserSelectedButtonCol, 2) ||
                	ins.pawnPossibilityJump(UserSelectedButtonRow, UserSelectedButtonCol, 2)) {
	                if (this.getBackground() == frame.p1_color) {
	                    JOptionPane.showMessageDialog(frame,
	                     "Umm.. you can't move Pawn there.", 
	                     "Error Message", JOptionPane.WARNING_MESSAGE);
	                    this.getBackground();
	                    this.setBackground(this.getBackground());
	                }
	                else {
	                	if (ins instanceof BGLogic)
	                		((BGLogic) ins).pawnMove(UserSelectedButtonRow, UserSelectedButtonCol, 2);
	                    // Paint existing P2 pawn normal color
	                    for (int row = 0; row < 17; row++) {
	                        for (int col = 0; col < 17; col++) {
	                            if (frame.button_board[row][col].getBackground() == frame.p2_color) {
	                                frame.button_board[row][col].setBackground(null);
	                            }
	                        }
	                    }
	                    // P2 Win
	                    if (this.getY() == 5) {
	                        JOptionPane.showMessageDialog(frame,
	                        "You Win, " + frame.player_info[0][3].getText() + "!", 
	                        "Congratulations", JOptionPane.INFORMATION_MESSAGE);
	                    this.getBackground();
	                    this.setBackground(this.getBackground());
	                    }
	                    // Move P2 Pawn
	                    this.setBackground(frame.p2_color);
	                    // Set P1 turn
	                    frame.player_info[0][5].setText("");
	                    frame.player_info[0][5].setEnabled(false);
	                    frame.player_info[0][2].setText("It's your turn!");
	                    frame.player_info[0][2].setEnabled(true);
	                    frame.input_board[0][0].setEnabled(true);
	                    frame.input_board[0][1].setEnabled(true);
	                    frame.input_board[0][2].setEnabled(false);
	                    frame.input_board[0][3].setEnabled(false);
	                    
	                    for (int row = 0; row < 17; row++) {
                        	for (int col = 0; col < 17; col++) {
                      			frame.button_board[row][col].setEnabled(false);
                      		}
                  		}
	                }
            	}
            	else {
            		JOptionPane.showMessageDialog(frame,
            				"Umm.. you can't move Pawn there!", 
            				"Error Message", JOptionPane.WARNING_MESSAGE);
            	}
            }

            // When p2 wants to build a wall...
            if (frame.UserSelected.equals("P2 Wall") && 
            frame.player_info[0][5].getText().equals("It's your turn!") && 
            frame.p2_wall_count != 0 && 
            ins.wallPossibility(UserSelectedButtonRow, UserSelectedButtonCol)) {
            	
        		if (frame.p2_wall_set % 2 == 0) {
        			ins.setCurrentPos(UserSelectedButtonRow, UserSelectedButtonCol);
        			this.setOpaque(true);
	                this.setBorderPainted(false);
	                this.setBackground(Color.BLACK);
	                frame.p2_wall_set += 1;
        		}
        		else if (frame.p2_wall_set % 2 == 1) {
        			int pos_r1 = ins.getCurrentR(); // The row of the wall selected in the first
        			int pos_c1 = ins.getCurrentC(); // The col of the wall selected in the first
        			int pawn_r = ins.getCurrentLoc2()[0];
        			int pawn_c = ins.getCurrentLoc2()[1];
    				if (ins.wallPossibilityInspector(pos_r1, pos_c1, 
    					UserSelectedButtonRow, UserSelectedButtonCol) &&
    					ins.connectivityInspector(pawn_r, pawn_c, pos_r1, pos_c1, 
    							UserSelectedButtonRow, UserSelectedButtonCol)) {
	                	this.setOpaque(true);
	                	this.setBorderPainted(false);
	                	this.setBackground(Color.BLACK);
	                	frame.p2_wall_set += 1;
    				}
    				else {
    					frame.p2_wall_set -= 1;
    					BGButton first = frame.button_board[pos_r1][pos_c1]; // The first selected wall
            			first.setOpaque(false);
            			first.setBorderPainted(true);
    	                first.setBackground(null);
    				}
        		}
            	
                if (frame.p2_wall_set % 2 == 0 && frame.p2_wall_set == 2) {
                    // Fill Space between walls...
                    System.out.println("Fill space between walls...");
                    if (UserSelectedButtonRow % 2 != 0) { 
						// Vertical : Up-Down Connection
                		int pos_c1 = ins.getCurrentC(); // The col of the wall selected in the first
                		System.out.println(pos_c1);
                		int dot_col = (pos_c1 + UserSelectedButtonCol) / 2; // Column of the point (small button) between the first wall and the second wall.
                		frame.button_board[UserSelectedButtonRow][dot_col].
                        setOpaque(true);
                        frame.button_board[UserSelectedButtonRow][dot_col].
                        setBorderPainted(false);
                        frame.button_board[UserSelectedButtonRow][dot_col].
                        setBackground(Color.BLACK);
                    	
                        frame.p2_wall_set -= 2;
                        frame.p2_wall_count -= 1;
                        frame.player_info[0][4].setText(Integer.toString(frame.p2_wall_count));

                        // Set P2 turn
                        frame.player_info[0][5].setText("");
                        frame.player_info[0][5].setEnabled(false);
                        frame.player_info[0][2].setText("It's your turn!");
                        frame.player_info[0][2].setEnabled(true);
                        frame.player_info[0][5].setEnabled(false);
                        frame.input_board[0][0].setEnabled(true);
                        frame.input_board[0][1].setEnabled(true);
                        frame.input_board[0][2].setEnabled(false);
                        frame.input_board[0][3].setEnabled(false);
                        
                        for (int row = 0; row < 17; row++) {
                        	for (int col = 0; col < 17; col++) {
                      			frame.button_board[row][col].setEnabled(false);
                      		}
                  		}
                        
                    }
                    if (UserSelectedButtonRow % 2 == 0) { // Horizontal : Left-Right Connection
                		int pos_r1 = ins.getCurrentR(); // The row of the wall selected in the first
                		int dot_row = (pos_r1 + UserSelectedButtonRow) / 2; // The row of the dot (small button) between the first wall and the second wall.
                		frame.button_board[dot_row][UserSelectedButtonCol].
                        setOpaque(true);
                        frame.button_board[dot_row][UserSelectedButtonCol].
                        setBorderPainted(false);
                        frame.button_board[dot_row][UserSelectedButtonCol].
                        setBackground(Color.BLACK);
                    	
                        frame.p2_wall_set -= 2;

                        frame.p2_wall_count -= 1;
                        frame.player_info[0][4].setText(Integer.toString(frame.p1_wall_count));
                        // Set P2 turn
                        frame.player_info[0][5].setText("");
                        frame.player_info[0][5].setEnabled(false);
                        frame.player_info[0][2].setText("It's your turn!");
                        frame.player_info[0][2].setEnabled(true);
                        frame.input_board[0][2].setEnabled(false);
                        frame.input_board[0][3].setEnabled(false);
                        frame.input_board[0][0].setEnabled(true);
                        frame.input_board[0][1].setEnabled(true);
                        
                        for (int row = 0; row < 17; row++) {
                        	for (int col = 0; col < 17; col++) {
                      			frame.button_board[row][col].setEnabled(false);
                      		}
                  		}
                        
                    }
                }
            }

            // -------------------------------------------- P2 END --------------------------------------------

            // ---------------------------------------- USER SELECTED SETTING END ----------------------------------------

        }
    }
    
}