package dentistrymanager;

import java.sql.*;
import java.util.ArrayList;

public class Partner {
	
	private String name;
	
	// Constructor
	public Partner(String name) {
		this.name = name;
	}
	
	// Accessors
	public String getName() {
		return name;
	}
	
	// Other Methods
	public ArrayList<Appointment> getWeekAppointments(Connection connection, int week) {
		ArrayList<Appointment> appointments = new ArrayList<>();
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT * FROM Appointment WHERE partner = " + name
												+ " AND date BETWEEN "+ DateUtilities.startWeek(week) 
												+ " AND " + DateUtilities.endWeek(week)+ ";";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next())
				appointments.add(new Appointment());
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		}
		return appointments;
	}
	
	public ArrayList<Appointment> getDaysAppointments(Connection connection) {
		ArrayList<Appointment> appointments = new ArrayList<>();
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT * FROM Appointment WHERE partner = " + name
												+ " AND date = "+ DateUtilities.today() + ";";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next())
				appointments.add(new Appointment());
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		}
		return appointments;
	}
	
	public Appointment getNextAppointment(Connection connection) {
		Appointment nextAppointment = null;
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT * FROM Appointment WHERE appointmentID = "
						+ "(SELECT MIN(appointmentID) FROM Appointment WHERE partner = " + name + "AND finish = FALSE;";
			ResultSet res = stmt.executeQuery(sql);
			if(res.next())
				nextAppointment = new Appointment();
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		}
		return nextAppointment;
	}
	
	// Static methods
	/*
	public static Partner getPartner(Connection connection, String name) {
				
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
