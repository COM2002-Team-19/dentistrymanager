package dentistrymanager;

import java.util.ArrayList;
import java.sql.*;

public class Treatment {
	
	// Instance variables 
	private String name;
	private double cost;
	private String typeOfTreatment;
	
	// Constructor
	public Treatment(String name, double cost, String typeOfTreatment) {
		this.name = name;
		this.cost = cost;
		this.typeOfTreatment = typeOfTreatment;
	}
	
	// Accessors
	public String getName() {
		return name;
	}
	public double getCost() {
		return cost;
	}
	public String getTypeOfTreatment() {
		return typeOfTreatment;
	}
	
	// Static methods
	
	// Returns list of treatments of a given type
	public static ArrayList<Treatment> getAllByType(Connection connection, String typeOfTreatment) {
		ArrayList<Treatment> treatments = new ArrayList<Treatment>();
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT * FROM Treatment WHERE typeOfTreatment = '" + typeOfTreatment + "';";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next())
				treatments.add(new Treatment(res.getString("name"), 
											res.getDouble("cost"), 
											res.getString("typeOfTreatment")));
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		} 
		return treatments;
	}
	
	public static ArrayList<Treatment> getAll(Connection connection) {
		ArrayList<Treatment> treatments = new ArrayList<Treatment>();
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT * FROM Treatment;";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next())
				treatments.add(new Treatment(res.getString("name"), 
											res.getDouble("cost"), 
											res.getString("typeOfTreatment")));
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		} 
		return treatments;
	} 
}
