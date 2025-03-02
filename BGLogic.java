import java.util.LinkedList;
import java.util.Optional;
import java.util.List;

public class BGLogic implements Inspector {

	private BGGraph graph = new BGGraph(81);
	private LinkedList<Integer> blocks = new LinkedList<>();
	private int p1_current_loc = 4;		// 4 is initial location(mapped) of p1
	private int p2_current_loc = 76;	// 76 is initial location(mapped) of p2
	private int current_pos_r1;	// The row of the first selected wall
	private int current_pos_c1;	// The col of the first selected wall
	/* current_r = current_loc / 9 * 2
	 * current_c = current_loc % 9 * 2
	 * Always multiply 2 
	 * */
	
	public BGLogic() {
		for (int row = 0; row < 81; row += 9)
			for (int col = 0; col < 8; col++) 
				graph.addEdge(col+row, col+row+1);
		for (int col = 0; col < 9; col++) // ribbons
			for (int row = 0; row < 72; row += 9) 
				graph.addEdge(row+col, row+col+9);
	}
	
	private boolean isConnected(BGGraph graph, int position) {
		
		int vertices = graph.vertices;
		boolean visited[] = new boolean[vertices];
		LinkedList<Integer> adjacencyList[] = graph.adjacencyList;
		
		DFS(position, adjacencyList, visited);
		
		boolean connection = true;
		
		for (int i = 0; i < vertices; i++) {
			if (visited[i]);
			else {
				connection = false;
				break;
			}
		}
		return connection;
	}
	
	// array: call-by-reference
	private void DFS(int source, LinkedList<Integer> adjacencyList[], boolean[] visited) {
		
		visited[source] = true;

		for (int i = 0; i < adjacencyList[source].size(); i++) {
			
			int neighbor = adjacencyList[source].get(i);
			
			if (visited[neighbor] == false) {
				DFS(neighbor, adjacencyList, visited);
				
			}
		}
		
	}
	
	public boolean connectivityInspector(int pawn_r, int pawn_c, int wall_pos_r1, int wall_pos_c1, int wall_pos_r2, int wall_pos_c2) {
		if (!(pawn_r % 2 == 0 && pawn_c % 2 == 0)) {
			System.out.println("connectivityInspector:" + pawn_r + ", " + pawn_c);
			System.out.println("It's illegal position of pawn");
			return false;
		}
		if (!(wall_pos_r1 % 2 == wall_pos_r2 % 2 && wall_pos_c1 % 2 == wall_pos_c2 % 2)) {
			System.out.println("connectivityInspector:" + wall_pos_r1+ ", " + wall_pos_c1 + "//" + wall_pos_r2 + ", " + wall_pos_c2);
			System.out.println("It's illegal position of wall");
			return false;
		}
		int pawn = coordinateMappingToInteger(pawn_r, pawn_c);
		int v01; int v02; int v11; int v12;
		int e1 = coordinateMappingToInteger(wall_pos_r1, wall_pos_c1);
		int e2 = coordinateMappingToInteger(wall_pos_r2, wall_pos_c2);
		int[] e1_row_col = IntegerMappingToCoordinate(e1);
		int[] e2_row_col = IntegerMappingToCoordinate(e2);
		if (e1 > 0 /*&& e2 > 0*/) {
			v01 = coordinateMappingToInteger(e1_row_col[0], e1_row_col[1] - 1);
			v02 = coordinateMappingToInteger(e1_row_col[0], e1_row_col[1] + 1);
			v11 = coordinateMappingToInteger(e2_row_col[0], e2_row_col[1] - 1);
			v12 = coordinateMappingToInteger(e2_row_col[0], e2_row_col[1] + 1);
			System.out.println("e1 > 0 - v01: " + v01);System.out.println("e1 > 0 - v02: " + v02);
			System.out.println("e1 > 0 - v11: " + v11);System.out.println("e1 > 0 - v12: " + v12);
			graph.removeEdge(v01, v02);
			graph.removeEdge(v11, v12);
		}
		else if (e1 < 0 /*&& e2 < 0*/) {
			v01 = coordinateMappingToInteger(e1_row_col[0] - 1, e1_row_col[1]);
			v02 = coordinateMappingToInteger(e1_row_col[0] + 1, e1_row_col[1]);
			v11 = coordinateMappingToInteger(e2_row_col[0] - 1, e2_row_col[1]);
			v12 = coordinateMappingToInteger(e2_row_col[0] + 1, e2_row_col[1]);
			System.out.println("e1 < 0 - v01: " + v01);System.out.println("e1 < 0 - v02: " + v02);
			System.out.println("e1 < 0 - v11: " + v11);System.out.println("e1 < 0 - v12: " + v12);
			graph.removeEdge(v01, v02);
			graph.removeEdge(v11, v12);
		}
		else {
			System.out.print("No such edge.");
			return false;
		}
		BGLogic inspection = new BGLogic();
		boolean connectivity = inspection.isConnected(graph, pawn);
		if (connectivity) {
			blocks.addLast(e1);
			blocks.addLast(e2);
			System.out.println("Possible to put a wall");
			return connectivity;
		}
		else {
			graph.addEdge(v01, v02);
			graph.addEdge(v11, v12);
			System.out.println("Impossible to put a wall");
			return connectivity;
		}
	}
	
