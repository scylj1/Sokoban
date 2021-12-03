package gameRole;

import java.io.File;

/**
 * A singleton class that set the wall color for the game.
 * @author Lekang Jiang
 */
public class WallColor {

	private File m_file = new File(getClass().getClassLoader().getResource("black_wall.png").getFile());
	private static WallColor m_wallcolor = new WallColor();

	/**
	 * Get the singleton class.
	 */
	public static WallColor getwallcolor() {
		return m_wallcolor;
	}
	
	/**
	 * Set the wall color to a file.
	 * @param id , String 
	 */
	public void setWallColor(String id) {
		 switch (id) {
             case "black":
            	 m_file = new File(getClass().getClassLoader().getResource("black_wall.png").getFile());
                 break;
             case "brown":
            	 m_file = new File(getClass().getClassLoader().getResource("brown_wall.png").getFile());
            	 break;
             case "grey":
            	 m_file = new File(getClass().getClassLoader().getResource("grey_wall.png").getFile());
            	 break;
             case "khaki":
            	 m_file = new File(getClass().getClassLoader().getResource("khaki_wall.png").getFile());
                 break;
		 }             
	}
	
	/**
	 * Get the wall file.
	 * @return File 
	 */
	public File getwall() {
		return m_file;
	}
}
