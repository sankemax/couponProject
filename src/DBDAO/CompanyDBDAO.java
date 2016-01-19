package DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import DAO.CompanyDAO;
import DAO.CouponDAO;
import beans.Company;
import beans.Coupon;
import connections.ConnectionPool;

public class CompanyDBDAO implements CompanyDAO {
	ConnectionPool connectionPool;
	
	public CompanyDBDAO() {
		connectionPool = ConnectionPool.getInstance();
	}

	@Override
	public void createCompany(Company company) {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedSt = null;
		ResultSet generatedKeys = null;
		String sql = null;
		
		try {
						
			sql = "INSERT INTO company(comp_name, password, email) VALUES(?, ?, ?)";
			
			preparedSt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedSt.setString(1, company.getCompName());
			preparedSt.setString(2, company.getPassword());
			preparedSt.setString(3, company.getEmail());
			preparedSt.executeUpdate();

			connection.commit();
			generatedKeys = preparedSt.getGeneratedKeys();
			if (generatedKeys.next()) {
				
				company.setCompanyId(generatedKeys.getLong(1));
			} else {
				// TODO throw exception?
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		} finally {
			
			try {
				if (preparedSt != null) preparedSt.close();
				if (generatedKeys != null) generatedKeys.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public boolean isNameExists(String companyName) {
		
		Connection connection = connectionPool.getConnection();
		Statement statement = null;
		ResultSet result = null;
		String sql = null;
		
		try {
			
			sql = "SELECT company.comp_name FROM company WHERE company.comp_name = '" + companyName + "' FETCH FIRST ROW ONLY";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);
			if (result.next()) return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				if (statement != null) statement.close();
				if (result != null) result.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connectionPool.returnConnection(connection);
		}
		
		return false;
	}

	@Override
	public void removeCompany(Company company) {

		Connection connection = connectionPool.getConnection();
		String sql = null;
		PreparedStatement preparedSt = null;
		
		try {
			
			long companyId = company.getId();
			CouponDAO couponDAO = new CouponDBDAO();
			List<Coupon> couponsOwnedByCompany = couponDAO.getAllCouponsCompany(companyId);
			
			connection.setAutoCommit(false);
			
			for (Coupon coupon : couponsOwnedByCompany) {
				
				long couponId = coupon.getId();
				sql = "DELETE FROM customer_coupon WHERE coupon_id = ?";
				preparedSt = connection.prepareStatement(sql);
				
				preparedSt.setLong(1, couponId);
				preparedSt.executeQuery();
				preparedSt.close();
				
				sql = "DELETE FROM company_coupon WHERE coupon_id = ?";
				preparedSt = connection.prepareStatement(sql);
				
				preparedSt.setLong(1, couponId);
				preparedSt.executeQuery();
				preparedSt.close();
				
				sql = "DELETE FROM coupon WHERE id = ?";
				preparedSt = connection.prepareStatement(sql);
				
				preparedSt.setLong(1, couponId);
				preparedSt.executeQuery();
				preparedSt.close();
			}
			
			sql = "DELETE FROM company WHERE id = ?";
			preparedSt = connection.prepareStatement(sql);
			
			preparedSt.setLong(1, companyId);
			preparedSt.executeQuery();
			
			connection.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			
			try {
				if (preparedSt != null) preparedSt.close();
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public void updateCompany(Company company) {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedSt = null;
		String sql = null;
		
		try {
						
			sql = "UPDATE company SET comp_name = ?, password = ?, email = ? WHERE id = ?";
			
			preparedSt = connection.prepareStatement(sql);
			preparedSt.setString(1, company.getCompName());
			preparedSt.setString(2, company.getPassword());
			preparedSt.setString(3, company.getEmail());
			preparedSt.setLong(4, company.getId());
			preparedSt.executeUpdate();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {
			
			try {
				if (preparedSt != null) preparedSt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public Company getCompany(long id) {
		
		Connection connection = connectionPool.getConnection();
		ResultSet result = null;
		Statement statement = null; 
		String sql = null;
		Company company = null;
		
		try {
			
			sql = "SELECT * FROM company WHERE company.id = '" + id + "' FETCH FIRST ROW ONLY";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);
			if (result.next()) {
				
				company = new Company(result.getString(2), result.getString(3), result.getString(4));
				company.setCompanyId(result.getLong(1));
			} else {
				// TODO throw ecxeption
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				if (statement != null) statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connectionPool.returnConnection(connection);
		}
		return company;
	}

	@Override
	public List<Company> getAllCompanies() {
		
		Connection connection = connectionPool.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		String sql = null;
		List<Company> companyList = new ArrayList<>();
		
		try {
			
			sql = "SELECT * FROM company";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				
				Company company = new Company(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
				company.setCompanyId(resultSet.getLong(1));
				companyList.add(company);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				if (statement != null) statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connectionPool.returnConnection(connection);
		}
		return companyList;
	}

	@Override
	public boolean login(String compName, String password) {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		String sql = null;
		
		try {
			
			sql = "SELECT company.id FROM company WHERE company.comp_name = ? AND company.password = ? FETCH FIRST ROW ONLY";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, compName);
			preparedStatement.setString(2, password);
			
			result = preparedStatement.executeQuery();
			if (result.next()) return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				if (preparedStatement != null) preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connectionPool.returnConnection(connection);
		}
		return false;
	}

	@Override
	public Company getCompanyByName(String name) {
		
		Connection connection = connectionPool.getConnection();
		Statement statement = null;
		ResultSet result = null;
		String sql = null;
		Company company = new Company();
		
		try {
			
			sql = "SELECT * FROM company WHERE COMP_NAME = '" + name + "'";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);
			
			if (result.next()) {
				
				company.setCompanyId(result.getLong(1));
				company.setCompName(result.getString(2));
				company.setPassword(result.getString(3));
				company.setEmail(result.getString(4));
			} else {
				// TODO some kind of exception?
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				if (statement != null) statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connectionPool.returnConnection(connection);
		}
		
		return company;
	}
}
