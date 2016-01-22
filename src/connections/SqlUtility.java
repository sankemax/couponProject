package connections;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlUtility {

	public static void rollbackConnection(Connection connection) {
		try {
			
			if (connection != null) connection.rollback();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void closeStatement(Statement statement) {
		
		try {
			
			if (statement != null) statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void closeResultSet(ResultSet resultSet) {
		
		try {
			
			if (resultSet != null) resultSet.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// in case the connection is not part of the connectionPool
	public static void closeConnection(Connection connection) {
		
		try {
			
			if (connection != null) connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
