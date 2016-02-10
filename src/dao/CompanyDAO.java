package dao;


import java.util.List;

import core.CouponSystemException;
import core.beans.Company;


public interface CompanyDAO {
	public void createCompany(Company company) throws CouponSystemException;
	public void removeCompany(Company company) throws CouponSystemException;
	public void updateCompany(Company company) throws CouponSystemException;
	public Company getCompany(long id) throws CouponSystemException;
	public List<Company> getAllCompanies() throws CouponSystemException;
	public boolean login(String compName, String password) throws CouponSystemException;
	public Company getCompanyByName(String name) throws CouponSystemException;
	boolean isNameExists(String companyName) throws CouponSystemException;
	
}	
