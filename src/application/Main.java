package application;
	
import java.io.IOException;

import application.view.QueryController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private Stage mainStage;
	private BorderPane mainContainer;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {

			mainStage = primaryStage;			
			mainStage.setTitle("My request panel");
			
			initMainPanel();
			initContent();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void initMainPanel() {
		// Load the FXML loader
		FXMLLoader loader = new FXMLLoader();

		loader.setLocation(Main.class.getResource("view/MainContainer.fxml"));
		try {

			// TODO - go back original and put the BordePane inside the QueriesContainer along with toolbar and JPanel
			mainContainer = (BorderPane) ((BorderPane) loader.load()).getChildren().get(1);

			Scene scene = new Scene(mainContainer);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			mainStage.setScene(scene);
			mainStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initContent() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/QueriesContainer.fxml"));
		try {

			AnchorPane queriesContainer = (AnchorPane) loader.load();
			mainContainer.setCenter(queriesContainer);
			
			QueryController controler = loader.getController();			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	public static void main(String[] args) {
		launch(args);
	}
}
