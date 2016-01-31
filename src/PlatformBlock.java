import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This is the PlatformBlock class which creates the
 * blocks that the platform player walks on.
 * It is responsible for constructing the blocks and
 * populating a couple of ArrayLists with information
 * about the blocks.
 * 
 * It is dependent on no other classes.
 * 
 * It can be used by calling platformBlock.createPlatformLists(root);
 * to populate the root with blocks specified by the level data.
 * 
 * @author Aditya Srinivasan
 *
 */
public class PlatformBlock {
	
	private int blockX;
	private int blockY;
	private int blockWidth;
	private int blockHeight;
	private Color blockColor;
	private ArrayList<Node> platforms = new ArrayList<Node>();
	private ArrayList<String> platformTypes = new ArrayList<String>();
	
	/**
	 * Initializes a PlatformBlock with only width and height information
	 * 
	 * @param w - block width
	 * @param h - block height
	 */
	public PlatformBlock(int w, int h) {
		blockWidth = w;
		blockHeight = h;
	}
	
	/**
	 * Initializes a PlatformBlock with multiple parameters
	 * 
	 * @param x - position in the x-axis
	 * @param y - position in the y-axis
	 * @param w - block width
	 * @param h - block height
	 * @param color - block color
	 * @param type - type of block (normal/death/checkpoint/end)
	 */
	public PlatformBlock(int x, int y, int w, int h, Color color, String type) {
		blockX = x;
		blockY = y;
		blockWidth = w;
		blockHeight = h;
		blockColor = color;
	}
	
	/**
	 * Returns the height of a block
	 * @return
	 */
	public int getHeight() {
		return blockHeight;
	}
	
	/**
	 * Returns the width of a block
	 * @return
	 */
	public int getWidth() {
		return blockWidth;
	}
	
	/**
	 * Returns the populated list of PlatformBlocks
	 * @return
	 */
	public ArrayList<Node> getPlatformList() {
		return platforms;
	}
	
	/**
	 * Returns the populated list of PlatformBlock types
	 * @return
	 */
	public ArrayList<String> getPlatformTypeList() {
		return platformTypes;
	}
	
	/**
	 * Creates a Node based on the parameters of a PlatformBlock
	 * 
	 * @param platformBlock - the PlatformBlock to use as reference
	 * @param root - the root to add the block to
	 * @return
	 */
	public Node createBlockEntity(PlatformBlock platformBlock, Pane root) {
        Rectangle entity = new Rectangle(platformBlock.blockWidth, platformBlock.blockHeight);
        entity.setTranslateX(platformBlock.blockX);
        entity.setTranslateY(platformBlock.blockY);
        entity.setFill(platformBlock.blockColor);

        root.getChildren().add(entity);
        return entity;
    }
	
	/**
	 * Initializes a block and adds it to the required lists
	 * 
	 * @param i - x-coordinate of block
	 * @param j - y-coordinate of block
	 * @param blockColor - color of block
	 * @param platformType - type of block
	 * @param root - the Pane to which the block is added
	 */
	private void createAndAddBlockToList(int i, int j, Color blockColor, String platformType, Pane root) {
		PlatformBlock block = new PlatformBlock(j*Settings.BLOCK_WIDTH, i*Settings.BLOCK_HEIGHT, Settings.BLOCK_WIDTH, Settings.BLOCK_HEIGHT, blockColor, platformType);
			Node blockNode = createBlockEntity(block, root);
			platforms.add(blockNode);
			platformTypes.add(platformType);
	}
	
	/**
	 * Populates the platform and platformType lists
	 * with PlatformBlocks
	 * 
	 * @param root - the Pane to which the blocks are added
	 */
	public void createPlatformLists(Pane root) {
		platforms.clear();
		platformTypes.clear();
		for (int i = 0; i < PlatformLevelData.LEVEL.length; i++) {
			String line = PlatformLevelData.LEVEL[i];
			for (int j = 0; j < line.length(); j++) {
				switch (line.charAt(j)) {
               		case '0':
               			break;
               		case '1':
               			createAndAddBlockToList(i, j, Settings.BLOCK_COLOR, "platform", root);
               			break;
               		case 'c':
               			createAndAddBlockToList(i, j, Settings.CHECKPOINT_BLOCK_COLOR, "checkpoint", root);
               			break;
               		case 'x':
               			createAndAddBlockToList(i, j, Settings.DEATH_BLOCK_COLOR, "death", root);
               			break;
               		case '!':
               			createAndAddBlockToList(i, j, Settings.FINISH_BLOCK_COLOR, "finish", root);
               			break;
				}
			}
		}
	}
}
