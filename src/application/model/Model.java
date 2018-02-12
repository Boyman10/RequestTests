package application.model;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.model.observer.IObserver;
import javafx.collections.ObservableList;

/**
 * Model class handling observed textarea and table view
 * @author hackme
 *
 */
public class Model  {

	private ArrayList<IObserver> lObs = new ArrayList<IObserver>();
	Logger logger = LogManager.getRootLogger();

	private String query;
	// result set :
	private Object[][] data;
	private Object[] column;
	
	// https://java.developpez.com/faq/javafx/?page=Collections-observables
	private ObservableList obs; 
	
	/**
	 * Constructor initializing query
	 * @param query
	 */
	public Model(String query) {

		this.query = query;
	}

	public void makeQuery() {

		try {

			Connection conn = PostgresConnection.getInstance();
			conn.setAutoCommit(false);

			Statement state = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			ResultSet result = ((java.sql.Statement) state).executeQuery(this.query);

			//On récupère les meta afin de récupérer le nom des colonnes
			ResultSetMetaData meta = result.getMetaData();
			//On initialise un tableau d'Object pour les en-têtes du tableau
			column = new Object[meta.getColumnCount()];

			for(int i = 1 ; i <= meta.getColumnCount(); i++) {
				column[i-1] = meta.getColumnName(i);
				logger.info("Column" + column[i-1]);
			}
			//Petite manipulation pour obtenir le nombre de lignes
			 result.last();
			int rowCount = result.getRow();

			data = new Object[ result.getRow()][meta.getColumnCount()];

			//On revient au départ
			result.beforeFirst();
			int j = 1;			

			//On remplit le tableau d'Object[][]
			while( result.next()){
				for(int i = 1 ; i <= meta.getColumnCount(); i++)
					data[j-1][i-1] = result.getObject(i);

				j++;
			}

        
			result.close();
			state.close();
			
			this.notifyObserver();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Observer Pattern for communicating with Controller and View
	 * @param o
	 */
	public void addObserver(IObserver o) {

		lObs.add(o);
	}

	public void notifyObserver() {

		for(IObserver obs : this.lObs )
			obs.update(obs);

	}



}