	public void setCurrentPos(int wall_pos_r1, int wall_pos_c1) {
		current_pos_r1 = wall_pos_r1;
		System.out.println(wall_pos_r1);
		current_pos_c1 = wall_pos_c1;
		System.out.println(wall_pos_r1);
	}
	
	public int getCurrentR() { return current_pos_r1; }
	
	public int getCurrentC() { return current_pos_c1; }
	
	public void currentPosInitialize() {
		current_pos_r1 = -128;
		current_pos_c1 = -128;
	}
			
	/** public int[] arrayOfPossibleBlockPosition(int pos1)
	 * When you click on the block position (link), 
	 * it passes the possible block position (connection) that is adjacent to it as an array.
	 * @param pos1
	 * @return
	 */
	public int[] arrayOfPossibleBlockPosition(int pos1) {
		List <Integer> s = blocks.stream().filter(i -> wallPossible(i, pos1)).toList();
		int size = s.size();
		int[] possible_block_position = new int[size];
		for (int i = 0; i < size; i++)
			possible_block_position[i] = s.get(i);
		return possible_block_position;
	}
	
	public int[] arrayOfPossibleWallPosition(int wall_pos_r1, int wall_pos_c1) {
		int e1 = coordinateMappingToInteger(wall_pos_r1, wall_pos_c1);
		List <Integer> s = blocks.stream().filter(i -> wallPossible(i, e1)).toList();
		int size = s.size();
		int[] possible_block_position = new int[size];
		for (int i = 0; i < size; i++)
			possible_block_position[i] = s.get(i);
		return possible_block_position;
	}
	private boolean wallPossible(int wall, int e) {
		if (wall == e + 1 || wall == e - 1 || wall == e + 10 || wall == e - 10 )
			return true;
		else
			return false;
	}
	
	/** public boolean blockPossibility(int pos1)
	 * Is the block position (connection) a position that can be placed when pressed?
	 * @param pos1 : Block location
	 * @return
	 */
	public boolean blockPossibility(int pos1) {
		boolean possibility = true;
		Optional <Integer> equals = blocks.stream().filter(i -> (i == pos1)).findAny();
		possibility = !(equals.isPresent());
		return possibility;
	}
	
	public boolean wallPossibility(int wall_pos_r1, int wall_pos_c1) {
		System.out.println("wallPossibility(int wall_pos_r1, int wall_pos_c1): " + " wall_pos_r1" + ", " + wall_pos_c1);
		int e1 = coordinateMappingToInteger(wall_pos_r1, wall_pos_c1);
		boolean possibility = true;
		Optional <Integer> equals = blocks.stream().filter(i -> (i == e1)).findAny();
		possibility = !(equals.isPresent());
		return possibility;
	}
	
	/** public boolean blockPossibility(int pos1, int pos2)
	 * Is the block position (connection) a position that can be placed when pressed?
	 * In particular, make the first choice and then check the second location.
	 * @param pos1 : Block location 1
	 * @param pos2 : Block location 2
	 * @return
	 */
	public boolean blockPossibility(int pos1, int pos2) {
		boolean possibility = true;
		Optional <Integer> equals = blocks.stream().filter(i -> (i == pos2)).findAny();
		possibility = !(equals.isPresent());
		return possibility;
	}
	
