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
		/*
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
		*/
		
		/*
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
		*/
		/*
		try (Connection con = DBConnect.getConnection(false)){
			
			ArrayList<HealthcarePlan> plans = HealthcarePlan.getAll(con);
			for(HealthcarePlan p: plans)
				System.out.println(p);
			
			Partner partner = Partner.getAll(con).get(0);
			ArrayList<Appointment> appointments = partner.getDaysAppointments(con);
			for(Appointment ap: appointments)
				System.out.println(ap.toString());
			
			
			ArrayList<TypeOfTreatment> types = TypeOfTreatment.getAllByPartner(con, "DENTIST");
			for(TypeOfTreatment t: types)
				System.out.println(t.getName());
			
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		} 
		*/
		/*
		Patient patient = new Patient();
		try (Connection con = DBConnect.getConnection(false)){
			patient = Patient.getPatientByID(con, 1);
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		} 
		
		try (Connection con = DBConnect.getConnection(true)){
			String partner = "DENTIST";
			Date date = DateTimeUtilities.stringToDate("2016", "11", "21");
			Time sTime = DateTimeUtilities.stringToTime("1600");
			Time eTime = DateTimeUtilities.stringToTime("1700");
			String tot = "REPAIR";
			int cot = 0;
			
			Appointment app = new Appointment(partner, date, sTime, eTime, patient, tot, cot);
			app.add(con);
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		} catch (DuplicateKeyException e) {
			
		}
		*/
		try (Connection con = DBConnect.getConnection(false)){
			
			
			//System.out.println(CoveredTreatment.getCoveredCost(con, 1, "CHECK-UP"));
			
			Partner partner = new Partner("DENTIST");
			Appointment ap = partner.getNextAppointment(con);
			System.out.println(ap);
			
			//System.out.println(CourseOfTreatment.getCourseOfTreatment(con, 1).getCourseOfTreatment());
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		}
	}
}