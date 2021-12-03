package gameSettings;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Take some String as input and pop up a window to show this messages.
 * @author Lekang Jiang
 *
 */
public class Dialog {

	public Dialog(String dialogTitle, String dialogMessage, Effect dialogMessageEffect) {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setResizable(false);
		dialog.setTitle(dialogTitle);

		Text text1 = new Text(dialogMessage);
		text1.setTextAlignment(TextAlignment.CENTER);
		text1.setFont(javafx.scene.text.Font.font(14));

		if (dialogMessageEffect != null) {
			text1.setEffect(dialogMessageEffect);
		}

		VBox dialogVbox = new VBox(20);
		dialogVbox.setAlignment(Pos.CENTER);
		dialogVbox.setBackground(Background.EMPTY);
		dialogVbox.getChildren().add(text1);

		Scene dialogScene = new Scene(dialogVbox, 350, 150);
		dialog.setScene(dialogScene);
		dialog.show();
	}
}
