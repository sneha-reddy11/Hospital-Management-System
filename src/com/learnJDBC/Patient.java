package com.learnJDBC; // Declares that this class belongs to the package named 'com.learnJDBC'

import java.sql.*; // Imports all classes from the java.sql package (e.g., Connection, PreparedStatement, ResultSet, etc.)
import java.sql.Connection; // Specifically imports the Connection class (though already included by the above line)
import java.util.Scanner; // Imports Scanner class to take input from the user through the console

public class Patient { // Declaration of the public class 'Patient'
	private Connection connection; // Declares a private variable 'connection' of type Connection to connect to the database
	private Scanner scanner; // Declares a private variable 'scanner' of type Scanner to read user input

	public Patient(Connection connection,Scanner scanner) // Constructor that accepts a Connection and Scanner object
	{
		this.connection=connection; // Assigns the passed connection object to the class-level connection variable
		this.scanner=scanner; // Assigns the passed scanner object to the class-level scanner variable
	}
	
	public void addPatient() // Method to add a new patient to the database
	{
		System.out.println("Enter Patient's Name"); // Prompting the user to enter patient's name
		String name=scanner.next(); // Reading patient's name from the user input
		
		System.out.println("Enter Patient's Age"); // Prompting the user to enter patient's age
		int age=scanner.nextInt(); // Reading patient's age from the user input
		
		System.out.println("Enter Patient's Gender"); // Prompting the user to enter patient's gender
		String gender=scanner.next(); // Reading patient's gender from the user input
	
	try {
		String Query="Insert into patients(name,age,gender) values(?,?,?)"; // SQL query to insert patient data with placeholders
		
		PreparedStatement preparedStatement=connection.prepareStatement(Query); // Creating a PreparedStatement object with the SQL query
		
		preparedStatement.setString(1, name); // Replacing 1st placeholder with patient's name
		preparedStatement.setInt(2, age); // Replacing 2nd placeholder with patient's age
		preparedStatement.setString(3, gender); // Replacing 3rd placeholder with patient's gender
		
		int affectedRows=preparedStatement.executeUpdate(); // Executing the insert query and storing number of affected rows
		
		if(affectedRows>0) { // If one or more rows are affected, insertion was successful
			System.out.println("Patient Added Successfully"); // Informing user of successful addition
		}
		else {
			System.out.println("Failed to add patient!!"); // Informing user that patient could not be added
		}
	}catch(SQLException e) { // Catching any SQL exception that occurs during insertion
		e.printStackTrace(); // Printing stack trace to debug the error
	}
    }
	
	public void viewPatients() { // Method to retrieve and display all patient records
		String query="select * from patients"; // SQL query to select all patient records
		
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query); // Creating PreparedStatement to execute query
			ResultSet resultset=preparedStatement.executeQuery(); // Executing the query and storing results in a ResultSet
			
			System.out.println("Patients: "); // Displaying heading for output
			System.out.println("+-------------+---------------------+------+----------+"); // Table border
			System.out.println("| Patient ID  | Name                | Age  | Gender   |"); // Table headers
			System.out.println("+-------------+---------------------+------+----------+"); // Table border
			
			while(resultset.next()) { // Looping through the ResultSet
				int id=resultset.getInt("id"); // Getting 'id' from the current row
				String name=resultset.getString("name"); // Getting 'name' from the current row
				int age=resultset.getInt("age"); // Getting 'age' from the current row
				String gender=resultset.getString("gender"); // Getting 'gender' from the current row
				
				System.out.printf("|%-13s|%-21s|%-6s|%-10s|\n",id,name,age,gender); // Printing patient info in formatted row
				System.out.println("+-------------+---------------------+------+----------+"); // Printing row border
			}
		}catch(SQLException e) { // Catching any SQL exceptions that may occur
			e.printStackTrace(); // Printing the exception details
		}
	}
	
	public boolean getPatientByID(int id) // Method to check whether a patient with given ID exists
	{
		String query="select * from patients where Id=?"; // SQL query to select a patient with specific ID
		
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query); // Creating PreparedStatement with query
			preparedStatement.setInt(1, id); // Setting the ID in the query
			ResultSet resultset=preparedStatement.executeQuery(); // Executing the query and getting results
			
			if(resultset.next()) // If at least one record exists with that ID
			{
				return true; // Patient found, return true
			}
			else {
				return false; // No matching patient, return false
			}
		}catch(SQLException e) {e.printStackTrace();} // Handling any exceptions and printing stack trace
		
		return false; // Return false in case of exception or no match
	}
}
