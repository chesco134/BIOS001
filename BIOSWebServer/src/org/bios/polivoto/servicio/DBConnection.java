package org.bios.polivoto.servicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private Connection con;
	
	public Connection makeConnection(String usr, String psswd) {
		con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connStatement = ("jdbc:mysql://localhost/HelloDatabase");
			con = DriverManager.getConnection(connStatement,usr,psswd);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("\n\n\n\t " + e.toString() + "\n\n\n");
		}
		return con;
	}

	public void closeConnection() {
		try {
			con.close();			
		} catch (SQLException e) {
			System.out.println("\n\n\n\t " + e.toString() + "\n\n\n");
		}
	}
}