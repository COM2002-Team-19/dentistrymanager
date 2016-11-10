package dentistrymanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HealthcarePlan {
	
	// Instance variables
	private String name;
	private double monthlyPayment;
	
	// Constructor
	public HealthcarePlan(String name, double monthlyPayment) {
		this.name = name;
		this.monthlyPayment = monthlyPayment;
	}
	
	// Accessors
	public String getName() {
		return name;
	}

	public double getMonthlyPayment() {
		return monthlyPayment;
	}
	
	// Static methods
	public static ArrayList<HealthcarePlan> getPlans(Connection connection) {
		
		ArrayList<HealthcarePlan> plans = new ArrayList<>();
		
		try(Statement stmt = connection.createStatement()) {
			String query = "SELECT * FROM HealthCarePlan;";
			ResultSet res = stmt.executeQuery(query);
			while(res != null) {
				plans.add(new HealthcarePlan(res.getString("name"), res.getDouble("monthlyPayment")));
			}
		} catch (SQLException e) {
			DBConnect.printSQLError(e);
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return plans;
	}	
}