	public boolean wallPossibility(int wall_pos_r1, int wall_pos_c1, int wall_pos_r2, int wall_pos_c2) {
		int e2 = coordinateMappingToInteger(wall_pos_r2, wall_pos_c2);
		boolean possibility = true;
		Optional <Integer> equals = blocks.stream().filter(i -> (i == e2)).findAny();
		possibility = !(equals.isPresent());
		return possibility;
	}
	
	public boolean wallPossibilityInspector(int wall_pos_r1, int wall_pos_c1, int wall_pos_r2, int wall_pos_c2) {
		System.out.println("wallPossibilityInspector (1):" + wall_pos_r1 + ", " +  wall_pos_c1);
		System.out.println("wallPossibilityInspector (2):" + wall_pos_r2 + "   " +  wall_pos_c2);
		int e1 = coordinateMappingToInteger(wall_pos_r1, wall_pos_c1);
		int e2 = coordinateMappingToInteger(wall_pos_r2, wall_pos_c2);
		System.out.println("wallPossiIns e1: " + e1);
		System.out.println("wallPossiIns e2: " + e2);
		if (blockPossibility(e1)) {
			System.out.println("blockPossibility successed");
			if (wallPossible(e1, e2) && blockPossibility(e1, e2)) {
				System.out.println("wallPossible && blockPossibility successed");
				int dot_row = (IntegerMappingToCoordinate(e1)[0] + IntegerMappingToCoordinate(e2)[0]) / 2;
				int dot_col = (IntegerMappingToCoordinate(e1)[1] + IntegerMappingToCoordinate(e2)[1]) / 2;
				System.out.println("dot_row: " + dot_row);
				System.out.println("dot_col: " + dot_col);
				if (e1 > 0 /*&& e2 > 0 */) { /* Cross Check */
					// IntegerMappingToCoordinate (e < 0): -e % 10 * 2 - 1 (row), -e / 10 * 2 (col)
					List <Integer> y_walls_on_dot_row = blocks.stream()
							.filter(i -> (i < 0 && -i % 10 * 2 - 1 == dot_row && -i / 10 * 2 < dot_col /*left-side*/))
							.toList();
					if (y_walls_on_dot_row.size() % 2 == 0)
						return true;
					else
						return false;
				}
				else if (e1 < 0 /*%% e2 < 0 */) { /* Cross Check */
					// IntegerMappingToCoordinate (e > 0): e / 10 * 2 (row), e % 10 * 2 - 1 (col)
					List <Integer> y_walls_on_dot_col = blocks.stream()
							.filter(i -> (i > 0 && i % 10 * 2 - 1 == dot_col && i / 10 * 2 < dot_row /*up-side*/)) // row vs row, col vs col
							.toList();
					if (y_walls_on_dot_col.size() != 0)
						System.out.println(y_walls_on_dot_col.get(0));
					if (y_walls_on_dot_col.size() % 2 == 0)
						return true;
					else
						return false;
				}
				else {
					System.out.println("illegal e.\n"
							+ "If this message was printed, "
							+ "you have to check wallPossible.");
					return false;
				}
					
			}
			else
				return false;
		}
		else
			return false;
	}
	
	public int[] getCurrentLoc1() {
		int[] current_loc = { p1_current_loc / 9 * 2, p1_current_loc % 9 * 2 };
		return current_loc;
	}
	
	public int[] getCurrentLoc2() { 
		int[] current_loc = { p2_current_loc / 9 * 2, p2_current_loc % 9 * 2}; //NOT Virtual values! (NOT p2_current_loc / 9, p2_current_loc % 9 )
		return current_loc;	//It means we need to translate Integer(Virtual) to coordination(Realization)
	}
	
	public void pawnMove(int pawn_r, int pawn_c, int id) {
		if (id == 1) {
			System.out.println("pawnMove id 1 | r: " + pawn_r + ", c: " + pawn_c);
			p1_current_loc = coordinateMappingToInteger(pawn_r, pawn_c);
			System.out.println("pawnMove id 1 | loc: " + p1_current_loc); 
		}
		else if (id == 2){
			System.out.println("pawnMove id 2 | r: " + pawn_r + ", c: " + pawn_c);
			p2_current_loc = coordinateMappingToInteger(pawn_r, pawn_c);
			System.out.println("pawnMove id 2 | loc: " + p2_current_loc);
		}
	}
	
