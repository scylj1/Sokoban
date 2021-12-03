package gameController;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import gameProcessor.Level;
import gameRole.GameObject;
import gameRole.GraphicObject;
import gameProcessor.GameModel;
import gameSettings.Debug;
import gameSettings.Dialog;
import gameSettings.GameComplete;
import gameSettings.GameRecord;
import gameView.GameView;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

/**
 * The controller for the game. Update data in the model {@code GameEngine}
 * and show view through {@code GameView}.
 * <p>Contains all the functions in the menu bar.
 * @author Lekang Jiang 
 */
public class GameController {
	
	private GameModel m_gameEngine;	
	private GameView m_gameView;
	private File m_saveFile;
	private GameComplete m_game = GameComplete.getgame();
	private Debug m_debug = Debug.getdebug();
	
	/**
     * Constructor of {@code GameView}
	 */
	public GameController(GameModel gameEngine, GameView gameView) {
		this.m_gameEngine = gameEngine;
		this.m_gameView = gameView;		
	}
	
	/**
     * Set the model {@code GameEngine}, and show the view
     * @param input 
     * @param filename 
	 */
	public void initializeGame(InputStream input, String filename) {
		m_game.reset();
		m_gameEngine.SetGameEngine(input, true, filename);
		reloadGrid();
	}

	/**
     * Set event handler for the game, when key is pressed, the keeper could move
	 */	
	public void setEventFilter() {
		m_gameView.GetStage().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			m_gameEngine.getMovement().handleKey(event.getCode());
			reloadGrid();
		});
	}

	/**
     * Load a new game, when the player choose a new map file
	 */	
	public void loadGameFile() throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Save File");
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("Sokoban save file", "*.skb"));
		m_saveFile = fileChooser.showOpenDialog(m_gameView.GetStage());

		if (m_saveFile != null) {
			if (m_debug.isDebugActive()) {
				GameModel.logger.info("Loading save file: " + m_saveFile.getName());
			}
			m_gameEngine.GetMusic().stopMusic();
			initializeGame(new FileInputStream(m_saveFile), m_saveFile.getName());
		}
	}

	/**
     * Update the data in {@code GameView} for it to show the view.
     * <P> If the game is complete {@code GameRecord} class is called to record the score.
	 */	
	private void reloadGrid() {
		if (m_game.isGameComplete()) {		
			new GameRecord(m_gameEngine.getMovement().getMapLoader().getMapSetName(), 
					m_gameEngine.getMovement().getMapLoader().getmaxlevel(), 
					m_gameEngine.getMovement().getMovesCount());						
			return;
		}

		Level currentLevel = m_gameEngine.getMovement().getCurrentLevel();
		Level.LevelIterator levelGridIterator = (Level.LevelIterator) currentLevel.iterator();		
		m_gameView.GetGameGrid().getChildren().clear();
		
		while (levelGridIterator.hasNext()) {
			addObjectToGrid(levelGridIterator.next(), levelGridIterator.getCurrentPosition());
		}
		m_gameView.GetGameGrid().autosize();		
		m_gameView.GetInformationGrid().getChildren().clear();
		
		String mapname = m_gameEngine.getMovement().getMapLoader().getMapSetName();
		String levelname = m_gameEngine.getMovement().getCurrentLevel().getName();
		int levelnumber = m_gameEngine.getMovement().getCurrentLevel().getIndex();
		int levelmoves = m_gameEngine.getMovement().GetMoves();
		int totalmoves = m_gameEngine.getMovement().getMovesCount();
		Label label1 = new Label("Map name: " +  mapname
				+ "\n\nCurrent level name: " + levelname
				+ "\n\nCurrent level number: " + levelnumber
				+ "\n\nCurrent level movements: " + levelmoves
				+ "\n\nTotal movements: " + totalmoves
				+ "\n\nInstructions: \nUse arrow keys to control the charactor     "
				+ "\nand move the crates."
				+ "\n\nGoal: \nMove all the crates to target position.");
		label1.setFont(new Font("Arial", 20));
		label1.setTranslateX(20);
		label1.setTranslateY(20);
		label1.setWrapText(true);

		m_gameView.GetInformationGrid().getChildren().addAll(label1);
		m_gameView.GetInformationGrid().autosize();
		m_gameView.GetStage().sizeToScene();
	}

	/**
     * Add a {@code GameObject} to the required location in <em>gameGrid</em>.
     * @param gameObject 
     * @param location 
	 */	
	private void addObjectToGrid(GameObject gameObject, Point location) {
		GraphicObject graphicObject = new GraphicObject(gameObject);
		m_gameView.GetGameGrid().add(graphicObject, location.y, location.x);

	}

	/**
     * Exit the game.
	 */	
	public void closeGame() {
		System.exit(0);
	}

	/**
     * Save the current game to a new map file.
	 */	
	public void saveGame() {
		m_gameEngine.getMovement().recordmoves();
		List<char[][]> list = m_gameEngine.getMovement().getMapLoader().levelstostring();
		try {
			String filename = "saved" + 
		        m_gameEngine.getMovement().getMapLoader().getMapSetName() + ".skb";
			File file = new File(System.getProperty("user.dir") 
					+ "/src/main/resources/" + filename);
			if (!(file.isFile() && file.exists())) {
				file.createNewFile();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(("MapSetName: " + 
			    m_gameEngine.getMovement().getMapLoader().getMapSetName() + "\n").getBytes());
			int levelindex = 0;
			for (char[][] array : list) {
				fileOutputStream.write(("LevelName: " 
			        + m_gameEngine.getMovement().GetLevels().get(levelindex).getName() 
			        + "\n").getBytes());
				for(int i = 0; i < array.length; i++){ 
			        for(int j = 0; j < array[i].length; j++){
				        fileOutputStream.write(array[i][j]);
			        }
			        fileOutputStream.write(("\n").getBytes());
				}
				fileOutputStream.write(("\n").getBytes());
				levelindex++;
			}
			fileOutputStream.close();

		} catch (Exception e) {
			System.out.println("Read file failed");
			e.printStackTrace();
		}
	}

	/**
     * Load a new map file.
	 */	
	public void loadGame() throws IOException {
		try {
			loadGameFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
     * Save current score.
	 */	
	public void saveScore() {
		new GameRecord(m_gameEngine.getMovement().getMapLoader().getMapSetName(), 
				m_gameEngine.getMovement().getCurrentLevel().getIndex()-1, 
				m_gameEngine.getMovement().getMovesCount());
	}

	/**
     * Move back to the last step.
	 */	
	public void undo() {
		m_gameEngine.getMovement().undo();
		reloadGrid();
	}
	
	/**
     * Move back to the beginning of current level.
	 */	
	public void resetLevel() {
		m_gameEngine.getMovement().resetLevel();
		reloadGrid();
	}

	/**
     * Show the information about this game.
	 */	
	public void showAbout() {
		String title = "About This Game";
		String message = "Enjoy the Game!";
		new Dialog(title, message, null);
	}

	/**
     * Play the music if it stops; stop the music if it is playing.
	 */	
	public void toggleMusic() {
		if (m_gameEngine.GetMusic().isPlayingMusic()) {
			m_gameEngine.GetMusic().stopMusic();
		} 
		else {
			m_gameEngine.GetMusic().playMusic();
		}
	}

	/**
     * Toggle for debug.
	 */	
	public void toggleDebug() {
		m_debug.toggleDebug();
		reloadGrid();
	}
}
