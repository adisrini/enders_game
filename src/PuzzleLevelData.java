/**
 * This class has the puzzle level data which can
 * be fairly easily modified. To view instructions on
 * how to modify existing levels or create new
 * ones, visit the header of Puzzle.java! 
 * 
 * It is not dependent on other classes.
 * 
 * It can be used by modifying the puzzle tile values below
 * to change the appearance of a particular level.
 * 
 * LEGEND
 * ------
 * -1 : blank tile representing out of bounds
 *  0 : default tile with no mirror that can be toggled by click
 *  3 : your spaceship position
 *  4 : obstacle or wall
 *  5 : enemy spaceship position
 *  
 * NOTE: Please follow the formats of existing levels when creating
 * a new level. Some rules to follow:
 * 1. Surround the level with blocks of type -1 so that a boundary
 *    is clearly defined.
 * 2. Place your spaceship and the enemy spaceship in any of the
 *    edge positions except for the corners
 *    (ANY OF THE o's, NONE OF THE x's)
 *    
 *                x o o o o o x
 *                o			  o
 *                o			  o
 *                o  	      o
 *                o			  o
 *                x o o o o o x
 *                
 * 3. Please keep the matrix rectangular (but not necessarily square)
 *  
 *  
 * @author Aditya Srinivasan
 *
 */
public class PuzzleLevelData {
	
	public static final int[][] LEVEL1 = new int[][] {
		{-1, -1,  3, -1, -1, -1},
		{-1,  0,  0,  0,  0, -1},
		{-1,  0,  4,  0,  0, -1},
		{-1,  0,  0,  0,  0, -1},
		{-1,  0,  0,  0,  0, -1},
		{-1, -1, -1,  5, -1, -1}
	};
	
	public static final int[][] LEVEL2 = new int[][] {
		{-1, -1, -1, -1, -1,  3, -1, -1},
		{-1,  0,  0,  0,  0,  0,  0, -1},
		{-1,  4,  4,  4,  4,  4,  0, -1},
		{-1,  0,  0,  0,  0,  0,  0, -1},
		{-1,  0,  0,  0,  0,  0,  0, -1},
		{-1,  0,  0,  0,  0,  0,  0, -1},
		{-1,  0,  0,  0,  0,  0,  0, -1},
		{-1,  5, -1, -1, -1, -1, -1, -1}
	};
	
	public static final int[][] LEVEL3 = new int[][] {
		{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  5, -1, -1, -1},
		{-1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  3},
		{-1,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0, -1},
		{-1,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  0, -1},
		{-1,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  4, -1},
		{-1,  4,  4,  4,  4,  4,  4,  0,  4,  4,  0,  0,  4, -1},
		{-1,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  4, -1},
		{-1,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  4, -1},
		{-1,  0,  4,  4,  4,  0,  0,  0,  0,  4,  0,  0,  4, -1},
		{-1,  0,  4,  4,  4,  0,  0,  0,  0,  4,  0,  0,  4, -1},
		{-1,  0,  4,  4,  4,  0,  0,  0,  0,  4,  0,  0,  4, -1},
		{-1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4, -1},
		{-1,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  4, -1},
		{-1, -1, -1, -1, -1, -1, -1, -1, -1, -4, -1, -1, -1, -1}
	};
	
	public static final int[][] LEVEL4 = new int[][] {
		{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		{-1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1},
		{-1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  0,  0,  0,  0, -1},
		{-1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  0,  0,  0, -1},
		{-1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  0,  0, -1},
		{ 3,  0,  0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  0,  0,  5},
		{-1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  0,  0, -1},
		{-1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  0,  0,  0, -1},
		{-1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  0,  0,  0,  0,  0,  0, -1},
		{-1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, -1},
		{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
	};
	
}
