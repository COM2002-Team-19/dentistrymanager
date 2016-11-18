package dentistrymanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Appointment {
	
	// Private variables
	private int appointmentID;
	private int patientID;
	private String partner;
	private long date;
	private int startTime;
	private int endTime;
	private boolean finish;
	private int courseOfTreatment;
	
	// Constructor
	public Appointment(int appointmentID, String partner, long date, int startTime, int endTime, 
			boolean finish, int patientID, int courseOfTreatment) {
		this.appointmentID = appointmentID;
		this.partner = partner; 
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.finish = finish;
		this.patientID = patientID;
		this.courseOfTreatment = courseOfTreatment;
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
	
	public int getCourseOfTreatment() {
		return courseOfTreatment;
	}
	
	// Other Methods
	public String toString() {
		return appointmentID + " : " + patientID + " : " + partner + " : " + date  + " : " +  startTime  + " : " 
				+ endTime  + " : " + finish;  
	}
	
	// Database methods
	public boolean finish(Connection connection) {
		try(Statement stmt = connection.createStatement()) {
			String sql = "UPDATE Appointment SET finish = TRUE WHERE appointmentID = " + appointmentID + ";";
			int numRows = stmt.executeUpdate(sql);
			connection.commit();
			return numRows == 1 ? true : false;
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
			DBConnect.rollback(connection);
			return false;
		}
	}
	
	public boolean add(Connection connection) throws DuplicateKeyException {
		try(Statement stmt = connection.createStatement()) {
			String sql = "INSERT INTO Appointment (partner, date, startTime, endTime) "
											+ "VALUES ('" + partner + "'," + startTime + "," + endTime + ");";
			stmt.executeUpdate(sql);
			if(patientID > 0) {
				sql = "INSERT INTO ApointmentsPerPatient VALUES (" + patientID + ", " + appointmentID + ");";
				stmt.executeUpdate(sql);
			}
			
			if(courseOfTreatment > 0) {
				sql = "INSERT INTO AppointmentsPerCourseOfTreatment VALUES ('" + courseOfTreatment + "', " + appointmentID + ");";
				stmt.executeUpdate(sql);
			}
			connection.commit();
			return true;
		} catch(SQLException e) {
			DBConnect.rollback(connection);
			if(e.getErrorCode() == 1062)
				throw new DuplicateKeyException("Appointment"); // replace by plan.toString()
			DBConnect.printSQLError(e);
			return false;
		}
	}
	
	public boolean delete(Connection connection) throws DeleteForeignKeyException {
		try(Statement stmt = connection.createStatement()) {
			String[] sqls = new String[] {"DELETE FROM AppointmentsPerCourseOfTreatment WHERE appointmentID = " + appointmentID + ";",
										  "DELETE FROM AppointmentsPerPatient WHERE appointmentID = " + appointmentID + ";",
										  "DELETE FROM Appointment WHERE appointmentID = " + appointmentID + ";"};
			
			for(String sql: sqls)
				stmt.addBatch(sql);
			stmt.executeBatch();
			connection.commit();
			return true;
		} catch(SQLException e) {
			DBConnect.rollback(connection);
			if(e.getErrorCode() == 1451)
				throw new DeleteForeignKeyException("TreatmentRecord", this.toString());
			DBConnect.printSQLError(e);
			return false;
		}
	}
	
	// Static methods
	public static ArrayList<Appointment>findByPartnerPatient(Connection connection, String patient, String partner) {
		ArrayList<Appointment> appointments = new ArrayList<>();
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT a.*, ap.patientID, ac.courseOfTreatment FROM Appointment a "
							+ "LEFT OUTER JOIN AppointmentsPerPatient ap ON a.appointmentID = ap.appointmentID"
							+ "LEFT JOIN Patient p ON ap.patientID = p.patientID"
							+ "LEFT OUTER JOIN AppointmentsPerCourseOfTreatment ac ON a.appointmentID = ac.appointmentID"
							+ "WHERE a.partner = '%" + partner + "%' AND (p.forename = '%" + patient +"%' "
									+ "OR p.surname = '%" + patient +"%');";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next())
				appointments.add(new Appointment(res.getInt("appointmentID"), res.getString("partner"), res.getLong("Date"), 
												 res.getInt("startTime"), res.getInt("endTime"), res.getBoolean("finish"),
												 DBUtilities.nullToZero(res.getString("patientID")),
												 DBUtilities.nullToZero(res.getString("courseOfTreatment"))));
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		}
		return appointments;
	}
}
