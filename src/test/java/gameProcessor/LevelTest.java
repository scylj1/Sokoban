package gameProcessor;

import static org.junit.Assert.*;
import java.awt.Point;
import java.util.ArrayList;
import org.junit.Test;

import gameRole.GameObject;

public class LevelTest {

	@Test
	public void testGetName() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("WWWW");
		Level level = new Level("levelName", 1, list);
		assertEquals("levelName", level.getName());
	}

	@Test
	public void testGetIndex() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("WWWW");
		Level level = new Level("levelName", 1, list);
		assertEquals(1, level.getIndex());
	}
	
	@Test
	public void testGetKeeperPosition() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("WWWW");
		Level level = new Level("levelName", 1, list);
		assertEquals(new Point(0,0), level.getKeeperPosition());
	}
	
	@Test
	public void testleveltochar() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("WW");
		list.add("WW");
		Level level = new Level("levelName", 1, list);
		char c = 'W';
		assertEquals(c, level.leveltochar()[0][0]);
	}
	
	@Test
	public void testisComplete() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("WWWW");
		Level level = new Level("levelName", 1, list);
		assertEquals(true, level.isComplete());
	}
	
	@Test
	public void testgetTargetObject() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("WWWW");
		Level level = new Level("levelName", 1, list);
		assertEquals(GameObject.fromChar('W'), level.getTargetObject(new Point(0,0), new Point(0,0)));
	}
	
	@Test
	public void testgetObjectAt() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("WWWW");
		Level level = new Level("levelName", 1, list);
		assertEquals(GameObject.fromChar('W'), level.getObjectAt(new Point(0,0)));
	}

	@Test
	public void testmoveObjectBy() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("WC");
		list.add("CW");
		Level level = new Level("levelName", 1, list);
		level.moveGameObjectBy(level.getObjectAt(new Point(0,0)), new Point(0,0), new Point(0,1));
		assertEquals(GameObject.CRATE, level.getObjectAt(new Point(0,0)));
	}
	
	@Test
	public void testmoveObjectTo() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("WC");
		list.add("CW");
		Level level = new Level("levelName", 1, list);
		level.moveGameObjectTo(level.getObjectAt(new Point(0,0)), new Point(0,0), new Point(0,1));
		assertEquals(GameObject.CRATE, level.getObjectAt(new Point(0,0)));
		assertEquals(GameObject.WALL, level.getObjectAt(new Point(0,1)));
	}
	
	@Test
	public void testchangeKeeper() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("KC");
		list.add("CW");
		Level level = new Level("levelName", 1, list);
		level.changeKeeper(new Point(0,0), "LEFT");
		assertEquals(GameObject.KEEPER_LEFT, level.getObjectAt(new Point(0,0)));
	}
	
	@Test
	public void testiterator_hasNext() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("KC");
		list.add("CW");
		Level level = new Level("levelName", 1, list);
		assertEquals(true, level.iterator().hasNext());
		
	}
	
	@Test
	public void testiterator_next() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("KC");
		list.add("CW");
		Level level = new Level("levelName", 1, list);
		assertEquals(GameObject.WALL, level.iterator().next());	
	}
}

