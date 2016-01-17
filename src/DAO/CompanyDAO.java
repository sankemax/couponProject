package DAO;


import java.util.List;
import beans.Company;


public interface CompanyDAO {
	public void createCompany(Company company);
	public void removeCompany(Company company);
	public void updateCompany(Company company);
	public Company getCompany(long id);
	public List<Company> getAllCompanies();
	public boolean login(String compName, String password);
	public Company getCompanyByName(String name);
	boolean isNameExists(String companyName);
	
}	
