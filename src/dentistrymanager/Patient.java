package dentistrymanager;

import java.sql.*;

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
	public Patient(int id, Title t, String f, String s, long d, String p, double b, Address a){
		patientID = id;
		title = t;
		forename = f.toUpperCase().substring(0, 1) + f.substring(1).toLowerCase(); // saves in form Xx...xx
		surname = s.toUpperCase().substring(0, 1) + s.substring(1).toLowerCase();
		dateOfBirth = d;
		phoneNo = p;
		balance = b;
		address = a;
	}
	
	// Chained constructor allowing string param for title
	public Patient(String t, String f, String s, long d, String p, double b, Address a){
		this(0, Title.called(t), f, s, d, p, b, a);
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
			stmt.executeUpdate(sql);
			connection.commit();
			return true;
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
			DBConnect.rollback(connection);
			return false;
		}
	}
	
	// Delete
	public boolean delete(Connection connection) throws DeleteForeignKeyException{
		try(Statement stmt = connection.createStatement()){
			String sql = "DELETE FROM Patient WHERE patientID = " + patientID;
			int numRows = stmt.executeUpdate(sql);
			return numRows == 1 ? true : false;
		} catch(SQLException e) {
			if(e.getErrorCode() == 1451)
				throw new DeleteForeignKeyException("Patient", this.toString());
			else {
				DBConnect.printSQLError(e);
				return false;
			}
		}
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