	public boolean pawnPossibility(int pawn_r, int pawn_c, int id) {
		if (id == 1) {
			System.out.println("pawnPossibility id 1");
			boolean possibility = isPossibleLocation(p1_current_loc, pawn_r, pawn_c);
			return possibility;
		}
		else if (id == 2) {
			System.out.println("pawnPossibility id 2");
			boolean possibility = isPossibleLocation(p2_current_loc, pawn_r, pawn_c);
			return possibility;
		}
		else {
			System.out.println("No such player");
			return false;
		}
	}

	private boolean isPossibleLocation(int p_current_loc, int pawn_r, int pawn_c) {
		int current_r = p_current_loc / 9 * 2; // Not virtual values, but real values(row, col)
		int current_c = p_current_loc % 9 * 2;
		int pawn = coordinateMappingToInteger(pawn_r, pawn_c); // virtualization
		int adjacency = p_current_loc-pawn;
		if (adjacency == -1 || adjacency == 1 
			|| adjacency == -9 || adjacency == 9) {
			// inputed pawn_loc is on EestWestNorthSouth
			System.out.println("pawnPossibility id : if");
			int e = coordinateMappingToInteger((current_r + pawn_r) / 2, (current_c + pawn_c) / 2);
			boolean possibility = true;
			Optional <Integer> equals = blocks.stream().filter(i -> (i == e)).findAny();
			possibility = !(equals.isPresent());
			System.out.println("isPossibleLocation: There is no wall near by you.");
			return possibility;
		}
		else { 
			System.out.println("pawnPossibility id : else");
			System.out.println("It's not adjacent location");
			return false;
		}
	}
	
	public boolean pawnPossibilityJump(int pawn_r, int pawn_c, int id) {
		
		int pawn = coordinateMappingToInteger(pawn_r, pawn_c); // virtualization
		
		boolean nearby_you = (p1_current_loc - 1 == p2_current_loc || 
							  p1_current_loc + 1 == p2_current_loc ||
							  p1_current_loc - 9 == p2_current_loc ||
							  p1_current_loc + 9 == p2_current_loc);
		
		boolean they_are_in_near;
		if (id == 1) { // they : p2, clicked loc (pawn_r, pawn_c)
			they_are_in_near = (p2_current_loc - 1 == pawn ||
								p2_current_loc + 1 == pawn ||
								p2_current_loc - 9 == pawn ||
								p2_current_loc + 9 == pawn);
		}
		else if (id == 2) { // they : p1, clicked loc (pawn_r, pawn_c)
			they_are_in_near = (p1_current_loc - 1 == pawn ||
								p1_current_loc + 1 == pawn ||
								p1_current_loc - 9 == pawn ||
								p1_current_loc + 9 == pawn);
		}
		else {
			System.out.println("pawnPossibilityJump: UNKNOWN ID");
			return false;
		}
		
		boolean all_in_a_vertical = p1_current_loc % 9 * 2 /*col*/ == pawn_c && pawn_c == p2_current_loc % 9 * 2;
		boolean all_in_a_horizontal = p1_current_loc / 9 * 2 /*row*/ == pawn_r && pawn_r == p2_current_loc / 9 * 2;
		boolean all_in_a_line = all_in_a_vertical || all_in_a_horizontal;
		
		if (nearby_you && they_are_in_near && all_in_a_line) {
			if (id == 1) {
				boolean possibility = isPossibleLocationJump(
						p1_current_loc, pawn_r, pawn_c, all_in_a_vertical, all_in_a_horizontal);
				return possibility;
			}
			
			else {	/* id == 2 */
				boolean possibility = isPossibleLocationJump(
						p2_current_loc, pawn_r, pawn_c, all_in_a_vertical, all_in_a_horizontal);
				return possibility;
			}
		}
		
		else {
			System.out.println(nearby_you + ", " + they_are_in_near + ", " + all_in_a_line);
			System.out.println("pawnPossibilityJump: illegal movement");
			return false;
		}
		
	}

