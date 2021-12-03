package gameProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to read from the map file and convert it to {@code Level}
 * @author Lekang Jiang
 */
public class Mapload {
	private List<Level> m_levels = new ArrayList<>(5);
	private String m_mapSetName; 
	private int m_maxlevel;

	/**
	 * Getter for {@code levels}
	 * @return m_levels 
	 *
	 */
	public List<Level> getLevels(){
		return m_levels;
	}
	
	/**
	 * Getter for {@code mapSetName}
	 * @return m_mapSetName
	 */
	public String getMapSetName() {
		return m_mapSetName;
	}

	/**
	 * Getter for {@code maxlevel}
	 * @return m_maxlevel
	 */
	public int getmaxlevel() {
		return m_maxlevel;
	}

	/**
	 * Convert the map file to a list of {@code Level}
	 * @param input 
	 * @return List 
	 */
	public List<Level> loadGameFile(InputStream input) {

		int levelIndex = 0;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
			boolean parsedFirstLevel = false;
			List<String> rawLevel = new ArrayList<>();
			String levelName = "";

			while (true) {
				String line = reader.readLine();

				// Break the loop if EOF is reached
				if (line == null) {
					if (rawLevel.size() != 0) {
						Level parsedLevel = new Level(levelName, ++levelIndex, rawLevel);
						m_levels.add(parsedLevel);
					}
					m_maxlevel = levelIndex;
					break;
				}

				if (line.contains("MapSetName")) {
					m_mapSetName = line.replace("MapSetName: ", "");
					continue;
				}

				if (line.contains("LevelName")) {
					if (parsedFirstLevel) {
						Level parsedLevel = new Level(levelName, ++levelIndex, rawLevel);
						m_levels.add(parsedLevel);
						rawLevel.clear();
					} else {
						parsedFirstLevel = true;
					}

					levelName = line.replace("LevelName: ", "");
					continue;
				}

				line = line.trim();
				line = line.toUpperCase();
				// If the line contains at least 2 WALLS, add it to the list
				if (line.matches(".*W.*W.*")) {
					rawLevel.add(line);
				}
			}

		} catch (IOException e) {
			GameModel.logger.severe("Error trying to load the game file: " + e);
		} catch (NullPointerException e) {
			GameModel.logger.severe("Cannot open the requested file: " + e);
		}

		return m_levels;
	}

	/**
	 * Convert {@code levels} to char array
	 * @return List 
	 */
	public List<char[][]> levelstostring() {
		List<char[][]> list = new ArrayList<char[][]>();
		for (Level l : m_levels) {
			list.add(l.leveltochar());
		}
		return list;
	}
}
