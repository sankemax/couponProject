package beans;

public class Customer {

	private long customerId;
	private String custName;
	private String password;
	
	public Customer() {}
	
	public Customer(String custName, String password) {
		super();
		this.setCustName(custName);
		this.setPassword(password);
	}

	public long getId() {
		return customerId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [id=" + customerId + ", custName=" + custName + ", password=" + password + "]";
	}

	public void setId(long customerId) {
		this.customerId = customerId;
	}
	
	
}
