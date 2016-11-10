package dentistrymanager;

import java.util.ArrayList;
import java.sql.*;

public class Treatment {
	
	// Instance variables 
	private String name;
	private double cost;
	
	// Constructor
	public Treatment(String name, double cost) {
		this.name = name;
		this.cost = cost;
	}
	
	// Accessors
	public String getName() {
		return name;
	}
	
	public double getCost() {
		return cost;
	}

	// Static methods
	public static ArrayList<Treatment> getAll(Connection connection) {
		return new ArrayList<Treatment>();
	}
	
}
