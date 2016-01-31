import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This is the class for running the Platformer mode
 * of this game. It loads UI elements, coordinates movement
 * and deaths and other interactions with other
 * classes.
 * 
 * It is dependent on Bullet.java, PlatformEnemy.java, PlatformPlayer.java, Main.java,
 * and Popup.java.
 * 
 * It is called by stating platformer.start(stage) to begin the game.
 * 
 * @author Aditya Srinivasan
 *
 */
public class Platformer extends Application {

    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();

    private ArrayList<Node> platforms = new ArrayList<Node>();

	private HashMap<Node, Boolean> easyEnemyFloorList = new HashMap<Node, Boolean>();
	private ArrayList<Node> mediumEnemyList = new ArrayList<Node>();
	private ArrayList<Node> hardEnemyList = new ArrayList<Node>();
	
	private ArrayList<Node> enemyNodeList = new ArrayList<Node>();
	private ArrayList<PlatformEnemy> enemyList = new ArrayList<PlatformEnemy>();

    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();
    private Pane messageRoot = new Pane();
    
    private int[] messageSlot = new int[] {0, 0, 0};
    private int pauseSlot;
    private int godModeSlot;
    private int invisibleSlot;

    private Node playerNode;
    private Node bulletNode;
    
    private boolean canShoot = true;
    private boolean godMode = false;
    private boolean invisible = false;
    private boolean gravity = true;
    private boolean running = true;
	private boolean mediumEnemyDirectionToggle = true;
    
    private int[] checkpointCoordinates;
	private int checkpointPosition = 0;

    private int levelWidth;
    
    private Scene scene;
    private Stage stage;
    
    private int lives = Settings.PLAYER_LIVES;
    
    private PlatformPlayer player;
    private PlatformEnemy enemy1;
    private PlatformEnemy enemy2;
	private PlatformEnemy enemy3;
	private PlatformEnemy enemy4;
	private PlatformEnemy enemy5;
	private PlatformEnemy enemy6;
	private PlatformEnemy boss;
	
	private boolean bossDead = false;
    private boolean leapOfFaith = false;
    private boolean flagLoaded = false;
	
    private PlatformBlock platformBlock = new PlatformBlock(Settings.BLOCK_WIDTH, Settings.BLOCK_HEIGHT);
    private Bullet bullet;

	private Point2D bulletVelocity = new Point2D(0, 0);

	private long timeShot;

	private Node enemyNodeHit;
	private PlatformEnemy enemyHit;
	
	/**
	 * Returns the level width
	 * @return
	 */
	public int getLevelWidth() {
		return levelWidth;
	}
	
	/**
	 * Returns the player node
	 * @return
	 */
	public Node getPlayerNode() {
		return playerNode;
	}
    
	/** Initializes the UI, including players, enemies,
	 * blocks, and other elements. Also sets the
	 * screen to move with the player.
	 * 
	 */
    private void initializeContent() {
    	loadImage(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT, 0, 0, "platformer-bg.jpg", appRoot);
    	
    	levelWidth = PlatformLevelData.LEVEL[0].length() * Settings.BLOCK_WIDTH;
    	
    	platformBlock.createPlatformLists(gameRoot);
    	
    	platforms = platformBlock.getPlatformList();
    	
    	createAndLoadPlayer();
    	
    	createAndLoadEnemies();
    	
    	checkpointCoordinates = new int[] {Settings.PLAYER_STARTING_X, Settings.PLAYER_STARTING_Y};
    	
    	translateScreen(gameRoot, Settings.SCREEN_OFFSET);
   		
        loadLivesToUI();
        
   		appRoot.getChildren().addAll(gameRoot, uiRoot, messageRoot);
    }
    
    /**
     * Initializes the player and adds to the main root
     */
    private void createAndLoadPlayer() {
    	player = new PlatformPlayer(Settings.PLAYER_STARTING_X, Settings.PLAYER_STARTING_Y, Settings.PLAYER_WIDTH, Settings.PLAYER_HEIGHT, Settings.PLAYER_URL);    	
    	playerNode = player.getPlayerAsNode(player);
    	player.setInstances(playerNode, platformBlock, this);
    	
    	gameRoot.getChildren().add(playerNode);
    }
    
