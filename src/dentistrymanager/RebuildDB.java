package dentistrymanager;

import java.sql.*;

public class RebuildDB {

	public static void main(String[] args) {
		
		try (Connection con = DBConnect.getConnection(true)){
			
			DatabaseBuilder builder = new DatabaseBuilder(con);
			
			System.out.println(builder.checkIfAllTablesExist());
			
			builder.resetTables();
			builder.fillWithDefaultValues();
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		} 
		
	}
}