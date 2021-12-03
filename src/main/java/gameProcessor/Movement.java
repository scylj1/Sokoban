package gameProcessor;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import gameRole.GameGrid;
import gameRole.GameObject;
import gameSettings.Debug;
import gameSettings.Dialog;
import gameSettings.GameComplete;
import gameSettings.GameRecord;
import javafx.scene.input.KeyCode;

/**
 * Control the movement of the keeper and crates
 * @author Lekang Jiang 
 */
public class Movement {

	private List<Level> m_levels;
	private Level m_currentLevel;
	private GameComplete m_game = GameComplete.getgame();
	private Debug m_debug = Debug.getdebug();
	private List<String> m_lists = new ArrayList<String>();  // contains the past moves of keeper	
	private List<String> m_crate = new ArrayList<String>();  // contains boolean value of if the crate moves
	private Mapload m_loader = new Mapload();
	private int m_movesCount = 0; 
	private int m_totalmovesCount = 0;
	private File m_file;  // save current moves and levels

	public Movement(InputStream input) {
		m_levels = m_loader.loadGameFile(input);
		m_currentLevel = getNextLevel();
	}

	/**
	 * Getter for {@code currentLevel}
	 * @return m_currentlevel 
	 */
	public Level getCurrentLevel() {
		return m_currentLevel;
	}
	
	/**
	 * Getter for {@code movesCount}
	 * @return m_movesCount
	 */
	public int GetMoves() {
		return m_movesCount;
	}

	/**
	 * Getter for {@code totalmovesCount}
	 * @return m_totalmovesCount
	 */
	public int getMovesCount() {
		return m_totalmovesCount;
	}

	/**
	 * Getter for {@code levels}
	 * @return m_levels
	 */
	public List<Level> GetLevels(){
		return m_levels;
	}

	/**
	 * Getter for {@code loader}
	 * @return m_loader 
	 */
	public Mapload getMapLoader() {
		return m_loader;
	}

	/**
	 * Get next level. If current level is the last level, return {@code null}.
	 * @return level 
	 */
	public Level getNextLevel() {
		if (m_currentLevel == null) {
			return m_levels.get(0);
		}

		int currentLevelIndex = m_currentLevel.getIndex();
		if (currentLevelIndex < m_levels.size()) {
			return m_levels.get(currentLevelIndex);
		}

		m_game.Complete();
		return null;
	}

	/**
	 * Decide which direction should the keeper go back to, and call {@code back} function
	 * to move back a step.
	 *
	 */
	public void undo() {
		
		if (m_lists.size() > 0) {
			String code = m_lists.get(m_lists.size() - 1);
			String code1 = "";
			if (m_lists.size() > 1) {
				code1 = m_lists.get(m_lists.size() - 2);
			}
			m_lists.remove(m_lists.size() - 1);
			String bool = m_crate.get(m_crate.size() - 1);
			m_crate.remove(m_crate.size() - 1);
			switch (code) {
			case "UP":
				back(new Point(1, 0), bool, code1);
				break;

			case "RIGHT":
				back(new Point(0, -1), bool, code1);
				break;

			case "DOWN":
				back(new Point(-1, 0), bool, code1);
				break;

			case "LEFT":
				back(new Point(0, 1), bool, code1);
				break;

			default:
				break;
			}

			if (m_debug.isDebugActive()) {
				System.out.println(code);
			}
		}
	}

	/**
	 * Move back one step of the game.
	 * @param delta
	 * @param bool 
	 * @param code 
	 */
	public void back(Point delta, String bool, String code) {

		Point keeperPosition = m_currentLevel.getKeeperPosition();
		GameObject keeper = m_currentLevel.getObjectAt(keeperPosition);

		if (m_debug.isDebugActive()) {
			System.out.println("Current level state:");
			System.out.println(m_currentLevel.toString());
			System.out.println("Keeper pos: " + keeperPosition);
		}

		m_currentLevel.moveGameObjectBy(keeper, keeperPosition, delta);

		if (bool.equalsIgnoreCase("true")) {
			Point back = new Point((int) -delta.getX(), (int) -delta.getY());
			Point cratePoint = GameGrid.translatePoint(keeperPosition, back);
			GameObject crateTarget = m_currentLevel.getObjectAt(cratePoint);
			m_currentLevel.moveGameObjectBy(crateTarget, cratePoint, delta);
		}

		keeperPosition.translate((int) delta.getX(), (int) delta.getY());
		m_currentLevel.changeKeeper(keeperPosition, code);
		m_movesCount--;
		m_totalmovesCount--;
	}

	
	/**
	 * Reset current level.
	 *
	 */
	public void resetLevel() {
		while (m_lists.size() > 0) {
			undo();
		}
	}

	/**
	 * Use {@code KeCode} to decide which direction would the keeper move to.
	 * @param code 
	 */
	public void handleKey(KeyCode code) {

		switch (code) {
		case UP:
			move(new Point(-1, 0), code);
			break;

		case RIGHT:
			move(new Point(0, 1), code);
			break;

		case DOWN:
			move(new Point(1, 0), code);
			break;

		case LEFT:
			move(new Point(0, -1), code);
			break;

		default:
			new Dialog("Prompt", "Please use arrow key", null);
		}
		
		if (m_debug.isDebugActive()) {
			System.out.println(code);
		}
	}

