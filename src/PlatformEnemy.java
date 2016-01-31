import java.util.ArrayList;
import java.util.Arrays;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This is a class to construct a PlatformEnemy.
 * It includes functionality to move enemies
 * and shoot bullets from enemies
 * 
 * It is dependent on Platformer.java and PlatformBlock.java
 * 
 * It can be used by calling PlatformEnemy enemy = new PlatformEnemy(0, 0, 100, 100, "enemy.jpg", 3);
 * to create and return a node of a 100x100 enemy at the origin with image "enemy.jpg" and 3 lives.
 * 
 * @author Aditya Srinivasan
 *
 */
public class PlatformEnemy {

	private int enemyX;
	private int enemyY;
	private int enemyWidth;
	private int enemyHeight;
	private String enemyURL;
	private int enemyLives;
	
	private Platformer platformer;
	private PlatformBlock platformBlock;
	private Node playerNode;
	
	Bullet bullet = new Bullet();
	
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>(3);
	private ArrayList<Integer> easiness = new ArrayList<Integer>(3);
	private ArrayList<Node> bulletNodes = new ArrayList<Node>(3);
	private ArrayList<Boolean> canShoot = new ArrayList<Boolean>(3);
	private ArrayList<Long> timesShot = new ArrayList<Long>(3);
	private ArrayList<Point2D> bulletVelocities = new ArrayList<Point2D>(3);
	
	
	/**
	 * Populate the ArrayLists of information regarding enemies and bullets
	 */
	public void establishEnemyBullets() {
		bullets.addAll(Arrays.asList(null, null, null));
		easiness.addAll(Arrays.asList(Settings.HARD_ENEMY_EASINESS, Settings.HARD_ENEMY_EASINESS, Settings.HARD_ENEMY_EASINESS));
		bulletNodes.addAll(Arrays.asList(null, null, null));
		canShoot.addAll(Arrays.asList(true, true, true));
		timesShot.addAll(Arrays.asList(null, null, null));
		bulletVelocities.addAll(Arrays.asList(new Point2D(0, 0), new Point2D(0, 0), new Point2D(0, 0)));
	}
	
	/**
	 * The constructor for a PlatformEnemy
	 * @param x - the X position of the enemy
	 * @param y - the Y position of the enemy
	 * @param w - the width of the enemy
	 * @param h - the height of the enemy
	 * @param url - the image URL of the enemy
	 * @param lives - the number of lives of the enemy
	 */
	public PlatformEnemy(int x, int y, int w, int h, String url, int lives) {
		enemyX = x;
		enemyY = y;
		enemyWidth = w;
		enemyHeight = h;
		enemyURL = url;
		enemyLives = lives;
	}
	
	/**
	 * Returns the number of lives of an enemy
	 * 
	 * @return
	 */
	public int getEnemyLives() {
		return enemyLives;
	}
	
	/**
	 * Creates instances of other classes
	 * 
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
	 * Returns a node based on an Enemy object
	 * 
	 * @param enemy - the object to help construct the node
	 * @return
	 */
	public Node getEnemyAsNode(PlatformEnemy enemy) {
		Image img = new Image(getClass().getClassLoader().getResourceAsStream(enemyURL));
    	ImageView imgV = new ImageView(img);
    	imgV.setFitWidth(enemyWidth);
    	imgV.setFitHeight(enemyHeight);
    	imgV.setTranslateX(enemyX);
    	imgV.setTranslateY(enemyY);
		return imgV;
	}
	
	
	/**
	 * Animates the easy floor enemies right and left along the x-direction
	 */
	public void animateEasyEnemyFloor() {
    	for(Node enemy : platformer.getEasyEnemyFloorList().keySet()) {
    		if(platformer.getEasyEnemyFloorList().get(enemy)) {
    			moveEasyEnemyFloorX(enemy, 1);
    		} else {
    			moveEasyEnemyFloorX(enemy, -1);
    		}
    	}
	}
	
	/**
	 * Moves an enemy a certain value in a given direction along the x-axis
	 * @param enemy - the node to move
	 * @param value - the amount to move and direction (as given by algebraic sign)
	 */
	public void moveEasyEnemyFloorX(Node enemy, int value) {
    	boolean movingRight = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
            for(int j = 0; j < platformBlock.getPlatformList().size(); j++) {
                if(platformer.checkNodeNodeCollision(enemy, platformBlock.getPlatformList().get(j))) {
                    if (movingRight) {
                        if(enemy.getTranslateX() + enemyWidth == platformBlock.getPlatformList().get(j).getTranslateX()) {
                        	platformer.setEasyEnemyFloorList(enemy, false);
                            return;
                        }
                    }
                    else {
                        if (enemy.getTranslateX() == platformBlock.getPlatformList().get(j).getTranslateX() + platformBlock.getWidth()) {
                        	platformer.setEasyEnemyFloorList(enemy, true);
                            return;
                        }
                    }
                }
            }
            enemy.setTranslateX(enemy.getTranslateX() + (movingRight ? 1 : -1));
        }
    }
	
	/**
	 * Animates the medium flying enemies up and down along the y-direction
	 * @param toggle - the direction in which to travel (true = up, false = down)
	 */
	public void animateMediumEnemy(boolean toggle) {
    	for(Node enemy : platformer.getMediumEnemyList()) {
    		moveMediumEnemyY(enemy, 2, toggle);
    	}
    }
	
	/**
	 * Moves an enemy a certain amount in a given direction
	 * @param enemy - the enemy to move
	 * @param value - the amount to move
	 * @param toggle - the direction to move in
	 */
	public void moveMediumEnemyY(Node enemy, int value, boolean toggle) {
        for (int i = 0; i < Math.abs(value); i++) {
            enemy.setTranslateY(enemy.getTranslateY() + (toggle ? 1 : -1));
        }
    }
	
	/**
	 * Shoots a bullet from a certain enemy to a certain player
	 * @param player - the node to aim at
	 * @param index - index of enemy to shoot from
	 */
	public void hardEnemyShoot(Node player, int index) {
		bullet.enemyShoot(platformer.getHardEnemyList().get(index), index, canShoot, bulletVelocities, bulletNodes, bullets, timesShot, platformer);
		if(platformer.getHardEnemyList().size() != 0) {
			bullet.moveEnemyMissile((int)playerEnemyDistance(platformer.getHardEnemyList().get(index), player)[0]/easiness.get(index), (int)playerEnemyDistance(platformer.getHardEnemyList().get(0), player)[1]/easiness.get(index), index, canShoot, bulletVelocities, bullets, bulletNodes, platformer, platformBlock);
		}
	}
	
	/**
	 * Reinitializes the boss missiles when it is killed
	 */
	public void bossDeath() {
		bullet.reinitializeEnemyMissile(2, canShoot, bulletVelocities, bulletNodes, platformer);
	}
	
	/**
	 * Returns the x and y distances between two nodes
	 * @param enemy - the enemy node
	 * @param player - the player node
	 * @return
	 */
	private double[] playerEnemyDistance(Node enemy, Node player) {
		double dx = enemy.getTranslateX() - player.getTranslateX();
		double dy = enemy.getTranslateY() - player.getTranslateY();
		return new double[] {dx, dy};
	}
	
	/**
	 * Removes a life from an enemy
	 */
	public void removeLife() {
		enemyLives--;		
	}
	
}
