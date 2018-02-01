import java.util.ArrayList;

/**
 * A state in the search represented by the (x,y) coordinates of the square and
 * the parent. In other words a (square,parent) pair where square is a Square,
 * parent is a State.
 * 
 * You should fill the getSuccessors(...) method of this class.
 * 
 */
public class State {

	private Square square;
	private State parent;

	// Maintain the gValue (the distance from start)
	// You may not need it for the BFS but you will
	// definitely need it for AStar
	private int gValue;

	// States are nodes in the search tree, therefore each has a depth.
	private int depth;

	/**
	 * @param square
	 *            current square
	 * @param parent
	 *            parent state
	 * @param gValue
	 *            total distance from start
	 */
	public State(Square square, State parent, int gValue, int depth) {
		this.square = square;
		this.parent = parent;
		this.gValue = gValue;
		this.depth = depth;
	}

	/**
	 * @param visited
	 *            explored[i][j] is true if (i,j) is already explored
	 * @param maze
	 *            initial maze to get find the neighbors
	 * @return all the successors of the current state
	 */
	public ArrayList<State> getSuccessors(boolean[][] explored, Maze maze) {

		//check all four neighbors (up, right, down, left)
		//true if successor is either explored, out of bounds, or a wall
		boolean up = true;
		boolean right = true;
		boolean down = true;
		boolean left = true;

		char[][] mazeMatrix = maze.getMazeMatrix();

		//checks for out of bounds coordinates and rejects them
		//checks for wall coordinates and rejects them
		//checks whether explored or not
		if (square.Y + 1 < explored[square.X].length)
			if (mazeMatrix[square.X][square.Y + 1] != '%')
				right = explored[square.X][square.Y + 1];
		if (square.X + 1 < explored.length) 
			if (mazeMatrix[square.X + 1][square.Y] != '%')
				down = explored[square.X + 1][square.Y];
		if (square.Y - 1 >= 0)
			if (mazeMatrix[square.X][square.Y - 1] != '%')
				left = explored[square.X][square.Y - 1];
		if (square.X - 1 >= 0)
			if (mazeMatrix[square.X - 1][square.Y] != '%')
				up = explored[square.X - 1][square.Y];

		//add successors that are valid above and unexplored in the list
		ArrayList<State> unvisited = new ArrayList<State>();
		if (left == false)
			unvisited.add(new State(new Square(square.X, square.Y - 1), 
					this, gValue + 1, depth + 1));
		if (down == false)
			unvisited.add(new State(new Square(square.X + 1, square.Y), 
					this, gValue + 1, depth + 1));
		if (right == false)
			unvisited.add(new State(new Square(square.X, square.Y + 1), 
					this, gValue + 1, depth + 1));
		if (up == false)
			unvisited.add(new State(new Square(square.X - 1, square.Y), 
					this, gValue + 1, depth + 1));

		// return all unvisited neighbors
		return unvisited;
	}

	/**
	 * @return x coordinate of the current state
	 */
	public int getX() {
		return square.X;
	}

	/**
	 * @return y coordinate of the current state
	 */
	public int getY() {
		return square.Y;
	}

	/**
	 * @param maze initial maze
	 * @return true is the current state is a goal state
	 */
	public boolean isGoal(Maze maze) {
		if (square.X == maze.getGoalSquare().X
				&& square.Y == maze.getGoalSquare().Y)
			return true;

		return false;
	}

	/**
	 * @return the current state's square representation
	 */
	public Square getSquare() {
		return square;
	}

	/**
	 * @return parent of the current state
	 */
	public State getParent() {
		return parent;
	}

	/**
	 * You may not need g() value in the BFS but you will need it in A-star
	 * search.
	 * 
	 * @return g() value of the current state
	 */
	public int getGValue() {
		return gValue;
	}

	/**
	 * @return depth of the state (node)
	 */
	public int getDepth() {
		return depth;
	}
}
