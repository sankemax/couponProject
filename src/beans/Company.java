package beans;

import java.io.Serializable;

public class Company implements Serializable{

	private static final long serialVersionUID = 1L;
	private long companyId;
	private String companyName;
	private String password;
	private String email;
	
	public Company() {}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public Company(String compName, String password, String email) {
		super();
		this.setCompName(compName);
		this.setPassword(password);
		this.setEmail(email);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompName() {
		return companyName;
	}

	public void setCompName(String compName) {
		this.companyName = compName;
	}

	public long getId() {
		return companyId;
	}

	@Override
	public String toString() {
		return "Company [id=" + companyId + ", compName=" + companyName + ", password=" + password + ", email=" + email + "]";
	}
	
	
}
