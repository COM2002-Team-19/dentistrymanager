package dentistrymanager;

import java.sql.*;
import java.util.*;

public class DatabaseBuilder {
	
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
							+ "dateOfBirth DATE NOT NULL,"
							+ "phoneNo VARCHAR (15) NOT NULL,"
							+ "balance REAL DEFAULT 0,"
							+ "houseNumber INT (5) NOT NULL,"
							+ "postCode VARCHAR (10) NOT NULL,"
							+ "FOREIGN KEY (houseNumber, postCode) REFERENCES Address (houseNumber, postCode));",	
								
			// HealthcarePlane table
			"CREATE TABLE HealthcarePlan ("
							+ "name VARCHAR (30) NOT NULL PRIMARY KEY,"
							+ "monthlyPayment REAL);",
								
			// PatientSubscription table
			"CREATE TABLE PatientPlan ("
							+ "patientID INT (10) NOT NULL PRIMARY KEY,"
							+ "plan VARCHAR (30) NOT NULL,"
							+ "startDate DATE NOT NULL,"
							+ "endDate DATE NOT NULL,"
							+ "FOREIGN KEY (patientID) REFERENCES Patient (patientID),"
							+ "FOREIGN KEY (plan) REFERENCES HealthcarePlan (name));",
									
			// TypeOfTreatment table
			"CREATE TABLE TypeOfTreatment ("
							+ "name VARCHAR (30) NOT NULL PRIMARY KEY,"
							+ "duration INT (3));",	
																
			// Treatment table
			"CREATE TABLE Treatment ("
							+ "name VARCHAR (30) NOT NULL PRIMARY KEY,"
							+ "cost REAL NOT NULL,"
							+ "typeOfTreatment VARCHAR (30) NOT NULL,"
							+ "FOREIGN KEY (typeOfTreatment) REFERENCES TypeOfTreatment (name));",
										
			// Coverage table
			"CREATE TABLE Coverage ("
							+ "plan VARCHAR (30) NOT NULL,"
							+ "typeOfTreatment VARCHAR (30) NOT NULL,"
							+ "numOfTreatments INT(3) NOT NULL,"
							+ "costCovered REAL NOT NULL,"
							+ "PRIMARY KEY (plan, typeOfTreatment),"
							+ "FOREIGN KEY (plan) REFERENCES HealthcarePlan (name),"
							+ "FOREIGN KEY (typeOfTreatment) REFERENCES TypeOfTreatment (name));",
																
			// Partner table
			"CREATE TABLE Partner ("
							+ "name VARCHAR (30) NOT NULL PRIMARY KEY);",
						
			// Appointment table
			"CREATE TABLE Appointment ("
							+ "appointmentID INT (10) NOT NULL PRIMARY KEY,"
							+ "partner VARCHAR (30) NOT NULL,"
							+ "date DATE NOT NULL,"
							+ "startTime TIME NOT NULL,"
							+ "endTime TIME NOT NULL,"
							+ "finish BOOL DEFAULT FALSE,"
							+ "typeOfTreatment VARCHAR (30) NOT NULL,"
							+ "FOREIGN KEY (partner) REFERENCES Partner (name),"
							+ "FOREIGN KEY (typeOfTreatment) REFERENCES TypeOfTreatment (name));",
								
			// TreatmentRecord table
			"CREATE TABLE TreatmentRecord ("
							+ "treatmentRecordID INT (10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
							+ "appointmentID INT (10) NOT NULL,"
							+ "treatment VARCHAR (30) NOT NULL,"
							+ "outstandingCost REAL NOT NULL,"
							+ "coveredCost REAL NOT NULL,"
							+ "FOREIGN KEY (appointmentID) REFERENCES Appointment (appointmentID),"
							+ "FOREIGN KEY (treatment) REFERENCES Treatment (name));",	
										
			// AppointmentsPerPatient table
			"CREATE TABLE AppointmentsPerPatient ("
							+ "patientID INT (10) NOT NULL,"
							+ "appointmentID INT (10) NOT NULL,"
							+ "PRIMARY KEY (patientID, appointmentID),"
							+ "FOREIGN KEY (patientID) REFERENCES Patient (patientID),"
							+ "FOREIGN KEY (appointmentID) REFERENCES Appointment (appointmentID));",
												
