package dentistrymanager;

import java.sql.*;

public class ForTest {

	public static void main(String[] args) {
		
		try (Connection con = DBConnect.getConnection()){
			
			TableBuilder builder = new TableBuilder(con);
			
			System.out.println(builder.checkIfAllTablesExist());
			
			if(!builder.checkIfAllTablesExist()) {
				builder.resetTables();
			}
				
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		}
	}
}
