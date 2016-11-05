package dentistrymanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	
	public static final String HOST = "jdbc:mysql://stusql.dcs.shef.ac.uk/";
	public static final String DB_NAME = "team019";
	public static final String USER = "team019";
	public static final String PASSWORD = "982e4ce3";
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(HOST + DB_NAME, USER, PASSWORD);	
	}
	
	public static void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {
			DBConnect.printSQLError(e);;
		}
	}
	
	public static void printSQLError(SQLException e) {
		while (e != null) {
			
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
}
