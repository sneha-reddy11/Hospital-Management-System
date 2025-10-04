package com.learnJDBC; // Declares that this class belongs to the 'com.learnJDBC' package

import java.sql.Connection; // Imports the Connection class to establish a connection with the database
import java.sql.PreparedStatement; // Imports PreparedStatement to execute parameterized SQL queries
import java.sql.ResultSet; // Imports ResultSet to hold data retrieved from the database
import java.sql.SQLException; // Imports SQLException to handle SQL-related exceptions

public class Doctor { // Declaration of the public class 'Doctor'
	
	private Connection connection; // Declares a private variable 'connection' to be used for database operations
	
	public Doctor(Connection connection) // Constructor that receives a Connection object
	{
		this.connection=connection; // Assigns the passed connection to the class-level connection variable
	}
	
	public void viewDoctors() { // Method to display all doctors from the database
		String query="select * from doctors"; // SQL query to fetch all records from the doctors table
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query); // Creates a PreparedStatement to execute the query
			ResultSet resultset=preparedStatement.executeQuery(); // Executes the query and stores the result in a ResultSet
			System.out.println("Doctors: "); // Displays a heading for the doctor list
			System.out.println("+----+--------+----------------+"); // Prints the top border of the table
			System.out.println("|ID  | Name   | Specialization |"); // Prints the table headers
			System.out.println("+----+--------+----------------+"); // Prints the border below the headers
			while(resultset.next()) { // Loops through each row in the ResultSet
				int id=resultset.getInt("id"); // Retrieves the doctor's ID from the current row
				String name=resultset.getString("name"); // Retrieves the doctor's name from the current row
				String specialization=resultset.getString("specialization"); // Retrieves the doctor's specialization from the current row
				
				System.out.printf("|%-4s|%-8s|%-16s|\n",id,name,specialization); // Prints the current row in formatted output
				System.out.println("+----+--------+----------------+"); // Prints a border line after each row
			}
		}catch(SQLException e) { // Catches any SQLException that may occur
			e.printStackTrace(); // Prints the exception stack trace for debugging
		}
	}
	
	public boolean getDoctorByID(int id) // Method to check whether a doctor exists with the given ID
	{
		String query="select * from doctors where Id=?"; // SQL query to fetch doctor with the specified ID
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query); // Prepares the SQL statement
			preparedStatement.setInt(1, id); // Sets the ID parameter in the query
			ResultSet resultset=preparedStatement.executeQuery(); // Executes the query and stores the result
			if(resultset.next()) // Checks if a row exists in the result (i.e., doctor found)
			{
				return true; // Returns true if doctor with given ID exists
			}
			else {
				return false; // Returns false if doctor with given ID does not exist
			}
		}catch(SQLException e) {e.printStackTrace();} // Catches and prints any SQLException
		return false; // Returns false in case of exception or no matching record
	}

}