    /**
     * Initializes each enemy and adds each to a list
     * and then the main root
     */
    private void createAndLoadEnemies() {
    	enemy1 = new PlatformEnemy(Settings.ENEMY_1_STARTING_X, Settings.ENEMY_1_STARTING_Y, Settings.EASY_ENEMY_WIDTH, Settings.EASY_ENEMY_HEIGHT, Settings.EASY_ENEMY_URL, Settings.EASY_ENEMY_LIVES);
        Node enemyNode1 = enemy1.getEnemyAsNode(enemy1);
        
        enemy2 = new PlatformEnemy(Settings.ENEMY_2_STARTING_X, Settings.ENEMY_2_STARTING_Y, Settings.EASY_ENEMY_WIDTH, Settings.EASY_ENEMY_HEIGHT, Settings.EASY_ENEMY_URL, Settings.EASY_ENEMY_LIVES);
        Node enemyNode2 = enemy2.getEnemyAsNode(enemy2);
        
        enemy3 = new PlatformEnemy(Settings.ENEMY_3_STARTING_X, Settings.ENEMY_3_STARTING_Y, Settings.MEDIUM_ENEMY_WIDTH, Settings.MEDIUM_ENEMY_HEIGHT, Settings.MEDIUM_ENEMY_URL, Settings.MEDIUM_ENEMY_LIVES);
        Node enemyNode3 = enemy3.getEnemyAsNode(enemy3);
        
        enemy4 = new PlatformEnemy(Settings.ENEMY_4_STARTING_X, Settings.ENEMY_4_STARTING_Y, Settings.MEDIUM_ENEMY_WIDTH, Settings.MEDIUM_ENEMY_HEIGHT, Settings.MEDIUM_ENEMY_URL, Settings.MEDIUM_ENEMY_LIVES);
        Node enemyNode4 = enemy4.getEnemyAsNode(enemy4);
        
        enemy5 = new PlatformEnemy(Settings.ENEMY_5_STARTING_X, Settings.ENEMY_5_STARTING_Y, Settings.HARD_ENEMY_WIDTH, Settings.HARD_ENEMY_HEIGHT, Settings.HARD_ENEMY_URL, Settings.HARD_ENEMY_LIVES);
        Node enemyNode5 = enemy5.getEnemyAsNode(enemy5);
        
        enemy6 = new PlatformEnemy(Settings.ENEMY_6_STARTING_X, Settings.ENEMY_6_STARTING_Y, Settings.HARD_ENEMY_WIDTH, Settings.HARD_ENEMY_HEIGHT, Settings.HARD_ENEMY_URL, Settings.HARD_ENEMY_LIVES);
        Node enemyNode6 = enemy6.getEnemyAsNode(enemy6);
        
        boss = new PlatformEnemy(Settings.BOSS_STARTING_X, Settings.BOSS_STARTING_Y, Settings.BOSS_WIDTH, Settings.BOSS_HEIGHT, Settings.BOSS_URL, Settings.BOSS_LIVES);
        Node bossNode = boss.getEnemyAsNode(boss);
        
        enemy1.setInstances(playerNode, platformBlock, this);
        enemy1.establishEnemyBullets();
    	
    	easyEnemyFloorList.put(enemyNode1, true);
    	easyEnemyFloorList.put(enemyNode2, true);
    	
    	mediumEnemyList.addAll(Arrays.asList(enemyNode3, enemyNode4));
    	
    	hardEnemyList.addAll(Arrays.asList(enemyNode5, enemyNode6, bossNode));
    	
    	enemyNodeList.addAll(Arrays.asList(enemyNode1, enemyNode2, enemyNode3, enemyNode4, enemyNode5, enemyNode6, bossNode));
    	
    	enemyList.addAll(Arrays.asList(enemy1, enemy2, enemy3, enemy4, enemy5, enemy6, boss));
    	
        gameRoot.getChildren().addAll(enemyNode1, enemyNode2, enemyNode3, enemyNode4, enemyNode5, enemyNode6, bossNode);
    }
    
    /**
     * Loads lives to the UI
     */
    private void loadLivesToUI() {
    	for(int i = 0; i < lives; i++) {
        	loadImage(Settings.HEART_WIDTH, Settings.HEART_HEIGHT, Settings.HEART_OFFSET+i*Settings.HEART_SPACING, Settings.HEART_Y, Settings.HEART_URL, uiRoot);
        }
    }
    
    /**
     * Pauses the game
     */
    public void pause() {
    	running = false;
    }
    
    /** Returns the game stage
     * 
     * @return
     */
    public Stage getStage() {
    	return stage;
    }
    
    /**
     * Puts a key-value pair in the easy enemy list
     * @param node
     * @param bool
     */
    public void setEasyEnemyFloorList(Node node, Boolean bool) {
    	easyEnemyFloorList.put(node, bool);
    }
    
