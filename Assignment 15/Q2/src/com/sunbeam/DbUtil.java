package com.sunbeam;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {
	
	public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/DAC";
	public static final String DB_USER = "D1_TARUNK_80767";
	public static final String DB_PASSWORD = "1535";
	
	//Loading and Registering JDBC  Driver
	static {
		try {
			Class.forName(DB_DRIVER);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	//Creating the JDBC connection
	public static Connection getConnection() throws Exception{
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}
}
