package application.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.controller.QueryThread;
import application.model.Model;
import application.model.observer.IObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;


/**
 * Controller Class being called from the view to operate some kind of actions
 * The view is the MainContainer one
 * It must be located next to the FXML file
 * @author hackme
 *
 */
public class QueryController implements IObserver {

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
	public QueryController() {
		
		
		
	}
	
	/**
	 * Inserting data via Query
	 */
	@FXML
	public void insertData() {
		
		String query = queryTextarea.getText().trim();

		// if empty line trigger an error !
		if (!query.equals("")) {
			QueryThread qt = new QueryThread(query,this);			
		    Thread t = new Thread(qt);
		    t.start();
			//queryTable.setRowFactory(new Model(query));
			logger.info("This is an info message : inserting data..." + query);
		}
		else {
			Alert problem = new Alert(AlertType.ERROR);
			problem.setTitle("Error");
			problem.setHeaderText("PLease enter a query !");
			problem.showAndWait();
		}
	}

	@Override
	public void update(Object meta) {

		// We retrieve the meta data to input into our View Table :
		logger.info("updating object meta");
	}

}
