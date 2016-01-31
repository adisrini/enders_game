import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

/**
 * This is the main program, it is basically boilerplate to create
 * the game.
 * 
 * It is dependent on Platformer and Puzzle to run.
 * 
 * It can be used by calling main.start(stage);
 * 
 * @author Aditya Srinivasan
 * @category CS308
 */
public class Main extends Application {
	
	private Stage stage;
	
	private static final int TITLE_X = 360;
	private static final int TITLE_Y = 250;
	
	private static final int MENU_X = 510;
	private static final int MENU_Y = 380;
	
	private static final int TITLE_WIDTH = 500;
	private static final int TITLE_HEIGHT = 80;
	private static final int TITLE_FONT_SIZE = 70;
	
	private static final int SEPARATOR_X = 200;
	
	private static final Color MENU_ITEM_HIGHLIGHT = Color.AZURE;
	private static final int MENU_ITEM_FONT_SIZE = 22;
	private static final int MENU_ITEM_WIDTH = 200;
	private static final int MENU_ITEM_HEIGHT = 30;
	
	/**
	 * Creates the main menu including titles, menus and handles mouse clicks
	 * @return
	 */
	private Parent mainMenu() {
		Pane root = new Pane();
		root.setPrefSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
		
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("main-bg.jpg"));
		ImageView imgV = new ImageView(image);
		imgV.setFitWidth(Settings.WINDOW_WIDTH);
		imgV.setFitHeight(Settings.WINDOW_HEIGHT);
		root.getChildren().add(imgV);
		
		Title title = new Title("E N D E R ' S   G A M E");
		title.setTranslateX(TITLE_X);
		title.setTranslateY(TITLE_Y);
		
		MenuItem play = new MenuItem("P L A Y");
		MenuItem exit = new MenuItem("E X I T");
		
		MenuBox menu = new MenuBox(
				play,
				exit);
		
		play.setOnMouseClicked(event -> {
			MenuItem destroyer = new MenuItem("D E S T R O Y E R");
			MenuItem strategist = new MenuItem("S T R A T E G I S T");
			MenuBox modes = new MenuBox(destroyer, strategist);
			modes.setTranslateX(MENU_X);
			modes.setTranslateY(MENU_Y);
			destroyer.setOnMouseClicked(e -> {
				Platformer platformer = new Platformer();
				try {
					platformer.start(stage);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
			strategist.setOnMouseClicked(e -> {
				Puzzle puzzle = new Puzzle();
				try {
					puzzle.start(stage);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			});
			root.getChildren().remove(menu);
			root.getChildren().add(modes);
		});
		
		exit.setOnMouseClicked(event -> System.exit(0));
		
		menu.setTranslateX(MENU_X);
		menu.setTranslateY(MENU_Y);
		
		root.getChildren().addAll(title, menu);
		
		return root;
	}
	
	/**
	 * 
	 * @return an instance of the main class
	 */
	public Main getMain() {
		return this;
	}

	/**
	 * Establishes the scene, title, and loads the main menu
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		Scene scene = new Scene(mainMenu());
		primaryStage.setTitle("ENDER'S GAME");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * This is a small class (too small to make into another Java file)
	 * that creates a title pane with text and a border
	 */
	private class Title extends StackPane {
		public Title(String name) {
			Rectangle bg = new Rectangle(TITLE_WIDTH, TITLE_HEIGHT);
			bg.setStroke(Color.WHITE);
			bg.setStrokeWidth(2);
			bg.setFill(null);
			
			Text text = new Text(name);
			text.setFill(Color.WHITE);
			text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, TITLE_FONT_SIZE));
			
			setAlignment(Pos.CENTER);
			getChildren().addAll(bg, text);
		}
	}
	
	/**
	 * This is a small class (too small to make into another Java file)
	 * that creates a menubox with menuitems
	 * 
	 */
	private class MenuBox extends VBox {
		public MenuBox(MenuItem... items) {
			getChildren().add(createSeparator());
			
			for(MenuItem item:items) {
				getChildren().addAll(item, createSeparator());
			}
		}
		
		private Line createSeparator() {
			Line sep = new Line();
			sep.setEndX(SEPARATOR_X);
			sep.setStroke(Color.DARKGREY);
			return sep;
		}
	}
	
	/**
	 * This is a small class that creates a menuitem
	 * with text and a decorative gradient as well as
	 * handling mouse hovers and clicks
	 */
	private class MenuItem extends StackPane {
		public MenuItem(String name) {
			LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
				new Stop(0, MENU_ITEM_HIGHLIGHT),
				new Stop(0.3, Color.BLACK),
				new Stop(0.7, Color.BLACK),
				new Stop(1, MENU_ITEM_HIGHLIGHT)
			});
			
			Rectangle bg = new Rectangle(MENU_ITEM_WIDTH, MENU_ITEM_HEIGHT);
			bg.setOpacity(0.4);
			
			Text text = new Text(name);
			text.setFill(Color.DARKGREY);
			text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, MENU_ITEM_FONT_SIZE));
			
			setAlignment(Pos.CENTER);
			getChildren().addAll(bg, text);
			
			setOnMouseEntered(event -> {
				bg.setFill(gradient);
				text.setFill(Color.WHITE);
			});
			
			setOnMouseExited(event -> {
				bg.setFill(Color.BLACK);
				text.setFill(Color.DARKGREY);
			});
			
			setOnMousePressed(event -> {
				bg.setFill(Color.ORANGERED);
			});
			
			setOnMouseReleased(event -> {
				bg.setFill(gradient);
			});
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
