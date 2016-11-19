package dentistrymanager;

public class PlanSubscription {
	
	// Instance variables
	private int patientID;
	private String plan;
	private long startDate;
	private long endDate;
	
	// Constructor
	public PlanSubscription(int patientID, String plan, long startDate, long endDate) {
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
	public long getStartDate() {
		return startDate;
	}
	public long getEndDate() {
		return endDate;
	}
	
	// Database methods
	
	
}
