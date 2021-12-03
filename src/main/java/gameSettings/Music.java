package gameSettings;

import javax.sound.sampled.LineUnavailableException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Control background music for the game.
 *
 * @author Lekang Jiang
 */
public class Music {
	
	private MediaPlayer m_player;

	/**
	 * Create a music player and play the music.
	 *
	 */
	public void createPlayer() throws LineUnavailableException {
		Media music = new Media(getClass().getClassLoader().getResource("puzzle_theme.wav").toString());		
		m_player = new MediaPlayer(music);
		m_player.setAutoPlay(true);
		m_player.setCycleCount(MediaPlayer.INDEFINITE);
	}

	/**
	 * Play the music.
	 *
	 */
	public void playMusic() {
		m_player.play();
	}

	/**
	 * Stop the music.
	 *
	 */
	public void stopMusic() {
		m_player.stop();
	}

	/**
	 * Judge if the music is playing.
	 * @return boolean
	 */
	public boolean isPlayingMusic() {
		return m_player.getStatus() == MediaPlayer.Status.PLAYING;
		
	}

}
