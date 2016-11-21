package dentistrymanager;

import java.sql.*;
import java.util.ArrayList;

public class TreatmentRecord {
	
	// Instance variables
	public int treatmentRecordID;
	public int appointmentID;
	public String treatment;
	public double outstandingCost;
	public double coveredCost;
	
	// Constructor
	public TreatmentRecord(int treatmentRecordID, int appointmentID, String treatment, double outstandingCost, double coveredCost) {
		this.treatmentRecordID = treatmentRecordID;
		this.appointmentID = appointmentID;
		this.treatment = treatment;
		this.outstandingCost = outstandingCost;
		this.coveredCost = coveredCost;
	}
	
	public TreatmentRecord(int appointmentID, String treatment, double outstandingCost, double coveredCost) {
		this(0, appointmentID, treatment, outstandingCost, coveredCost);
	}
	
	// Accessors
	public int getAppointmentID() {
		return appointmentID;
	}
	public String getTreatment() {
		return treatment;
	}
	public double getOutstandingCost() {
		return outstandingCost;
	}
	public double getCoveredCost() {
		return coveredCost;
	}
	
	// Other methods
	public String toString() {
		return appointmentID + " | " + treatment + " | " + outstandingCost + " | " + coveredCost;
	}
	
	// Database methods
	
	// Add treatment record to database
	public boolean add(Connection connection) throws DuplicateKeyException {
		try(Statement stmt = connection.createStatement()) {
			String sql = "INSERT INTO TreatmentRecord (appointmentID, treatment, outstandingCost, coveredCost) "
						+ "VALUES (" + appointmentID + ", '" + treatment + "', " + outstandingCost + ", " + coveredCost + ");";
			stmt.executeUpdate(sql);
			connection.commit();
			return true;
		} catch (SQLException e) {
			DBConnect.rollback(connection);
			if(e.getErrorCode() == 1062)
				throw new DuplicateKeyException("TreatmentRecord"); // replace by plan.toString()
			DBConnect.printSQLError(e);
			return false;
		}
	}
	
	// Delete treatment record to database
	public boolean delete(Connection connection) throws DeleteForeignKeyException {
		try(Statement stmt = connection.createStatement()) {
			String sql = "DELETE FROM TreatmentRecord WHERE treatmentRecordID = " + treatmentRecordID + ";"; 
			stmt.executeUpdate(sql);
			connection.commit();
			return true;
		} catch(SQLException e) {
			DBConnect.rollback(connection);
			if(e.getErrorCode() == 1451)
				throw new DeleteForeignKeyException("TreatmentRecord"); // replace by plan.toString()
			return false;
		}
	}
	
	// Static methods
	
	// Returns all recorded treatments from an appointment
	public static ArrayList<TreatmentRecord> getAll(Connection connection, int appointmentID) {
		ArrayList<TreatmentRecord> treatmentsPerformed = new ArrayList<>();
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT * FROM TreatmentRecord WHERE appointmentID = " + appointmentID + ";";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next())
				treatmentsPerformed.add(new TreatmentRecord(res.getInt("treatmentRecordID"), 
															res.getInt("appointmentID"), res.getString("treatment"), 
															res.getDouble("outstandingCost"), res.getDouble("coveredCost"))); 
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		}
		return treatmentsPerformed;
	}
}
