package dentistrymanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Address {
	
	// Private variables
	private int houseNumber;
	private String street;
	private String district;
	private String city;
	private String postCode;
	
	// Constructor
	public Address(	int houseNumber, String street, String district, 
					String city, String postCode){
		this.houseNumber = houseNumber;
		this.street = streer;
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
	
	public static Address getAddress(Connection connection, int addressID) {
		
		Address address = null;
		
		try(Statement stmt = connection.createStatement()){
			String query = "SELECT * FROM Address WHERE addressID = "+addressID;
			ResultSet res = stmt.executeQuery(query);
			address = new Address(	res.getInt("houseNumber"), 
									res.getString("street"), 
									res.getString("district"), 
									res.getString("city"), 
									res.getString("postcode"));
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return address;
	}
}
