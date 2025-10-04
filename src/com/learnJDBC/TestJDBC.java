package com.learnJDBC;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
public class TestJDBC {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","HappyLearning");
        System.out.println("Connection Created");
	}

}
