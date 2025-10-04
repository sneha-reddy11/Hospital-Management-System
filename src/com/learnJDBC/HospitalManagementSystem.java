package com.learnJDBC; // Declares that this class belongs to the 'com.learnJDBC' package

import java.sql.Connection; // Imports Connection class to handle database connection
import java.sql.DriverManager; // Imports DriverManager to establish database connection
import java.sql.PreparedStatement; // Imports PreparedStatement to execute parameterized SQL queries
import java.sql.ResultSet; // Imports ResultSet to hold data returned from queries
import java.sql.SQLException; // Imports SQLException to handle SQL-related errors
import java.util.Scanner; // Imports Scanner class for reading input from the user

public class HospitalManagementSystem { // Declares the main class 'HospitalManagementSystem'

	private static final String url="jdbc:mysql://localhost/hospital"; // URL for connecting to the 'hospital' MySQL database
    private static final String username="root"; // Database username
    private static final String password="HappyLearning"; // Database password
    
	public static void main(String[] args) { // Main method - entry point of the program
		// TODO Auto-generated method stub
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver"); // Loads the MySQL JDBC driver class
        	
        }catch(ClassNotFoundException e) { // Catches if the driver class is not found
        	e.printStackTrace(); // Prints the error stack trace
        }
        
        Scanner scanner=new Scanner(System.in); // Creates a Scanner object to read user input
        
        try {
        	Connection connection=DriverManager.getConnection(url,username,password); // Establishes connection to the database
        	
        	Patient patient=new Patient(connection, scanner); // Creates a Patient object using the database connection and scanner
        	Doctor doctor=new Doctor(connection); // Creates a Doctor object using the database connection
        	
        	while(true) { // Infinite loop to continuously show the menu until exit
        		System.out.println("Hospital Management System"); // Displays the title
        		System.out.println("1.Add Patient"); // Option 1
        		System.out.println("2.View Patients"); // Option 2
        		System.out.println("3.View Doctors"); // Option 3
        		System.out.println("4.Book Appointment"); // Option 4
        		System.out.println("5.Exit"); // Option 5
        		System.out.println("Please Enter Your Choice"); // Prompt for user choice
        		
        		int choice=scanner.nextInt(); // Reads the user's choice
        		
        		switch(choice) // Switch block to handle different menu choices
        		{
        		case 1:
        			//Add Patient
        			patient.addPatient(); // Calls addPatient() method from Patient class
        			System.out.println(); // Prints a blank line
        			break; // Exits the current case
        			
        		case 2:
        			//View  Patient
        			patient.viewPatients(); // Calls viewPatients() method from Patient class
        			System.out.println(); // Prints a blank line
        			break; // Exits the current case
        			
        		case 3:
        			//view doctors
        			doctor.viewDoctors(); // Calls viewDoctors() method from Doctor class
        			System.out.println(); // Prints a blank line
        			break; // Exits the current case
        			
        		case 4:
        			//Book Appointmnet
        			bookAppointment(patient,doctor,connection,scanner); // Calls bookAppointment() method
        			System.out.println(); // Prints a blank line
        			break; // Exits the current case
        			
        		case 5:
        			System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!!"); // Exit message
        			return; // Exits the program
        			
        		default:
        			System.out.println("Please Enter Valid Input"); // Handles invalid menu choices
        			break; // Exits default case
        		}
        	}
        }catch(SQLException e) { // Catches SQL exceptions
        	e.printStackTrace(); // Prints the error stack trace
        }
	}
	
    public static void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner) { // Method to book an appointment
        	System.out.println("Please Enter Patient ID:"); // Prompts user for patient ID
        	int patientID=scanner.nextInt(); // Reads patient ID
        	
        	System.out.println("Please  Enter Doctor ID:"); // Prompts user for doctor ID
        	int doctorID=scanner.nextInt(); // Reads doctor ID
        	
        	System.out.println("Please Enter Appointment date(YYYY-MM-DD):"); // Prompts user for appointment date
        	String appointmentDate=scanner.next(); // Reads appointment date
        	
        	if(patient.getPatientByID(patientID) && doctor.getDoctorByID(doctorID)) // Checks if both patient and doctor exist
        	{
        		if(checkDoctorAvailability(doctorID,appointmentDate,connection)) { // Checks if the doctor is available on that date
        			String appointmentQuery="INSERT INTO appointments(patients_id,doctor_id,appointment_date) values(?,?,?)"; // SQL insert query
        			
        			try {
        				PreparedStatement preparedStatement=connection.prepareStatement(appointmentQuery); // Prepares the query
        				preparedStatement.setInt(1, patientID); // Sets patient ID
        				preparedStatement.setInt(2, doctorID); // Sets doctor ID
                        preparedStatement.setString(3, appointmentDate); // Sets appointment date
                        
                        int rowsAffected=preparedStatement.executeUpdate(); // Executes the insert query
                        
                        if(rowsAffected>0) { // If at least one row is affected, appointment is booked
                        	System.out.println("Appointment Booked"); // Success message
                        }
                        else {
                        	System.out.println("Appointment could not be booked"); // Failure message
                        }
        			}catch(SQLException e) { // Catches SQL exceptions
        				e.printStackTrace(); // Prints the error stack trace
        			}
        		}
        		else {
        			System.out.println("Doctor not available on this date!!"); // Doctor not free on selected date
        		}
        	}
        	else {
        		System.out.println("Either doctor or patient doesn't exist!!!"); // If patient or doctor ID is invalid
        	}
    }
    
    public static boolean checkDoctorAvailability(int doctorID,String appointmentDate,Connection connection) // Method to check if doctor is available
    {
    	String query="SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?"; // SQL query to count appointments
    	
    	try {
    		PreparedStatement preparedStatement=connection.prepareStatement(query); // Prepares the query
    		preparedStatement.setInt(1, doctorID); // Sets doctor ID
    		preparedStatement.setString(2, appointmentDate); // Sets date
    		
    		ResultSet resultset=preparedStatement.executeQuery(); // Executes the query
    		
            if(resultset.next()) { // Moves to the first row of result
            	int count=resultset.getInt(1); // Gets the count value
            	if(count==0) { // If count is 0, doctor is available
            		return true; // Returns true - available
            	}
            	else {
            		return false; // Returns false - already booked
            	}
            }
    	}catch(SQLException e) { // Catches SQL exception
    		e.printStackTrace(); // Prints error
    	}
    	return false; // Default return value if exception occurs
    }
    
}
