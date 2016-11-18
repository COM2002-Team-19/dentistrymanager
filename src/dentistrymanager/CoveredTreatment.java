package dentistrymanager;

import java.sql.*;

public class CoveredTreatment {
	
	public String typeOfTreatment;
	public int patientID;
	public int coveredTreatmentsLeft;
	
	public CoveredTreatment(String typeOfTreatment, int patientID, int coveredTreatmentsLeft) {
		this.typeOfTreatment = typeOfTreatment;
		this.patientID = patientID;
		this.coveredTreatmentsLeft = coveredTreatmentsLeft;
	}
	
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

}
