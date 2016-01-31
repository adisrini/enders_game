import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * This class is the PuzzleTile class which
 * creates tiles for the user to add mirrors
 * or for there to be blocks. 
 * 
 * It is dependent on no other classes.
 * 
 * It can be used by calling PuzzleTile tile = new PuzzleTile(0, 0, 5, 1);
 * to instantiate a puzzle tile with the enemy image at the origin of
 * level 1.
 * 
 * @author Aditya Srinivasan
 *
 */
public class PuzzleTile extends StackPane {
	private int x;
	private int y;
	private int id;
	
	private int toggle = 0;
	
	private Image defaultImage;
	private Image mirror1Image;
	private Image mirror2Image;
	private Image wallImage;
	private Image shipImage;
	private Image enemyShipImage;
	
	/**
	 * Returns the tileID
	 * @return
	 */
	public int getTileId() {
		return id;
	}
	
	/**
	 * This is the constructor for the puzzle tile
	 * @param x - tile x-coordinate
	 * @param y - tile y-coordinate
	 * @param id - tile ID
	 * @param level
	 */
	public PuzzleTile(int x, int y, int id, int tileWidth) {
		this.x = x;
		this.y = y;
		this.id = id;
		
		ImageView imageView = new ImageView();
		
		switch(id) {
		case -1:
			return;
		case 0:
			defaultImage = new Image(Main.class.getResourceAsStream(Settings.TILE_DEFAULT_URL));
			mirror1Image = new Image(Main.class.getResourceAsStream(Settings.TILE_MIRROR_1_URL));
			mirror2Image = new Image(Main.class.getResourceAsStream(Settings.TILE_MIRROR_2_URL));
			
			imageView.setImage(defaultImage);
			
			setOnMouseClicked(event -> {
				toggleMirror();
				switch(toggle) {
					case 0:
						imageView.setImage(defaultImage);
						this.id = 0;
						break;
					case 1:
						imageView.setImage(mirror1Image);
						this.id = 1;
						break;
					case 2:
						imageView.setImage(mirror2Image);
						this.id = 2;
						break;
					default:
						break;
				}
			});			
			break;
		case 3:
			shipImage = new Image(Main.class.getResourceAsStream(Settings.TILE_SPACESHIP_URL));
			imageView.setImage(shipImage);
			
			break;
		case 4:
			wallImage = new Image(Main.class.getResourceAsStream(Settings.TILE_WALL_URL));
			imageView.setImage(wallImage);
			
			break;	
		case 5:
			enemyShipImage = new Image(Main.class.getResourceAsStream(Settings.TILE_ALIEN_URL));
			imageView.setImage(enemyShipImage);
			
		break;
		}
		
		imageView.setFitWidth(tileWidth);
		imageView.setFitHeight(tileWidth);
		
		setAlignment(Pos.CENTER);
		getChildren().addAll(imageView);
	}
	
	/**
	 * Toggles the mirror position
	 * @return
	 */
	private int toggleMirror() {
		toggle++;
		toggle = toggle % 3;
		return toggle;
	}
	
}