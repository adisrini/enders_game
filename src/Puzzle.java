import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

// This entire file is part of my masterpiece.
// ADITYA SRINIVASAN

/* READ BELOW!
 * Brief description of the refactor and how to use:
 * 
 * Edits were made in this class, PuzzleTile.java, 
 * PuzzleLevelData.java, and Settings.java. The code
 * now allows for level customization and addition
 * in a much more friendly, flexible, and modular way. Several
 * new methods were implemented that ran algorithms to
 * determine previously defined constants so that the level data
 * in PuzzleLevelData.java can be altered with little other
 * modifications necessary.
 * 
 * To customize an existing level, simply change the values in
 * the int[][] array located in PuzzleLevelData.java. To add a new level:
 * 
 * 1. Declare a new level in PuzzleLevelData.java in the
 *    same way as the other levels and add whatever tiles you want!
 * 2. Declare a tile width for your new level in Settings.java
 *    in the same way that is done for other levels
 * 3. Declare your new level as indicated in the declarations
 *    below
 * 4. Append the level you just created and the tile width you
 *    specified at the indicated places below
 * 5. Enjoy your new level!
 * 
 * Try it out! Customize an existing level or add a new one!
 * 
 */

/**
 * This is the main Puzzle class. It sets the stage
 * for this mode and coordinates level changes,
 * mirror deflections for the level.
 * 
 * It is dependent on Bullet.java, Popup.java, and Main.java
 * 
 * It can be used by calling puzzle.start(stage) to begin the game.
 * 
 * @author Aditya Srinivasan
 *
 */
public class Puzzle extends Application {
	
	private PuzzleTile[][] levelTileGrid;
	
	private boolean levelCleared = false;
	private boolean popupCreated = false;
	
	private Timeline timeline;
	
	private Stage stage;
	
	private int bulletIndex;
	
	private ArrayList<int[][]> levelsList = new ArrayList<int[][]>();
	private ArrayList<int[]> dimensionsList = new ArrayList<int[]>();
	private boolean[] directions = new boolean[4];
	private ArrayList<int[]> startingCoordsList = new ArrayList<int[]>();
	private ArrayList<int[]> endingCoordsList = new ArrayList<int[]>();
	private ArrayList<Integer> tileWidthsList = new ArrayList<Integer>();
	private ArrayList<int[]> startingTranslationList = new ArrayList<int[]>();
	
	private int[][] level1 = PuzzleLevelData.LEVEL1;
	private int[][] level2 = PuzzleLevelData.LEVEL2;
	private int[][] level3 = PuzzleLevelData.LEVEL3;
	private int[][] level4 = PuzzleLevelData.LEVEL4;
	// DECLARE ADDITIONAL LEVELS HERE
	
	private int[] currentCoords = new int[2];
		
	private boolean movingRight;
	private boolean movingLeft;
	private boolean movingDown;
	private boolean movingUp;
	
	private ArrayList<int[]> pathTaken = new ArrayList<int[]>();
	
	private Node bulletNode;
	private Bullet bullet;
	
	private int level = 1;
	private int numberOfLevels;
	
	private Pane gridRoot = new Pane();
	private Pane appRoot = new Pane();
	
	private Popup popup;
	
	/**
	 * This initializes the content including loading
	 * the background and setting the first level.
	 * @return
	 */
	private Pane initializeContent() {
		appRoot.setPrefSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
		
		Image bgImage = new Image(getClass().getClassLoader().getResourceAsStream("puzzle-bg.jpg"));
		ImageView bg = new ImageView(bgImage);
		
		appRoot.getChildren().add(bg);
		
		establishLevelsData();
		
		setLevel();
		
		return appRoot;
	}
	
