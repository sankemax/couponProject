package DBDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DAO.CompanyDAO;
import DAO.CouponDAO;
import beans.Company;
import beans.Coupon;
import connections.ConnectionPool;
import connections.SqlUtility;
import core.CouponSystemException;

public class CompanyDBDAO implements CompanyDAO {
	ConnectionPool connectionPool;
	
	public CompanyDBDAO() throws CouponSystemException {
		connectionPool = ConnectionPool.getInstance();
	}

	@Override
	public void createCompany(Company company) throws CouponSystemException {

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
			
			generatedKeys.next();
			company.setCompanyId(generatedKeys.getLong(1));
			
		} catch (SQLException e) {
			
			SqlUtility.rollbackConnection(connection);
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
			
		} finally {
			
			SqlUtility.closeStatement(preparedSt);
			SqlUtility.closeResultSet(generatedKeys);
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public boolean isNameExists(String companyName) throws CouponSystemException {
		
		Connection connection = connectionPool.getConnection();
		Statement statement = null;
		ResultSet result = null;
		String sql = null;
		boolean flag = false ;
		
		try {
			
			sql = "SELECT company.comp_name FROM company WHERE company.comp_name = '" + companyName + "' FETCH FIRST ROW ONLY";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);
			if (result.next()) flag = true;
			
			return flag;
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		} finally {
			
			SqlUtility.closeStatement(statement);
			SqlUtility.closeResultSet(result);
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public void removeCompany(Company company) throws CouponSystemException {

		// this line is for the demonstration of the first part of the project only
		company.setCompanyId(getCompanyByName(company.getCompName()).getId());
		
		Connection connection = connectionPool.getConnection();
		String sql = null;
		PreparedStatement preparedSt = null;
		ResultSet couponsId = null;
		
		try {
			
			connection.setAutoCommit(false);

			long companyId = company.getId();
			sql = "SELECT id FROM coupon c INNER JOIN company_coupon cc ON c.id = cc.coupon_id WHERE comp_id = ?";
			preparedSt = connection.prepareStatement(sql);
			
			preparedSt.setLong(1, companyId);
			couponsId = preparedSt.executeQuery();	
			
			while(couponsId.next()) {
				
				long couponId = couponsId.getLong(1);
				sql = "DELETE FROM customer_coupon WHERE coupon_id = ?";
				preparedSt = connection.prepareStatement(sql);
				
				preparedSt.setLong(1, couponId);
				preparedSt.executeUpdate();
				preparedSt.close();
				
				sql = "DELETE FROM company_coupon WHERE coupon_id = ?";
				preparedSt = connection.prepareStatement(sql);
				
				preparedSt.setLong(1, couponId);
				preparedSt.executeUpdate();
				preparedSt.close();
				
				sql = "DELETE FROM coupon WHERE id = ?";
				preparedSt = connection.prepareStatement(sql);
				
				preparedSt.setLong(1, couponId);
				preparedSt.executeUpdate();
				preparedSt.close();
			}
			
			sql = "DELETE FROM company WHERE id = ?";
			preparedSt = connection.prepareStatement(sql);
			
			preparedSt.setLong(1, companyId);
			preparedSt.executeUpdate();
			
			connection.commit();
			
		} catch (SQLException e) {
			SqlUtility.rollbackConnection(connection);
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
			
		} finally {
			
			SqlUtility.closeResultSet(couponsId);
			SqlUtility.closeStatement(preparedSt);
			connectionPool.returnConnection(connection);
		}		
	}

	@Override
	public void updateCompany(Company company) throws CouponSystemException {

		// this line is for the demonstration of the first part of the project only
		company.setCompanyId(getCompanyByName(company.getCompName()).getId());
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedSt = null;
		String sql = null;
		
		try {
						
			sql = "UPDATE company SET password = ?, email = ? WHERE id = ?";
			
			preparedSt = connection.prepareStatement(sql);
			preparedSt.setString(1, company.getPassword());
			preparedSt.setString(2, company.getEmail());
			preparedSt.setLong(3, company.getId());
			
			if (preparedSt.executeUpdate() == 0){
				throw new CouponSystemException(CouponSystemException.COMPANY_NOT_EXIST);
			}
					
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
			
		} finally {
			
			SqlUtility.closeStatement(preparedSt);
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public Company getCompany(long id) throws CouponSystemException {
				
		Connection connection = connectionPool.getConnection();
		ResultSet result = null;
		Statement statement = null; 
		String sql = null;
		Company company = null;
		
		try {
			
			sql = "SELECT * FROM company WHERE company.id = " + id + "FETCH FIRST ROW ONLY";
			statement = connection.createStatement();
			result = statement.executeQuery(sql);
			if (result.next()) {
				
				company = new Company(result.getString(2), result.getString(3), result.getString(4));
				company.setCompanyId(result.getLong(1));
				
			} else {
				throw new CouponSystemException(CouponSystemException.COMPANY_NOT_EXIST);
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
			
		} finally {
			
			SqlUtility.closeStatement(statement);
			connectionPool.returnConnection(connection);
		}
		return company;
	}

	@Override
	public List<Company> getAllCompanies() throws CouponSystemException {
		
		Connection connection = connectionPool.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		String sql = null;
		List<Company> companyList = new ArrayList<>();
		
		try {
			
			sql = "SELECT * FROM company";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if(resultSet.next()){
				do{
					Company company = new Company(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
					company.setCompanyId(resultSet.getLong(1));
					companyList.add(company);
				}while(resultSet.next()); 
			}else {
				throw new CouponSystemException(CouponSystemException.COMPANIES_NOT_EXIST);
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
			
		} finally {
			
			SqlUtility.closeStatement(statement);
			connectionPool.returnConnection(connection);
		}
		return companyList;
	}

	@Override
	public boolean login(String compName, String password) throws CouponSystemException {
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		String sql = null;
		boolean flag = false;
		
		try {
			
			sql = "SELECT company.id FROM company WHERE company.comp_name = ? AND company.password = ? FETCH FIRST ROW ONLY";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, compName);
			preparedStatement.setString(2, password);
			
			result = preparedStatement.executeQuery();
			if (result.next()) flag = true;
			return flag;
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		} finally {
			
			SqlUtility.closeStatement(preparedStatement);
			connectionPool.returnConnection(connection);
		}
	}

	@Override
	public Company getCompanyByName(String name) throws CouponSystemException {
		
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
				throw new CouponSystemException(CouponSystemException.COMPANY_NOT_EXIST);
			}
			return company;
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		} finally {
			
			SqlUtility.closeStatement(statement);
			connectionPool.returnConnection(connection);
		}
	}
}