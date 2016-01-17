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
		
		try {
			
			connection.setAutoCommit(false);
			
			String sql = "INSERT INTO company(comp_name, password, email) VALUES(?, ?, ?)";
			
			PreparedStatement preparedSt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedSt.setString(1, company.getCompName());
			preparedSt.setString(2, company.getPassword());
			preparedSt.setString(3, company.getEmail());
			preparedSt.executeUpdate();
			
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
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public boolean isNameExists(String companyName) {
		
		Connection connection = connectionPool.getConnection();
		String sql = "SELECT company.comp_name FROM company WHERE company.comp_name = '" + companyName + "' FETCH FIRST ROW ONLY";
		
		try {
			
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			if (result.next()) return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionPool.returnConnection(connection);
		}
		
		return false;
	}

	@Override
	public void removeCompany(Company company) {

		Connection connection = connectionPool.getConnection();
		
		try {
			
			long companyId = company.getId();
			CouponDAO couponDAO = new CouponDBDAO();
			List<Coupon> couponsOwnedByCompany = couponDAO.getAllCouponsCompany(companyId);
			
			connection.setAutoCommit(false);
			String sql;
			PreparedStatement preparedSt;
			
			for (Coupon coupon : couponsOwnedByCompany) {
				
				long couponId = coupon.getId();
				sql = "DELETE FROM customer_coupon WHERE coupon_id = ?";
				preparedSt = connection.prepareStatement(sql);
				
				preparedSt.setLong(1, couponId);
				preparedSt.executeQuery();
				
				sql = "DELETE FROM company_coupon WHERE coupon_id = ?";
				preparedSt = connection.prepareStatement(sql);
				
				preparedSt.setLong(1, couponId);
				preparedSt.executeQuery();
				
				sql = "DELETE FROM coupon WHERE id = ?";
				preparedSt = connection.prepareStatement(sql);
				
				preparedSt.setLong(1, couponId);
				preparedSt.executeQuery();
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
		}
	}

	@Override
	public void updateCompany(Company company) {

		Connection connection = connectionPool.getConnection();
		
		try {
			
			connection.setAutoCommit(false);
			
			String sql = "UPDATE company SET comp_name = ?, password = ?, email = ? WHERE id = ?";
			
			PreparedStatement preparedSt = connection.prepareStatement(sql);
			preparedSt.setString(1, company.getCompName());
			preparedSt.setString(2, company.getPassword());
			preparedSt.setString(3, company.getEmail());
			preparedSt.setLong(4, company.getId());
			preparedSt.executeUpdate();
			
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
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public Company getCompany(long id) {
		
		Connection connection = connectionPool.getConnection();
		String sql = "SELECT * FROM company WHERE company.id = '" + id + "' FETCH FIRST ROW ONLY";
		
		try {
			
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			if (result.next()) {
				
				Company company = new Company(result.getString(2), result.getString(3), result.getString(4));
				company.setCompanyId(result.getLong(1));
				return company;
			} else {
				// TODO throw ecxeption
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionPool.returnConnection(connection);
		}
		return null;
	}

	@Override
	public List<Company> getAllCompanies() {
		
		Connection connection = connectionPool.getConnection();
		String sql = "SELECT * FROM company";
		List<Company> companyList = new ArrayList<>();
		
		try {
			
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				
				Company company = new Company(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
				company.setCompanyId(resultSet.getLong(1));
				companyList.add(company);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionPool.returnConnection(connection);
		}
		return companyList;
	}

	@Override
	public boolean login(String compName, String password) {
		
		Connection connection = connectionPool.getConnection();
		String sql = "SELECT company.id FROM company WHERE company.comp_name = ? AND company.password = ? FETCH FIRST ROW ONLY";
		
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, compName);
			preparedStatement.setString(2, password);
			
			ResultSet result = preparedStatement.executeQuery();
			if (result.next()) return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connectionPool.returnConnection(connection);
		}
		return false;
	}

	@Override
	public Company getCompanyByName(String name) {
		
		Connection connection = connectionPool.getConnection();
		Company company = new Company();
		
		try {
			
			String sql = "SELECT * FROM company WHERE COMP_NAME = '" + name + "'";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
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
			connectionPool.returnConnection(connection);
		}
		
		return company;
	}
}