	private boolean isPossibleLocationJump(int p_current_loc, int pawn_r, int pawn_c, boolean vertical, boolean horizontal) {
		
		int current_r = p_current_loc / 9 * 2;
		int current_c = p_current_loc % 9 * 2;
		
		if (vertical && !horizontal) { // horizontal == false
			System.out.println("e's row : current_r < e && e < pawn_r || current_r > e && e > pawn_r");
			// IntegerMappingToCoordinate (e < 0): -e % 10 * 2 - 1 (row), -e / 10 * 2 (col)
			boolean possibility;
			Optional <Integer> equals = blocks.stream()
					.filter(i -> (i < 0 && -i / 10 * 2 == current_c /* && current_c == pawn_c */&&
										(current_r < -i % 10 * 2 - 1 && -i % 10 * 2 - 1 < pawn_r || 
										 current_r > -i % 10 * 2 - 1 && -i % 10 * 2 - 1 > pawn_r)))
					.findAny();
			possibility = !(equals.isPresent());
			if (possibility) System.out.println("isPossibleLocationSpecialized(Vertical): There is no wall between past and future.");
			else System.out.println(equals + ", " + blocks);
			return possibility;
		}
		else if (horizontal&& !vertical) { // vertical == false
			System.out.println("e's col : current_c < e && e < pawn_c || current_c > e && e > pawn_c");
			// IntegerMappingToCoordinate (e > 0): e / 10 * 2 (row), e % 10 * 2 - 1 (col)
			boolean possibility;
			Optional <Integer> equals = blocks.stream()
					.filter(i -> (i > 0 && i / 10 * 2 == current_r /* && current_r == pawn_r */ &&
										(current_c < i % 10 * 2 - 1  && i % 10 * 2 - 1  < pawn_c || 
										 current_c > i % 10 * 2 - 1  && i % 10 * 2 - 1  > pawn_c)))
					.findAny();
			possibility = !(equals.isPresent());
			if (possibility) System.out.println("isPossibleLocationSpecialized(Horizontal): There is no wall between past and future.");
			else System.out.println(equals);
			return possibility;
		}
		else {
			System.out.println("isPossibleLocationSpecialized : else . !(Vertical ^ Horizontal) == 1 is Impossible ");
			return false;
		}
	}
	
	public boolean pawnPossibilityDiagonal(int pawn_r, int pawn_c, int id) {
		
		int pawn = coordinateMappingToInteger(pawn_r, pawn_c); // virtualization
		
		boolean nearby_you = (p1_current_loc - 1 == p2_current_loc || 
							  p1_current_loc + 1 == p2_current_loc ||
							  p1_current_loc - 9 == p2_current_loc ||
				  			  p1_current_loc + 9 == p2_current_loc);
		
		boolean is_it_diagonal_move;
		boolean we_are_in_triangle;
		double PI = Math.PI;
		double direction = 126.0;
		if (id == 1) {
			is_it_diagonal_move = (p1_current_loc - 10 /*-1-9*/ == pawn || 
								   p1_current_loc -  8 /*+1-9*/ == pawn ||
								   p1_current_loc +  8 /*-1+9*/ == pawn ||
								   p1_current_loc + 10 /*+1+9*/ == pawn);
			we_are_in_triangle = ( p1_current_loc - 9 == p2_current_loc && 
										((p2_current_loc - 1 == pawn && (direction = PI * 3/4) != 126.0) || 
										(p2_current_loc + 1 == pawn && (direction = PI * 1/4) != 126.0)) ||
								   p1_current_loc + 9 == p2_current_loc && 
								   		((p2_current_loc - 1 == pawn && (direction = PI * 5/4) != 126.0) || 
								   		(p2_current_loc + 1 == pawn && (direction = PI * 7/4) != 126.0)) || /* X(UD) pattern */
								   p1_current_loc - 1 == p2_current_loc && 
								   		((p2_current_loc + 9 == pawn && (direction = PI * -5/4) != 126.0)|| 
								   		(p2_current_loc - 9 == pawn && (direction = PI * -3/4) != 126.0))||
								   p1_current_loc + 1 == p2_current_loc && 
								   		((p2_current_loc + 9 == pawn && (direction = PI * -1/4) != 126.0)|| 
								   		(p2_current_loc - 9 == pawn && (direction = PI * -7/4) != 126.0))   /* X(LR) pattern */
								 );
		}
		else if (id == 2) {
			is_it_diagonal_move = (p2_current_loc - 10 /*-1-9*/ == pawn || 
								   p2_current_loc -  8 /*+1-9*/ == pawn ||
								   p2_current_loc +  8 /*-1+9*/ == pawn ||
								   p2_current_loc + 10 /*+1+9*/ == pawn);
			we_are_in_triangle = ( p2_current_loc - 9 == p1_current_loc && 
										((p1_current_loc - 1 == pawn && (direction = PI * 3/4) != 126.0) || 
										(p1_current_loc + 1 == pawn && (direction = PI * 1/4) != 126.0)) ||
								   p2_current_loc + 9 == p1_current_loc && 
								   		((p1_current_loc - 1 == pawn && (direction = PI * 5/4) != 126.0) || 
								   		(p1_current_loc + 1 == pawn && (direction = PI * 7/4) != 126.0)) || /* X(UD) pattern */
								   p2_current_loc - 1 == p1_current_loc && 
								   		((p1_current_loc + 9 == pawn && (direction = PI * -5/4) != 126.0)|| 
								   		(p1_current_loc - 9 == pawn && (direction = PI * -3/4) != 126.0))||
								   p2_current_loc + 1 == p1_current_loc && 
								   		((p1_current_loc + 9 == pawn && (direction = PI * -1/4) != 126.0)|| 
								   		(p1_current_loc - 9 == pawn && (direction = PI * -7/4) != 126.0))   /* X(LR) pattern */
								 );
		}
		else {
			System.out.println("pawnPossibilityDiagonal: UNKNOWN ID");
			return false;
		}
		
		if (nearby_you && is_it_diagonal_move && we_are_in_triangle) {
			
			if (id == 1) {
				boolean possibility = isPossibleLocationDiagonal(p1_current_loc, pawn_r, pawn_c, direction);
				return  possibility;
			}
			
			else {	/* id == 2 */
				boolean possibility = isPossibleLocationDiagonal(p2_current_loc, pawn_r, pawn_c, direction);
				return possibility;
			}
		}
		
		else {
			System.out.println(nearby_you + ", " + is_it_diagonal_move + ", " + we_are_in_triangle);
			System.out.println("pawnPossibilityDiagonal: illegal movement");
			return false;
		}
		
	}

