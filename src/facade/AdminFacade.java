package facade;

import java.util.List;

import DAO.CompanyDAO;
import DAO.CustomerDAO;
import DBDAO.CompanyDBDAO;
import DBDAO.CustomerDBDAO;
import beans.Company;
import beans.Customer;

public class AdminFacade implements CouponClientFacade{
	
	private CustomerDAO customerDAO;
	private CompanyDAO companyDAO;
	
	public AdminFacade(){
		
		customerDAO = new CustomerDBDAO();
		companyDAO = new CompanyDBDAO();
	}
	
	public void createCompany(Company company){
		
		if(companyDAO.isNameExists(company.getCompName())){
			// TODO Auto-generated method stub
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
	
	public void createCustomer(Customer customer){
		
		if(customerDAO.isNameExists(customer)){
			//TODO 	
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
	public CouponClientFacade login(String name, String password, String clientType) {
		if(name.equals("admin")){
			// TODO Auto-generated catch block
		}
		if(password.equals("1234")){
			// TODO Auto-generated catch block
		}
		return this;
	}

}
