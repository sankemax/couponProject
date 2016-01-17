package DBDAO;

import java.util.List;
import DAO.CustomerDAO;
import beans.Customer;

public class CustomerDBDAO implements CustomerDAO {

	@Override
	public void createCustomer(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCustomer(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Customer getCustomer(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getAllCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean login(String compName, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Customer getCustomerByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isNameExists(Customer customer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean IsPurchased(long customerId, long CouponId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void purchaseCoupon(long customerId, long CouponId) {
		// TODO Auto-generated method stub
		
	}

}
