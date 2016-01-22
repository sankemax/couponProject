package connections;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DbOperations {
	
	private static final String dbUrl = "jdbc:derby://localhost:1527/couponDB;create=true;user=admin;password=123";
	private static final String driverName = "org.apache.derby.jdbc.ClientDriver"; 
	private static final String tableInfoPath = "src/TABLES.txt";
	
	public static void main(String[] args) {	
		createDbAndTables();
	}
	
	public static void createDbAndTables() {
		
		File file = new File(tableInfoPath);
		String sql = "";
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try(Scanner sc = new Scanner(file);){
			
			// loading the Apache Derby driver
			Class.forName(driverName);
			System.out.println("driver loaded");
			
			connection = DriverManager.getConnection(dbUrl);
			System.out.println("connection received");
			connection.setAutoCommit(false);
			
			List<String> tableNames = new ArrayList<>();		
			while (sc.hasNext()) {
				String line = sc.nextLine();
				if (line.contains("CREATE TABLE")) tableNames.add(line.split("\\s+")[2].toUpperCase());
				sql += line;
			}
			
			String[] statements = sql.split(";");
			for (int i = 0; i < statements.length; i++ ) {
								
				resultSet = connection.getMetaData().getTables(null, null, tableNames.get(i), null);
				if (!resultSet.next()) {
					
					statement = connection.createStatement();
					statement.executeUpdate(statements[i]);
					SqlUtility.closeStatement(statement);
				}
			}
			connection.commit();
			System.out.println("tables created successfully");
			
		} catch (SQLException | FileNotFoundException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SqlUtility.rollbackConnection(connection);
			
		} finally {
			
			SqlUtility.closeConnection(connection);
		}
	}
}