    /**
     * Returns the easy enemy list
     * @return
     */
    public HashMap<Node, Boolean> getEasyEnemyFloorList() {
    	return easyEnemyFloorList;
    }
    
    /**
     * Returns the medium enemy list
     * @return
     */
    public ArrayList<Node> getMediumEnemyList() {
    	return mediumEnemyList;
    }
    
    /**
     * Returns the hard enemy list
     * @return
     */
    public ArrayList<Node> getHardEnemyList() {
    	return hardEnemyList;
    }
    
    /**
     * Translates the screen as the player moves
     * @param root
     * @param offsetAmount
     */
    private void translateScreen(Pane root, int offsetAmount){
    	playerNode.translateXProperty().addListener((obs, old, newValue) -> {
    		int offset = newValue.intValue();
    		if (offset > offsetAmount && offset < levelWidth - offsetAmount) {
    			root.setLayoutX(-(offset - offsetAmount));
    		}
    	});
    }
    
    /**
     * Loads an image to a specified root
     * @param w - image width
     * @param h - image height
     * @param x - image X position
     * @param y - image Y position
     * @param url - image URL
     * @param root - the root to add the image to
     */
    private void loadImage(int w, int h, int x, int y, String url, Pane root) {
    	Image img = new Image(getClass().getClassLoader().getResourceAsStream(url));
    	ImageView imgV = new ImageView(img);
    	imgV.setFitWidth(w);
    	imgV.setFitHeight(h);
    	imgV.setTranslateX(x);
    	imgV.setTranslateY(y);
    	root.getChildren().add(imgV);
    }
    
    /**
     * Checks whether the player is trying to shoot and, if so, calls the shoot
     * method
     */
    private void checkShoot() {        
        if(isPressed(Settings.SHOOT_KEY)) {
        	shoot();
        }        
        moveBulletX((int)bulletVelocity.getX());
    }
    
    /**
     * Checks whether the player can shoot and, if so, creates a bullet and 
     * shoots it in the direction the player is facing.
     * After a certain timeout duration, the bullet is reinitialized
     * so the player can shoot another one
     */
    private void shoot() {
    	if(canShoot) {
    		bullet = new Bullet(Settings.BULLET_SIZE, Settings.BULLET_SECONDARY_COLOR);
    		bulletNode = bullet.getBulletAsNode();
    		bulletNode.setTranslateX(playerNode.getTranslateX());
    		bulletNode.setTranslateY(playerNode.getTranslateY());
    		gameRoot.getChildren().add(bulletNode);
    		if(player.getFacingRight()) {
    			bulletVelocity = bulletVelocity.add(Settings.BULLET_SPEED, 0);
    		} else {
    			bulletVelocity = bulletVelocity.add(-(Settings.BULLET_SPEED), 0);
    		}
			timeShot = System.currentTimeMillis();
    		canShoot = false;
    	}
    	if(System.currentTimeMillis() - timeShot > Settings.BULLET_TIMEOUT) {
    		reinitializeBullet();
    	}
    }
    
    /**
     * Resets the bullet
     */
    private void reinitializeBullet() {
		canShoot = true;
		bulletVelocity = new Point2D(0, 0);
		gameRoot.getChildren().remove(bulletNode);
	}

