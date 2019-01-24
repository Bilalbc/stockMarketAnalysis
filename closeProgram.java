//Class Name: closeProgram
//Description: This is a simple class which is mainly used for popping up a prompt to check if the user is sure that they want to exit the program.
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class closeProgram {

	//Boolean variable to store yes or no.
	static boolean anwser;

	//display method which holds all the code and has a boolean return type.
	public static boolean display(String title, String message) {
		//creates another window.
		Stage window = new Stage();
		
		//Sets window settings
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		
		//Makes a label displaying a message.
		Label label = new Label();
		label.setText(message);
		
		//Makes two buttons to check if the user selects yes or no. 
		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");
		
		//If the yes button is clicked, then set the boolean to true.
		yesButton.setOnAction(e -> {
			anwser = true;
			window.close();
			
		});
		
		//If the no button is clicked, then set the boolean to false.
		noButton.setOnAction(e -> {
			anwser = false;
			window.close();
		});
		
		//Makes the layout for this scene
		VBox layout = new VBox(20);
		layout.getChildren().addAll(label, yesButton, noButton);
		layout.setAlignment(Pos.CENTER);
		
		//Makes the scene and set its layout.
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		//returns true or false.
		return anwser;
	
	}
}
