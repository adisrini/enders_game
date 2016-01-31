import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;


/**
 * This is the class that handles all constants, colors,
 * fonts, and other things that can adjust the look and
 * feel of the game. Feel free to experiment with (some of)
 * the values to see how it affects gameplay! 
 * 
 * It is dependent on no other classes but almost
 * every other class is dependent on it.
 * 
 * It can be used by modifying an existing constant or
 * creating a new one to alter the gameplay.
 * 
 * @author Aditya Srinivasan
 *
 */
public class Settings {

	public static final int WINDOW_WIDTH = 1280;
	public static final int WINDOW_HEIGHT = 720;
	
	public static final String FONT = "Tw Cen MT Condensed";
	
	public static final KeyCode UP_KEY = KeyCode.UP;
	public static final KeyCode RIGHT_KEY = KeyCode.RIGHT;
	public static final KeyCode LEFT_KEY = KeyCode.LEFT;
	public static final KeyCode SHOOT_KEY = KeyCode.SPACE;
	public static final KeyCode PAUSE_KEY = KeyCode.P;
	public static final int BLOCK_WIDTH = 60;			// don't mess
	public static final int BLOCK_HEIGHT = 60;			// don't mess
	public static final double FRAMES_PER_SECOND = 60;
	public static final double MILLISECOND_DELAY = 1000/FRAMES_PER_SECOND;
	public static final int PLAYER_WIDTH = 28;
	public static final int PLAYER_HEIGHT = 40;
	public static final int EASY_ENEMY_WIDTH = 30;
	public static final int EASY_ENEMY_HEIGHT = 50;		// don't mess
	public static final int MEDIUM_ENEMY_WIDTH = 100;
	public static final int MEDIUM_ENEMY_HEIGHT = 100;
	public static final long MEDIUM_ENEMY_TOGGLE_TIME = 1000;
	public static final int HARD_ENEMY_WIDTH = 76;
	public static final int HARD_ENEMY_HEIGHT = 50;
	public static final int BOSS_WIDTH = 300;
	public static final int BOSS_HEIGHT = 300;

	public static final double BULLET_SIZE = 7;
	public static final double BULLET_SPEED = 10;
	public static final Color BULLET_PRIMARY_COLOR = Color.GRAY;
	public static final Color BULLET_SECONDARY_COLOR = Color.WHITE;
	public static final long BULLET_TIMEOUT = 2000;
	
	public static final int PLAYER_STARTING_X = 0;
	public static final int PLAYER_STARTING_Y = 300;
	public static final int PLAYER_LIVES = 3;
	
	public static final int SCREEN_OFFSET = 640;

	public static final double POPUP_WIDTH = 600;
	public static final double POPUP_HEIGHT = 300;
	public static final double POPUP_X = 320;
	public static final double POPUP_Y = 150;
	public static final double POPUP_TITLE_SIZE = 70;
	public static final double POPUP_BODY_SIZE = 40;
	public static final double POPUP_BUTTON_WIDTH = 200;
	public static final double POPUP_BUTTON_HEIGHT = 50;
	public static final double POPUP_TITLE_Y = -80;
	public static final double POPUP_BUTTON_Y = 80;

	public static final int ENEMY_1_STARTING_X = 1500;
	public static final int ENEMY_1_STARTING_Y = 605;
	public static final int ENEMY_2_STARTING_X = 2860;
	public static final int ENEMY_2_STARTING_Y = 305;
	public static final int ENEMY_3_STARTING_X = 3300;
	public static final int ENEMY_3_STARTING_Y = 300;
	public static final int ENEMY_4_STARTING_X = 3700;
	public static final int ENEMY_4_STARTING_Y = 400;
	public static final int ENEMY_5_STARTING_X = 4150;
	public static final int ENEMY_5_STARTING_Y = 120;
	public static final int ENEMY_6_STARTING_X = 4400;
	public static final int ENEMY_6_STARTING_Y = 120;
	public static final int BOSS_STARTING_X = 6200;
	public static final int BOSS_STARTING_Y = 370;
	
	public static final int HEART_WIDTH = 40;
	public static final int HEART_HEIGHT = 40;
	public static final int HEART_OFFSET = 10;
	public static final int HEART_SPACING = 50;
	public static final int HEART_Y = 10;
	public static final String HEART_URL = "heart.png";
	public static final int CHEAT_MESSAGE_SPACING = 200;
	public static final int CHEAT_MESSAGE_OFFSET = 1100;
	public static final int CHEAT_MESSAGE_Y = 40;
	public static final double CHEAT_MESSAGE_SIZE = 30;
	
	public static final String PLAYER_URL = "player.png";
	public static final String EASY_ENEMY_URL = "easy_bug.png";
	public static final String MEDIUM_ENEMY_URL = "medium_bug.png";
	public static final String HARD_ENEMY_URL = "hard_bug.png";
	public static final String BOSS_URL = "boss.png";
	public static final Color BLOCK_COLOR = Color.DODGERBLUE;
	public static final Color CHECKPOINT_BLOCK_COLOR = Color.WHITE;
	public static final Color DEATH_BLOCK_COLOR = Color.BLACK;
	public static final Color FINISH_BLOCK_COLOR = Color.GREEN;
	public static final int EASY_ENEMY_LIVES = 1;
	public static final int MEDIUM_ENEMY_LIVES = 2;
	public static final int HARD_ENEMY_LIVES = 3;
	public static final int BOSS_LIVES = 15;
	public static final int HARD_ENEMY_EASINESS = 50;		// scale of 10 (hardest) to 70 (easiest)
	public static final double BOSS_TRIGGER_X = 5200;		// don't mess
	public static final long ENEMY_BULLET_TIMEOUT = 1500;
	
	public static final int PUZZLE_LEVEL1_TILE_WIDTH = 100;
	
	public static final int PUZZLE_LEVEL2_TILE_WIDTH = 70;
	
	public static final int PUZZLE_LEVEL3_TILE_WIDTH = 40;
	
	public static final int PUZZLE_LEVEL4_TILE_WIDTH = 50;
	
	public static final double PUZZLE_BULLET_SPEED = 100;
	public static final String TILE_DEFAULT_URL = "tile-default.png";
	public static final String TILE_MIRROR_1_URL = "tile-backslash.png";
	public static final String TILE_MIRROR_2_URL = "tile-forwardslash.png";
	public static final String TILE_SPACESHIP_URL = "spaceship.png";
	public static final String TILE_ALIEN_URL = "alien.png";
	public static final String TILE_WALL_URL = "brick.png";
	
}
