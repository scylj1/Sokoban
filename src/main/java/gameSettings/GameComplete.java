package gameSettings;

/** A singleton class that record if the game is complete.
 * @author Lekang Jiang
 */
public class GameComplete {

	private boolean m_gameComplete;

	// Singleton
	private static GameComplete m_game = new GameComplete();

	private GameComplete() {
		this.m_gameComplete = false;
	}

	/** 
	 * Getter for singleton class {@code game}
	 */
	public static GameComplete getgame() {
		return m_game;
	}

	/** 
	 * Change class variable to true
	 */
	public void Complete() {
		this.m_gameComplete = true;
	}
	
	/** 
	 * Change class variable to false
	 */
	public void reset() {
		this.m_gameComplete = false;
	}

	/** 
	 * Return if the game is complete.
	 * @return boolean
	 */
	public boolean isGameComplete() {
		return m_gameComplete;
	}
}
