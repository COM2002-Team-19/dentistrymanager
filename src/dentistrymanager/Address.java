package dentistrymanager;

import java.sql.*;


public class Address {
	
	// Private variables
	private int houseNumber;
	private String street;
	private String district;
	private String city;
	private String postCode;
	
	// Constructor
	public Address(int houseNumber, String street, String district, String city, String postCode){
		this.houseNumber = houseNumber;
		this.street = street;
		this.district = district;
		this.city = city;
		this.postCode = postCode;
	}
	
	// Accessors
	public int getHouseNumber() {
		return houseNumber;
	}
	
	public String getStreet() {
		return street;
	}
	
	public String getDistrict() {
		return district;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getPostCode() {
		return postCode;
	}
	
	// Database methods
	
	// Add
	public boolean add(Connection connection) throws DuplicateKeyException {
		try(Statement stmt = connection.createStatement()) {;
			String sql = "INSERT INTO Address VALUES (" + houseNumber + ", '"
														+ postCode.toUpperCase() + "', '" 
														+ street + "', '"
														+ district + "', '" 
														+ city + "');";
			stmt.executeUpdate(sql);
			connection.commit();
			return true;
		} catch (SQLException e) { 
			if(e.getErrorCode() == 1062)
				throw new DuplicateKeyException("Address");
			DBConnect.printSQLError(e);
			return false;
		}
	}
	
	// Delete
	public boolean delete(Connection connection) throws DeleteForeignKeyException {
		try(Statement stmt = connection.createStatement()) {
			String sql = "DELETE FROM Address WHERE"
					+ " houseNumber = " +  houseNumber 
					+ " AND postCode = " + postCode 
					+ ");";
			int numRows = stmt.executeUpdate(sql);
			connection.commit();
			return numRows == 1 ? true : false;
		} catch (SQLException e) { 
			if(e.getErrorCode() == 1451)
				throw new DeleteForeignKeyException("Address");
			else {
				DBConnect.printSQLError(e);
				return false;
			}
		}
		
	}
}
