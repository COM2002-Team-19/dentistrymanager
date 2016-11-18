package dentistrymanager;

import java.sql.*;

public class ForTest {

	public static void main(String[] args) {
		
		/*
		try (Connection con = DBConnect.getConnection(false)){
			Statement stmt = con.createStatement();
			String sql = "SELECT a.*, m.* FROM testa a LEFT OUTER JOIN testm m ON a.id = m.val2;";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next()) {
				System.out.println(res.getString(1) + " " + res.getString(2) + " " + res.getString(3) + " " +res.getString(4) + " " + res.getString(5));
				System.out.println(res.getString(1) + " " + res.getString(2) + " " + DBUtilities.nullToZero(res.getString(3)) 
                					+ " " +res.getString(4) + " " + res.getString(5));
			}
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		}
		*/
		
		try (Connection con = DBConnect.getConnection(true)){
			
			DatabaseBuilder builder = new DatabaseBuilder(con);
			
			System.out.println(builder.checkIfAllTablesExist());
			
			if(!builder.checkIfAllTablesExist()) {
				builder.resetTables();
				builder.fillWithDefaultValues();
			}
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		} 
		
		// Store new Address and Patient
		Address testAddress = new Address(34, "clementson rd.","south yorkshire","sheffield","s10 1gs");
		Patient testPatient = new Patient("dr","Dan","PetERs",19960906,"0345678911", testAddress);
		
		try (Connection con = DBConnect.getConnection(true)){
			boolean executeNext = false;
			try{
				executeNext = testAddress.add(con);
			} catch (DuplicateKeyException e) {
				executeNext = true;
			}
			
			if(executeNext)
				testPatient.add(con);
			
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		} 
		
		Patient testPatient1 = new Patient("dr","Fox","Mulder",19960906,"0345678911", testAddress);
		
		try (Connection con = DBConnect.getConnection(true)){
			boolean executeNext = false;
			try{
				executeNext = testAddress.add(con);
			} catch (DuplicateKeyException e) {
				executeNext = true;
			}
			
			if(executeNext)
				testPatient1.add(con);
			
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		} 		
	}
}