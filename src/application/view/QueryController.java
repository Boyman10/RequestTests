package application.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.model.Model;
import application.model.observer.IObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;


/**
 * Controller Class being called from the view to operate some kind of actions
 * The view is the MainContainer one
 * It must be located next to the FXML file
 * @author hackme
 *
 */
public class QueryController implements IObserver {

	Logger logger = LogManager.getRootLogger();

	//https://docs.oracle.com/javafx/2/ui_controls/table-view.htm
	@FXML
	private TableView queryTable;

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

		Object[][] alldata = (Object[][]) data;

		// We retrieve the meta data to input into our View Table :
		logger.info("updating object data/meta of size  " + alldata.length);

		TableColumn<Map,String>[] nameCol = new TableColumn[column.length];
		// Updating columns
		queryTable.getColumns().clear();
		
		
		for (int i=0;i < column.length; i++) {
			nameCol[i] = new TableColumn<Map,String>();
			nameCol[i].setCellValueFactory(new MapValueFactory(column[i]));
			nameCol[i].setMinWidth(100);
		}



		// Populating columns now :
		queryTable = new TableView<>(generateDataInMap(column,alldata));

		queryTable.getColumns().setAll(nameCol);
	      
	}

	private ObservableList<Map> generateDataInMap(Object[] keys, Object[][] alldata ) {

		// Number of rows
		int max = alldata.length;
		String[] myKeys = new String[keys.length];
		for(int i=0;i<keys.length;i++)
			myKeys[i] = (String) keys[i];

		ObservableList<Map> listData = FXCollections.observableArrayList();
		


		for (int i = 1; i < max; i++) {
					
			Map<String, String> dataRow = new HashMap<>();
			
			for(int j = 1; j < alldata[i-1].length; j++)
			{
				
				String curStr = alldata[i-1][j-1].toString();
				dataRow.put(myKeys[j-1],  curStr);

				
			}
			listData.add(dataRow);
		}

		return listData;
	}
}
