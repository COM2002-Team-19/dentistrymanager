package dentistrymanager;

import java.sql.*;

public class PlanSubscription {
	
	// Instance variables
	private int patientID;
	private String plan;
	private Date startDate;
	private Date endDate;
	
	// Constructor
	public PlanSubscription(int patientID, String plan, Date startDate, Date endDate) {
		this.patientID = patientID;
		this.plan = plan;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	// Accessors
	public int getPatientID() {
		return patientID;
	}
	public String getPlan() {
		return plan;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	
	// Database methods
	
	// Static methods
	public static boolean reset(Connection connection) {
		try(Statement stmt = connection.createStatement()) {
			String currentDate = DateTimeUtilities.today();
			
			String sql = "DELETE FROM CoveredTreatment WHERE patientID = "
							+ "(SELECT patientID FROM PatientPlan WHERE endDate <= '" + currentDate + "');";
			int numRowsDeleted = stmt.executeUpdate(sql);
			
			sql = "INSERT INTO CoveredTreatment "
					+ "SELECT c.typeOfTreatment, pp.patientID, c.numOfTreatments "
					+ "FROM Coverage c, PatientPlan pp "
					+ "WHERE c.plan = pp.plan AND pp.endDate <= '" + currentDate + "';";
			
			int numRowsCreated = stmt.executeUpdate(sql);
			if(numRowsDeleted == numRowsCreated)
				return true;
			else {
				DBConnect.rollback(connection);
				return false;
			}
		} catch (SQLException e) {
			DBConnect.rollback(connection);
			DBConnect.printSQLError(e);
			return false;
		}
	}
	
}