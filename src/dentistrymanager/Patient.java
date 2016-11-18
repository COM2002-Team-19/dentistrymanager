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
		
	// Constructor
	public Patient(int id, String t, String f, String s, long d, String p, double b, Address a){
		patientID = id;
		title = Title.called(t);
		forename = f.toUpperCase().substring(0, 1) + f.substring(1).toLowerCase(); // saves in form Xx...xx
		surname = s.toUpperCase().substring(0, 1) + s.substring(1).toLowerCase();
		dateOfBirth = d;
		phoneNo = p;
		balance = b;
		address = a;
	}
	
	// Chained constructor with default 0 values for ID and balance
	public Patient(String t, String f, String s, long d, String p, Address a){
		this(0, t, f, s, d, p, 0.00, a);
	}
	

	// Accessors
	public Title getTitle() { return title;	}
	public String getForename() { return forename;}
	public String getSurname() { return surname;}
	public long getDateOfBirth() { return dateOfBirth;}
	public String getPhoneNo() { return phoneNo;}
	public double balance() { return balance;}
	public Address getAddress() { return address;}
	
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
	
	// Static methods
	public static ArrayList<Patient> getPatient(Connection connection, String patientSearch){
		ArrayList<Patient> patients = new ArrayList<>();
		try(Statement stmt = connection.createStatement()){
			String sql = "SELECT p.*, a.street, a.district, a.city FROM Patient p "
							+ "JOIN Address a ON p.houseNumber=a.houseNumber AND p.postCode=a.postCode "
							+" WHERE forename LIKE '%" + patientSearch +"%' OR surname LIKE '%"+ patientSearch +"%';";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next()){
				patients.add(new Patient(res.getInt("patientID"), res.getString("title"), res.getString("forename"),
								res.getString("surname"), res.getLong("dateOfBirth"), res.getString("phoneNo"),
								res.getDouble("balance"), 
								new Address(res.getInt("houseNumber"), res.getString("Street"), res.getString("district"),
											res.getString("city"), res.getString("postCode"))
							));
			}

		}
		catch(SQLException e){
			DBConnect.printSQLError(e);
		}
		return patients;
	}
	
	// Other methods
	public String toString() {
		String s = "";
		if (title != null)
			s += title + " ";
		s += this.forename + " " + this.surname + "\nBorn: " + this.dateOfBirth + "\nContact number: " + this.phoneNo;
		if (address != null)
			s += "\n" + this.address;
		return s;
	}
}
