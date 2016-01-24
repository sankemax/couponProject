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
	
	public AdminFacade(){
		customerDAO = new CustomerDBDAO();
		companyDAO = new CompanyDBDAO();
	}
	
	public void createCompany(Company company) throws CouponSystemException{
		if (companyDAO.isNameExists(company.getCompName())){
			throw new CouponSystemException(CouponSystemException.COMPANY_EXISTS);
		}
		companyDAO.createCompany(company);
	}
	
	public void removeCompany(Company company){
		companyDAO.removeCompany(company);
	}

	public void updateCompany(Company company){
		companyDAO.updateCompany(company);
	}
	
	public Company getCompany(long id){
			return companyDAO.getCompany(id);
	}
	
	public List<Company> getAllCompanies(){
		return companyDAO.getAllCompanies();
	}
	
	public void createCustomer(Customer customer) throws CouponSystemException{
		if (customerDAO.isNameExists(customer)){
			throw new CouponSystemException(CouponSystemException.CUSTOMER_EXISTS);
		}
		customerDAO.createCustomer(customer);

	}
	
	public void removeCustomer(Customer customer){
			customerDAO.removeCustomer(customer);
	}
	
	public void updateCustomer(Customer customer){
		customerDAO.updateCustomer(customer);

	}
	
	public Customer getCustomer(long id){
		return customerDAO.getCustomer(id);
	}
	
	public List<Customer> getAllCustomer(){
		return customerDAO.getAllCustomer();
	}
	
	@Override
	public CouponClientFacade login(String name, String password) throws CouponSystemException {
		
		if (name.equals("admin") && password.equals("1234")) {
			return this;
		} else {
			throw new CouponSystemException(CouponSystemException.USERNAME_PASSWORD_ERROR);
		}
	}
}
