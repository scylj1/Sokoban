package gameRole;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Split <em>sprites.png</em> file into different pieces.
 * <p> Wall, diamond, keeper, crate picture are created for {@code GameObject}
 * @author Lekang Jiang
 */
public class Sprite {

	public Sprite() throws Exception{
		File sourcePic = new File(getClass().getClassLoader().getResource("sprites.png").getFile());

		BufferedImage pic1 = ImageIO.read(sourcePic);
		BufferedImage pic2 = pic1.getSubimage(320, 129, 42, 58);
		File desImage = new File(System.getProperty("user.dir") + "/src/main/resources/keeper_right.png");
		ImageIO.write(pic2, "png", desImage);

		pic2 = pic1.getSubimage(320, 304, 42, 58);
		desImage = new File(System.getProperty("user.dir") + "/src/main/resources/keeper_left.png");
		ImageIO.write(pic2, "png", desImage);

		pic2 = pic1.getSubimage(384, 0, 37, 58);
		desImage = new File(System.getProperty("user.dir") + "/src/main/resources/keeper_up.png");
		ImageIO.write(pic2, "png", desImage);

		pic2 = pic1.getSubimage(361, 249, 42, 58);
		desImage = new File(System.getProperty("user.dir") + "/src/main/resources/keeper_down.png");
		ImageIO.write(pic2, "png", desImage);
		
		pic2 = pic1.getSubimage(256, 128, 64, 64);
		desImage = new File(System.getProperty("user.dir") + "/src/main/resources/crate.png");
		ImageIO.write(pic2, "png", desImage);

		pic2 = pic1.getSubimage(192, 192, 64, 64);
		desImage = new File(System.getProperty("user.dir") + "/src/main/resources/crate_on_diamond.png");
		ImageIO.write(pic2, "png", desImage);
		
		pic2 = pic1.getSubimage(192, 384, 32, 32);
		desImage = new File(System.getProperty("user.dir") + "/src/main/resources/diamond.png");
		ImageIO.write(pic2, "png", desImage);
				
		pic2 = pic1.getSubimage(0, 256, 64, 64);
		desImage = new File(System.getProperty("user.dir") + "/src/main/resources/grey_wall.png");
		ImageIO.write(pic2, "png", desImage);

		pic2 = pic1.getSubimage(0, 320, 64, 64);
		desImage = new File(System.getProperty("user.dir") + "/src/main/resources/brown_wall.png");
		ImageIO.write(pic2, "png", desImage);
		
		pic2 = pic1.getSubimage(64, 0, 64, 64);
		desImage = new File(System.getProperty("user.dir") + "/src/main/resources/black_wall.png");
		ImageIO.write(pic2, "png", desImage);
		
		pic2 = pic1.getSubimage(64, 64, 64, 64);
		desImage = new File(System.getProperty("user.dir") + "/src/main/resources/khaki_wall.png");
		ImageIO.write(pic2, "png", desImage);

	}
}
