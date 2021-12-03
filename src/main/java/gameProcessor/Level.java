package gameProcessor;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

import gameRole.GameGrid;
import gameRole.GameObject;
import gameSettings.*;

/**
 * Change the list of string to list of {@code GameGrid}
 * @author Lekang Jiang - Modified 
 */
public final class Level implements Iterable<GameObject> {

    private final String m_name;
    private final GameGrid m_objectsGrid;
    private final GameGrid m_diamondsGrid;
    private final int m_index;
    private int m_numberOfDiamonds = 0;
    private Point m_keeperPosition = new Point(0, 0);
    private Debug m_debug = Debug.getdebug();
    
    // For each raw level, change string to objectsGrid, diamondsGrid, keeperPosition 
    public Level(String levelName, int levelIndex, List<String> raw_level) {
        if (m_debug.isDebugActive()) {
            System.out.printf("[ADDING LEVEL] LEVEL [%d]: %s\n", levelIndex, levelName);
        }

        m_name = levelName;
        m_index = levelIndex;

        int rows = raw_level.size();
        int columns = raw_level.get(0).trim().length();

        m_objectsGrid = new GameGrid(rows, columns);
        m_diamondsGrid = new GameGrid(rows, columns);

        for (int row = 0; row < raw_level.size(); row++) {

            // Loop over the string one char at a time because it should be the fastest way:
            // http://stackoverflow.com/questions/8894258/fastest-way-to-iterate-over-all-the-chars-in-a-string
            for (int col = 0; col < raw_level.get(row).length(); col++) {
                // The game object is null when the we're adding a floor or a diamond
                GameObject curTile = GameObject.fromChar(raw_level.get(row).charAt(col));

                if (curTile == GameObject.DIAMOND) {
                    m_numberOfDiamonds++;
                    m_diamondsGrid.putGameObjectAt(curTile, row, col);
                    curTile = GameObject.FLOOR;
                } else if (curTile == GameObject.KEEPER) {
                    m_keeperPosition = new Point(row, col);
                }

                m_objectsGrid.putGameObjectAt(curTile, row, col);
                curTile = null;
            }
        }
    }

    /**
     * Getter for {@code name}
     * @return m_name 
     */
    public String getName() {
        return m_name;
    }

    /**
     * Getter for {@code index}
     * @return m_index 
     */
    public int getIndex() {
        return m_index;
    }
  
    /**
     * Getter for {@code keeperPosition}
     * @return m_keeperPosition 
     */
    public Point getKeeperPosition() {
        return m_keeperPosition;
    }
    
    /**
     * Convert levels back to char array.
     * @return array 
     */
    public char[][] leveltochar() {
    	char[][] array = new char[m_objectsGrid.GetRows()][m_objectsGrid.GetColumns()];
    	for (int i = 0; i < m_objectsGrid.GetRows(); i++) {
    		for (int j = 0; j < m_objectsGrid.GetColumns(); j++) {
    			if (m_objectsGrid.getGameObjectAt(i, j) == GameObject.CRATE) {
    				array[i][j] = 'C';
    			}
    			else if (m_objectsGrid.getGameObjectAt(i, j) == GameObject.WALL) {
    				array[i][j] = 'W';
    			}
    			else if (m_objectsGrid.getGameObjectAt(i, j) == GameObject.FLOOR) {
    				array[i][j] = ' ';
    			}
    			else {
    				array[i][j] = 'S';
    			}   			
    		}
    	}
    	for (int i = 0; i < m_diamondsGrid.GetRows(); i++) {
    		for (int j = 0; j < m_diamondsGrid.GetColumns(); j++) {
    			if (m_diamondsGrid.getGameObjectAt(i, j) == GameObject.DIAMOND) {
    				if (m_objectsGrid.getGameObjectAt(i, j) == GameObject.CRATE) {
    				    array[i][j] = 'O';
    				}
    				else {
    					array[i][j] = 'D';
    				}
    			}  			
    		}
    	}
    	return array;
    }
   
