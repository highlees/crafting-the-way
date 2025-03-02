import java.awt.*;
import javax.swing.*;

public class BGFrame extends JFrame {

    public BGButton button;
    public BGButton[][] button_board;
    public PlayerButton[][] player_info;
    public InputButton[][] input_board;
    public int row;
    public int col;
    public int face;
    public int p1_wall_set = 0;
    public int p1_wall_count = 10;
    public int p2_wall_count = 10;
    public int p2_wall_set = 0;
    public String UserSelected;
    public int UserSelectedButtonRow;
    public int UserSelectedButtonCol;
    public Color p1_color = new Color(255, 153, 0);
    public Color p1_movable = Color.ORANGE;
    public Color p2_color = new Color(0, 0, 128);
    public Color p2_movable = new Color(106, 106, 187);
    public Color p1_p2_movable = Color.GREEN;
    public Color UserRequestedColor;
    public String p1_message = "";
    public String p2_message = "";
    public Inspector ins;

    public BGFrame(Inspector ins) {
    	this.ins = ins;
        button_board = new BGButton[9 + 8][9 + 8];
        input_board = new InputButton[1][4];
        player_info = new PlayerButton[1][6];
        for (int row = 0; row < 1; row++) {
            for (int col = 0; col < 6; col++) {
                player_info[row][col] = new PlayerButton(this);
            }
        }
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        
        //--------------------------------------- Player 1 SETTING START ---------------------------------------

        JPanel p1 = new JPanel(new FlowLayout());

        player_info[0][0].setText("P1 Name");
        player_info[0][0].setEnabled(false);
        p1.add(player_info[0][0]);
        player_info[0][1].setText("Wall Count");
        player_info[0][1].setEnabled(false);
        p1.add(player_info[0][1]);
        player_info[0][2].setText("P1 turn");
        player_info[0][2].setEnabled(false);
        p1.add(player_info[0][2]);

        input_board[0][0] = new InputButton(this, ins);
        input_board[0][0].setEnabled(false);
        input_board[0][0].setText("P1 Pawn");
        input_board[0][1] = new InputButton(this, ins);
        input_board[0][1].setEnabled(false);
        input_board[0][1].setText("P1 Wall");
        p1.add(input_board[0][0]);
        p1.add(input_board[0][1]);

        //--------------------------------------- Player 1 SETTING END ---------------------------------------
        

        //--------------------------------------- BOARD SETTING START ---------------------------------------
        JPanel p2 = new JPanel(new FlowLayout());
        
        for (int row = 0; row < 1; row++) {
            for (int col = 0; col < 17; col++) {

                button_board[row][col] = new BGButton(this, ins);

                // -------------------- Player 1 PAWN --------------------
                if (col == 8) {
                    button_board[row][col].setOpaque(true);
                    button_board[row][col].setBackground(p1_color);
                }
                // -------------------------------------------------------

                button_board[row][col].setPreferredSize(new Dimension(50, 50));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 50));
                }
                p2.add(button_board[row][col]);
            }
        }
        for (int row = 1; row < 2; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col] = new BGButton(this, ins);
                button_board[row][col].setPreferredSize(new Dimension(50, 15));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 15));
                }
                p2.add(button_board[row][col]);
            }
        }
        for (int row = 2; row < 3; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col] = new BGButton(this, ins);
                button_board[row][col].setPreferredSize(new Dimension(50, 50));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 50));
                }
                p2.add(button_board[row][col]);
            }
        }
        for (int row = 3; row < 4; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col] = new BGButton(this, ins);
                button_board[row][col].setPreferredSize(new Dimension(50, 15));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 15));
                }
                p2.add(button_board[row][col]);
            }
        }
        for (int row = 4; row < 5; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col] = new BGButton(this, ins);
                button_board[row][col].setPreferredSize(new Dimension(50, 50));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 50));
                }
                p2.add(button_board[row][col]);
            }
        }
        for (int row = 5; row < 6; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col] = new BGButton(this, ins);
                button_board[row][col].setPreferredSize(new Dimension(50, 15));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 15));
                }
                p2.add(button_board[row][col]);
            }
        }
        for (int row = 6; row < 7; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col] = new BGButton(this, ins);
                button_board[row][col].setPreferredSize(new Dimension(50, 50));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 50));
                }
                p2.add(button_board[row][col]);
            }
        }
        for (int row = 7; row < 8; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col] = new BGButton(this, ins);
                button_board[row][col].setPreferredSize(new Dimension(50, 15));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 15));
                }
                p2.add(button_board[row][col]);
            }
        }
        for (int row = 8; row < 9; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col] = new BGButton(this, ins);
                button_board[row][col].setPreferredSize(new Dimension(50, 50));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 50));
                }
                p2.add(button_board[row][col]);
            }
        }
        for (int row = 9; row < 10; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col] = new BGButton(this, ins);
                button_board[row][col].setPreferredSize(new Dimension(50, 15));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 15));
                }
                p2.add(button_board[row][col]);
            }
        }
        for (int row = 10; row < 11; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col] = new BGButton(this, ins);
                button_board[row][col].setPreferredSize(new Dimension(50, 50));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 50));
                }
                p2.add(button_board[row][col]);
            }
        }
        for (int row = 11; row < 12; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col] = new BGButton(this, ins);
                button_board[row][col].setPreferredSize(new Dimension(50, 15));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 15));
                }
                p2.add(button_board[row][col]);
            }
        }
        for (int row = 12; row < 13; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col] = new BGButton(this, ins);
                button_board[row][col].setPreferredSize(new Dimension(50, 50));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 50));
                }
                p2.add(button_board[row][col]);
            }
        }
        for (int row = 13; row < 14; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col] = new BGButton(this, ins);
                button_board[row][col].setPreferredSize(new Dimension(50, 15));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 15));
                }
                p2.add(button_board[row][col]);
            }
        }
        for (int row = 14; row < 15; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col] = new BGButton(this, ins);
                button_board[row][col].setPreferredSize(new Dimension(50, 50));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 50));
                }
                p2.add(button_board[row][col]);
            }
        }
        for (int row = 15; row < 16; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col] = new BGButton(this, ins);
                button_board[row][col].setPreferredSize(new Dimension(50, 15));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 15));
                }
                p2.add(button_board[row][col]);
            }
        }
        for (int row = 16; row < 17; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col] = new BGButton(this, ins);
                button_board[row][col].setPreferredSize(new Dimension(50, 50));
                if (col % 2 != 0) {
                    button_board[row][col] = new BGButton(this, ins);
                    button_board[row][col].setPreferredSize(new Dimension(15, 50));
                }

                // -------------------- Player 2 PAWN --------------------
                if (col == 8) {
                    button_board[row][col].setOpaque(true);
                    button_board[row][col].setBackground(new Color(0, 0, 128));
                }
                // -------------------------------------------------------

                p2.add(button_board[row][col]);
            }
        }
        for (int row = 0; row < 17; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col].setEnabled(false);
            }
        }

        //--------------------------------------- BOARD SETTING END ---------------------------------------

        //--------------------------------------- Player 2 SETTING START ---------------------------------------

        player_info[0][3].setText("P2 Name");
        player_info[0][3].setEnabled(false);
        p2.add(player_info[0][3]);
        player_info[0][4].setText("Wall Count");
        player_info[0][4].setEnabled(false);
        p2.add(player_info[0][4]);
        player_info[0][5].setText("");
        player_info[0][5].setEnabled(false);
        p2.add(player_info[0][5]);

        input_board[0][2] = new InputButton(this, ins);
        input_board[0][2].setEnabled(false);
        input_board[0][2].setText("P2 Pawn");
        input_board[0][3] = new InputButton(this, ins);
        input_board[0][3].setEnabled(false);
        input_board[0][3].setText("P2 Wall");
        p2.add(input_board[0][2]);
        p2.add(input_board[0][3]);

        //--------------------------------------- Player 2 SETTING END ---------------------------------------

        JPanel p3 = new JPanel(new FlowLayout());
        // There is nothing...

        cp.add(p1, BorderLayout.NORTH);
        cp.add(p2, BorderLayout.CENTER);
        cp.add(p3, BorderLayout.SOUTH);

        setTitle("Crafting the Way");
        setSize(700,775);
        setResizable(false); //
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public void deactivate_pawn() {
        for (int row = 0; row < 17; row++) {
            for (int col = 0; col < 17; col++) {

                if (row % 2 == 0 && col % 2 == 0) {
                    button_board[row][col].setEnabled(false);
                }
                else {
                    if (col % 2 != 0 && row % 2 != 0) {
                        button_board[row][col].setEnabled(false); 
                    }
                    else {
                        button_board[row][col].setEnabled(true);
                    }
                }
            }
        }
    }

    public void deactivate_wall() {
        for (int row = 0; row < 17; row++) {
            for (int col = 0; col < 17; col++) {
                button_board[row][col].setOpaque(true);
                
                if (button_board[row][col].getBackground().equals(p2_color)) {
                    button_board[row][col].setOpaque(true);
                    button_board[row][col].setBackground(p2_color);
                }

                if (row % 2 == 0 && col % 2 == 0) {
                    button_board[row][col].setEnabled(true);
                
                }
                else {
                    button_board[row][col].setEnabled(false);
                }
            }
        }
    }

    public void UserRequest(Color UserRequestColor) {
        UserRequestedColor = UserRequestColor;
    }

    public void UserSelect(String s) {
        UserSelected = s;
    }

    public void UserRequestRow(int row) {
        UserSelectedButtonRow = row;
    }
    public void UserRequestCol(int col) {
        UserSelectedButtonCol = col;
    }

}