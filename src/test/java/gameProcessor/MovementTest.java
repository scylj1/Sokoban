package gameProcessor;

import static org.junit.Assert.*;
import java.awt.Point;
import java.io.InputStream;
import org.junit.Test;
import javafx.scene.input.KeyCode;

public class MovementTest {

	@Test
	public void testGetMoves() {
		InputStream in = getClass().getClassLoader().getResourceAsStream("SampleGame.skb");
		Movement move = new Movement(in);
		assertEquals(0, move.GetMoves());
	}

	@Test
	public void testGetMovesCount() {
		InputStream in = getClass().getClassLoader().getResourceAsStream("SampleGame.skb");
		Movement move = new Movement(in);
		assertEquals(0, move.getMovesCount());
	}

	@Test
	public void testHandleKey() {
		InputStream in = getClass().getClassLoader().getResourceAsStream("SampleGame.skb");
		Movement move = new Movement(in);
		move.handleKey(KeyCode.LEFT);
		assertEquals(1, move.GetMoves());
	}

	@Test
	public void testMove() {
		InputStream in = getClass().getClassLoader().getResourceAsStream("SampleGame.skb");
		Movement move = new Movement(in);
		move.move(new Point(0, -1), KeyCode.LEFT);
		assertEquals(1, move.GetMoves());
	}
}
