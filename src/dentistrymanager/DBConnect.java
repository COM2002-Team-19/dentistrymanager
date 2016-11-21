package dentistrymanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	
	public static final String HOST = "jdbc:mysql://stusql.dcs.shef.ac.uk/";
	public static final String DB_NAME = "team019";
	public static final String USER = "team019";
	public static final String PASSWORD = "982e4ce3";
	
	public static Connection getConnection(boolean manualCommit) throws SQLException {
		Connection connection = DriverManager.getConnection(HOST + DB_NAME, USER, PASSWORD);	
		if(manualCommit)
			connection.setAutoCommit(false);
		return connection;
	}	
	
	public static void rollback(Connection connection) {
		try {
			if (connection != null)
				connection.rollback();
		} catch(SQLException e) {
			printSQLError(e);
		}
	}
	
	public static void printSQLError(SQLException e) {
		
		// Print error details
		System.err.println("SQLState: " + e.getSQLState());
	    System.err.println("Error Code: " + e.getErrorCode());
	    System.err.println("Message: " + e.getMessage());
	    
	    // Print cause of error
	    Throwable t = e.getCause();
	    while (t != null) {
	    	System.out.println("Cause : " + t);
	    	t = t.getCause();
	    }
	}
}
