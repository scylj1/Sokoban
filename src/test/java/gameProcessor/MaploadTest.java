package gameProcessor;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import org.junit.Test;

public class MaploadTest {

	@Test
	public void testGetLevels() {
		Mapload load = new Mapload();
		assertEquals(new ArrayList<>(), load.getLevels());
	}

	@Test
	public void testGetMapSetName() {
		Mapload load = new Mapload();
		InputStream in = getClass().getClassLoader().getResourceAsStream("SampleGame.skb");
		load.loadGameFile(in);
		assertEquals("SampleGame", load.getMapSetName());
	}

	@Test
	public void testGetmaxlevel() {
		Mapload load = new Mapload();
		InputStream in = getClass().getClassLoader().getResourceAsStream("SampleGame.skb");
		load.loadGameFile(in);
		assertEquals(5, load.getmaxlevel());
	}

}
