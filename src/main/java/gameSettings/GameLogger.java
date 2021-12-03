package gameSettings;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import gameProcessor.*;
/**
 * Class {@code GameLogger} is used to record game logs.
 *
 * <p>This class delivers some functionality that record some log messages to 
 * a file
 *
 */

public class GameLogger extends Logger {

    private static Logger m_logger = Logger.getLogger("GameLogger");
    private DateFormat m_dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Calendar m_calendar = Calendar.getInstance();
    
    public GameLogger() throws IOException {
        super("GameLogger", null);

        File directory = new File(System.getProperty("user.dir") + "/" + "logs");
        directory.mkdirs();

        FileHandler fh = new FileHandler(directory + "/" + GameModel.GAME_NAME + ".log");
        m_logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

    /**
     * Create a formatted message contains {@code message} and <em>time</em>.
     * @param message a String contains some messages
     * @return a formatted message with <em>time</em>
     */
    private String createFormattedMessage(String message) {
        return m_dateFormat.format(m_calendar.getTime()) + " -- " + message;
    }

    /**
     * Log a formatted INFO message by using messages created by 
     * {@link sample.GameLogger#createFormattedMessage(String)}
     * @param message a String contains some messages
     * @return a formatted message with INFO
     */
    public void info(String message) {
        m_logger.info(createFormattedMessage(message));
    }

    /**
     * Log a formatted WARNING message by using messages created by 
     * {@link sample.GameLogger#createFormattedMessage(String)}
     * @param message a String contains some messages
     * @return a formatted message with WARNING
     */
    public void warning(String message) {
        m_logger.warning(createFormattedMessage(message));
    }

    /**
     * Log a formatted SEVERE message by using messages created by 
     * {@link sample.GameLogger#createFormattedMessage(String)}
     * @param message a String contains some messages
     * @return a formatted message with SEVERE
     */
    public void severe(String message) {
        m_logger.severe(createFormattedMessage(message));
    }
}