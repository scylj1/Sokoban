package gameRole;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameObjectTest {

	@Test
	public void testGetCharSymbol() {
		assertEquals('W', GameObject.fromChar('W').getCharSymbol());
	}

	@Test
	public void testFromChar() {
		assertEquals(GameObject.WALL, GameObject.fromChar('W'));
		assertEquals(GameObject.CRATE, GameObject.fromChar('C'));
	}

}
