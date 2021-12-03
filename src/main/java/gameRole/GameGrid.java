package gameRole;

import java.awt.*;
import gameSettings.Debug;

/**
 * Used to contain {@code GameObject}, and show the map
 * @author Lekang Jiang - Modified 
 */
public class GameGrid  {

    private final int m_COLUMNS;
    private final int m_ROWS;
    private GameObject[][] m_gameObjects;
    private Debug m_debug = Debug.getdebug();
    
    public GameGrid(int columns, int rows) {
        m_COLUMNS = columns;
        m_ROWS = rows;
        m_gameObjects = new GameObject[m_COLUMNS][m_ROWS];
    }
    
    /**
     * Getter for {@code ROWS}
     * @return m_ROWS
     */
    public int GetRows() {
    	return m_ROWS;
    }
    
    /**
     * Getter for {@code COLUMNS}
     * @return m_COLUMNS
     */
    public int GetColumns() {
    	return m_COLUMNS;
    }

    /**
     * Take 2 {@code Point}, return a {@code Point} that is the sum of 2 points
     * @param sourceLocation
     * @param delta 
     */
    public static Point translatePoint(Point sourceLocation, Point delta) {
        Point translatedPoint = new Point(sourceLocation);
        translatedPoint.translate((int) delta.getX(), (int) delta.getY());
        return translatedPoint;
    }   

    /**
     * Return the {@code GameObject} at position of the sum of 2 points
     * @param source
     * @param delta 
     * @return GameObject 
     */
    public GameObject getTargetFromSource(Point source, Point delta) {
        return getGameObjectAt(translatePoint(source, delta));
    }

    /**
     * Return the {@code GameObject} on index of (col, row)
     * @param col
     * @param row 
     * @return GameObject 
     */
    public GameObject getGameObjectAt(int col, int row) throws ArrayIndexOutOfBoundsException {
        if (isPointOutOfBounds(col, row)) {
            if (m_debug.isDebugActive()) {
                System.out.printf("Trying to get null GameObject from COL: %d  ROW: %d", col, row);
            }
            throw new ArrayIndexOutOfBoundsException("The point [" + col + ":" + row + "] is outside the map.");
        }

        return m_gameObjects[col][row];
    }

    /**
     * Return the {@code GameObject} at a point
     * @return GameObject 
     */
    public GameObject getGameObjectAt(Point p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null.");
        }

        return getGameObjectAt((int) p.getX(), (int) p.getY());
    }

    /**
     * Put {@code GameObject} at a given point (x, y)
     * @param x
     * @param y
     * @param gameObject 
     * @return boolean  
     */
    public boolean putGameObjectAt(GameObject gameObject, int x, int y) {
        if (isPointOutOfBounds(x, y)) {
            return false;
        }

        m_gameObjects[x][y] = gameObject;
        return m_gameObjects[x][y] == gameObject;
    }

    /**
     * Put {@code GameObject} at a given {@code Point}
     * @param gameObject 
     * @param p 
     * @return boolean
     */
    public boolean putGameObjectAt(GameObject gameObject, Point p) {
        return p != null && putGameObjectAt(gameObject, (int) p.getX(), (int) p.getY());
    }

    /**
     * Judge if {@code Point} is out of bound.
     * @param x 
     * @param y
     * @return boolean
     *
     */
    private boolean isPointOutOfBounds(int x, int y) {
        return (x < 0 || y < 0 || x >= m_COLUMNS || y >= m_ROWS);
    }
     
}