	private boolean isPossibleLocationDiagonal(int p_current_loc, int pawn_r, int pawn_c, double direction) {
		
		if (direction != (double) 0 && direction != 126.0); /* Only pawnPossibilityDiagonal(int pawn_r, int pawn_c, int id) call this. */
		else {
			System.out.println("isPossibleLocationSpecialized : illegal direction(== 0)");
			return false;
		}
		
		double up_down_triangle = Math.sin(direction);
		double left_right_triangle = Math.cos(direction);
		
		if (direction > 0)	{/* X(UD) direction */
			
			if (up_down_triangle > 0) {	/* UP-Left triangle || UP-Right triangle, pawn_r decide which one is selected. */	
			
				int wall_pos_r1 = pawn_r + 1; 			
				/* int wall_pos_c1 = pawn_c; */
				/* int wall_pos_r2 = wall_pos_r1
				 * pawn_r + 1 == p_current_loc / 9 - 1; */
				int wall_pos_c2 = p_current_loc % 9 * 2;
				// IntegerMappingToCoordinate (e < 0): -e % 10 * 2 - 1 (row), -e / 10 * 2 (col)
				boolean possibility;
				Optional <Integer> equals = blocks.stream()
						.filter(i -> (i < 0 && (-i % 10 * 2 - 1 == wall_pos_r1 /* == wall_pos_r2 */) && 
											   (-i / 10 * 2 == pawn_c /*wall_pos_c1*/ || -i / 10 * 2 == wall_pos_c2)))
						.findAny();
				possibility = !(equals.isPresent());
				if (possibility) System.out.println("isPossibleLocationSpecialized(North): There is no wall on your own way.");
				else System.out.println(equals);
				return possibility;
			}
			else {	/* Down-Left triangle || Down-Right triangle, pawn_r decide which one is selected. */
				
				int wall_pos_r1 = pawn_r - 1; 			
				/* int wall_pos_c1 = pawn_c; */
				/* int wall_pos_r2 = wall_pos_r1; 
				 * pawn_r - 1 == p_current_loc / 9 + 1*/
				int wall_pos_c2 = p_current_loc % 9 * 2;
				// IntegerMappingToCoordinate (e < 0): -e % 10 * 2 - 1 (row), -e / 10 * 2 (col)
				boolean possibility;
				Optional <Integer> equals = blocks.stream()
						.filter(i -> (i < 0 && (-i % 10 * 2 - 1 == wall_pos_r1 /* == wall_pos_r2 */) && 
											   (-i / 10 * 2 == pawn_c /*wall_pos_c1*/ || -i / 10 * 2 == wall_pos_c2)))
						.findAny();
				possibility = !(equals.isPresent());
				if (possibility) System.out.println("isPossibleLocationSpecialized(South): There is no wall on your own way.");
				else System.out.println(equals);
				return possibility;
			}
			
		}
		
		else { /* X(LR) direction */
			
			if (left_right_triangle < 0) {	/* Left-Up triangle || Left-Down triangle, pawn_c decide which one is selected. */
				
				/* int wall_pos_r1 = pawn_r; */
				int wall_pos_c1 = pawn_c + 1;
				int wall_pos_r2 = p_current_loc / 9 * 2; 
				/* int wall_pos_c2 = wall_pos_c1
				 * pawn_c + 1 == p_current_loc % 9 - 1*/
				// IntegerMappingToCoordinate (e > 0): e / 10 * 2 (row), e % 10 * 2 - 1 (col)
				boolean possibility;
				Optional <Integer> equals = blocks.stream()
						.filter(i -> (i > 0 && (-i % 10 * 2 - 1 == wall_pos_c1 /* == wall_pos_c2 */) && 
								   			   (-i / 10 * 2 == pawn_r /*wall_pos_r1*/ || -i / 10 * 2 == wall_pos_r2)))
						.findAny();
				possibility = !(equals.isPresent());
				if (possibility) System.out.println("isPossibleLocationSpecialized(West): There is no wall on your own way.");
				else System.out.println(equals);
				return possibility;
			}
			
			else { /* Right-Up triangle || Right-Down triangle, pawn_c decide which one is selected. */
				
				/* int wall_pos_r1 = pawn_r; */
				int wall_pos_c1 = pawn_c - 1;
				int wall_pos_r2 = p_current_loc / 9 * 2; 
				/* int wall_pos_c2 = wall_pos_c1
				 * pawn_c - 1 == p_current_loc % 9 + 1*/
				// IntegerMappingToCoordinate (e > 0): e / 10 * 2 (row), e % 10 * 2 - 1 (col)
				boolean possibility;
				Optional <Integer> equals = blocks.stream()
						.filter(i -> (i > 0 && (-i % 10 * 2 - 1 == wall_pos_c1 /* == wall_pos_c2 */) && 
								   			   (-i / 10 * 2 == pawn_r /*wall_pos_r1*/ || -i / 10 * 2 == wall_pos_r2)))
						.findAny();
				possibility = !(equals.isPresent());
				if (possibility) System.out.println("isPossibleLocationSpecialized(East): There is no wall on your own way.");
				else System.out.println(equals);
				return possibility;
			}
			
		}
		
	}

