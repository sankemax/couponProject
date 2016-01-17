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

public class CustomerDBDAO implements CustomerDAO {
	
	ConnectionPool pool = ConnectionPool.getInstance();
	@Override
	public void createCustomer(Customer customer) {
		Connection connection = pool.getConnection();
		
		PreparedStatement ps;
		ResultSet rs;
			try {
				String sql = "INSERT INTO Customer (CUST_NAME, PASSWORD) VALUES(?,?)";
				ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, customer.getCustName());
				ps.setString(2, customer.getPassword());
				ps.execute();
				
				rs = ps.getGeneratedKeys();
				rs.next();
				customer.setId(rs.getLong(1));
				
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
		
		Statement st;
		
		try {
			
			connection.setAutoCommit(false);
			String sql1 = "DELETE FROM Customer_Coupon  WHERE CUST_ID =" + customer.getId();
			String sql2 = "DELETE FROM Customer  WHERE ID =" + customer.getId();
			st = connection.createStatement();
			st.execute(sql1);
			st.execute(sql2);
			connection.commit();
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public void updateCustomer(Customer customer) {
		
		
		Connection connection = pool.getConnection();
		
		PreparedStatement ps;
		
		try {
			String sql = "UPDATE Customer SET PASSWORD = ? WHERE ID = ?";
			ps = connection.prepareStatement(sql);
			
			ps.setString(1, customer.getPassword());
			ps.setLong(2, customer.getId());
			if(!ps.execute()){
				//TODO æøé÷ú ùâéàä: ùí ìà ÷ééí áîòøëú
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
		
		Statement st;
		ResultSet rs;
		Customer customer = null ;
		
		try {
			st = connection.createStatement();
			String sql = "SELECT * FROM Customer WHERE ID = " + id;
			rs = st.executeQuery(sql);
			
			if(rs.next()){
				customer = new Customer(rs.getString(2), rs.getString(3));
				customer.setId(rs.getLong(1));
			}else{
				// TODO æøé÷ú ùâéàä: ú.æ. ìà ÷ééí áîòøëú
			}
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
		
		Statement st;
		ResultSet rs;
		List<Customer> customers = new ArrayList<>();
		
		try {
			st = connection.createStatement();
			String sql = "SELECT * FROM Customer";
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				Customer customer = new Customer(rs.getString(2), rs.getString(3));
				customer.setId(rs.getLong(1));
				customers.add(customer);
			}
			
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
		
		Statement st;
		ResultSet rs;
		Customer customer = null ;
		
		try {
			st = connection.createStatement();
			String sql = "SELECT * FROM Customer WHERE CUST_NAME = '" + name + "'";
			rs = st.executeQuery(sql);
			
			if(rs.next()){
				customer = new Customer(rs.getString(2), rs.getString(3));
				customer.setId(rs.getLong(1));
			}else{
				// TODO æøé÷ú ùâéàä: ùí ìà ÷ééí áîòøëú
			}
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
		
		Statement st;
		ResultSet rs;
		
		try {
			st = connection.createStatement();
			String sql = "SELECT * FROM Customer WHERE CUST_NAME = '" +customer.getCustName()+"'";
			rs = st.executeQuery(sql);
			
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(connection);
		}
		return false;
	}
	
	@Override
	public boolean IsPurchased(long customerId, long couponId) {
		
		Connection connection = pool.getConnection();
		PreparedStatement ps;
		ResultSet rs;
			
		try {
			String sql = "SELECT * FROM Customer_Coupon WHERE CUST_ID = ? AND COUPON_ID = ?";
			ps = connection.prepareStatement(sql);
			ps.setLong(1, customerId);
			ps.setLong(2, couponId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(connection);
		}
		return false;
	}

	@Override
	public void purchaseCoupon(long customerId, long couponId) {
		Connection connection = pool.getConnection();
		
		PreparedStatement ps;
		Statement st;
		
		try {
			connection.setAutoCommit(false);
			String sql1 = "INSERT INTO Customer_Coupon (CUST_ID, COUPON_ID) VALUES(?,?)";
			ps = connection.prepareStatement(sql1);
			ps.setLong(1, customerId);
			ps.setLong(2, couponId);
			ps.execute();
			
			String sql2 = "UPDATE Coupon SET AMOUNT = AMOUNT - 1 WHERE ID = " + couponId;
			st = connection.createStatement();
			st.execute(sql2);
			connection.commit();
		
			
			
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public boolean login(String compName, String password) {
		Connection connection = pool.getConnection();
		
		Statement st;
		ResultSet rs;
		
		try {
			st = connection.createStatement();
			String sql = "SELECT * FROM Customer WHERE CUST_NAME = '" + compName + "'";
			rs = st.executeQuery(sql);
			
			if(!rs.next()){
				// TODO æøé÷ú ùâéàä: ùí îùúîù ìà ÷ééí áîòøëú
				System.out.println("ùí ìà ÷ééí");
			}else if (!rs.getString(2).equals(password)){
				// TODO æøé÷ú ùâéàä: äñéñîà ìà úåàîú ìùí îùúîù
				System.out.println("ñéñîà ìà ðëåðä");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(connection);
		}
		return true;
	}

}
