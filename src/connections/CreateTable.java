package connections;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CreateTable {

	public static void main(String[] args) {
		
		String url = "jdbc:derby://localhost:1527/couponProject;create=true";
		File f = new File("src/TABLES.txt");
		String sql = "";
		
		// loading the Apache Derby driver
		String driverName = "org.apache.derby.jdbc.ClientDriver";

		try {
			Class.forName(driverName);
			System.out.println("driver loaded");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// TODO create exception
		}
		
		try(Connection con = DriverManager.getConnection(url);
			Scanner sc = new Scanner(f);){
			
			while (sc.hasNext()) {
				sql += sc.nextLine();
			}
			String[] statements = sql.split(";");
			
			for (int i = 0; i < statements.length; i++ ) {
				
				Statement st = con.createStatement();
				st.executeUpdate(statements[i]);
			}
		} catch (SQLException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

