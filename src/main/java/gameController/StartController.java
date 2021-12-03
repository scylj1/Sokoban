package gameController;

import java.io.InputStream;

import gameProcessor.GameModel;
import gameRole.WallColor;
import gameView.GameView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioMenuItem;
import javafx.stage.Stage;

/**
 * The controller for the start screen. 
 * <p>Contains set wall color function and start the game function.
 * 
 * @author Lekang Jiang
 *
 */
public class StartController {
	@FXML
	private RadioMenuItem black, brown, grey, khaki;

	@FXML
	private Button start;

    /**
     * Get the wall color choice from player and
	 * initialise {@code WallColor} class for {@code GameObject}
	 */
	public void setWallColor(ActionEvent event) {
		RadioMenuItem item = (RadioMenuItem) event.getSource();
		String id = item.getId();
		WallColor wallcolor = WallColor.getwallcolor();
		wallcolor.setWallColor(id);
	}

	/**
     * When the player click <em>start</em>, MVC pattern is used for the game.
	 * <p>Initialise {@code GameView} for view,
	 * {@code GameEngine} for model,
	 * {@code GameController} for controller.
	 * <p> Load a default game for user to play.
	 */
	public void startGame(ActionEvent event) throws Exception {
		Stage stage = (Stage) start.getScene().getWindow();
		GameView gameView = new GameView(stage);
		GameModel gameEngine = new GameModel();
		GameController control = new GameController(gameEngine, gameView);
		gameView.SetGameView(control);		
		InputStream in = getClass().getClassLoader().getResourceAsStream("SampleGame.skb");
		control.initializeGame(in, "SampleGame.skb");
		control.setEventFilter();
	}

}
