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
}