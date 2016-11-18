<<<<<<< HEAD:src/dentistrymanager/HealthCarePlan.java
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
	
	// OtherMethods
	public String toString() {
		String plan = name + " | " + monthlyPayment + " | ";
		
		for(Coverage cov: coverage)
			plan += cov.toString();
		return  plan;
	}
	
	// Static methods
	public static ArrayList<HealthcarePlan> getAll(Connection connection) {
		ArrayList<HealthcarePlan> plans = new ArrayList<>();
		try(Statement stmt = connection.createStatement()) {
			String query = "SELECT * FROM HealthCarePlan;";
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
=======
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
	
	// OtherMethods
	public String toString() {
		
		// Complete method ...
		return  "";
	}
	
	// Static methods
	public static ArrayList<HealthcarePlan> getAll(Connection connection) {
		ArrayList<HealthcarePlan> plans = new ArrayList<>();
		try(Statement stmt = connection.createStatement()) {
			String query = "SELECT * FROM HealthCarePlan;";
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
>>>>>>> 46e12039940669943b1a4afe55b0e7c00fa74509:src/dentistrymanager/HealthcarePlan.java
}