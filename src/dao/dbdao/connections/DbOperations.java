package dao.dbdao.connections;

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

import core.CouponSystemException;

public class DbOperations {
	
	public static final String dbName = "couponDB";
	public static final String dbUser = "admin";
	public static final String dbPassword = "123";	
	public static final String dbUrl = "jdbc:derby://localhost:1527/" + dbName + ";create=true;user=" + dbUser + ";password=" + dbPassword;
	public static final String driverName = "org.apache.derby.jdbc.ClientDriver"; 
	public static final String tableInfoPath = "src/TABLES.txt";
	
	/**
	 * Reads the file TABLES.txt that is located in the src directory which contains an SQL script
	 *  for the creation of the tables. The method creates only the tables that are not already created.
	 *  If all the tables were already created It inserts nothing.  
	 * @throws CouponSystemException if the file TABLES.txt is not found, if the derby client drivers were not found
	 * or if there's an SQLException.
	 */
	public static void createDbAndTables() throws CouponSystemException {
		
		File file = new File(tableInfoPath);
		StringBuilder sqlBuilder = new StringBuilder();
		String sql = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try(Scanner sc = new Scanner(file);){
			
			// loading the Apache Derby driver
			Class.forName(driverName);
			
			connection = DriverManager.getConnection(dbUrl);
			connection.setAutoCommit(false);
			
			List<String> tableNames = new ArrayList<>();
			
			// reads the file TABLES.txt line by line
			while (sc.hasNext()) {
				String line = sc.nextLine();
				
				// if a line contains the command "create table", add the next word which is the tables' name to a list
				// of table names (tableNames)
				if (line.contains("CREATE TABLE")) {
					
					// split by whitespaces and take the String from the 2nd index. It contains the name of the table
					tableNames.add(line.split("\\s+")[2].toUpperCase());
				}
				sqlBuilder.append(line);
			}
			
			sql = sqlBuilder.toString();
			
			// create SQL commands for each table separately
			String[] statements = sql.split(";");
			
			// execute the commands to create each table. the loop is as big as the number of tables to create
			for (int i = 0; i < statements.length; i++ ) {
								
				// if the table already exists in the DB, the resultSet will be empty
				resultSet = connection.getMetaData().getTables(null, null, tableNames.get(i), null);
				
				// if the resultSet is empty it means the table doesn't exist and need to be created.
				// otherwise it exists and there's no need to create it.
				if (!resultSet.next()) {
					
					statement = connection.createStatement();
					statement.executeUpdate(statements[i]);
					SqlUtility.closeStatement(statement);
				}
			}
			connection.commit();
			
		} catch (SQLException | ClassNotFoundException | FileNotFoundException e) {
			
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
			
		} finally {
			SqlUtility.closeConnection(connection);
		}
	}
}

