package dentistrymanager;

import java.sql.*;

public class CourseOfTreatment {
	
	private int courseOfTreatment;
	private int patientID;
	private boolean complete;
	
	public CourseOfTreatment(int courseOfTreatment, int patientID, boolean complete) {
		this.courseOfTreatment = courseOfTreatment;
		this.patientID = patientID;
		this.complete = complete;
	}
	
	public CourseOfTreatment() {
		this(0,0,false);
	}
	
	public int getCourseOfTreatment() {
		return courseOfTreatment;
	}
	
	public int getPatientID() {
		return patientID;
	}
	
	public boolean isComplete() {
		return complete;
	}
	
	// Database methods
	
	// Add
	public boolean add(Connection connection) {
		try(Statement stmt = connection.createStatement()) {
			String sql = "INSERT INTO CourseOfTreatment (courseOfTreatment, patientID) "
													+ "VALUES('"+ courseOfTreatment +"', " + patientID + ");";
			int numRowsUpdated = stmt.executeUpdate(sql);
			connection.commit();
			return numRowsUpdated == 1 ? true : false;
		} catch (SQLException e) {
			DBConnect.rollback(connection);
			DBConnect.printSQLError(e);
			return false;
		}
	}
	
	// isComplete
	public boolean setComplete(Connection connection) {
		try(Statement stmt = connection.createStatement()) {
			String sql = "UPDATE CourseOfTreatment SET complete = TRUE WHERE courseOfTreatment = " + courseOfTreatment + ";";
			int numRowsUpdated = stmt.executeUpdate(sql);
			connection.commit();
			return numRowsUpdated == 1 ? true : false;
		} catch (SQLException e) {
			DBConnect.rollback(connection);
			DBConnect.printSQLError(e);
			return false;
		}
	}
	
	// Static methods
	public static CourseOfTreatment getCourseOfTreatment(Connection connection, int patientID) {
		CourseOfTreatment courseOfTreatment = new CourseOfTreatment();
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT * FROM CourseOfTreatment WHERE patientID = " + patientID + " AND  complete = FALSE ;";
			
			ResultSet res = stmt.executeQuery(sql);
			if(res.first())
				courseOfTreatment = new CourseOfTreatment(res.getInt("courseOfTreatment"), res.getInt("patientID"), 
																							res.getBoolean("complete"));
			return courseOfTreatment;
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		}
		return courseOfTreatment;
	}

}
