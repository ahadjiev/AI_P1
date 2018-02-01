import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {

		// explored list is a Boolean array that indicates if a state 
		// associated with a given position in the maze has already been explored. 
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];

		// frontier for astar list
		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();

		// calculate the heuristic value using euclidean distance for every square in the maze
		double[][] hMatrix = new double[maze.getNoOfRows()][maze.getNoOfCols()];
		for (int i = 0; i < hMatrix.length; i++) {
			for (int j = 0; j < hMatrix[i].length; j++) {
				Square goal = maze.getGoalSquare();
				hMatrix[i][j] = (double) Math.sqrt((double)(Math.pow((goal.Y - j), 2) + Math.pow((goal.X - i), 2)));

			}
		}

		// initialize the root state and add to the frontier list
		Square player = maze.getPlayerSquare();
		State playerState = new State (player, null, 0, 0);
		StateFValuePair playerSTP = new StateFValuePair(playerState, 0);
		frontier.add(playerSTP);

		//loop until frontier is empty
		while (!frontier.isEmpty()) {
			// find the minimum stateFValuePair.
			StateFValuePair parentPair = frontier.poll();
			State parentState = parentPair.getState();
			Square parentSquare = parentState.getSquare();
			noOfNodesExpanded++;

			//update the explored nodes
			explored[parentSquare.X][parentSquare.Y] = true;

			//add successors to frontier queue
			ArrayList<State> successors = parentState.getSuccessors(explored, maze);
			for (State successor : successors) {
				explored[successor.getSquare().X][successor.getSquare().Y] = true;
				frontier.add(new StateFValuePair(successor, successor.getGValue() 
						+ hMatrix[successor.getX()][successor.getY()]));
				//update maxDepthSearched
				if (maxDepthSearched < successor.getDepth()) 
					maxDepthSearched = successor.getDepth();
			}

			//update maxSizeOfFrontier
			if (frontier.size() > maxSizeOfFrontier) {
				maxSizeOfFrontier = frontier.size();
			}

			// return true if a solution has been found and
			// update the maze if a solution found
			if (parentState.isGoal(maze)) {
				cost = parentState.getGValue();
				State temp = parentState;
				while (temp != null){
					if (maze.getMazeMatrix()[temp.getSquare().X][temp.getSquare().Y] == ' ')
						maze.setOneSquare(temp.getSquare(),'.');
					temp = temp.getParent();
				}
				return true;
			}
		}
		// return false if there is no solution
		return false;
	}
}
