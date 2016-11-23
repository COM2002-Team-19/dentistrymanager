package dentistrymanager;

import java.sql.*;

public class CoveredTreatment {
	
	// Instance variables
	public String typeOfTreatment;
	public int patientID;
	public int coveredTreatmentsLeft;
	
	// Constructor
	public CoveredTreatment(String typeOfTreatment, int patientID, int coveredTreatmentsLeft) {
		this.typeOfTreatment = typeOfTreatment;
		this.patientID = patientID;
		this.coveredTreatmentsLeft = coveredTreatmentsLeft;
	}
	
	// Accessors
	public String getTypeOfTreatment() {
		return typeOfTreatment;
	}
	public int getPatientID() {
		return patientID;
	}
	public int getCoveredTreatmentsLeft() {
		return coveredTreatmentsLeft;
	}
	
	// Database methods
	public boolean setCoveredTreatmentsLeft(Connection connection, int coveredTreatmentsLeft) {
		try(Statement stmt = connection.createStatement()) {
			String sql = "UPDATE CoveredTreatment SET coveredTreatmentsLeft = " + coveredTreatmentsLeft
					 							+ " WHERE typeOfTreatment = '" + typeOfTreatment + "' AND "
					 							+ " patientID = " + patientID + ";";
			int numRowsUpdated = stmt.executeUpdate(sql);
			return numRowsUpdated == 1 ? true : false;
		} catch(SQLException e) {
			DBConnect.rollback(connection);
			DBConnect.printSQLError(e);
			return false;
		}
	}
	
	public static boolean updateCoveredTreatments(Connection connection, int appointmentID, int patientID) {
		try(Statement stmt = connection.createStatement()) {
			connection.setAutoCommit(false);
			String sql = "UPDATE CoveredTreatment SET coveredTreatmentsLeft = coveredTreatmentsLeft - 1 "
							+ "WHERE coveredTreatmentsLeft > 0 AND patientID = " + patientID + " "
							+ "AND typeOfTreatment IN (SELECT t.typeOfTreatment FROM TreatmentRecord tr, Treatment t"
							+ " WHERE tr.treatment = t.name AND tr.appointmentID = " + appointmentID + ");";
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
	public static double getCoveredCost(Connection connection, int patientID, String treatment) {
		double costCovered = 0;
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT c.costCovered "
						+ "FROM Patient p, PatientPlan pp, Coverage c, CoveredTreatment ct, Treatment t "
						+ "WHERE ct.patientID = p.patientID "
						+ "AND pp.patientID = p.patientID "
						+ "AND c.typeOfTreatment = t.typeOfTreatment "
						+ "AND c.plan = pp.plan "
						+ "AND t.typeOfTreatment = ct.typeOfTreatment "
						+ "AND p.patientID = '" + patientID + "' "
						+ "AND t.name = '" + treatment + "' "
						+ "AND ct.coveredTreatmentsLeft > 0 ;";
			System.out.println(sql);
			ResultSet res = stmt.executeQuery(sql);
			if(res.first())
				costCovered = (double)DBUtilities.nullToZero(res.getString("costCovered"));
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		}
		return costCovered;
	}
}