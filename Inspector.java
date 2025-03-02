public interface Inspector {

	public boolean connectivityInspector(int pawn_r, int pawn_c, int wall_pos_r1, int wall_pos_c1, int wall_pos_r2, int wall_pos_c2);
	
	public void setCurrentPos(int wall_pos_r1, int wall_pos_c1);
	
	public int getCurrentR();
	
	public int getCurrentC();
	
	public void currentPosInitialize();
	
	public int[] arrayOfPossibleBlockPosition(int pos1);
	
	public int[] arrayOfPossibleWallPosition(int wall_pos_r1, int wall_pos_c1);
	
	public boolean blockPossibility(int wall_pos_r1, int wall_pos_c1);
	
	public boolean wallPossibility(int pawn_r, int pawn_c);
	
	public boolean wallPossibility(int wall_pos_r1, int wall_pos_c1, int wall_pos_r2, int wall_pos_c2);
	
	public boolean wallPossibilityInspector(int wall_pos_r1, int wall_pos_c1, int wall_pos_r2, int wall_pos_c2);
	
	public int[] getCurrentLoc1();
	
	public int[] getCurrentLoc2() ;
	
	public void pawnMove(int pawn_r, int pawn_c, int id);
	
	public boolean pawnPossibility(int pawn_r, int pawn_c, int id);
	
	public boolean pawnPossibilityJump(int pawn_r, int pawn_c, int id);
	
	public boolean pawnPossibilityDiagonal(int pawn_r, int pawn_c, int id);
}
