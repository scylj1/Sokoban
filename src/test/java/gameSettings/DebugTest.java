package gameSettings;

import static org.junit.Assert.*;

import org.junit.Test;

public class DebugTest {

	@Test
	public void testIsDebugActive() {
		Debug debug = Debug.getdebug();
		assertEquals(false, debug.isDebugActive());
	}

	@Test
	public void testToggleDebug() {
		Debug debug = Debug.getdebug();
		assertEquals(false, debug.isDebugActive());
		debug.toggleDebug();
		assertEquals(true, debug.isDebugActive());
	}

}
