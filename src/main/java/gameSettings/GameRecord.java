package gameSettings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The class is used when a level is complete, the game is complete or 
 * <em>Save Score</em> function is used.
 * <p> When a level is complete, it shows the first 3 high score player, 
 * and current player's score.
 * <p> When the game is complete or <em>Save Score</em> function is used, 
 * the player can enter his name and record the score.
 *
 * @author Lekang Jiang
 */
public class GameRecord {
	private Stage m_entername; // pop up new windows for players to enter name
	private HBox m_hbox; // pop up box
	private TextField m_field;  // text field to enter names
	private String m_name;
	private String m_mapname;
	private ArrayList<String> m_content = new ArrayList<>(); // contains content in score file
	private int m_level, m_move;
	private File m_file;  // score file
	private GameComplete m_game = GameComplete.getgame();

	/**
	 * Constructor
	 * <p> Initialise class variables. 
	 * If game is complete, {@code enterName()} function is called
	 * and read the past record to  {@code content} 
	 * @param mapname 
	 * @param level 
	 * @param move
	 */
	public GameRecord(String mapname, int level, int move) {
		this.m_level = level;
		this.m_move = move;
		this.m_mapname = mapname;
		if (m_game.isGameComplete()) {
			enterName();
			try {
				m_file = new File(System.getProperty("user.dir") 
						+ "/src/main/resources/record/" + mapname + ".txt");
				if (m_file.isFile() && m_file.exists()) {
					FileInputStream input = new FileInputStream(m_file);
					InputStreamReader read = new InputStreamReader(input);
					BufferedReader bufferedReader = new BufferedReader(read);
					String lineTxt = null;
					while ((lineTxt = bufferedReader.readLine()) != null) {
						m_content.add(lineTxt);
					}
					read.close();
				} else {
					m_file.createNewFile();
				}
			} catch (Exception e) {
				System.out.println("Read file failed");
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * Pop up a new window that allows player to enter name and record the scores.
	 *
	 */
	public void enterName() {
		m_entername = new Stage();
		m_hbox = new HBox();
		Label label = new Label("Enter your name: ");
		label.setFont(new Font("Arial", 20));
		m_field = new TextField();
		m_field.setEditable(true);
		m_field.setAlignment(Pos.CENTER_LEFT);
		Button confirm = new Button("Confirm");
		confirm.setOnAction(actionEvent -> confirmname());
		HBox.setMargin(label, new Insets(20, 20, 20, 20)); 
	    HBox.setMargin(m_field, new Insets(20, 20, 20, 20)); 
	    HBox.setMargin(confirm, new Insets(20, 20, 20, 20)); 
		m_hbox.getChildren().addAll(label, m_field, confirm);
		m_entername.setTitle("Enter name to record you score");
		m_entername.setScene(new Scene(m_hbox));
		m_entername.show();
	}

	/**
	 * When player enter a name and click <em>confirm</em>,
	 * close the pop up window and record the score to file.
	 *
	 */
	public void confirmname() {
		if (m_field.getText() != null && !m_field.getText().isEmpty()) {
			m_name = m_field.getText();
			m_entername.close();
			record();
		}
	}

	/**
	 * Record the current player's score to a permanent score list.
	 * <p> The list is ordered by levels achieved. If the level is the same,
	 * less movements would have higher score.
	 *
	 */
	public void record() {
		int i;
		for (i = 2; i <= m_content.size(); i = i + 3) {
			String Level = m_content.get(m_content.size() - i);
			int recordlevel = Integer.parseInt(Level.replace("Level: ", ""));
			if (m_level > recordlevel) {
				continue;
			} else if (m_level == recordlevel) {
				String Move = m_content.get(m_content.size() - i + 1);
				int recordmove = Integer.parseInt(Move.replace("Total moves: ", ""));
				if (m_move < recordmove) {
					continue;
				} else {
					break;
				}
			} else {
				break;
			}
		}

		try {
			File file = new File(System.getProperty("user.dir") + "/src/main/resources/record/" + m_mapname + ".txt");
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			for (int j = 0; j < m_content.size() + 3; j++) {
				if (j + i <= m_content.size() + 1) {
					String content1 = m_content.get(j) + "\n";
					fileOutputStream.write(content1.getBytes());
				} else if (j + i == m_content.size() + 2) {
					String content1 = "Player: " + m_name + "\nLevel: " + m_level + "\nTotal moves: " + m_move + "\n";
					fileOutputStream.write(content1.getBytes());
					j = j + 2;
				} else {
					String content1 = m_content.get(j - 3) + "\n";
					fileOutputStream.write(content1.getBytes());
				}
			}
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		show();
	}

	/**
	 * Show the first 3 highest player with scores, 
	 * and the current player's score.
	 *
	 */
	public void show() {
		ArrayList<String> content1 = new ArrayList<>();
		try {
			m_file = new File(System.getProperty("user.dir") + "/src/main/resources/record/" + m_mapname + ".txt");
			if (m_file.isFile() && m_file.exists()) {
				FileInputStream input = new FileInputStream(m_file);
				InputStreamReader read = new InputStreamReader(input);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					content1.add(lineTxt);
				}
				read.close();
			} else {
				System.out.println("File not found");
			}
		} catch (Exception e) {
			System.out.println("Read file failed");
			e.printStackTrace();
		}
		String record = "First 3 high score player\n";
		for (int i = 0; i < 9; i++) {
			if (i < content1.size()) {
				record = record + content1.get(i) + "  ";
				if (i % 3 == 2) {
					record = record + "\n";
				}
			}

		}
		record = record + "\nYour level: " + m_level + "  Your Total moves: " + m_move;
		new Dialog("Congratulations", record, null);
	}
}
