package gameRole;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import gameProcessor.*;
import gameSettings.Debug;

/**
 * Define the graphic object for the game. 
 * <p>Different graphic object (wall, keeper, floor, crate, etc) 
 * has corresponding pictures.
 * @author Lekang Jiang - Modified
 */
public class GraphicObject extends GridPane {

	private Debug debug = Debug.getdebug();
	
	/**
	 * Constructor
	 * <p> Change different {@code GameObject} into {@code GraphicObject}.
	 *
	 */
    public GraphicObject(GameObject obj) {
        File file;
        switch (obj) {
            case WALL:
                file = WallColor.getwallcolor().getwall();
                addimage(file);              
                break;

            case CRATE:
            	file = new File(getClass().getClassLoader().getResource("crate.png").getFile());
            	addimage(file);
                break;

            case DIAMOND:
            	file = new File(getClass().getClassLoader().getResource("diamond.png").getFile());
            	addimage(file);

                // TODO: fix memory leak.
                if (debug.isDebugActive()) {
                    FadeTransition ft = new FadeTransition(Duration.millis(1000), this);
                    ft.setFromValue(1.0);
                    ft.setToValue(0.2);
                    ft.setCycleCount(Timeline.INDEFINITE);
                    ft.setAutoReverse(true);
                    ft.play();
                }

                break;

            case KEEPER:
            	file = new File(getClass().getClassLoader().getResource("keeper_down.png").getFile());
            	addimage(file);
                break;
                
            case KEEPER_DOWN:
            	file = new File(getClass().getClassLoader().getResource("keeper_down.png").getFile());
            	addimage(file);
                break;
                
            case KEEPER_UP:
            	file = new File(getClass().getClassLoader().getResource("keeper_up.png").getFile());
            	addimage(file);
                break;
                
            case KEEPER_LEFT:
            	file = new File(getClass().getClassLoader().getResource("keeper_left.png").getFile());
            	addimage(file);
                break;
                
            case KEEPER_RIGHT:
            	file = new File(getClass().getClassLoader().getResource("keeper_right.png").getFile());
            	addimage(file);
                break;

            case FLOOR:
            	Rectangle floor = new Rectangle();
            	floor.setFill(Color.WHITE);
                floor.setHeight(30);
                floor.setWidth(30);
                this.add(floor,0,0);
                break;

            case CRATE_ON_DIAMOND:
            	file = new File(getClass().getClassLoader().getResource("crate_on_diamond.png").getFile());
            	addimage(file);
                break;

            default:
                String message = "Error in Level constructor. Object not recognized.";
                GameModel.logger.severe(message);
                throw new AssertionError(message);
        }   	
    }
    
    /**
	 * Take file as input, read it to {@code BufferedImage},
	 * and change it to {@code ImageView}, 
	 * finally add it to {@code GraphicObject}.
	 * @param File 
	 */
    void addimage(File file) {
    	BufferedImage bf = null;
        try {
        	bf = ImageIO.read(file);           
        } catch (IOException ex) {
            System.out.println("Image failed to load.");
        }
 
        WritableImage wr = null;
        if (bf != null) {
            wr = new WritableImage(bf.getWidth(), bf.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < bf.getWidth(); x++) {
                for (int y = 0; y < bf.getHeight(); y++) {
                    pw.setArgb(x, y, bf.getRGB(x, y));
                }
            }
        }
 
        ImageView imageView = new ImageView(wr);
    	imageView.setFitHeight(30);
    	imageView.setFitWidth(30);
    	this.add(imageView,0,0);
    }

}
