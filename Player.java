import java.awt.*;

public class Player {

    public int PawnLocationX(Color c, BGFrame f) {
        for (int row = 0; row < 17; row++) {
            for (int col = 0; col < 17; col++) {
                if (f.button_board[row][col].getBackground().equals(c)) {
                    return f.button_board[row][col].getX();
                }
            }
        }
        return 0;
    }

    public int PawnLocationY(Color c, BGFrame f) {
        for (int row = 0; row < 17; row++) {
            for (int col = 0; col < 17; col++) {
                if (f.button_board[row][col].getBackground().equals(c)) {
                    return f.button_board[row][col].getY();
                }
            }
        }
        return 0;
    }
    
}
