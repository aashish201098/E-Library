package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/elib";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";    
    private static Connection con = null;
    private static Statement st = null;
    
	private static Connection getConnection(){
		
		try {
			con = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static Statement getStatement() {
		con = getConnection();
		try {
			st = con.createStatement();
		} catch (SQLException e) {
			System.out.println("Error in getting Statement Object");
		}
		
		return st;
	}
	
}
