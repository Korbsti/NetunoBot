package me.korbsti.netuno.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

	public static Connection connection;
	
	
	
	public static void connectMySQL(String database, String username, String password, String driver) {
		
		try {
			//Class.forName(driver);
			//jdbc:mysql://u119_Sc1h2qipRm:82BGD=sR=if6dVUaTT@Cw@y6
			
			
			//connection = DriverManager.getConnection("mysql://customer_205127_netuno:JugJsj!zp!rsLkisA5pT@na05-sql.pebblehost.com/customer_205127_netuno");
			
			connection = DriverManager.getConnection(database, username, password);
			
			if(connection != null) {
				System.out.println("Connected to database");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
}
 