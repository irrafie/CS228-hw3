package edu.iastate.cs228.hw3;

import java.io.FileNotFoundException;

/**
 *  
 * @author	Irfan Farhan Mohamad Rafie
 *
 */

public class EightPuzzle 
{
	/**
	 * This static method solves an 8-puzzle with a given initial state using two heuristics which 
	 * compare the board configuration with the goal configuration by the number of mismatched tiles, 
	 * and by the Manhattan distance, respectively.  The goal configuration is set for all puzzles as
	 * 
	 * 			1 2 3
	 * 			8   4
	 * 			7 6 5
	 * 
	 * @param s0
	 * @return
	 */
	public static String solve8Puzzle(State s0)
	{
		// TODO 
		
		// 1) Return null if the puzzle is not solvable. 
				

		// 2) Otherwise, solve the puzzle with two heuristics.  The two solutions may be different
		//    but must have the same length for optimality. 
		
		Heuristic h[] = {Heuristic.TileMismatch, Heuristic.ManhattanDist }; 
		String [] moves = new String[2]; 
		
		for (int i = 0; i < 2; i++)
		{
			moves[i] = AStar(s0, h[i]); 
		}
		
		// 3) Combine the two solution strings into one that would print out in the 
		//    output format specified in Section 5 of the project description.
		
		return null; 
	}

	
	/**
	 * This method implements the A* algorithm to solve the 8-puzzle with an input initial state s0. 
	 * The algorithm is described in Section 3 of the project description. 
	 * 
	 * Precondition: the puzzle is solvable with the initial state s0.
	 * 
	 * @param s0  initial state
	 * @param h   heuristic 
	 * @return    solution string 
	 */
	public static String AStar(State s0, Heuristic h)
	{
		
		// TODO 
		
		// Initialize the two lists used by the algorithm. 
		OrderedStateList OPEN = new OrderedStateList(h, true); 
		OrderedStateList CLOSE = new OrderedStateList(h, false);
					
		
		// Implement the algorithm described in Section 3 to solve the puzzle. 
		// Once a goal state s is reached, call solutionPath(s) and return the solution string. 
			
		
		return null; 
						
	}
	
	
	
	/**
	 * From a goal state, follow the predecessor link to trace all the way back to the initial state. 
	 * Meanwhile, generate a string to represent board configurations in the reverse order, with 
	 * the initial configuration appearing first. Between every two consecutive configurations 
	 * is the move that causes their transition. A blank line separates a move and a configuration.  
	 * In the string, the sequence is preceded by the total number of moves and a blank line. 
	 * 
	 * See Section 5 in the projection description for an example. 
	 * 
	 * Call the toString() method of the State class. 
	 * 
	 * @param goal
	 * @return
	 */
	private String solutionPath(State goal)
	{
		// TODO 
		
		return null; 
	}
	
	
	
}
