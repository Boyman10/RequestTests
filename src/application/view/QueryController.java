package application.view;

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
import javafx.scene.layout.AnchorPane;
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
	private AnchorPane anchorTable;

	@FXML
	private Label timer;

	@FXML
	private TextArea queryTextarea;
	private TableView queryTable;
	
	
	/**
	 * Constructor class
	 */
	public QueryController() {
		
		anchorTable = new AnchorPane();
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
		
		initialize(column,alldata);
		
	}


	
	
	/**
	 * Initialization with fake data
	 */
	public void initialize(Object[] column,Object[][] alldata) {
	    
	    logger.info("This is an info message : initializing...");
	 
	    // Updating columns
	 	queryTable.getColumns().clear();
	 		
        TableColumn<Map,String>[] nameCol = new TableColumn[column.length];
		
		
		for (int i=0;i < column.length; i++) {
			
			// set column name
			nameCol[i] = new TableColumn<Map,String>((String)column[i]);
			
			nameCol[i].setCellValueFactory(new MapValueFactory(column[i]));
			nameCol[i].setMinWidth(100);
		}
 
        queryTable = new TableView<>(generateDataInMap(column,alldata));        
        queryTable.setEditable(true);
        queryTable.getSelectionModel().setCellSelectionEnabled(true);
        queryTable.getColumns().setAll(nameCol);
        
        Callback<TableColumn<Map, String>, TableCell<Map, String>>
            cellFactoryForMap = new Callback<TableColumn<Map, String>,
                TableCell<Map, String>>() {
                    @Override
                    public TableCell call(TableColumn p) {
                        return new TextFieldTableCell(new StringConverter() {
                            @Override
                            public String toString(Object t) {
                                return t.toString();
                            }
                            @Override
                            public Object fromString(String string) {
                                return string;
                            }                                    
                        });
                    }
        };


        for (int i=0;i < column.length; i++) {
        	nameCol[i].setCellFactory(cellFactoryForMap);
        }
        
        logger.info("This is an info message : added CellFactory...");
        
        
        anchorTable.getChildren().add(queryTable);
        
	}	
	
    private ObservableList<Map> generateDataInMap(Object[] col,Object[][] data) {
        int max = data.length;
        logger.info("Observable List ");
        
        ObservableList<Map> allData = FXCollections.observableArrayList();
        for (int i = 0; i < max; i++) {
        	
            Map<String, String> dataRow = new HashMap<>();
 
            for(int j = 0;j<data[i].length;j++) {
            	dataRow.put(col[j].toString(),data[i][j].toString());
            	 logger.info("filling map : " + col[j] + " and data : " + data[i][j]);
            }
            allData.add(dataRow);
        }
        
        logger.info("This is an info message : initializing map");
        
        
        return allData;
    }
    
    
}