	/**
	 * Populates array lists with information about starting coordinates, ending coordinates,
	 * dimensions, translations, and tile widths for each of the levels
	 */
	private void establishLevelsData() {
		levelsList.addAll(Arrays.asList(level1, level2, level3, level4));		// APPEND ADDITIONAL LEVELS CREATED HERE
		tileWidthsList.addAll(Arrays.asList(
				Settings.PUZZLE_LEVEL1_TILE_WIDTH,
				Settings.PUZZLE_LEVEL2_TILE_WIDTH,
				Settings.PUZZLE_LEVEL3_TILE_WIDTH,
				Settings.PUZZLE_LEVEL4_TILE_WIDTH)); 			// APPEND ADDITIONAL TILE-WIDTHS HERE
		numberOfLevels = levelsList.size();
		for(int i = 0; i < levelsList.size(); i++) {
			dimensionsList.add(new int[] {levelsList.get(i).length, levelsList.get(i)[0].length});
			detectStartingAndEndCoordinates(levelsList.get(i));
			startingTranslationList.add(calculateXYTranslations(i));
		}
	}
	
	/**
	 * Detects the starting shooting direction based on the placement
	 * of the player's ship
	 * @param level - the level data to analyze
	 * @return - an array of four booleans indicating the direction to face
	 */
	private boolean[] detectStartingDirection(int[][] level) {
		boolean[] directions = new boolean[] {false, false, false, false};	// indices correspond to 0 - up, 1 - right, 2 - down, 3 - left
		for(int i = 0; i < level.length; i++) {
			for(int j = 0; j < level[i].length; j++) {
				if(level[i][j] == 3) {
					if(inBoundsOfArray(i - 1, level.length) && level[i - 1][j] == 0) {
						directions[0] = true;
					} else if(inBoundsOfArray(i + 1, level.length) && level[i + 1][j] == 0) {
						directions[2] = true;
					} else if(inBoundsOfArray(j + 1, level[i].length) && level[i][j + 1] == 0) {
						directions[1] = true;
					} else if(inBoundsOfArray(j - 1, level[i].length) && level[i][j - 1] == 0) {
						directions[3] = true;
					}
				}
			}
		}
		return directions;
	}
	
	/**
	 * Determines the start coordinates of the player's position and the end
	 * coordinates of the enemy's position
	 * @param level - the level data to analyze
	 */
	private void detectStartingAndEndCoordinates(int[][] level) {
		for(int i = 0; i < level.length; i++) {
			for(int j = 0; j < level[i].length; j++) {
				if(level[i][j] == 3) {
					startingCoordsList.add(new int[] {i, j});
				}
				if(level[i][j] == 5) {
					endingCoordsList.add(new int[] {i, j});
				}
			}
		}
	}
	
	/**
	 * Determines whether an index is within an array
	 * @param index - index in question
	 * @param length - length of array
	 * @return
	 */
	private boolean inBoundsOfArray(int index, int length) {
		return index >= 0 && index < length;
	}
	
	/**
	 * Calculates the X and Y translations of a grid to place it within
	 * the center of the screen
	 * @param levelIndex
	 * @return
	 */
	private int[] calculateXYTranslations(int levelIndex) {
		int gridWidth = dimensionsList.get(levelIndex)[1] * tileWidthsList.get(levelIndex);
		int xOffset = (Settings.WINDOW_WIDTH - gridWidth)/2;
		int gridHeight = dimensionsList.get(levelIndex)[0] * tileWidthsList.get(levelIndex);
		int yOffset = (Settings.WINDOW_HEIGHT - gridHeight)/2;
		return new int[] {xOffset, yOffset};
	}
	
	/**
	 * Loads the specified level and resets other
	 * variables used throughout the game
	 */
	public void setLevel() {
		appRoot.getChildren().remove(gridRoot);
		bulletIndex = 0;
		gridRoot.getChildren().clear();
		pathTaken.clear();
		loadLevel(gridRoot);
		gridRoot.setTranslateX(startingTranslationList.get(level - 1)[0]);
		gridRoot.setTranslateY(startingTranslationList.get(level - 1)[1]);
		appRoot.getChildren().add(gridRoot);
	}
	