    /**
     * Moves the bullet a specified value in the x-direction
     * in a specified direction (based on algebraic sign) and
     * checks for bullet collisions with platforms or enemies
     * @param value - amount to move bullet
     */
	private void moveBulletX(int value) {
    	boolean movingRight = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
            for(int j = 0; j < platforms.size(); j++) {
                if(checkNodeNodeCollision(bulletNode, platforms.get(j)) || checkEnemyCollision(bulletNode)) {
                    if(movingRight) {
                        if(bulletNode.getTranslateX() + Settings.BULLET_SIZE == platforms.get(j).getTranslateX() && this.getGravity()) {
                            bulletNode.setTranslateX(bulletNode.getTranslateX() - 1);
                            reinitializeBullet();
                            if(checkEnemyCollision(bulletNode)) {
                            	enemyHit.removeLife();
                            	if(enemyHit.getEnemyLives() == 0) {
                            		if(enemyHit == boss) {
                            			bossDead = true;
                            			enemy1.bossDeath();
                            		}
                            		removeEnemy();
                            	}
                            }
                            return;
                        }
                    }
                    else {
                        if (bulletNode.getTranslateX() == platforms.get(j).getTranslateX() + platformBlock.getHeight() && this.getGravity()) {
                            reinitializeBullet();
                            if(checkEnemyCollision(bulletNode)) {
                            	enemyHit.removeLife();
                            	if(enemyHit.getEnemyLives() == 0) {
                            		removeEnemy();
                            	}
                            }
                            return;
                        }
                    }
                }
            }
            bulletNode.setTranslateX(bulletNode.getTranslateX() + (movingRight ? 1 : -1));
        }
	}
	
	/**
	 * Checks whether two nodes have collided
	 * @param node1
	 * @param node2
	 * @return
	 */
	public boolean checkNodeNodeCollision(Node node1, Node node2) {
		return node1.getBoundsInParent().intersects(node2.getBoundsInParent());
	}
	
	/**
	 * Returns invisibility status of player
	 * @return
	 */
	public boolean getInvisible() {
		return invisible;
	}
	
	/**
	 * Returns gravity status
	 * @return
	 */
	public boolean getGravity() {
		return gravity;
	}
	
	/**
	 * Checks whether an enemy has collided with a specified node
	 * @param node
	 * @return
	 */
	private boolean checkEnemyCollision(Node node) {
		for(int i = 0; i < enemyNodeList.size(); i++) {
			if(node.getBoundsInParent().intersects(enemyNodeList.get(i).getBoundsInParent())) {
				enemyNodeHit = enemyNodeList.get(i);
				enemyHit = enemyList.get(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes the enemy that was hit from the game root
	 * and lists
	 */
	private void removeEnemy() {
		gameRoot.getChildren().remove(enemyNodeHit);
		enemyNodeList.remove(enemyNodeHit);
		enemyList.remove(enemyHit);
	}
    
	/**
	 * Checks whether the player has collided with an enemy
	 * and, if so, calls the player's death
	 */
    private void checkHitByEnemy() {
    	if(checkEnemyCollision(playerNode)) {
    		playerDeath();
    	}
    }

    /**
     * Sets the checkpoint coordinates to the current position
     * when the player hits a checkpoint block
     * @param coords
     * @param checkpoint
     */
    public void setCheckpoint(int[] coords, int checkpoint) {
    	checkpointCoordinates[0] = coords[0];
    	checkpointCoordinates[1] = coords[1];
    	checkpointPosition = checkpoint;
    }
    
    /**
     * Returns the game root
     * @return
     */
    public Pane getGameRoot() {
    	return gameRoot;
    }
    
    /**
     * If the player has lives, sends them back to the last checkpoint.
     * If lives are over, sends the player to the main menu
     */
    public void playerDeath() {
    	if(!godMode) {
    		if(leapOfFaith) {
        		for(int i = 0; i < PlatformLevelData.LEVEL.length; i++) {
        			PlatformLevelData.LEVEL[i] = PlatformLevelData.REVERT_LEVEL[i];
        		}
    			platformBlock.createPlatformLists(gameRoot);
    			leapOfFaith = false;
    		}
    		playerNode.setTranslateX(checkpointCoordinates[0]);
    		playerNode.setTranslateY(checkpointCoordinates[1]);
    		gravity = true;
    		gameRoot.setLayoutX(checkpointPosition);
    		lives--;
    		uiRoot.getChildren().remove(lives);
    		if(lives == 0) {
    			goToMainMenu();
    		}
    	}
    }
    
    /**
     * Goes to main menu and restarts the level
     */
    public void goToMainMenu() {
		for(int i = 0; i < PlatformLevelData.LEVEL.length; i++) {
			PlatformLevelData.LEVEL[i] = PlatformLevelData.REVERT_LEVEL[i];
		}
		platformBlock.createPlatformLists(gameRoot);
    	Main main = new Main();
		running = false;
		try {
			main.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * Checks whether a key is being pressed down
     * @param key
     * @return
     */
    public boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }
    
    /**
     * Sets a boolean indicating the boss has been killed
     */
    public void bossDeath() {
    	bossDead = true;
    }
    
    /**
     * The game loop that executes many commands that are
     * performed repeatedly
     */
    private void update() {
    	if(running) {
    		player.updateMovement();
    		enemy1.animateEasyEnemyFloor();
    		enemy1.animateMediumEnemy(mediumEnemyDirectionToggle);
    		enemy1.hardEnemyShoot(playerNode, 0);
    		enemy1.hardEnemyShoot(playerNode, 1);
    		if(!bossDead && leapOfFaith) {
    			enemy1.hardEnemyShoot(playerNode, 2);
    		}
    		checkShoot();
    		checkHitByEnemy();
    		leapOfFaith();
    		loadFlag();
    	}
    }    
    
    /**
     * Returns the UI root
     * @return
     */
    public Pane getUIRoot() {
    	return uiRoot;
    }
    
    /**
     * Loads the finish flag in the level when
     * the boss has been killed
     */
    private void loadFlag() {
    	if(bossDead && !flagLoaded) {
    		for(int i = 0; i < PlatformLevelData.LEVEL.length; i++) {
    			PlatformLevelData.LEVEL[i] = PlatformLevelData.FLAG[i];
    		}
			platformBlock.createPlatformLists(gameRoot);
			flagLoaded = true;
    	}
    }
    
    /**
     * Loads the final boss area when the player crosses
     * a certain point in the game
     */
    private void leapOfFaith() {
    	if(playerNode.getTranslateX() > Settings.BOSS_TRIGGER_X && !leapOfFaith) {
    		for(int i = 0; i < PlatformLevelData.LEVEL.length; i++) {
    			PlatformLevelData.LEVEL[i] = PlatformLevelData.LEAP[i];
    		}
			platformBlock.createPlatformLists(gameRoot);
			leapOfFaith = true;
    	}
    }
    
    /**
     * Finds an available slot for a message to be placed
     * @return
     */
    private int findSlot() {
		for(int i = 0; i < messageSlot.length; i++) {
			if(messageSlot[i] == 0) {
				return i;
			}
		}
		return -1;
    }
    
    /**
     * Styles text to match the theme of the game
     * @param message
     * @return
     */
    private Text styledText(String message) {
    	Text text = new Text(message);
		text.setFill(Color.WHITE);
		text.setTranslateY(Settings.CHEAT_MESSAGE_Y);
		text.setFont(Font.font(Settings.FONT, Settings.CHEAT_MESSAGE_SIZE));
		return text;
    }
    
    /**
     * Assigns a message to a slot and adds the message to 
     * the UI
     * @param cheatMode
     * @param cheatText
     * @param cheatSlot
     */
    private void assignSlot(boolean cheatMode, Text cheatText, int cheatSlot) {
    	cheatText.setTranslateX(Settings.CHEAT_MESSAGE_OFFSET - findSlot()*Settings.CHEAT_MESSAGE_SPACING);
    	if(cheatMode) {
			messageRoot.getChildren().add(cheatText);
			cheatSlot = findSlot();
			messageSlot[findSlot()] = 1;
		} else {
			messageRoot.getChildren().remove(cheatText);
			messageSlot[cheatSlot] = 0;
		}
    }
    
    /**
     * Initializes the game, the game loop, and timers
     * used for enemy motion
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
    	initializeContent();
    	
    	stage = primaryStage;
    	scene = new Scene(appRoot);
    	primaryStage.setTitle("ENDER'S GAME");
    	primaryStage.setScene(scene);
    	primaryStage.show();

		Text pausedText = styledText("PAUSED");
		Text godModeText = styledText("GOD MODE");
		Text invisibleText = styledText("INVISIBLE");
		
    	scene.setOnKeyPressed(event -> {
    		keys.put(event.getCode(), true);	
    		if(event.getCode() == KeyCode.P) {
    			running = !running;
    			assignSlot(!running, pausedText, pauseSlot);
    		}
    		if(event.getCode() == KeyCode.G) {
    			godMode = !godMode;
    			assignSlot(godMode, godModeText, godModeSlot);
    		}
    		if(event.getCode() == KeyCode.I) {
    			invisible = !invisible;
    			assignSlot(invisible, invisibleText, invisibleSlot);
    		}
    		if(event.getCode() == KeyCode.ESCAPE) {
    			goToMainMenu();
    		}
    		if(event.getCode() == KeyCode.SHIFT) {
    			if(player.canShift()) {
    				gravity = !gravity;
    				player.setCanShift(false);
    			}
    		}
    	});
    	scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        
    	KeyFrame frame = new KeyFrame(Duration.millis(Settings.MILLISECOND_DELAY),
    			e -> update());
    	Timeline animation = new Timeline();
    	animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
        
        Timer flyTimer = new Timer ();
        TimerTask flyTask = new TimerTask () {
        	@Override
        	public void run() {
        		mediumEnemyDirectionToggle = !mediumEnemyDirectionToggle ;
        	}
        };
        flyTimer.schedule(flyTask, 0l, Settings.MEDIUM_ENEMY_TOGGLE_TIME);
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}