package DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import DAO.CustomerDAO;
import beans.Customer;
import connections.ConnectionPool;
import connections.SqlUtility;
import core.CouponSystemException;

public class CustomerDBDAO implements CustomerDAO {
	
	ConnectionPool pool;
	
	public CustomerDBDAO() throws CouponSystemException {
		pool = ConnectionPool.getInstance();
	}
	
	@Override
	public void createCustomer(Customer customer) throws CouponSystemException {
		
		Connection connection = pool.getConnection();
		String sql = "INSERT INTO Customer (CUST_NAME, PASSWORD) VALUES(?,?)";
		ResultSet rs = null;
		
			try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); ) {
				
				ps.setString(1, customer.getCustName());
				ps.setString(2, customer.getPassword());
				ps.execute();
				
				rs = ps.getGeneratedKeys();
				rs.next();
				customer.setId(rs.getLong(1));
			} catch (SQLException e) {
				throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
			}finally {
				SqlUtility.closeResultSet(rs);
				pool.returnConnection(connection);
			}
	}

	@Override
	public void removeCustomer(Customer customer) throws CouponSystemException {
		
		Connection connection = pool.getConnection();
		String sql1 = "DELETE FROM Customer_Coupon  WHERE CUST_ID =" + customer.getId();
		String sql2 = "DELETE FROM Customer  WHERE ID =" + customer.getId();
		
		
		try(Statement st = connection.createStatement();){
			
			connection.setAutoCommit(false);
			
			st.execute(sql1);
			st.execute(sql2);
			connection.commit();
			
		} catch (SQLException e) {
			SqlUtility.rollbackConnection(connection);
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		}finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		
		
		Connection connection = pool.getConnection();
		String sql = "UPDATE Customer SET PASSWORD = ? WHERE ID = ? ";
		
		
		try(PreparedStatement ps = connection.prepareStatement(sql);) {
			
			ps.setString(1, customer.getPassword());
			ps.setLong(2, customer.getId());
			if (!ps.execute()){
				throw new CouponSystemException(CouponSystemException.CUSTOMER_NOT_EXISTS);
			}
			
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		}finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public Customer getCustomer(long id) throws CouponSystemException {
		
		Connection connection = pool.getConnection();
		String sql = "SELECT * FROM Customer WHERE ID = " + id + "FETCH FIRST ROW ONLY";
		
		ResultSet rs = null;
		Customer customer = null ;
		
		try(Statement st = connection.createStatement();) {
			
			rs = st.executeQuery(sql);
			
			if(rs.next()){
				customer = new Customer(rs.getString(2), rs.getString(3));
				customer.setId(rs.getLong(1));
			}else{
				throw new CouponSystemException(CouponSystemException.CUSTOMER_NOT_EXISTS);
			}
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		}finally {
			SqlUtility.closeResultSet(rs);
			pool.returnConnection(connection);
		}
		return customer;
	}

	@Override
	public List<Customer> getAllCustomer() throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = "SELECT * FROM Customer";
		
		ResultSet rs = null;
		List<Customer> customers = new ArrayList<>();
		
		try(Statement st = connection.createStatement();) {
			
			rs = st.executeQuery(sql);
			if(rs.next()){
				do{
					Customer customer = new Customer(rs.getString(2), rs.getString(3));
					customer.setId(rs.getLong(1));
					customers.add(customer);
				}while(rs.next());
			}else {
				throw new CouponSystemException(CouponSystemException.CUSTOMERS_NOT_EXISTS);
			}
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		}finally {
			SqlUtility.closeResultSet(rs);
			pool.returnConnection(connection);
		}
		return customers;
	}

	@Override
	public Customer getCustomerByName(String name) throws CouponSystemException {
		
		Connection connection = pool.getConnection();
		String sql = "SELECT * FROM Customer WHERE CUST_NAME = '" + name + "'FETCH FIRST ROW ONLY";
		
		ResultSet rs = null;
		Customer customer = null ;
		
		try(Statement st = connection.createStatement();){
			
			rs = st.executeQuery(sql);
			
			if(rs.next()){
				customer = new Customer(rs.getString(2), rs.getString(3));
				customer.setId(rs.getLong(1));
			}else{
				throw new CouponSystemException(CouponSystemException.CUSTOMER_NOT_EXISTS);
			}
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		}finally {
			SqlUtility.closeResultSet(rs);
			pool.returnConnection(connection);
		}
		return customer;
	}

	@Override
	public boolean isNameExists(Customer customer) throws CouponSystemException {
		
		Connection connection = pool.getConnection();
		String sql = "SELECT * FROM Customer WHERE CUST_NAME = '" +customer.getCustName()+ "'FETCH FIRST ROW ONLY";
		ResultSet rs = null;
		boolean flag = false;
		
		try(Statement st = connection.createStatement();){
			
			rs = st.executeQuery(sql);
			
			if(rs.next()){
				flag = true;
			}
			
			return flag;
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		}finally {
			SqlUtility.closeResultSet(rs);
			pool.returnConnection(connection);
		}
		
	}
	
	@Override
	public boolean isPurchased(long customerId, long couponId) throws CouponSystemException {
		
		Connection connection = pool.getConnection();
		String sql = "SELECT * FROM Customer_Coupon WHERE CUST_ID = ? AND COUPON_ID = ? FETCH FIRST ROW ONLY";
		ResultSet rs = null;
		boolean flag = false;
			
		try(PreparedStatement ps = connection.prepareStatement(sql);){
			
			ps.setLong(1, customerId);
			ps.setLong(2, couponId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				flag = true;
			}
			
			return flag;
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		}finally {
			SqlUtility.closeResultSet(rs);
			pool.returnConnection(connection);
		}
	}

	@Override
	public void purchaseCoupon(long customerId, long couponId) throws CouponSystemException {
		Connection connection = pool.getConnection();
		
		String sql1 = "INSERT INTO customer_coupon (CUST_ID, COUPON_ID) VALUES(?,?)";
		String sql2 = "UPDATE coupon SET amount = amount - 1 WHERE id = " + couponId;
		
		try (PreparedStatement ps = connection.prepareStatement(sql1); 
				Statement st = connection.createStatement()){
			
			connection.setAutoCommit(false);
			
			ps.setLong(1, customerId);
			ps.setLong(2, couponId);
			ps.execute();
			
			st.execute(sql2);
			connection.commit();
		
		} catch (SQLException e) {
			SqlUtility.rollbackConnection(connection);
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		} finally {
			
			pool.returnConnection(connection);
		}
	}

	@Override
	public boolean login(String customerName, String password) throws CouponSystemException {
		
		Connection connection = pool.getConnection();
		
		String sql = "SELECT customer.id FROM customer WHERE customer.cust_name = ? AND customer.password = ? FETCH FIRST ROW ONLY";
		boolean flag = false;
		ResultSet rs = null;
		try (PreparedStatement ps= connection.prepareStatement(sql)){
			ps.setString(1, customerName);
			ps.setString(2, password);
			rs = ps.executeQuery();
			
			if(rs.next()) flag = true;
			return flag;
		} catch (SQLException e) {
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
		} finally {
			SqlUtility.closeResultSet(rs);
			pool.returnConnection(connection);
		}
		
	}
}
