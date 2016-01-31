import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * This is a class that creates a popup
 * with information about level completion or failure.
 * 
 * It is dependent on Platformer.java and Puzzle.java
 * 
 * It can be used by calling Popup popup = new Popup("title", "body", "continue", puzzle);
 * and then the createPopup(); method in Puzzle.java to create a popup.
 * 
 * @author Aditya Srinivasan
 *
 */
public class Popup extends StackPane {
	
	/**
	 * This is a constructor for a popup for
	 * the Platformer.
	 * 
	 * @param title - header of the popup
	 * @param body - body text of the popup
	 * @param platformer
	 */
	public Popup(String title, String body, Platformer platformer) {
		
		Rectangle bg = new Rectangle(Settings.POPUP_WIDTH, Settings.POPUP_HEIGHT);
		bg.setFill(Color.BLACK);
		bg.setStroke(Color.WHITE);
		bg.setOpacity(0.7);
		
		Text titleText = new Text(title);
		titleText.setFill(Color.WHITE);
		titleText.setFont(Font.font(Settings.FONT, FontWeight.SEMI_BOLD, Settings.POPUP_TITLE_SIZE));
		
		Text bodyText = new Text(body);
		bodyText.setFill(Color.WHITE);
		bodyText.setFont(Font.font(Settings.FONT, FontWeight.SEMI_BOLD, Settings.POPUP_BODY_SIZE));
		
		Rectangle button = new Rectangle(Settings.POPUP_BUTTON_WIDTH, Settings.POPUP_BUTTON_HEIGHT);
		Text buttonText = new Text();
		
		buttonText.setText("MAIN MENU");
		button.setFill(Color.CADETBLUE);
		button.setOnMouseClicked(e -> {
			platformer.goToMainMenu();
		});
		
		buttonText.setFont(Font.font(Settings.FONT, FontWeight.SEMI_BOLD, Settings.POPUP_BODY_SIZE));
		buttonText.setFill(Color.WHITE);
		
		setAlignment(Pos.CENTER);
		
		titleText.setTranslateY(Settings.POPUP_TITLE_Y);
		button.setTranslateY(Settings.POPUP_BUTTON_Y);
		buttonText.setTranslateY(Settings.POPUP_BUTTON_Y);
		
		setTranslateX(Settings.POPUP_X);
		setTranslateY(Settings.POPUP_Y);
		
		getChildren().addAll(bg, titleText, bodyText, button, buttonText);
	}
	
	/**
	 * This is the constructor for a popup for the
	 * Puzzle.
	 * @param title - header of the popup
	 * @param body - body text of the popup
	 * @param status - kind of popup (continue/game over/main menu)
	 * @param puzzle
	 */
	public Popup(String title, String body, String status, Puzzle puzzle) {
		
		Rectangle bg = new Rectangle(Settings.POPUP_WIDTH, Settings.POPUP_HEIGHT);
		bg.setFill(Color.BLACK);
		bg.setStroke(Color.WHITE);
		bg.setOpacity(0.7);
		
		Text titleText = new Text(title);
		titleText.setFill(Color.WHITE);
		titleText.setFont(Font.font(Settings.FONT, FontWeight.SEMI_BOLD, Settings.POPUP_TITLE_SIZE));
		
		Text bodyText = new Text(body);
		bodyText.setFill(Color.WHITE);
		bodyText.setFont(Font.font(Settings.FONT, FontWeight.SEMI_BOLD, Settings.POPUP_BODY_SIZE));
		
		Rectangle button = new Rectangle(Settings.POPUP_BUTTON_WIDTH, Settings.POPUP_BUTTON_HEIGHT);
		Text buttonText = new Text();
		
		switch(status) {
			case "continue":
				buttonText.setText("GO!");
				button.setFill(Color.GREEN);
				button.setOnMouseClicked(e -> {
					puzzle.setLevelCleared(true);
				});
				break;
			case "main menu":
				buttonText.setText("MAIN MENU");
				button.setFill(Color.CADETBLUE);
				button.setOnMouseClicked(e -> {
					Main main = new Main();
					try {
						main.start(puzzle.getStage());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				});
		}
		
		buttonText.setFont(Font.font(Settings.FONT, FontWeight.SEMI_BOLD, Settings.POPUP_BODY_SIZE));
		buttonText.setFill(Color.WHITE);
		
		setAlignment(Pos.CENTER);
		
		titleText.setTranslateY(Settings.POPUP_TITLE_Y);
		button.setTranslateY(Settings.POPUP_BUTTON_Y);
		buttonText.setTranslateY(Settings.POPUP_BUTTON_Y);
		
		setTranslateX(Settings.POPUP_X);
		setTranslateY(Settings.POPUP_Y);
		
		getChildren().addAll(bg, titleText, bodyText, button, buttonText);
	}
	
}
