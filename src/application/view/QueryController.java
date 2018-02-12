package application.view;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.controller.QueryThread;
import application.model.Model;
import application.model.observer.IObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
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
	
	//http://www.java2s.com/Tutorials/Java/JavaFX/0650__JavaFX_TableView.htm
	@FXML
	private TableView<Object> queryTable;
	
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
			
			/*QueryThread qt = new QueryThread(query,this);			
		    Thread t = new Thread(qt);
		    t.start();
		    */
			
			Model model = new Model(query);
			model.addObserver(this);
			model.makeQuery();
			
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

	/**
	 * Retrieving data to update the table
	 */
	@Override
	public void update(Object data, Object[] column) {

		// We retrieve the meta data to input into our View Table :
		logger.info("updating object data/meta of size : " + ((ArrayList<Object>)data).size());
		
		TableColumn[] nameCol = new TableColumn[column.length];
		
		for (int i=0;i < column.length; i++) {
			nameCol[i] = new TableColumn((String)column[i]);
			nameCol[i].setMinWidth(100);
		}
 
		// Updating columns
		queryTable.getColumns().clear();
        queryTable.getColumns().addAll(nameCol);
        // inserting data :
		//queryTable.setItems((ObservableList<Object>) obj);
		// add row:
        ObservableList<Object> alldata = FXCollections.observableArrayList(data);

		queryTable.setItems(alldata);

	}
}
