package gameSettings;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameCompleteTest {

	@Test
	public void testComplete() {
		GameComplete game = GameComplete.getgame();
		assertEquals(false, game.isGameComplete());
		game.Complete();
		assertEquals(true, game.isGameComplete());
		
	}

	@Test
	public void testReset() {
		GameComplete game = GameComplete.getgame();
		assertEquals(false, game.isGameComplete());
		game.Complete();
		assertEquals(true, game.isGameComplete());
		game.reset();
		assertEquals(false, game.isGameComplete());
	}

	@Test
	public void testIsGameComplete() {
		GameComplete game = GameComplete.getgame();
		game.reset();
		assertEquals(false, game.isGameComplete());
	}

}
