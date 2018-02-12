package application.controller;

import application.model.Model;
import application.model.observer.IObserver;

/**
 * Thread dealing with model to separate the query from the main UI thread 
 * @author hackme
 *
 */
public class QueryThread implements Runnable {

	private Model model;
	
	/**
	 * Constructor to initialize the query String
	 * @param query
	 */
	public QueryThread(String query, IObserver qt) {
		
		this.model = new Model(query);
		this.model.addObserver(qt);
	}
	
	@Override
	public void run() {

		this.model.makeQuery();
		
	}

	
}
