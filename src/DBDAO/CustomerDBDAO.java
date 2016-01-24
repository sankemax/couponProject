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
	
	ConnectionPool pool = ConnectionPool.getInstance();
	@Override
	public void createCustomer(Customer customer) {
		
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
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				pool.returnConnection(connection);
			}
	}

	@Override
	public void removeCustomer(Customer customer) {
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public void updateCustomer(Customer customer) {
		
		
		Connection connection = pool.getConnection();
		String sql = "UPDATE Customer SET PASSWORD = ? WHERE ID = ? ";
		
		
		try(PreparedStatement ps = connection.prepareStatement(sql);) {
			
			ps.setString(1, customer.getPassword());
			ps.setLong(2, customer.getId());
			if (!ps.execute()){
				// TODO  the execute will always return false because there is no resultSet object so what is the purpose of this check?
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public Customer getCustomer(long id) {
		
		Connection connection = pool.getConnection();
		String sql = "SELECT * FROM Customer WHERE ID = " + id + "FETCH FIRST ROW ONLY";
		
		ResultSet rs;
		Customer customer = null ;
		
		try(Statement st = connection.createStatement();) {
			
			rs = st.executeQuery(sql);
			
			if(rs.next()){
				customer = new Customer(rs.getString(2), rs.getString(3));
				customer.setId(rs.getLong(1));
			}else{
				// TODO ����� �����: �.�. �� ���� ������
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(connection);
		}
		return customer;
	}

	@Override
	public List<Customer> getAllCustomer() {
		Connection connection = pool.getConnection();
		String sql = "SELECT * FROM Customer";
		
		ResultSet rs;
		List<Customer> customers = new ArrayList<>();
		
		try(Statement st = connection.createStatement();) {
			
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				Customer customer = new Customer(rs.getString(2), rs.getString(3));
				customer.setId(rs.getLong(1));
				customers.add(customer);
				
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(connection);
		}
		return customers;
	}

	@Override
	public Customer getCustomerByName(String name) {
		
		Connection connection = pool.getConnection();
		String sql = "SELECT * FROM Customer WHERE CUST_NAME = '" + name + "'FETCH FIRST ROW ONLY";
		
		ResultSet rs;
		Customer customer = null ;
		
		try(Statement st = connection.createStatement();){
			
			rs = st.executeQuery(sql);
			
			if(rs.next()){
				customer = new Customer(rs.getString(2), rs.getString(3));
				customer.setId(rs.getLong(1));
			}else{
				// TODO ����� �����: �� �� ���� ������
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(connection);
		}
		return customer;
	}

	@Override
	public boolean isNameExists(Customer customer) {
		
		Connection connection = pool.getConnection();
		String sql = "SELECT * FROM Customer WHERE CUST_NAME = '" +customer.getCustName()+ "'FETCH FIRST ROW ONLY";
		ResultSet rs;
		boolean flag = false;
		
		try(Statement st = connection.createStatement();){
			
			rs = st.executeQuery(sql);
			
			if(rs.next()){
				flag = true;
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(connection);
		}
		return flag;
	}
	
	@Override
	public boolean isPurchased(long customerId, long couponId) {
		
		Connection connection = pool.getConnection();
		String sql = "SELECT * FROM Customer_Coupon WHERE CUST_ID = ? AND COUPON_ID = ? FETCH FIRST ROW ONLY";
		ResultSet rs;
		boolean flag = false;
			
		try(PreparedStatement ps = connection.prepareStatement(sql);){
			
			ps.setLong(1, customerId);
			ps.setLong(2, couponId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				flag = true;
			}
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(connection);
		}
		return flag;
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
		
		String sql = "SELECT customer.id FROM customer WHERE customer.cust_name = '" + customerName + "' AND customer.password = '" + password + "' FETCH FIRST ROW ONLY";
		boolean flag = false;
		
		try (Statement st= connection.createStatement(); 
				ResultSet rs = st.executeQuery(sql);){
			
			if(rs.next()) flag = true;
			
		} catch (SQLException e) {
		
			e.printStackTrace(); // TODO for now
			throw new CouponSystemException(CouponSystemException.SYSTEM_ERROR);
			
		} finally {
			
			pool.returnConnection(connection);
		}
		return flag;
	}
}
