package dentistrymanager;

import java.sql.*;
import java.util.ArrayList;

public class TypeOfTreatment {
	
	// Instance variables
	private String name;
	private int duration;
	private ArrayList<Treatment> treatments;
	
	// Constructors
	public TypeOfTreatment(String name, int duration, ArrayList<Treatment> treatments) {
		this.name = name;
		this.duration = duration;
		this.treatments = treatments;
	}
	public TypeOfTreatment(String name, int duration) {
		this(name, duration, new ArrayList<>());
	}
	
	// Accessors
	public String getName() {
		return name;
	}
	public int getDuration() {
		return duration;
	}
	public ArrayList<Treatment> getTreatments() {
		return treatments;
	}
	
	// Static methods
	public static ArrayList<TypeOfTreatment> getAll(Connection connection) {
		ArrayList<TypeOfTreatment> typesOfTreatments = new ArrayList<>();
		try(Statement stmt = connection.createStatement()) {
			String sql = "SELECT * FROM TypeOfTreatment;";
			ResultSet res = stmt.executeQuery(sql);
			while(res.next()) {
				String name = res.getString("name");
				
				// Returns the treatments associated with the type
				ArrayList<Treatment> treatments = Treatment.getAllByType(connection, name);
				
				typesOfTreatments.add(new TypeOfTreatment(name, res.getInt("duration"), treatments));
			}
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		} 
		return typesOfTreatments;
	}	
}
