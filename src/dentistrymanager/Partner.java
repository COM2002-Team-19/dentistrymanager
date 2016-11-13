package dentistrymanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Partner {
	
	/*
	private String name;
	private Appointment[] appointments;
	
	// Constructor
	public Partner(String name, Appointments[] appointments) {
		this.name = name;
		this.appointments = appointments;
	}
	
	// Accessors
	public String getName() {
		return name;
	}
	
	public Appointment[] getAppointments() {
		return appointments;
	}
	
	// Static methods
	public static Partner getPartner(Connection connection, int partnerID) {
				
		Partner partner = null;
		
		try(Statement stmt = connection.createStatement()) {
			Appointment appointments = new Appointment();
			String query = "SELECT * FROM Partner WHERE partnerID = "+partnerID+";";
			ResultSet res = stmt.executeQuery(query);
			partner = new Partner(	res.getString("name"),
									appointments.getAppointments(connection, partnerID));
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return partner;
	}
	*/
}
