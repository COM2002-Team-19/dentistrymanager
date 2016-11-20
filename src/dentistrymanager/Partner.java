package dentistrymanager;

import java.sql.*;
import java.util.ArrayList;

public class Partner {
	
	// Instance variables
	private String name;
	
	// Constructor
	public Partner(String name) {
		this.name = name;
	}
	
	// Accessors
	public String getName() {
		return name;
	}
	
	// toString
	public String toString(){
		return this.getName();
	}
	
	// Instance Methods
	// Returns appointments for a given week
	public ArrayList<Appointment> getWeekAppointments(Connection connection, int week) {
		ArrayList<Appointment> appointments = new ArrayList<>();
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT a.*, ap.patientID, ac.courseOfTreatment FROM Appointment a " 
									+ "LEFT OUTER JOIN AppointmentsPerPatient ap ON a.appointmentID = ap.appointmentID "
									+ "LEFT OUTER JOIN AppointmentsPerCourseOfTreatment ac ON a.appointmentID = ac.appointmentID "
									+ "WHERE partner = '" + name + "' AND date BETWEEN " + DateUtilities.startWeek(week) 
									+ " AND " + DateUtilities.endWeek(week) + " AND finish = FALSE;";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next()) {
				Patient patient = new Patient();
				int patientID = DBUtilities.nullToZero(res.getString("patientID"));
				if(patientID != 0)
					patient = Patient.getPatientByID(connection, patientID);
				
				appointments.add(new Appointment(res.getInt("appointmentID"), res.getString("partner"), 
												res.getLong("date"), res.getInt("startTime"), res.getInt("endTime"), 
												res.getBoolean("finish"), patient, res.getString("typeOfTreatment"),
												DBUtilities.nullToZero(res.getString("courseOfTreatment"))));
			}
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		}
		return appointments;
	}
	
	// Returns appointments for current day
	public ArrayList<Appointment> getDaysAppointments(Connection connection) {
		ArrayList<Appointment> appointments = new ArrayList<>();
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT a.*, ap.patientID, ac.courseOfTreatment FROM Appointment a " 
						+ "LEFT OUTER JOIN AppointmentsPerPatient ap ON a.appointmentID = ap.appointmentID "
						+ "LEFT OUTER JOIN AppointmentsPerCourseOfTreatment ac ON a.appointmentID = ac.appointmentID "
						+ "WHERE partner = '" + name + "' AND date = "+ DateUtilities.today() + " AND finish = FALSE;";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next()) {
				Patient patient = new Patient();
				int patientID = DBUtilities.nullToZero(res.getString("patientID"));
				if(patientID != 0) {
					patient = Patient.getPatientByID(connection, patientID);
				}
				appointments.add(new Appointment(res.getInt("appointmentID"), res.getString("partner"), 
                                                 res.getLong("Date"), res.getInt("startTime"), res.getInt("endTime"), 
                                                 res.getBoolean("finish"), patient, res.getString("typeOfTreatment"),
                                                 DBUtilities.nullToZero(res.getString("courseOfTreatment"))));
			}
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		}
		return appointments;
	}
	
	// Returns immediate next appointment
	public Appointment getNextAppointment(Connection connection) {
		Appointment nextAppointment = null;
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT a.*, ap.patientID, ac.courseOfTreatment FROM Appointment a " 
							+ "LEFT OUTER JOIN AppointmentsPerPatient ap ON a.appointmentID = ap.appointmentID "	
							+ "LEFT OUTER JOIN AppointmentsPerCourseOfTreatment ac ON a.appointmentID = ac.appointmentID "
							+ "WHERE appointmentID = "
							+ "(SELECT MIN(appointmentID) FROM Appointment WHERE partner = '" + name + "' AND finish = FALSE);";
			ResultSet res = stmt.executeQuery(sql);
			if(res.next()) {
				Patient patient = new Patient();
				int patientID = DBUtilities.nullToZero(res.getString("patientID"));
				if(patientID != 0) {
					patient = Patient.getPatientByID(connection, patientID);
				}
				nextAppointment = new Appointment(res.getInt("appointmentID"), res.getString("partner"), 
						                          res.getLong("Date"), res.getInt("startTime"), res.getInt("endTime"), 
						                          res.getBoolean("finish"), patient, res.getString("typeOfTreatment"),
	                                              DBUtilities.nullToZero(res.getString("courseOfTreatment")));
			}
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		}
		return nextAppointment;
	}
	
	// Static methods
	public static ArrayList<Partner> getAll(Connection connection) {
		ArrayList<Partner> partners = new ArrayList<>();
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT * FROM Partner;";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next())
				partners.add(new Partner(res.getString("name")));
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		} catch (Exception e) {
			System.err.println(e);
		}
		return partners;
	}
}
