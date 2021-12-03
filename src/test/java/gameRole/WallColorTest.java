package gameRole;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;

public class WallColorTest {

	@Test
	public void testgetwall() {
		WallColor wallcolor = WallColor.getwallcolor();
		File file = new File(getClass().getClassLoader().getResource("black_wall.png").getFile());
		assertEquals(file, wallcolor.getwall());
	}
	
	@Test
	public void testsetWallColor() {
		WallColor wallcolor = WallColor.getwallcolor();
		File file = new File(getClass().getClassLoader().getResource("brown_wall.png").getFile());
		wallcolor.setWallColor("brown");
		assertEquals(file, wallcolor.getwall());
	}

}
