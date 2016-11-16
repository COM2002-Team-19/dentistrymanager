package dentistrymanager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Appointment {
	
	// Private variables
	private int appointmentID;
	private String partner;
	private long date;
	private int startTime;
	private int endTime;
	private boolean finish;
	
	// Constructor
	public Appointment(int appointmentID, String partner, long date, int startTime, int endTime, boolean finish) {
		this.appointmentID = appointmentID;
		this.partner = partner; 
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.finish = finish;
	}
	
	// Accessors
	public int getAppointmentID() {
		return appointmentID;
	}
	
	public String getPartner() {
		return partner;
	}
	
	public long getDate() {
		return date;
	}
	
	public int getStartTime() {
		return startTime;
	}

	public int getEndTime() {
		return endTime;
	}
	
	public boolean isFinished() {
		return finish;
	}
	
	// Database methods
	public boolean finish(Connection connection) {
		try(Statement stmt = connection.createStatement()) {
			String sql = "UPDATE Appointment SET finish = TRUE WHERE appointmentID = " + appointmentID + ";";
			int numRows = stmt.executeUpdate(sql);
			return numRows == 1 ? true : false;
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
			return false;
		}
	}
}
