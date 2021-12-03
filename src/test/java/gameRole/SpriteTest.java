package gameRole;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;


public class SpriteTest {

	@Test
	public void testSprite() throws Exception {
		new Sprite();
		File file = new File(getClass().getClassLoader().getResource("crate.png").getFile());
		assertEquals(true, file.isFile() && file.exists());
	}

}
