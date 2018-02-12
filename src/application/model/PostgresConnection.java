package application.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class to handle the PostreSQL Connection
 * @author hackme
 *
 */
public class PostgresConnection {

	  private String url = "jdbc:postgresql://localhost:5432/Ecole";
	  private String user = "test";
	  private String passwd = "1234";
	  private static Connection connect;
	   
	  private PostgresConnection(){
	    try {
	      connect = DriverManager.getConnection(url, user, passwd);
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	  }
	   
	   public static Connection getInstance(){
	    if(connect == null){
	      new PostgresConnection();
	    }
	    return connect;   
	  }   
	}