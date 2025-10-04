package com.learnJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCPreparedInsert {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con=null;
		//Statement stmt=null;
		//ResultSet rs=null;
		
        String URL="jdbc:mysql://localhost:3306/JDBCMySQL";
        String Username="root";
        String Password="HappyLearning";
        String Query ="Insert into students(id,name,dept) values(?,?,?)";
        try {
        	con=DriverManager.getConnection(URL,Username,Password);
            //stmt=con.createStatement();
            PreparedStatement preparedStatement=con.prepareStatement(Query);
            preparedStatement.setInt(1,9);
            preparedStatement.setString(2,"Swetha");
            preparedStatement.setString(3,"ECE");
            preparedStatement.execute();
            con.close(); 
            System.out.println("Inserted Successfully");
	    }
        catch(SQLException e) {
        	System.out.println("In catch"+e.getMessage());
        }
        finally {
        	if(con!=null) {
        		try {
        			//rs.close();
                    //stmt.close();
                    con.close(); 
                    System.out.println("Terminated Successfully");
        		}
        		catch(Exception e) {
        			System.out.println("Oops ! some serious issue");
        		}
        	}
        }      
	}
}
