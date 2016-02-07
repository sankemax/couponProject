package facade;

import java.util.List;

import DAO.CompanyDAO;
import DAO.CustomerDAO;
import DBDAO.CompanyDBDAO;
import DBDAO.CustomerDBDAO;
import beans.Company;
import beans.Customer;
import core.CouponSystemException;

public class AdminFacade implements CouponClientFacade{
	
	private CustomerDAO customerDAO;
	private CompanyDAO companyDAO;
	
	public AdminFacade() throws CouponSystemException{
		customerDAO = new CustomerDBDAO();
		companyDAO = new CompanyDBDAO();
	}
	
	public void createCompany(Company company) throws CouponSystemException{
		company.setCompName(company.getCompName().toLowerCase());
		
		if (companyDAO.isNameExists(company.getCompName())){
			throw new CouponSystemException(CouponSystemException.COMPANY_EXISTS);
		}
		companyDAO.createCompany(company);
	}
	
	public void removeCompany(Company company) throws CouponSystemException{
		companyDAO.removeCompany(company);
	}

	public void updateCompany(Company company) throws CouponSystemException{
		companyDAO.updateCompany(company);
	}
	
	public Company getCompany(long id) throws CouponSystemException{
			return companyDAO.getCompany(id);
	}
	
	public Company getCompanyByName(String name) throws CouponSystemException{
		return companyDAO.getCompanyByName(name.toLowerCase());
	}
	
	public List<Company> getAllCompanies() throws CouponSystemException{
		return companyDAO.getAllCompanies();
	}
	
	public void createCustomer(Customer customer) throws CouponSystemException{
		
		customer.setCustName(customer.getCustName().toLowerCase());
		
		if (customerDAO.isNameExists(customer)){
			throw new CouponSystemException(CouponSystemException.CUSTOMER_EXISTS);
		}
		customerDAO.createCustomer(customer);

	}
	
	public void removeCustomer(Customer customer) throws CouponSystemException{
			customerDAO.removeCustomer(customer);
	}
	
	public void updateCustomer(Customer customer) throws CouponSystemException{
		customerDAO.updateCustomer(customer);

	}
	
	public Customer getCustomer(long id) throws CouponSystemException{
		return customerDAO.getCustomer(id);
	}
	
	public Customer getCustomerByName(String name) throws CouponSystemException {
		return customerDAO.getCustomerByName(name.toLowerCase());
	}
	
	public List<Customer> getAllCustomer() throws CouponSystemException{
		return customerDAO.getAllCustomer();
	}
	
	@Override
	public CouponClientFacade login(String name, String password) throws CouponSystemException {
		name = name.toLowerCase();
		if (name.equals("admin") && password.equals("1234")) {
			return this;
		} else {
			throw new CouponSystemException(CouponSystemException.USERNAME_PASSWORD_ERROR);
		}
	}
}