	private int coordinateMappingToInteger(int row, int col) {
		if (row % 2 == 0 && col % 2 == 1) 		/* horizontal Edge-connection : positive */
			return row / 2 * 10 + (col + 1) / 2;		/* (row * 5 = row / 2 * 10) : It's already even, so it doesn't affect the order. */
		
		else if (row % 2 == 1 && col % 2 == 0)	/* vertical Edge-connection : negative */ 
			return -(col / 2 * 10 + (row + 1) / 2);		/* ribbons */
		
		else if (row % 2 == 1 && col % 2 == 1)
			return (row - 1) / 2 * 9 + (col - 1) / 2;
		
		else if (row % 2 == 0 && col % 2 == 0)
			return (row / 2) * 9 + (col / 2);
		
		else { System.out.println("coordinateMappingToInteger(int row, int col): " + 127);
			return 127;
		}
	}
	
	private int[] IntegerMappingToCoordinate(int e) {
		// pawn_loc_Integer is impossible to be mapped to coordinate.
		if (e > 0) {
			int[] pos = {e / 10 * 2, e % 10 * 2 - 1};
			return pos;
		}
		else if (e < 0) {
			int[] pos = { -e % 10 * 2 - 1, -e / 10 * 2 }; 
			return pos;
		}
		else {
			System.out.println("IntegerMappingToCoordinate(int e): illegal parameter e");
			int[] pos = {-127, -127};
			return pos;
		}
	}

}