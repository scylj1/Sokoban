package gameView;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

import gameController.GameController;
import gameProcessor.GameModel;

/**
 * The visualisation part of the game. 
 * <p>Show the view of the game for user to play.
 * <p>Contains 3 part, menu item, game map and game information.
 *
 * @author Lekang Jiang
 */
public class GameView {

	private MenuBar m_MENU;
	private GridPane m_gameGrid;	// show the main map	
	private GridPane m_information;  // show information of the map and current level
	private GridPane m_root;  // contains MENU, gameGrid and information
	private Stage m_stage;

	/**
     * Constructor of {@code GameView}
     * @param Stage used to show the view
	 */
	public GameView(Stage stage) {
		this.m_stage = stage;
	}

	/**
     * Getter for {@code stage}
     * @return Stage
	 */
	public Stage GetStage() {
		return m_stage;
	}
	
	/**
     * Getter for {@code gameGrid}
     * @return GridPane
	 */
	public GridPane GetGameGrid() {
		return m_gameGrid;
	}
	
	/**
     * Getter for {@code information}
     * @return GridPane
	 */
	public GridPane GetInformationGrid() {
		return m_information;
	}
	
	/**
     * Initialise the menu bar and show current {@code stage}
     * @param GameController
	 */
	public void SetGameView(GameController control) {	
		m_MENU = new MenuBar();
		MenuItem menuItemSaveGame = new MenuItem("Save Game");
		menuItemSaveGame.setOnAction(actionEvent -> control.saveGame());
		MenuItem menuItemLoadGame = new MenuItem("Load Game");
		menuItemLoadGame.setOnAction(actionEvent -> {
			try {
				control.loadGame();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		MenuItem menuItemSaveScore = new MenuItem("Save Score");
		menuItemSaveScore.setOnAction(actionEvent -> control.saveScore());
		MenuItem menuItemExit = new MenuItem("Exit");
		menuItemExit.setOnAction(actionEvent -> control.closeGame());
		Menu menuFile = new Menu("File");
		menuFile.getItems().addAll(menuItemSaveGame, menuItemLoadGame, menuItemSaveScore, 
				new SeparatorMenuItem(), menuItemExit);
		MenuItem menuItemUndo = new MenuItem("Undo");
		menuItemUndo.setOnAction(actionEvent -> control.undo());
		RadioMenuItem radioMenuItemMusic = new RadioMenuItem("Toggle Music");
		radioMenuItemMusic.setOnAction(actionEvent -> control.toggleMusic());
		RadioMenuItem radioMenuItemDebug = new RadioMenuItem("Toggle Debug");
		radioMenuItemDebug.setOnAction(actionEvent -> control.toggleDebug());
		MenuItem menuItemResetLevel = new MenuItem("Reset Level");
		menuItemResetLevel.setOnAction(actionEvent -> control.resetLevel());		
		Menu menuLevel = new Menu("Level");		
		menuLevel.getItems().addAll(menuItemUndo, radioMenuItemMusic, radioMenuItemDebug, 
				new SeparatorMenuItem(), menuItemResetLevel);
		MenuItem menuItemGame = new MenuItem("About This Game");
		Menu menuAbout = new Menu("About");
		menuAbout.setOnAction(actionEvent -> control.showAbout());
		menuAbout.getItems().addAll(menuItemGame);
		m_MENU.getMenus().addAll(menuFile, menuLevel, menuAbout);
		
		m_gameGrid = new GridPane();
	    m_information = new GridPane();
		m_root = new GridPane();
		m_root.add(m_MENU, 0, 0);
		m_root.add(m_gameGrid, 0, 1);
		m_root.add(m_information, 1, 1);
		
		m_stage.setTitle(GameModel.GAME_NAME);
		m_stage.setScene(new Scene(m_root));
		m_stage.show();
	}
}
