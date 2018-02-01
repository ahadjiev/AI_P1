import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public BreadthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main breadth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {

		// explored list is a 2D Boolean array that indicates if a state 
		//associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];

		// Queue implementing the Frontier list
		LinkedList<State> queue = new LinkedList<State>();

		//set player square and state
		Square player = maze.getPlayerSquare();
		State playerState = new State (player, null, 0, 0);
		queue.add(playerState);
		

		//loop until queue is empty
		while (!queue.isEmpty()) {
			//pop first state from Frontier Queue
			State parentState = queue.pop();
			Square parentSquare = parentState.getSquare();
			++noOfNodesExpanded;

			//update the explored nodes
			explored[parentSquare.X][parentSquare.Y] = true;
			
			if (parentState.getDepth() > maxDepthSearched){
				maxDepthSearched = parentState.getDepth();
			}
			
			//add successors to frontier queue
			ArrayList<State> successors = parentState.getSuccessors(explored, maze);
			for (State successor : successors) {
				Square sucSquare = successor.getSquare();
				explored[sucSquare.X][sucSquare.Y] = true;
				queue.add(successor);
				
				//update maxDepthSearched
				if (successor.getDepth() - 1 > maxDepthSearched) //-1 for root
					maxDepthSearched = successor.getDepth();
			}

            //update maxSizeOfFrontier
            if (queue.size() > maxSizeOfFrontier)
                maxSizeOfFrontier = queue.size();
            
            //update the maze if a solution found
            //return true if find a solution
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
		//return false if no solution
		return false;
	}
}
