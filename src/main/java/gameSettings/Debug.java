package gameSettings;

/** 
 * A singleton class that record if the {@code debug} mode is open.
 * @author Lekang Jiang
 */
public class Debug {

	private boolean m_debug;

	// Singleton
	private static Debug m_debugger = new Debug();

	private Debug() {
		this.m_debug = false;
	}

	/** 
	 * Getter for singleton class {@code debugger}.
	 * @return m_debugger
	 */
	public static Debug getdebug() {
		return m_debugger;
	}

	/** 
	 * Check if the {@code debug} mode is open.
	 */
	public boolean isDebugActive() {
		return m_debug;
	}

	/** 
	 * Switch the {@code debug} mode. 
	 */
	public void toggleDebug() {
		m_debug = !m_debug;
	}
}
