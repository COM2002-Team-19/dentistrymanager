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
	public static final String[] LIST_TABLES = {"Address", "Patient", "PatientPlan", "HealthcarePlan", "Coverage", 
												"TypeOfTreatment", "Treatment", "TreatmentRecord", "Appointment", 
												"Partner", "AppointmentsPerPatient", "CourseOfTreatment",
												"AppointmentsPerCourseOfTreatment", "CoveredTreatment"};
	
	public static final String[] CREATE_TABLES = {
			// Address table
			"CREATE TABLE Address ("
							+ "houseNumber INT (5) NOT NULL,"
							+ "postCode VARCHAR (10) NOT NULL,"
							+ "street VARCHAR (30) NOT NULL,"
							+ "district VARCHAR(30) NOT NULL,"
							+ "city VARCHAR (30) NOT NULL,"
							+ "PRIMARY KEY (houseNumber, postCode));",
								
			// Patient table			
			"CREATE TABLE Patient ("
							+ "patientID INT (10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
							+ "title VARCHAR (6) NOT NULL,"
							+ "forename VARCHAR (50) NOT NULL,"
							+ "surname VARCHAR (50) NOT NULL,"
							+ "dateOfBirth VARCHAR (10) NOT NULL,"
							+ "phoneNo VARCHAR (15) NOT NULL,"
							+ "balance REAL DEFAULT 0,"
							+ "houseNumber INT (5) NOT NULL,"
							+ "postCode VARCHAR (10) NOT NULL,"
							+ "FOREIGN KEY (houseNumber, postCode) REFERENCES Address (houseNumber, postCode));",	
								
			// HealthcarePlane table
			"CREATE TABLE HealthcarePlan ("
							+ "planID INT (10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
							+ "name VARCHAR (30) NOT NULL,"
							+ "monthlyPayment REAL);",
								
			// PatientSubscription table
			"CREATE TABLE PatientPlan ("
							+ "patientID INT (10) NOT NULL PRIMARY KEY,"
							+ "planID INT (10) NOT NULL,"
							+ "startDate DATE NOT NULL,"
							+ "endDate DATE NOT NULL,"
							+ "FOREIGN KEY (patientID) REFERENCES Patient (patientID),"
							+ "FOREIGN KEY (planID) REFERENCES HealthcarePlan (planID));",
									
			// TypeOfTreatment table
			"CREATE TABLE TypeOfTreatment ("
							+ "typeID INT (10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
							+ "type VARCHAR (30) NOT NULL,"
							+ "duration INT (3));",	
																
			// Treatment table
			"CREATE TABLE Treatment ("
							+ "treatmentID INT (10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
							+ "name VARCHAR (30) NOT NULL,"
							+ "cost REAL DEFAULT 0,"
							+ "typeOfTreatmentID INT (10) NOT NULL,"
							+ "FOREIGN KEY (typeOfTreatmentID) REFERENCES TypeOfTreatment (typeID));",
										
			// Coverage table
			"CREATE TABLE Coverage ("
							+ "planID INT (10) NOT NULL,"
							+ "typeOfTreatmentID INT (10) NOT NULL,"
							+ "numOfTreatments INT(3) NOT NULL,"
							+ "costCovered REAL NOT NULL,"
							+ "PRIMARY KEY (planID, typeOfTreatmentID),"
							+ "FOREIGN KEY (planID) REFERENCES HealthcarePlan (planID),"
							+ "FOREIGN KEY (typeOfTreatmentID) REFERENCES TypeOfTreatment (typeID));",
																
			// Partner table
			"CREATE TABLE Partner ("
							+ "partnerID INT (10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
							+ "name VARCHAR (30) NOT NULL);",
						
			// Appointment table
			"CREATE TABLE Appointment ("
							+ "appointmentID INT (10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
							+ "partnerID INT (10) NOT NULL,"
							+ "date DATE NOT NULL,"
							+ "startTime TIME NOT NULL,"
							+ "endTime TIME NOT NULL,"
							+ "finish BOOL DEFAULT FALSE,"
							+ "FOREIGN KEY (appointmentID) REFERENCES Partner (partnerID));",
								
			// TreatmentRecord table
			"CREATE TABLE TreatmentRecord ("
							+ "appointmentID INT (10) NOT NULL,"
							+ "treatmentID INT (10) NOT NULL,"
							+ "PRIMARY KEY (appointmentID, treatmentID),"
							+ "FOREIGN KEY (appointmentID) REFERENCES Appointment (appointmentID),"
							+ "FOREIGN KEY (treatmentID) REFERENCES Treatment (treatmentID));",	
										
			// AppointmentsPerPatient table
			"CREATE TABLE AppointmentsPerPatient ("
							+ "patientID INT (10) NOT NULL,"
							+ "appointmentID INT (10) NOT NULL,"
							+ "PRIMARY KEY (patientID, appointmentID),"
							+ "FOREIGN KEY (patientID) REFERENCES Patient (patientID),"
							+ "FOREIGN KEY (appointmentID) REFERENCES Appointment (appointmentID));",
												
			// CourseOfTreatment table
			"CREATE TABLE CourseOfTreatment ("
							+ "courseOfTreatmentID INT (10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
							+ "patientID INT (10) NOT NULL,"
							+ "isComplete BOOL DEFAULT FALSE,"
							+ "FOREIGN KEY (patientID) REFERENCES Patient (patientID));",
											
			// AppointmentsPerCourseOfTreatment table
			"CREATE TABLE AppointmentsPerCourseOfTreatment ("
							+ "courseOfTreatmentID INT (10) NOT NULL,"
							+ "appointmentID INT (10) NOT NULL,"
							+ "PRIMARY KEY (courseOfTreatmentID, appointmentID),"
							+ "FOREIGN KEY (courseOfTreatmentID) REFERENCES CourseOfTreatment (courseOfTreatmentID),"
							+ "FOREIGN KEY (appointmentID) REFERENCES Appointment (appointmentID));",		
							
			// CoveredTreatment table
			"CREATE TABLE CoveredTreatment ("
							+ "typeOfTreatmentID INT (10) NOT NULL,"
							+ "patientID INT (10) NOT NULL,"
							+ "coveredTreatmentsLeft INT (3) NOT NULL,"
							+ "PRIMARY KEY (typeOfTreatmentID, patientID),"
							+ "FOREIGN KEY (typeOfTreatmentID) REFERENCES TypeOfTreatment (typeID),"
							+ "FOREIGN KEY (patientID) REFERENCES Patient (patientID));"
												
	};
	
	public static final String[] DROP_TABLES = {
			
			// Disable foreign key check to allow drop without foreign key constraint violations
			"SET FOREIGN_KEY_CHECKS=0;",
			
			// Drop tables
			"DROP TABLE IF EXISTS Address;",
			"DROP TABLE IF EXISTS Patient;",
			"DROP TABLE IF EXISTS PatientPlan;",
			"DROP TABLE IF EXISTS HealthcarePlan;",
			"DROP TABLE IF EXISTS Coverage;",
			"DROP TABLE IF EXISTS TypeOfTreatment;",
			"DROP TABLE IF EXISTS Treatment;",
			"DROP TABLE IF EXISTS TreatmentRecord;",
			"DROP TABLE IF EXISTS Appointment;",
			"DROP TABLE IF EXISTS Partner;",
			"DROP TABLE IF EXISTS AppointmentsPerPatient;",
			"DROP TABLE IF EXISTS CourseOfTreatment",
			"DROP TABLE IF EXISTS AppointmentsPerCourseOfTreatment;",
			"DROP TABLE IF EXISTS CoveredTreatment;",
			
			// Enable foreign key check
			"SET FOREIGN_KEY_CHECKS=1;"
	};
	
	public static final HealthcarePlan[] PRESET_HEALTH_PLANS = {new HealthcarePlan("NHS free plan", 0),
																new HealthcarePlan("Maintenance plan", 15),
																new HealthcarePlan("Oral health plan", 21),
																new HealthcarePlan("Dental repair plan", 36)};
	
	
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
		try (Statement stmt = connection.createStatement()) {
			
			// Drops existing tables
			for(String query: DROP_TABLES)
				stmt.addBatch(query);
			stmt.executeBatch();
			
			// Recreates tables
			for(String query: CREATE_TABLES)
				stmt.addBatch(query);
			stmt.executeBatch();
			
			connection.commit();
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
			DBConnect.rollback(connection);
		} catch(Exception e) {
			System.err.println(e);
		}
	}
		
}