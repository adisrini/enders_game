import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.RadialGradientBuilder;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;

/**
 * A class for creating the bullet shot by enemies
 * and players and executing other related functions
 * like moving and reinitializing the bullet.
 * 
 * Depends on Platformer.java and PlatformBlock.java
 * 
 * It can be used by calling Bullet bullet = new Bullet(5, Color.GRAY);
 * to create an instance.
 * 
 * @author Aditya Srinivasan
 *
 */
@SuppressWarnings("deprecation")
public class Bullet {
	
	private Circle sphere;
	
	public Bullet() {
		// TODO Auto-generated constructor stub
	}
 
	/**
	 * The constructor for a bullet.
	 * 
	 * @param radius - the radius of the bullet
	 * @param fill - the color of the bullet highlight
	 */
    public Bullet(double radius, Color fill) {
        sphere = CircleBuilder.create()
                .centerX(radius)
                .centerY(radius)
                .radius(radius)
                .cache(true)
                .build();
 
        RadialGradient rgrad = RadialGradientBuilder.create()
                    .centerX(sphere.getCenterX() - sphere.getRadius() / 3)
                    .centerY(sphere.getCenterY() - sphere.getRadius() / 3)
                    .radius(sphere.getRadius())
                    .proportional(false)
                    .stops(new Stop(0.0, fill), new Stop(1.0, Settings.BULLET_PRIMARY_COLOR))
                    .build();
 
        sphere.setFill(rgrad);
    }
    
    /**
     * Create a bullet node
     * 
     * @return a bullet node
     */

	public Node getBulletAsNode() {
    	return sphere;
    }
    
	public void reinitializeEnemyMissile(int index, ArrayList<Boolean> canShoot, ArrayList<Point2D> velocities, ArrayList<Node> bullets, Platformer platformer) {
		canShoot.set(index, true);
		velocities.set(index, new Point2D(0, 0));
		platformer.getGameRoot().getChildren().remove(bullets.get(index));
	}
	
	/**
	 * Triggers the shooting action of an enemy and reinitializes bullet after timeout
	 * 
	 * @param enemy - enemy for which to start shooting
	 * @param index - index of enemy
	 * @param canShoot - list of booleans indicating whether enemy can shoot
	 * @param bulletVelocities - list of Point2D indicating bullet velocities
	 * @param bullets - list of bullets
	 * @param bulletNodes - list of bullet nodes
	 * @param platformer - instance of Platformer class
	 */
	public void enemyShoot(Node enemy, int index, ArrayList<Boolean> canShoot, ArrayList<Point2D> bulletVelocities, ArrayList<Node> bulletNodes, ArrayList<Bullet> bullets, ArrayList<Long> timesShot, Platformer platformer) {
    	if(canShoot.get(index)) {
    		bullets.set(index, new Bullet(Settings.BULLET_SIZE, Settings.BULLET_SECONDARY_COLOR));
    		bulletNodes.set(index, bullets.get(index).getBulletAsNode());
    		bulletNodes.get(index).setTranslateX(enemy.getTranslateX());
    		bulletNodes.get(index).setTranslateY(enemy.getTranslateY());
    		platformer.getGameRoot().getChildren().add(bulletNodes.get(index));
    		bulletVelocities.set(index, bulletVelocities.get(index).add(Settings.BULLET_SPEED, 0));
			timesShot.set(index, System.currentTimeMillis());
    		canShoot.set(index, false);
    	}
    	if(System.currentTimeMillis() - timesShot.get(index) > Settings.ENEMY_BULLET_TIMEOUT) {
    		reinitializeEnemyMissile(index, canShoot, bulletVelocities, bulletNodes, platformer);
    	}
    }
	
	/**
	 * Moves the missile of a specified enemy in the specified x and y directions
	 * 
	 * @param x - number of pixels to move bullet in x-direction
	 * @param y - number of pixels to move bullet in y-direction
	 * @param index - index of enemy to shoot from
	 * @param canShoot - list of booleans indicating whether enemy can shoot
	 * @param bulletVelocities - list of Point2D indicating bullet velocities
	 * @param bullets - list of bullets
	 * @param bulletNodes - list of bullet nodes
	 * @param platformer - instance of Platformer class
	 * @param platformBlock - instance of PlatformBlock class
	 */
	public void moveEnemyMissile(int x, int y, int index,
								  ArrayList<Boolean> canShoot,
								  ArrayList<Point2D> bulletVelocities,
								  ArrayList<Bullet> bullets, ArrayList<Node> bulletNodes,
								  Platformer platformer, PlatformBlock platformBlock) {
		
        for(int i = 0; i < Math.abs(x); i++) {
            bulletNodes.get(index).setTranslateX(bulletNodes.get(index).getTranslateX() + (index == 2 ? -2 : 1));
        }
        for(int i = 0; i < Math.abs(y); i++) {
        	bulletNodes.get(index).setTranslateY(bulletNodes.get(index).getTranslateY() + 1);
        }
        
    	if(platformer.checkNodeNodeCollision(bullets.get(index).getBulletAsNode(), platformer.getPlayerNode())) {
    		platformer.playerDeath();
    	}
    	for(Node platformNode : platformBlock.getPlatformList()) {
    		if(platformer.checkNodeNodeCollision(bullets.get(index).getBulletAsNode(), platformNode)) {
    			reinitializeEnemyMissile(index, canShoot, bulletVelocities, bulletNodes, platformer);
    		}
    	}
	}
	
}