			// CourseOfTreatment table
			"CREATE TABLE CourseOfTreatment ("
							+ "courseOfTreatment INT (10) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
							+ "patientID INT (10) NOT NULL,"
							+ "complete BOOL DEFAULT FALSE,"
							+ "FOREIGN KEY (patientID) REFERENCES Patient (patientID));",
											
			// AppointmentsPerCourseOfTreatment table
			"CREATE TABLE AppointmentsPerCourseOfTreatment ("
							+ "courseOfTreatment INT (10) NOT NULL,"
							+ "appointmentID INT (10) NOT NULL,"
							+ "PRIMARY KEY (courseOfTreatment, appointmentID),"
							+ "FOREIGN KEY (courseOfTreatment) REFERENCES CourseOfTreatment (courseOfTreatment),"
							+ "FOREIGN KEY (appointmentID) REFERENCES Appointment (appointmentID));",		
							
			// CoveredTreatment table
			"CREATE TABLE CoveredTreatment ("
							+ "typeOfTreatment VARCHAR (30) NOT NULL,"
							+ "patientID INT (10) NOT NULL,"
							+ "coveredTreatmentsLeft INT (3) NOT NULL,"
							+ "PRIMARY KEY (typeOfTreatment, patientID),"
							+ "FOREIGN KEY (typeOfTreatment) REFERENCES TypeOfTreatment (name),"
							+ "FOREIGN KEY (patientID) REFERENCES Patient (patientID));",
							
