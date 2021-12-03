package gameStart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import gameProcessor.GameModel;
import gameRole.Sprite;

/**
 * The entry of the game.
 * <p>The game starts by running the main method.
 * 
 * @author Lekang Jiang - Modified
 *
 */
public class Main extends Application {
    
	/**
	 * The program starts here. Calls start method.
	 */
    public static void main(String[] args) {    	
        launch(args);     
        System.out.println("Done!");
    }

    /**
	 * Show the start screen with wall color choices 
	 * and a <em>start </em>button to enter the game
	 * <p> Initialise {@code Sprite} class for {@code GameObject}
	 */
    @Override
    public void start(Stage primaryStage) throws Exception {    
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("start.fxml"));      
        primaryStage.setTitle(GameModel.GAME_NAME);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
		new Sprite();       
    }
}