	/**
	 * A generalized method to load the UI and other data for a specified level
	 * @param root
	 */
	private void loadLevel(Pane root) {
		int levelIndex = level - 1;
		int dimX = dimensionsList.get(levelIndex)[0];
		int dimY = dimensionsList.get(levelIndex)[1];
		levelTileGrid = new PuzzleTile[dimX][dimY];
		for(int i = 0; i < dimX; i++) {
			for(int j = 0 ; j < dimY; j++) {
				PuzzleTile tile = new PuzzleTile(i, j, levelsList.get(levelIndex)[i][j], tileWidthsList.get(levelIndex));
				tile.setTranslateX(j * tileWidthsList.get(levelIndex));
				tile.setTranslateY(i * tileWidthsList.get(levelIndex));
				
				levelTileGrid[i][j] = tile;
				
				root.getChildren().add(tile);
			}
		}
		
		createAndLoadBullet(levelIndex, root);
		
		directions = detectStartingDirection(levelsList.get(levelIndex));
		
		assignDirections();
		
		currentCoords = startingCoordsList.get(levelIndex);
	}
	
	/**
	 * Assigns boolean values to each of the four directions to indicate
	 * where to shoot
	 */
	private void assignDirections() {
		movingRight = false; movingDown = false; movingUp = false; movingLeft = false;
		
		for(int i = 0; i < directions.length; i++) {
			if(directions[i]) {
				switch(i) {
				case 0:
					movingUp = true; break;
				case 1:
					movingRight = true; break;
				case 2:
					movingDown = true; break;
				case 3:
					movingLeft = true; break;
				}
				break;
			}
		}
	}
	
	/**
	 * Creates and loads a new bullet
	 * @param levelIndex
	 * @param root
	 */
	private void createAndLoadBullet(int levelIndex, Pane root) {
		bullet = new Bullet(Settings.BULLET_SIZE, Color.GOLD);
		bulletNode = bullet.getBulletAsNode();
		bulletNode.setTranslateX(tileWidthsList.get(levelIndex)/2 + startingCoordsList.get(levelIndex)[1]*tileWidthsList.get(levelIndex));
		bulletNode.setTranslateY(tileWidthsList.get(levelIndex)/2 + startingCoordsList.get(levelIndex)[0]*tileWidthsList.get(levelIndex));		
		root.getChildren().add(bulletNode);
	}
	
	/**
	 * Fires the bullet and determines the path taken
	 * by the bullet.
	 * 
	 * @param levelTile
	 */
	private void fireBullet(PuzzleTile[][] levelTile) {
		if(movingRight) {
			currentCoords[1]++;
			int tileId = levelTile[currentCoords[0]][currentCoords[1]].getTileId();
			if(tileId == 0 || tileId == 3) {
			} else if(tileId == 1) {
				movingRight = false;
				movingDown = true;
			} else if(tileId == 2) {
				movingRight = false;
				movingUp = true;
			} else {
				movingRight = false;
			}
			pathTaken.add(new int[] {currentCoords[0], currentCoords[1]});
			fireBullet(levelTile);
		}
		if(movingDown) {
			currentCoords[0]++;
			int tileId = levelTile[currentCoords[0]][currentCoords[1]].getTileId();
			if(tileId == 0 || tileId == 3) {
			} else if(tileId == 1) {
				movingDown = false;
				movingRight = true;
			} else if(tileId == 2) {
				movingDown = false;
				movingLeft = true;
			} else {
				movingDown = false;
			}
			pathTaken.add(new int[] {currentCoords[0], currentCoords[1]});
			fireBullet(levelTile);
		}
		if(movingLeft) {
			currentCoords[1]--;
			int tileId = levelTile[currentCoords[0]][currentCoords[1]].getTileId();
			if(tileId == 0 || tileId == 3) {
			} else if(tileId == 1) {
				movingLeft = false;
				movingUp = true;
			} else if(tileId == 2) {
				movingLeft = false;
				movingDown = true;
			} else {
				movingLeft = false;
			}
			pathTaken.add(new int[] {currentCoords[0], currentCoords[1]});
			fireBullet(levelTile);
		}
		if(movingUp) {
			currentCoords[0]--;
			int tileId = levelTile[currentCoords[0]][currentCoords[1]].getTileId();
			if(tileId == 0 || tileId == 3) {
			} else if(tileId == 1) {
				movingUp = false;
				movingLeft = true;
			} else if(tileId == 2) {
				movingUp = false;
				movingRight = true;
			} else {
				movingUp = false;
			}
			pathTaken.add(new int[] {currentCoords[0], currentCoords[1]});
			fireBullet(levelTile);
		}
	}
	