	/**
	 * Keeper move one step.
	 * @param delta 
	 * @param code 
	 */
	public void move(Point delta, KeyCode code) {
		if (m_game.isGameComplete()) {
			return;
		}

		Point keeperPosition = m_currentLevel.getKeeperPosition();
		GameObject keeper = m_currentLevel.getObjectAt(keeperPosition);
		Point targetObjectPoint = GameGrid.translatePoint(keeperPosition, delta);
		GameObject keeperTarget = m_currentLevel.getObjectAt(targetObjectPoint);

		if (m_debug.isDebugActive()) {
			System.out.println("Current level state:");
			System.out.println(m_currentLevel.toString());
			System.out.println("Keeper pos: " + keeperPosition);
			System.out.println("Movement source obj: " + keeper);
			System.out.printf("Target object: %s at [%s]", keeperTarget, targetObjectPoint);
		}

		boolean keeperMoved = false;

		switch (keeperTarget) {

		case WALL:
			break;

		case CRATE:

			GameObject crateTarget = m_currentLevel.getTargetObject(targetObjectPoint, delta);
			if (crateTarget != GameObject.FLOOR) {
				break;
			}

			m_crate.add(String.valueOf(true));
			m_currentLevel.moveGameObjectBy(keeperTarget, targetObjectPoint, delta);
			m_currentLevel.moveGameObjectBy(keeper, keeperPosition, delta);
			keeperMoved = true;
			break;

		case FLOOR:
			m_currentLevel.moveGameObjectBy(keeper, keeperPosition, delta);
			keeperMoved = true;
			m_crate.add(String.valueOf(false));
			break;

		default:
			GameModel.logger.severe("The object to be moved was not a recognised GameObject.");
			throw new AssertionError("This should not have happened. Report this problem to the developer.");
		}

		if (keeperMoved) {
			m_lists.add(String.valueOf(code));
			m_currentLevel.changeKeeper(targetObjectPoint, String.valueOf(code));
			keeperPosition.translate((int) delta.getX(), (int) delta.getY());
			m_movesCount++;
			m_totalmovesCount++;
			if (m_currentLevel.isComplete()) {
				if (m_debug.isDebugActive()) {
					System.out.println("Level complete!");
				}
				m_currentLevel = getNextLevel();
				if (m_game.isGameComplete() == false) {
					GameRecord gamerecord = new GameRecord(m_loader.getMapSetName(), m_currentLevel.getIndex() - 1,
							m_totalmovesCount);
					gamerecord.show();
					m_lists.clear();
					m_crate.clear();
					m_movesCount = 0;
				}
			}
		}		
	}

	/**
	 * Record current level's movements to a file.
	 *
	 */
	public void recordmoves() {
		try {
			String filename = "saved" + m_loader.getMapSetName() + ".txt";
			m_file = new File(System.getProperty("user.dir") + "/src/main/resources/undo/" + filename);
			if (!(m_file.isFile() && m_file.exists())) {
				m_file.createNewFile();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(m_file);

			String content1 = String.valueOf(m_currentLevel.getIndex()) + "\n" + String.valueOf(m_movesCount) + "\n"
					+ String.valueOf(m_totalmovesCount) + "\n";

			fileOutputStream.write(content1.getBytes());
			for (String k : m_lists) {
				String keycode = k + "\n";
				fileOutputStream.write(keycode.getBytes());
			}
			for (String b : m_crate) {
				String keycode = b + "\n";
				fileOutputStream.write(keycode.getBytes());
			}

			fileOutputStream.close();

		} catch (Exception e) {
			System.out.println("Read file failed");
			e.printStackTrace();
		}
	}
	
	/**
	 * Load past game's level movements. 
	 * @param savedfile 
	 */
	public void loadmoves(String savedfile) {
		try {
			savedfile = savedfile.substring(0, savedfile.length()-4) + ".txt";
			File undofile = new File(System.getProperty("user.dir") + "/src/main/resources/undo/" + savedfile);
			if (undofile.isFile() && undofile.exists()) {
				FileInputStream input = new FileInputStream(undofile);
				InputStreamReader read = new InputStreamReader(input);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				int undolevel = Integer.parseInt(bufferedReader.readLine());
				m_currentLevel = m_loader.getLevels().get(undolevel-1);
				m_movesCount= Integer.parseInt(bufferedReader.readLine());
				m_totalmovesCount= Integer.parseInt(bufferedReader.readLine());
				m_crate.clear();
				m_lists.clear();
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (lineTxt.equalsIgnoreCase("false") || lineTxt.equalsIgnoreCase("true")) {
					    m_crate.add(lineTxt);
					}
					else {
						m_lists.add(lineTxt);
					}
				}
				read.close();
			} else {
				return;
			}
		} catch (Exception e) {
			System.out.println("Read file failed");
			e.printStackTrace();
		}
	}

}
