package dentistrymanager;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class TableBuilder {
	
	// Constants
	public static final String[] LIST_TABLES= {"Patient", "Appointment"};
	
	// Private variables
	private Connection connection;
	
	public TableBuilder(Connection connection) {
		this.connection = connection;	
	}
	
	public boolean checkIfTablesExist() {	
		boolean existTables = false;
		try {
			DatabaseMetaData dbMetaData = connection.getMetaData();
			ResultSet res = dbMetaData.getTables(null, null, "%", null);
			
			while (res.next()) {
			  System.out.println(res.getString(3));
			}
			
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		} catch(Exception e) {
			System.err.println(e);;
		} 
		return existTables;
	}

}
