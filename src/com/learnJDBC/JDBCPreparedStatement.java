package com.learnJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.sql.SQLException;

public class JDBCPreparedStatement {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con=null;
		//Statement stmt=null;
		ResultSet rs=null;
		
        String URL="jdbc:mysql://localhost:3306/JDBCMySQL";
        String Username="root";
        String Password="HappyLearning";
        String Query ="select * from Students where name=?";
        try {
        	con=DriverManager.getConnection(URL,Username,Password);
            //stmt=con.createStatement();
            PreparedStatement preparedStatement=con.prepareStatement(Query);
            preparedStatement.setString(1,"Maan");
            rs=preparedStatement.executeQuery();
            while(rs.next())
            {
            	System.out.println("---------------------------");
            	int id=rs.getInt("id");
            	String name=rs.getString("name");
            	String dept=rs.getString("dept");
            	
            	System.out.println("Student ID:"+id);
            	System.out.println("Student Name:"+name);
            	System.out.println("Student Department:"+dept);
            	
            	System.out.println("---------------------------");
            }
            
            rs.close();
            //stmt.close();
            con.close(); 
        }catch(SQLException e) {
        	System.out.println("In catch"+e.getMessage());
        }
        finally {
        	if(con!=null) {
        		try {
        			rs.close();
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
