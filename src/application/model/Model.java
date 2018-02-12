package application.model;

import java.beans.Statement;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import application.model.observer.IObserver;

/**
 * Model class handling observed textarea and table view
 * @author hackme
 *
 */
public class Model  {
	
	private ArrayList<IObserver> lObs = new ArrayList<IObserver>();
	
	private String query;
	// result set :
	DatabaseMetaData meta;
	
	/**
	 * Constructor initializing query
	 * @param query
	 */
	public Model(String query) {
		
		this.query = query;
	}
	
	public void makeQuery() {
		
	    try {

	        PostgresConnection.getInstance().prepareStatement(this.query);

	        PostgresConnection.getInstance().createStatement();

	        PostgresConnection.getInstance().setAutoCommit(false);

	        meta = PostgresConnection.getInstance().getMetaData();
	           
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
	        obs.update(meta);
		
}
	


}
