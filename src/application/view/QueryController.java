package application.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;

/**
 * Controller Class being called from the view to operate some kind of actions
 * The view is the MainContainer one
 * It must be located next to the FXML file
 * @author hackme
 *
 */
public class QueryController {

	Logger logger = LogManager.getRootLogger();
	
	@FXML
	private TableView<Model> queryTable;
	
	@FXML
	private Label timer;
	
	@FXML
	private TextArea queryTextarea;
	
	
	/**
	 * Constructor class
	 */
	public QueryController() {}
	
	/**
	 * Inserting data via Query
	 */
	@FXML
	public void insertData() {
		
		String query = queryTextarea.getText();

		// if empty line trigger an error !
		if (query == "") {
			//queryTable.setRowFactory(new Model(query));
			logger.info("This is an info message : inserting data...");
		}
		else {
			Alert problem = new Alert(AlertType.ERROR);
			problem.setTitle("Error");
			problem.setHeaderText("PLease enter a query !");
			problem.showAndWait();
		}
	}

}
