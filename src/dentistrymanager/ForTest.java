package dentistrymanager;

import java.sql.*;

public class ForTest {

	public static void main(String[] args) {
		
		Connection con = null;
		
		try {
			con = DBConnect.getConnection();
			
			TableBuilder builder = new TableBuilder(con);
			
			System.out.println(builder.checkIfTablesExist());
			
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		} finally {
			if(con != null)
				DBConnect.closeConnection(con);
		}
	}
}
