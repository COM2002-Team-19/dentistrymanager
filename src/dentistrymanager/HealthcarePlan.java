package dentistrymanager;

import java.sql.*;
import java.util.ArrayList;

public class HealthcarePlan {
	
	// Instance variables
	private String name;
	private double monthlyPayment;
	private ArrayList<Coverage> coverage;
	
	// Constructor
	public HealthcarePlan(String name, double monthlyPayment,  ArrayList<Coverage> coverage) {
		this.name = name;
		this.monthlyPayment = monthlyPayment;
		this.coverage = coverage;
	}
	public HealthcarePlan(String name, double monthlyPayment) {
		this(name, monthlyPayment, new ArrayList<>());
	}
	
	// Accessors
	public String getName() {
		return name;
	}
	public double getMonthlyPayment() {
		return monthlyPayment;
	}
	public ArrayList<Coverage> getCoverage() {
		return coverage;
	}
	
	// toString
	public String toString() {
		String plan = name + " | " + monthlyPayment + " | ";
		
		for(Coverage cov: coverage)
			plan += cov.toString();
		return  plan;
	}
	
	// Static methods
	
	//Returns list of all HealthcarePlans in database
	public static ArrayList<HealthcarePlan> getAll(Connection connection) {
		ArrayList<HealthcarePlan> plans = new ArrayList<>();
		try(Statement stmt = connection.createStatement()) {
			String query = "SELECT * FROM HealthcarePlan;";
			ResultSet res = stmt.executeQuery(query);
			while(res != null) {
				String plan = res.getString("name");
				
				// Get the plan coverage
				ArrayList<Coverage> planCoverage = Coverage.getCoverageByPlan(connection, plan);
				plans.add(new HealthcarePlan(plan, res.getDouble("monthlyPayment"), planCoverage));
			}
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		} 
		return plans;
	}
}