			// TypesOfTreatmentPerPatient table
			"CREATE TABLE TypesOfTreatmentPerPartner ("
							+ "typeOfTreatment VARCHAR (30) NOT NULL PRIMARY KEY,"
							+ "partner VARCHAR (30),"
							+ "FOREIGN KEY (typeOfTreatment) REFERENCES TypeOfTreatment (name),"
							+ "FOREIGN KEY (partner) REFERENCES Partner (name));"
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
			"DROP TABLE IF EXISTS TypesOfTreatmentPerPartner;",
			
			
			// Enable foreign key check
			"SET FOREIGN_KEY_CHECKS=1;"
	};
	
	public static final Partner[] PRESET_PARTNERS = {new Partner("HYGIENIST"),
													 new Partner("DENTIST")};
	
	public static final HealthcarePlan[] PRESET_HEALTH_PLANS = {
													new HealthcarePlan("NHS FREE PLAN", 0),
													new HealthcarePlan("MAINTENANCE PLAN", 15),
													new HealthcarePlan("ORAL HEALTHPLAN", 21),
													new HealthcarePlan("DENTAL REPAIR PLAN", 15)};
	
	public static final Coverage[] PRESET_PLAN_COVERAGE = {new Coverage("NHS FREE PLAN", "CHECK-UP", 2, 45),
															new Coverage("NHS FREE PLAN", "HYGIENE", 2, 45),
															new Coverage("NHS FREE PLAN", "REPAIR", 6, 500),
															new Coverage("MAINTENANCE PLAN", "CHECK-UP", 2, 45),
															new Coverage("MAINTENANCE PLAN", "HYGIENE", 2, 45),
															new Coverage("ORAL HEALTHPLAN", "CHECK-UP", 2, 45),
															new Coverage("ORAL HEALTHPLAN", "HYGIENE", 4, 45),
															new Coverage("DENTAL REPAIR PLAN", "CHECK-UP", 2, 45),
															new Coverage("DENTAL REPAIR PLAN", "HYGIENE", 2, 45),
															new Coverage("DENTAL REPAIR PLAN", "REPAIR", 2, 500)};
	
	
	
	public static final TypeOfTreatment[] PRESET_TYPES_OF_TREATMENT = {new TypeOfTreatment("REPAIR", 60),
																		new TypeOfTreatment("HYGIENE", 20),
																		new TypeOfTreatment("CHECK-UP", 20)};
	
	public static final Treatment[] PRESET_TREATMENTS = {new Treatment("HYGIENE", 45, "HYGIENE"),
														 new Treatment("CHECK-UP", 45, "CHECK-UP"),
														 new Treatment("SILVER AMALGAM FILLING", 90, "REPAIR"),
														 new Treatment("WHITE COMPOSITE RESIN FILLING", 150, "REPAIR"),
														 new Treatment("GOLD CROWN FITTING", 500, "REPAIR")};
	
	public static final String[][] PRESET_TYPES_TREATMENT_PER_PATIENT = {{"REPAIR", "DENTIST"}, {"CHECK-UP", "DENTIST"}, 
																		{"HYGIENE","HYGIENIST"}};
	
	// Private variables
	private Connection connection;
	
	// Constructor
	public DatabaseBuilder(Connection connection) {
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
		try(Statement stmt = connection.createStatement()) {
			
			// Drops existing tables
			for(String sql: DROP_TABLES)
				stmt.addBatch(sql);
			stmt.executeBatch();
			
			// Recreates tables
			for(String sql: CREATE_TABLES)
				stmt.addBatch(sql);
			stmt.executeBatch();
			connection.commit();
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
			DBConnect.rollback(connection);
		} 
	}
	
	public void fillWithDefaultValues() {
		try(PreparedStatement insertPartner = connection.prepareStatement("INSERT INTO Partner VALUES(?);");
			PreparedStatement insertPlan = connection.prepareStatement("INSERT INTO HealthcarePlan VALUES(?, ?);");
			PreparedStatement insertCoverage = connection.prepareStatement("INSERT INTO Coverage VALUES(?, ?, ?, ?);");
			PreparedStatement insertTypeTreatment = connection.prepareStatement("INSERT INTO TypeOfTreatment VALUES(?, ?);");
			PreparedStatement insertTreatment = connection.prepareStatement("INSERT INTO Treatment VALUES(?, ?, ?);");
			PreparedStatement insertTypeTreatPerPartner = connection.prepareStatement("INSERT INTO TypesOfTreatmentPerPartner VALUES(?, ?);");
			) {
			
			// Creates partners
			for(Partner partner: PRESET_PARTNERS) {
				insertPartner.setString(1, partner.getName());
				insertPartner.addBatch();
			} 
			insertPartner.executeBatch();
			connection.commit();
				
			// Creates types of treatments
			for(TypeOfTreatment type: PRESET_TYPES_OF_TREATMENT) {
				insertTypeTreatment.setString(1, type.getName());
				insertTypeTreatment.setInt(2, type.getDuration());
				insertTypeTreatment.addBatch();
			}
			insertTypeTreatment.executeBatch();
			
			// Creates health care plans
			for(HealthcarePlan plan: PRESET_HEALTH_PLANS) {
				insertPlan.setString(1, plan.getName());
				insertPlan.setDouble(2, plan.getMonthlyPayment());
				insertPlan.addBatch();
			}
			insertPlan.executeBatch();
	
			// Creates the health care plans coverage
			for(Coverage coverage: PRESET_PLAN_COVERAGE) {
				insertCoverage.setString(1, coverage.getPlan());
				insertCoverage.setString(2, coverage.getTypeOfTreatment());
				insertCoverage.setInt(3, coverage.getNumOfTreatments());
				insertCoverage.setDouble(4, coverage.getCostCovered());
				insertCoverage.addBatch();
			}
			insertCoverage.executeBatch();
			
			// Creates treatments
			for(Treatment treatment: PRESET_TREATMENTS) {
				insertTreatment.setString(1, treatment.getName());
				insertTreatment.setDouble(2, treatment.getCost());
				insertTreatment.setString(3, treatment.getTypeOfTreatment());
				insertTreatment.addBatch();
			}
			insertTreatment.executeBatch();	
			connection.commit();		
			
			// Creates types of treatment per patient
			for(String[] record: PRESET_TYPES_TREATMENT_PER_PATIENT) {
				insertTypeTreatPerPartner.setString(1, record[0]);
				insertTypeTreatPerPartner.setString(2, record[1]);
				insertTypeTreatPerPartner.addBatch();
			}
			insertTypeTreatPerPartner.executeBatch();
			connection.commit();
			
		} catch(SQLException e) {
			DBConnect.printSQLError(e);
			DBConnect.rollback(connection);
		} 
	}
		
}