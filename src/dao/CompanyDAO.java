package dao;


import java.util.List;
import core.CouponSystemException;
import core.beans.Company;


public interface CompanyDAO {
	
	/**
	 * Creates a company in the system.
	 * @param company bean which holds the information needed to create the company.
	 * @throws CouponSystemException if company already exists, or communication IO failed.
	 */
	public void createCompany(Company company) throws CouponSystemException;
	
	/**
	 * Removes a company from the system.
	 * @param company bean which holds the information necessary to remove the company.
	 * @throws CouponSystemException if company does not exist or if communication IO failed.
	 */
	public void removeCompany(Company company) throws CouponSystemException;
	
	/**
	 * Updates the password and email of a specified company.
	 * In case of identical information, the update will proceed and no errors will be thrown.
	 * @param company bean which holds the information of the update. 
	 * @throws CouponSystemException if company does not exist, or communication IO failed.
	 */
	public void updateCompany(Company company) throws CouponSystemException;
	
	/**
	 * Returns a company by ID.
	 * @param id number of the company that will be returned if exists in the system.
	 * @return <code><b>Company</b></code> bean that holds the information about the company with the requested id number.
	 * @throws CouponSystemException if the specified company id number does not exist, or if communication IO failed.
	 */
	public Company getCompany(long id) throws CouponSystemException;
	
	/**
	 * Returns a company by name.
	 * @param name of the company that will be returned if exists in the system.
	 * @return <code><b>Company</b></code> bean that holds the information about the company with the requested name.
	 * @throws CouponSystemException if such company name doesn't exist or if communication IO failed.
	 */
	public Company getCompanyByName(String name) throws CouponSystemException;
	
	/**
	 * Returns all companies that are registered in the system.
	 * @return A List that holds all the companies.
	 * @throws CouponSystemException if there are no companies in the system or communication IO failed.
	 */
	public List<Company> getAllCompanies() throws CouponSystemException;	
	
	/**
	 * Checks whether a company with the specified name and password exists.
	 * @param compName the name of the company.
	 * @param password the password of the company.
	 * @return <code><b>true</b></code> if such login exists; <code><b>false</b></code> otherwise.
	 * @throws CouponSystemException if specified company name doesn't exist, 
	 * if the name and password don't match or if communication IO failed.
	 */
	public boolean login(String compName, String password) throws CouponSystemException;
	
	
	/**
	 * Checks whether a company with the specified name exists.
	 * @param companyName name of the company.
	 * @return <code><b>true</b></code> if such name exists; <code><b>false</b></code> otherwise.
	 * @throws CouponSystemException if communication IO failed.
	 */
	boolean isNameExists(String companyName) throws CouponSystemException;
	
}	
