import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is used to construct a PlatformPlayer.
 * The class includes specifying parameters, and
 * player movement.
 * 
 * It is dependent on Platformer.java and PlatformBlock.java
 * 
 * It can be used by calling PlatformPlayer player = new PlatformPlayer(0, 0, 50, 50, "player.png")
 * to create a 50x50 player at the origin with image "player.png"
 * 
 * @author Aditya Srinivasan
 *
 */
public class PlatformPlayer {
	
	private int playerX;
	private int playerY;
	private int playerWidth;
	private int playerHeight;
	private String playerURL;
	public Point2D playerVelocity = new Point2D(0, 0);
	
	private boolean facingRight = true;
	private boolean canJump = true;
	private boolean canShift = true;
	
	private Platformer platformer;
	private PlatformBlock platformBlock;

	private Node playerNode;
	private int enemyLives;
	
	/**
	 * This is a constructor for the player.
	 * @param x - starting x-position
	 * @param y - starting y-position
	 * @param w - player width
	 * @param h - player height
	 * @param url - image URL
	 */
	public PlatformPlayer(int x, int y, int w, int h, String url) {
		playerX = x;
		playerY = y;
		playerWidth = w;
		playerHeight = h;
		playerURL = url;
	}
	
	/**
	 * Sets instances of other classes to be used for inter-class
	 * communication
	 * @param playerNode
	 * @param platformBlock
	 * @param platformer
	 */
	public void setInstances(Node playerNode, PlatformBlock platformBlock, Platformer platformer) {
		this.playerNode = playerNode;
		this.platformBlock = platformBlock;
		this.platformer = platformer;
	}
	
	/**
	 * Returns player height
	 * @return
	 */
	public int getHeight() {
		return playerHeight;
	}
	
	/**
	 * Returns player width
	 * @return
	 */
	public int getWidth() {
		return playerWidth;
	}
	
	/**
	 * Returns a node based on player parameters
	 * @param player
	 * @return
	 */
	public Node getPlayerAsNode(PlatformPlayer player) {
		Image img = new Image(getClass().getClassLoader().getResourceAsStream(playerURL));
    	ImageView imgV = new ImageView(img);
    	imgV.setFitWidth(playerWidth);
    	imgV.setFitHeight(playerHeight);
    	imgV.setTranslateX(playerX);
    	imgV.setTranslateY(playerY);
		return imgV;
	}
	
	/**
	 * Returns which direction the player is facing
	 * (true = right, false = left)
	 * @return
	 */
	public boolean getFacingRight() {
		return facingRight;
	}
	
	/**
	 * Updates the movement of the player given which
	 * key is being pressed
	 */
	public void updateMovement() {
        if(platformer.isPressed(Settings.UP_KEY) && playerNode.getTranslateY() >= 5) {
            jumpPlayer();
        }

        if(platformer.isPressed(Settings.LEFT_KEY) && playerNode.getTranslateX() >= 5) {
            movePlayerX(-5);
            facingRight = false;
        }

        if(platformer.isPressed(Settings.RIGHT_KEY) && playerNode.getTranslateX() + 40 <=  platformer.getLevelWidth() - 5) {
            movePlayerX(5);
            facingRight = true;
        }
        
        if(playerVelocity.getY() < 10) {					// controls gravity
        	if(platformer.getGravity()) {
        		playerVelocity = playerVelocity.add(0, 1);		// y controls jump height
        	}
        }
        if(playerVelocity.getY() > -10) {
        	if(!platformer.getGravity()) {
        		playerVelocity = playerVelocity.add(0, -1);
        	}
        }

        movePlayerY((int)playerVelocity.getY());
        
    }
	
	/**
	 * Moves the player a certain amount in the x-direction
	 * in a given direction (based on algebraic sign). Also
	 * checks for collisions with platforms.
	 * @param value
	 */
	public void movePlayerX(int value) {
        boolean movingRight = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
        	if(!platformer.getInvisible()) {
        		for(int j = 0; j < platformBlock.getPlatformList().size(); j++) {
        			if(platformer.checkNodeNodeCollision(playerNode, platformBlock.getPlatformList().get(j))) {
        				if(movingRight) {
        					if(playerNode.getTranslateX() + playerWidth == platformBlock.getPlatformList().get(j).getTranslateX() && platformer.getGravity()) {
        						return;
        					}
        				}
        				else {
        					if (playerNode.getTranslateX() == platformBlock.getPlatformList().get(j).getTranslateX() + platformBlock.getWidth() && platformer.getGravity()) {
        						return;
        					}
        				}
        			}
        		}
        	}
            playerNode.setTranslateX(playerNode.getTranslateX() + (movingRight ? 1 : -1));
        }
    }
	
	/**
	 * Moves the player a specified amount in the y-direction.
	 * Also checks for collisions with platform blocks.
	 * Handles events in case of landing on certain blocks
	 * @param value
	 */
	public void movePlayerY(int value) {
        boolean movingDown = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
            for(int j = 0; j < platformBlock.getPlatformList().size(); j++) {
                if(platformer.checkNodeNodeCollision(playerNode, platformBlock.getPlatformList().get(j))) {
                    if(movingDown) {
                        if (playerNode.getTranslateY() + playerHeight == platformBlock.getPlatformList().get(j).getTranslateY()) {
                        	handleBlockContact(j);
                            playerNode.setTranslateY(playerNode.getTranslateY() - 1);
                            canJump = true;
                            canShift = true;
                            return;
                        }
                    }
                    else {
                        if (playerNode.getTranslateY() == platformBlock.getPlatformList().get(j).getTranslateY() + platformBlock.getHeight()) {
                        	if(!platformer.getGravity() && platformBlock.getPlatformTypeList().get(j).equals("death")) {
                        		platformer.playerDeath();
                        	}
                        	canJump = true;
                        	canShift = true;
                            return;
                        }
                    }
                }
            }
            playerNode.setTranslateY(playerNode.getTranslateY() + (movingDown ? 1 : -1));
        }
    }

	/**
	 * Causes the player to jump vertically
	 */
	public void jumpPlayer() {
        if(canJump) {
        	playerVelocity = playerVelocity.add(0, (platformer.getGravity() ? -29 : 29));
            canJump = false;
        }
    }
	
	/**
	 * Handles the different cases of landing on blocks.
	 * Example: kills the player if he lands on a death block
	 * @param index
	 */
	private void handleBlockContact(int index) {
		if(platformBlock.getPlatformTypeList().get(index).equals("death")) {
    		platformer.playerDeath();
    	}
    	if(platformBlock.getPlatformTypeList().get(index).equals("checkpoint")) {
    		platformer.setCheckpoint(new int[] {(int) playerNode.getTranslateX(), (int) playerNode.getTranslateY()}, (int) platformer.getGameRoot().getLayoutX());
    	}
    	if(platformBlock.getPlatformTypeList().get(index).equals("finish")) {
    		Popup popup = new Popup("Congratulations!", "You finished! Play as Ender now!", platformer);
    		platformer.getUIRoot().getChildren().add(popup);
    		platformer.pause();
    	}
	}
	
	/**
	 * Sets whether the player can shift gravity
	 * @param canShift
	 */
	public void setCanShift(boolean canShift) {
		this.canShift = canShift;
	}
	
	/**
	 * Gets whether the player can shift gravity
	 * @return
	 */
	public boolean canShift() {
		return canShift;
	}
	

}
