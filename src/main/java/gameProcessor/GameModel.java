package gameProcessor;
import gameSettings.*;
import javax.sound.sampled.LineUnavailableException;
import java.io.*;
import java.util.NoSuchElementException;

/**
 * The model part of the game, the data changes here.
 * <p> Initialise {@code GameLogger} to record log messages,
 * {@code Movement} to control the moves
 * {@code Music} for background music.
 * @author Lekang Jiang
 */
public class GameModel {

    public static final String GAME_NAME = "Sokoban_scylj1";
    public static GameLogger logger;    
    private Music m_music;
    private Movement m_movement;
    
    /**
     * Getter for {@code movement}.
     * @return m_movement 
     */
    public Movement getMovement() {
    	return m_movement;
    }

    /**
     * Getter for {@code music}.
     * @return m_music 
     */
    public Music GetMusic() {
    	return m_music;
    }
    
   /**Initialise {@code GameLogger} to record log messages,
    * {@code Movement} to control the moves
    * {@code Music} for background music.
    * @param input
    * @param production 
    * @param filename 
    */
    public void SetGameEngine(InputStream input, boolean production, String filename) {
        try {
            logger = new GameLogger();
            m_movement = new Movement(input);
            m_movement.loadmoves(filename);

            if (production) {
            	m_music = new Music();          
                m_music.createPlayer();
            }
        } catch (IOException x) {
            System.out.println("Cannot create logger.");
        } catch (NoSuchElementException e) {
            logger.warning("Cannot load the default save file: " + e.getStackTrace());
        } catch (LineUnavailableException e) {
            logger.warning("Cannot load the music file: " + e.getStackTrace());
        }
    }    
}