	/**
	 * Sets the status of the level as cleared
	 * @param isCleared
	 */
	public void setLevelCleared(boolean isCleared) {
		levelCleared = isCleared;
	}
	
	/**
	 * Creates the popup if it hasn't been already
	 */
	private void createPopup() {
		if(!popupCreated) {
			appRoot.getChildren().add(popup);
			popupCreated = true;
		}	
	}
	
	/**
	 * Continues to next level if the popup
	 * is clicked
	 */
	private void nextLevel() {
		if(levelCleared) {
			timeline.stop();
			level++;
			appRoot.getChildren().remove(appRoot.getChildren().size() - 1);
			popupCreated = false;
			levelCleared = false;
			setLevel();
		}
	}
	
	/**
	 * Go to the main menu
	 */
	private void goToMainMenu() {
		if(levelCleared) {
			timeline.stop();
			appRoot.getChildren().remove(appRoot.getChildren().size() - 1);
			popupCreated = false;
			levelCleared = false;
			Main main = new Main();
			try {
				main.start(stage);
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Animates the bullet to follow the path taken
	 * @param tileSize
	 */
	private void animateBullet() {
		int tileSize = tileWidthsList.get(level - 1);
		timeline = new Timeline(new KeyFrame(Duration.millis(Settings.PUZZLE_BULLET_SPEED), e -> {
			if(bulletIndex < pathTaken.size()) {
    			TranslateTransition tt = new TranslateTransition(Duration.millis(Settings.PUZZLE_BULLET_SPEED), bulletNode);
    			tt.setToX(tileSize/2 + pathTaken.get(bulletIndex)[1]*tileSize);
    			tt.setToY(tileSize/2 + pathTaken.get(bulletIndex)[0]*tileSize);
    			tt.setCycleCount(1);
    			tt.play();
    			bulletIndex++;
    		}
			handleLevelCompletion();
	    }));
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
	}
	
	/**
	 * Handles popups in the event of level completion
	 */
	private void handleLevelCompletion() {
		int tileSize = tileWidthsList.get(level - 1);
		if(bulletNode.getTranslateY() == tileSize/2 + endingCoordsList.get(level - 1)[0]*tileSize && bulletNode.getTranslateX() == tileSize/2 + endingCoordsList.get(level - 1)[1]*tileSize) {
			if(level < numberOfLevels) {
				popup = new Popup("LEVEL " + level + " CLEARED!", "Continue on to Level " + Integer.toString(level+1) + "...", "continue", this);
				createPopup();
				nextLevel();
			} else {
				popup = new Popup("LEVEL " + level + " CLEARED!", "You win! Play as Mazer now!", "main menu", this);
				createPopup();
				goToMainMenu();
			}
		}
	}
	
	/**
	 * Sets the stage of the game including a key listener
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		
		Scene scene = new Scene(initializeContent());
		primaryStage.setScene(scene);
		primaryStage.show();	   
		
		scene.setOnKeyPressed(e -> {
		    if (e.getCode() == KeyCode.SPACE) {
		    	fireBullet(levelTileGrid);
		    	animateBullet();
		    }
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Returns the stage of the game
	 * @return
	 */
	public Stage getStage() {
		return stage;
	}

}
