package dentistrymanager;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TableBuilder {
	
	// Constants
	public static final String[] LIST_TABLES = {"Address", "Partner", "Treatment", "HealthcarePlan", "Patient", 
			 									"PatientSubscription", "Appointment", "Coverage", "TreatmentRecord"};
	
	public static final String[] CREATE_TABLES = {
			// Address table
			"CREATE TABLE Address (addressID INT (10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
								+ "houseNumber INT (5) NOT NULL,"
								+ "postCode VARCHAR (10) NOT NULL,"
								+ "street VARCHAR (30) NOT NULL,"
								+ "district VARCHAR(30) NOT NULL,"
								+ "city VARCHAR (30) NOT NULL);",
								
			// Partner table
			"CREATE TABLE Partner (partnerID INT (10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
								+ "name VARCHAR (30) NOT NULL);",
								
			// Treatment table
			"CREATE TABLE Treatment (treatmentID INT (10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
									+ "name VARCHAR (30) NOT NULL,"
									+ "cost REAL);",
								
			// HealthcarePlane table
			"CREATE TABLE HealthcarePlan (planID INT (10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
										+ "name VARCHAR (30) NOT NULL,"
										+ "monthlyPayment REAL);",
			
			// Patient table			
			"CREATE TABLE Patient (patientID INT (10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
								+ "title VARCHAR (5) NOT NULL,"
								+ "forename VARCHAR (50) NOT NULL,"
								+ "surname VARCHAR (50) NOT NULL,"
								+ "dateOfBirth VARCHAR (10) NOT NULL,"
								+ "contactPhoneNo VARCHAR (15) NOT NULL,"
								+ "addressID INT (10) NOT NULL,"
								+ "FOREIGN KEY (addressID) REFERENCES Address (addressID));",
			
			
			// PatientSubscription table
			"CREATE TABLE PatientSubscription (patientID INT (10) NOT NULL,"
									+ "planID INT (10) NOT NULL,"
									+ "startDate DATE,"
									+ "endDate DATE,"
									+ "FOREIGN KEY (patientID) REFERENCES Patient (patientID),"
									+ "FOREIGN KEY (planID) REFERENCES HealthcarePlan (planID));",
			
										
			// Appointment table			
			"CREATE TABLE Appointment (appointmentID INT (10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
									+ "patientID INT (10) NOT NULL,"
									+ "partnerID INT (10) NOT NULL,"
									+ "date DATE,"
									+ "startTime TIME,"
									+ "endTime TIME,"
									+ "finished BOOL DEFAULT FALSE,"
									+ "FOREIGN KEY (patientID) REFERENCES Patient (patientID),"
									+ "FOREIGN KEY (partnerID) REFERENCES Partner (partnerID));",
							
			// Coverage table
			"CREATE TABLE Coverage (planID INT (10) NOT NULL,"
									+ "treatmentID INT (10) NOT NULL,"
									+ "numOfTreatments INT(3) NOT NULL,"
									+ "FOREIGN KEY (planID) REFERENCES HealthcarePlan (planID),"
									+ "FOREIGN KEY (treatmentID) REFERENCES Treatment (treatmentID));",
			
			
			// TreatmentRecord table
			"CREATE TABLE TreatmentRecord(appointmentID INT (10) NOT NULL,"
										+ "treatmentID INT (10) NOT NULL,"
										+ "FOREIGN KEY (appointmentID) REFERENCES Appointment (appointmentID),"
										+ "FOREIGN KEY (treatmentID) REFERENCES Treatment (treatmentID));"									
	};
	
	public static final String[] DROP_TABLES = {
			
			// Disable foreign key check to allow drop without foreign key constraint violations
			"SET FOREIGN_KEY_CHECKS=0;",
			
			// Drop tables
			"DROP TABLE IF EXISTS Appointment;",
			"DROP TABLE IF EXISTS Patient;",
			"DROP TABLE IF EXISTS PatientSubscription;",
			"DROP TABLE IF EXISTS Address;",
			"DROP TABLE IF EXISTS Partner;",
			"DROP TABLE IF EXISTS Treatment;",
			"DROP TABLE IF EXISTS HealthcarePlan;",
			"DROP TABLE IF EXISTS Coverage;",
			"DROP TABLE IF EXISTS TreatmentRecord;",
			
			// Enable foreign key check
			"SET FOREIGN_KEY_CHECKS=1;"
	};
	
	public static final HealthcarePlan[] PRESET_HEALTH_PLANS = {new HealthcarePlan("NHS free plan", 0),
																new HealthcarePlan("Maintenance plan", 15),
																new HealthcarePlan("Oral health plan", 21),
																new HealthcarePlan("Dental repair plan", 36)
	};
	
	
	
	
	// Private variables
	private Connection connection;
	
	// Constructor
	public TableBuilder(Connection connection) {
		this.connection = connection;	
	}
	
	// Methods
	public boolean checkIfAllTablesExist() {
		
		Set<String> tables = new HashSet<String>(Arrays.asList(LIST_TABLES));
		boolean existAllTables = true;
		try {
			DatabaseMetaData dbMetaData = connection.getMetaData();
			
			// Obtains the names of all tables
			ResultSet res = dbMetaData.getTables(null, null, "%", null);
			
			// Creates a set with the tables in the database
			Set<String> tablesInDB = new HashSet<String>();
			while (res.next())
				tablesInDB.add(res.getString(3));
			
			// Checks that the database contains all the tables
			if(!tables.equals(tablesInDB))
				 existAllTables = false;
			
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		} catch(Exception e) {
			System.err.println(e);;
		} 
		return existAllTables;
	}
	
	public void resetTables() {
		dropTables();
		createTables();
	}
		
	// Private methods
	private void dropTables() {
		try (Statement stmt = connection.createStatement()) {
			for(String query: DROP_TABLES)
				stmt.addBatch(query);
			stmt.executeBatch();
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		} catch(Exception e) {
			System.err.println(e);;
		} 
	}
	
	private void createTables() {
		try (Statement stmt = connection.createStatement()) {
			for(String query: CREATE_TABLES)
				stmt.addBatch(query);
			stmt.executeBatch();
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
		} catch(Exception e) {
			System.err.println(e);;
		} 
	}
}