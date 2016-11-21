package dentistrymanager;

import java.sql.*;

import java.util.ArrayList;

public class Appointment {
	
	// Instance variables
	private int appointmentID;
	private String partner;
	private Date date;
	private Time startTime;
	private Time endTime;
	private boolean finish;
	private String typeOfTreatment;
	private int courseOfTreatment;
	private Patient patient;
	
	// Constructor for taking existing appointment from database
	public Appointment(int appointmentID, String partner, Date date, Time startTime, Time endTime, 
			boolean finish, Patient patient, String typeOfTreatment, int courseOfTreatment) {
		this.appointmentID = appointmentID;
		this.partner = partner; 
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.finish = finish;
		this.patient = patient;
		this.typeOfTreatment = typeOfTreatment;
		this.courseOfTreatment = courseOfTreatment;
	}
	// Constructor w/out ID i.e. wholly new appointment
	public Appointment(String partner, Date date, Time startTime, Time endTime, 
			Patient patient, String typeOfTreatment, int courseOfTreatment) {
		this.appointmentID = 0;
		this.partner = partner; 
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.finish = false;
		this.patient = patient;
		this.typeOfTreatment = typeOfTreatment;
		this.courseOfTreatment = courseOfTreatment;
	}
	
	// Accessors
	public int getAppointmentID() {
		return appointmentID;
	}
	public String getPartner() {
		return partner;
	}
	public Date getDate() {
		return date;
	}
	public Time getStartTime() {
		return startTime;
	}
	public Time getEndTime() {
		return endTime;
	}
	public boolean isFinished() {
		return finish;
	}
	public String getTypeOfTreatment() {
		return typeOfTreatment;
	}
	public int getCourseOfTreatment() {
		return courseOfTreatment;
	}
	public Patient getPatient() {
		return patient;
	}
	
	// Other Methods
	// toString
	public String toString() {
		return appointmentID + " : " + patient + " : " + partner + " : " + date  + " : " +  startTime  + " : " 
				+ endTime  + " : " + finish;  
	}
	
	// Database methods

	// Sets isFinished to true
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
	
	// Add
	public boolean add(Connection connection) throws DuplicateKeyException {
		try(Statement stmt = connection.createStatement()) {
			
			connection.setAutoCommit(true);
			String sql = "SELECT MAX(appointmentID) AS id FROM Appointment";
			ResultSet res = stmt.executeQuery(sql);
			int newId = 0;
			
			connection.setAutoCommit(false);
			if(res.first()) {
				newId = res.getInt("id") + 1;
				
				sql = "INSERT INTO Appointment (appointmentID, partner, date, startTime, endTime, typeOfTreatment) "
									+ "VALUES (" + newId + ", '" + partner + "','"  + date + "', '" 
									+ startTime + "','" + endTime + "','" + typeOfTreatment +"');";
				
				stmt.executeUpdate(sql);
				
				if(patient != null) {
					sql = "INSERT INTO AppointmentsPerPatient VALUES (" + patient.getPatientID() + ", " + newId + ");";
					stmt.executeUpdate(sql);
				}
				
				if(courseOfTreatment > 0) {
					sql = "INSERT INTO AppointmentsPerCourseOfTreatment VALUES ('" + courseOfTreatment + "', " + newId + ");";
					stmt.executeUpdate(sql);
				}
				connection.commit();
				return true;
			}
			return false;
		} catch(SQLException e) {
			DBConnect.rollback(connection);
			if(e.getErrorCode() == 1062)
				throw new DuplicateKeyException("Appointment"); // #TODO ? replace by plan.toString()
			DBConnect.printSQLError(e);
			return false;
		}
	}
	
	// Delete
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
	
	// Check availability
	public boolean checkAvailability(Connection connection) {
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT * FROM Appointment WHERE partner = '" + partner + "' AND date = '" + date 
							+ "' AND ((startTime <= '" + startTime + "' AND endTime >= '" + endTime + "') "
								+ " OR (startTime <= '" + startTime + "' AND endTime >= '" + startTime + "') "
								+ " OR (startTime <= '" + endTime + "' AND endTime >= '" + endTime + "'));";
			ResultSet res = stmt.executeQuery(sql);
			int numConflicts = 0;
			while(res.next())
				numConflicts++;
			return numConflicts > 0 ? false : true;
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
			return false;
		}
	}
	
	// Static methods
	public static ArrayList<Appointment> findByPartnerPatient(Connection connection, String patientSearchTerm, 
																							String partnerSearchTerm) {
		ArrayList<Appointment> appointments = new ArrayList<>();
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT a.*, ap.patientID, ac.courseOfTreatment FROM Appointment a "
							+ "LEFT OUTER JOIN AppointmentsPerPatient ap ON a.appointmentID = ap.appointmentID"
							+ "LEFT JOIN Patient p ON ap.patientID = p.patientID"
							+ "LEFT OUTER JOIN AppointmentsPerCourseOfTreatment ac ON a.appointmentID = ac.appointmentID"
							+ "WHERE a.partner = '%" + partnerSearchTerm + "%' AND (p.forename = '%" + patientSearchTerm +"%' "
									+ "OR p.surname = '%" + patientSearchTerm +"%') ORDER BY a.date, a.startTime;";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next()) {
				Patient patient = new Patient();
				int patientID = DBUtilities.nullToZero(res.getString("patientID"));
				if(patientID != 0) {
					patient = Patient.getPatientByID(connection, patientID);
				}
				appointments.add(new Appointment(res.getInt("appointmentID"), res.getString("partner"), res.getDate("Date"), 
												 res.getTime("startTime"), res.getTime("endTime"), res.getBoolean("finish"),
												 patient, res.getString("typeOfTreatment"),
												 DBUtilities.nullToZero(res.getString("courseOfTreatment"))));
			}
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		}
		return appointments;
	}
}