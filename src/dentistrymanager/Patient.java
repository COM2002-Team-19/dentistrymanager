package dentistrymanager;

import java.sql.*;
import java.util.ArrayList;

public class Patient {
	
	// Instance variables
	private int patientID;
	private Title title;
	private String forename;
	private String surname;
	private long dateOfBirth; // date of birth in format YYYYMMDD
	private String phoneNo;
	private double balance;
	private Address address;
	private PlanSubscription healthCarePlan;
		
	// Constructor
	public Patient(int id, String t, String f, String s, long d, String p, double b, Address a, PlanSubscription pl){
		patientID = id;
		title = Title.called(t);
		forename = f.toUpperCase().substring(0, 1) + f.substring(1).toLowerCase(); // saves in form Xx...xx
		surname = s.toUpperCase().substring(0, 1) + s.substring(1).toLowerCase();
		dateOfBirth = d;
		phoneNo = p;
		balance = b;
		address = a;
		healthCarePlan = pl;
	}
	
	// Chained constructor without plan
	public Patient(int id, String t, String f, String s, long d, String p, double b, Address a){
		this(id, t, f, s, d, p, b, a, null);
	}
	
	// Chained constructor with default 0 values for ID and balance
	public Patient(String t, String f, String s, long d, String p, Address a){
		this(0, t, f, s, d, p, 0.00, a, null);
	}
	
	public Patient() {
		this(0, "", "asdf", "qwerty", 0, "", 0, null, null);
	}
	

	// Accessors
	public int getPatientID() { return patientID; }
	public Title getTitle() { return title;	}
	public String getForename() { return forename;}
	public String getSurname() { return surname;}
	public long getDateOfBirth() { return dateOfBirth;}
	public String getPhoneNo() { return phoneNo;}
	public double getBalance() { return balance;}
	public Address getAddress() { return address;}
	public PlanSubscription gethealthCarePlan() { return healthCarePlan;}
	
	// Other methods
	public boolean hasHealthCarePlan() {
		return healthCarePlan == null ? false : true;
	}
	
	public String toString() {
		String s = "";
		if (title != null)
			s += title + " ";
		s += this.forename + " " + this.surname + "\nBorn: " + this.dateOfBirth + "\nContact number: " + this.phoneNo;
		if (address != null)
			s += "\n" + this.address;
		return s;
	}
	
	// Database Methods
	
	// Add
	public boolean add(Connection connection) {
		try(Statement stmt = connection.createStatement()) {
			String sql = "INSERT INTO Patient (title, forename, surname, dateOfBirth, phoneNo, balance, "
												+ "houseNumber, postCode) "
												+ "VALUES ('" 	+ title + "', '"
																+ forename + "', '"
																+ surname + "', '"
																+ dateOfBirth + "', '"
																+ phoneNo + "', "
																+ balance + ", "
																+ address.getHouseNumber() + ", '"
																+ address.getPostCode() + "');";
			int numRowsUpdated = stmt.executeUpdate(sql);
			connection.commit();
			return numRowsUpdated == 1 ? true : false;
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
			DBConnect.rollback(connection);
			return false;
		}
	}
	
	// Delete
	public boolean delete(Connection connection) throws DeleteForeignKeyException {
		try(Statement stmt = connection.createStatement()) {
			String sql = "DELETE FROM Patient WHERE patientID = " + patientID;
			int numRowsUpdated = stmt.executeUpdate(sql);
			connection.commit();
			return numRowsUpdated == 1 ? true : false;
		} catch(SQLException e) {
			DBConnect.rollback(connection);
			if(e.getErrorCode() == 1451)
				throw new DeleteForeignKeyException("Patient", this.toString());
			else {
				DBConnect.printSQLError(e);
				return false;
			}
		}
	}
	
	// Subscribe
	public boolean subscribe(Connection connection, HealthcarePlan plan) throws DuplicateKeyException {
		try(Statement stmt = connection.createStatement()) {		
			String sql = "INSERT INTO PatientPlan VALUES (" + patientID + ", '" + plan.getName() +"', "
							        + DateUtilities.today() + ", " + DateUtilities.oneYearFromToday() +");";
			stmt.addBatch(sql);
			
			// Get the plan coverage
			ArrayList<Coverage> planCoverage = Coverage.getCoverageByPlan(connection, plan.getName());
			for(Coverage coverage: planCoverage) {
				sql = "INSERT INTO CoveredTreatment VALUES ('" + coverage.getTypeOfTreatment() + "', " 
															+ patientID + ", " + coverage.getNumOfTreatments() + ");";
				stmt.addBatch(sql);
			}
			stmt.executeBatch();
			connection.commit();
			return true;							
		} catch(SQLException e) {
			DBConnect.rollback(connection);
			if(e.getErrorCode() == 1062)
				throw new DuplicateKeyException("Plan", this.toString()); // replace by plan.toString()
			DBConnect.printSQLError(e);
			return false;
		}
	}
	