    /**
     * Check if current level is complete.
     * @return boolean
     */
    public boolean isComplete() {
        int cratedDiamondsCount = 0;
        for (int row = 0; row < m_objectsGrid.GetRows(); row++) {
            for (int col = 0; col < m_objectsGrid.GetColumns(); col++) {
                if (m_objectsGrid.getGameObjectAt(col, row) == GameObject.CRATE && m_diamondsGrid.getGameObjectAt(col, row) == GameObject.DIAMOND) {
                    cratedDiamondsCount++;
                }
            }
        }

        return cratedDiamondsCount >= m_numberOfDiamonds;
    }

    /**
     * Get the {@code GameObject} at a point with input value of source point and delta.
     * @param source 
     * @param delta 
     * @return GameObject 
     */
    public GameObject getTargetObject(Point source, Point delta) {
        return m_objectsGrid.getTargetFromSource(source, delta);
    }

    /**
     * Get the {@code GameObject} and a given point.
     * @param p 
     * @return GameObject 
     */
    public GameObject getObjectAt(Point p) {
        return m_objectsGrid.getGameObjectAt(p);
    }

    /**
     * Move the {@code GameObject} to a new point.
     * 
     * @param object 
     * @param source 
     * @param delta 
     */
    public void moveGameObjectBy(GameObject object, Point source, Point delta) {
        moveGameObjectTo(object, source, GameGrid.translatePoint(source, delta));
    }

    /**
     * Move the {@code GameObject} at destination to source, and source is move to destination
     */
    public void moveGameObjectTo(GameObject object, Point source, Point destination) {
        m_objectsGrid.putGameObjectAt(getObjectAt(destination), source);
        m_objectsGrid.putGameObjectAt(object, destination);
    }
    
    /**
     * Change the keeper's direction based on the key code.
     * 
     * @param keeper 
     * @param code 
     */
    public void changeKeeper(Point keeper, String code) {
    	switch (code) {
		case "UP":
			m_objectsGrid.putGameObjectAt(GameObject.KEEPER_UP, keeper);
			break;

		case "RIGHT":
			m_objectsGrid.putGameObjectAt(GameObject.KEEPER_RIGHT, keeper);
			break;

		case "DOWN":
			m_objectsGrid.putGameObjectAt(GameObject.KEEPER_DOWN, keeper);
			break;

		case "LEFT":
			m_objectsGrid.putGameObjectAt(GameObject.KEEPER_LEFT, keeper);
			break;
		default:
			m_objectsGrid.putGameObjectAt(GameObject.KEEPER_DOWN, keeper);
			break;
    	}
    }  

    /**
     * Get a inner class {@code LevelIterator}
     */
    @Override
    public Iterator<GameObject> iterator() {
        return new LevelIterator();
    }

    /**
     * Inner class implements {@code Iterator}.
     * Used to walk through all the {@code GameObject} in {@code GameGrid}.
     */
    public class LevelIterator implements Iterator<GameObject> {

        int column = 0;
        int row = 0;

        /**
         * Check if {@code GameGrid} has next {@code GameObject}
         */
        @Override
        public boolean hasNext() {
            return !(row == m_objectsGrid.GetRows() - 1 && column == m_objectsGrid.GetColumns());
        }

        /**
         * Get next {@code GameObject} in {@code GameGrid}.
         */
        @Override
        public GameObject next() {
            if (column >= m_objectsGrid.GetColumns()) {
                column = 0;
                row++;
            }

            GameObject object = m_objectsGrid.getGameObjectAt(column, row);
            GameObject diamond = m_diamondsGrid.getGameObjectAt(column, row);
            GameObject retObj = object;

            column++;

            if (diamond == GameObject.DIAMOND) {
                if (object == GameObject.CRATE) {
                    retObj = GameObject.CRATE_ON_DIAMOND;
                } else if (object == GameObject.FLOOR) {
                    retObj = diamond;
                } else {
                    retObj = object;
                }
            }

            return retObj;
        }

        /**
         * Get current position as a {@code Point}
         */
        public Point getCurrentPosition() {
            return new Point(column, row);
        }
    }
}