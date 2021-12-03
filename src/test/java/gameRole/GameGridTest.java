package gameRole;

import static org.junit.Assert.*;
import java.awt.Point;
import org.junit.Test;

public class GameGridTest {

	@Test
	public void testGetRows() {
		GameGrid grid = new GameGrid(1,2);
		assertEquals(2, grid.GetRows());
	}

	@Test
	public void testGetColumns() {
		GameGrid grid = new GameGrid(1,2);
		assertEquals(1, grid.GetColumns());
	}

	@Test
	public void testTranslatePoint() {
		assertEquals(new Point(1,1), GameGrid.translatePoint(new Point(0,1),new Point(1,0)));
	}

	@Test
	public void testGetTargetFromSource() {
		GameGrid grid = new GameGrid(2,2);
		grid.putGameObjectAt(GameObject.CRATE, 1, 1);
		assertEquals(GameObject.CRATE, grid.getTargetFromSource(new Point(0,1),new Point(1,0)));
	}

	@Test
	public void testGetGameObjectAtIntInt() {
		GameGrid grid = new GameGrid(1,2);
		grid.putGameObjectAt(GameObject.CRATE, 0, 0);
		assertEquals(GameObject.CRATE, grid.getGameObjectAt(0, 0));
	}

	@Test
	public void testGetGameObjectAtPoint() {
		GameGrid grid = new GameGrid(1,2);
		grid.putGameObjectAt(GameObject.CRATE, 0, 0);
		assertEquals(GameObject.CRATE, grid.getGameObjectAt(new Point(0, 0)));
	}

	@Test
	public void testPutGameObjectAtGameObjectIntInt() {
		GameGrid grid = new GameGrid(1,2);		
		assertEquals(true, grid.putGameObjectAt(GameObject.CRATE, 0, 0));
	}

	@Test
	public void testPutGameObjectAtGameObjectPoint() {
		GameGrid grid = new GameGrid(1,2);		
		assertEquals(true, grid.putGameObjectAt(GameObject.CRATE, new Point(0, 0)));
	}

}