	// Unsubscribe
	public boolean unsubscribe(Connection connection) throws DeleteForeignKeyException {
		try(Statement stmt = connection.createStatement()) {
			String[] sqls = new String[] {"DELETE FROM PatientPlan WHERE patientID = " + patientID + ";",
										 "DELETE FROM CoveredTreatment WHERE patientID = " + patientID + ";"};
			for(String sql: sqls)
				stmt.addBatch(sql);
			stmt.executeBatch();
			connection.commit();
			return true;			
		} catch(SQLException e) {
			DBConnect.rollback(connection);
			if(e.getErrorCode() == 1451)
				throw new DeleteForeignKeyException("Plan", this.toString()); // replace by plan.toString()
			DBConnect.printSQLError(e);
			return false;
		}
	}
	
	public ArrayList<String> getAmountOwed(Connection connection) {
		ArrayList<String> amountOwedDetails = new ArrayList<>();
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT tr.treatment, tr.outstandingCost, tr.coveredCost "
								+ "FROM Patient p "
								+ "JOIN AppointmentsPerPatient ap ON ap.patientID = p.patientID "
								+ "JOIN Appointment a ON ap.appointmentID = a.appointmentID "
								+ "JOIN TreatmentRecord tr ON tr.appointmentID = a.appointmentID "
								+ "LEFT OUTER JOIN AppointmentsPerCourseOfTreatment acs ON acs.appointmentID = a.appointmentID "
								+ "LEFT OUTER JOIN CourseOfTreatment ct ON ct.courseOfTreatment = acs.courseOfTreatment "
								+ "WHERE a.finish = TRUE AND tr.outstandingCost > 0 "
								+ "AND (ct.complete IS NULL OR ct.complete = TRUE);";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next())
				amountOwedDetails.add(res.getString("treatment") + " " + res.getDouble("outstandingCost")  + " " + 
																						res.getDouble("coveredCost"));
			
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		}
		return amountOwedDetails;
	}
	
	// Static methods
	// Returns all patients
	public static ArrayList<Patient> getPatients(Connection connection, String patientSearch){
		ArrayList<Patient> patients = new ArrayList<>();
		try(Statement stmt = connection.createStatement()){
			String sql = "SELECT p.*, a.street, a.district, a.city, pp.plan, pp.startDate, pp.endDate FROM Patient p "
							+ "JOIN Address a ON p.houseNumber=a.houseNumber AND p.postCode=a.postCode "
							+ "LEFT OUTER JOIN PatientPlan pp ON pp.patientID=p.patientID "
							+" WHERE forename LIKE '%" + patientSearch +"%' OR surname LIKE '%"+ patientSearch +"%';";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next()){
				PlanSubscription subscribedPlan = null;
				String plan = DBUtilities.nullToBlanks(res.getString("plan"));
				if(!plan.equals(DBUtilities.BLANKS))
					subscribedPlan = new PlanSubscription(res.getInt("patientID"), res.getString("plan"),
																	res.getLong("startDate"), res.getLong("endDate"));
				
				patients.add(new Patient(res.getInt("patientID"), res.getString("title"), res.getString("forename"),
								res.getString("surname"), res.getLong("dateOfBirth"), res.getString("phoneNo"),
								res.getDouble("balance"), 
								new Address(res.getInt("houseNumber"), res.getString("Street"), res.getString("district"),
											res.getString("city"), res.getString("postCode")),	subscribedPlan
							));
			}

		}
		catch(SQLException e){
			DBConnect.printSQLError(e);
		}
		return patients;
	}
	
	public static Patient getPatientByID(Connection connection, int patientID) {
		Patient patient = new Patient();
		try(Statement stmt = connection.createStatement()){
			String sql = "SELECT p.*, a.street, a.district, a.city, pp.plan, pp.startDate, pp.endDate FROM Patient p "
							+ "JOIN Address a ON p.houseNumber = a.houseNumber AND p.postCode = a.postCode "
							+ "LEFT OUTER JOIN PatientPlan pp ON pp.patientID = p.patientID "
							+ "WHERE p.patientID = " + patientID +";";
			ResultSet res = stmt.executeQuery(sql);
			if(res.first()){
				PlanSubscription subscribedPlan = null;
				String plan = DBUtilities.nullToBlanks(res.getString("plan"));
				if(!plan.equals(DBUtilities.BLANKS))
					subscribedPlan = new PlanSubscription(res.getInt("patientID"), res.getString("plan"),
																	res.getLong("startDate"), res.getLong("endDate"));
				
				patient = new Patient(res.getInt("patientID"), res.getString("title"), res.getString("forename"),
										res.getString("surname"), res.getLong("dateOfBirth"), res.getString("phoneNo"),
										res.getDouble("balance"), 
										new Address(res.getInt("houseNumber"), res.getString("Street"), res.getString("district"),
													res.getString("city"), res.getString("postCode")), subscribedPlan);
			}
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		}
		return patient;
	}
}