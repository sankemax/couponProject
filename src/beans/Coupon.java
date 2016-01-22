package beans;

import java.io.Serializable;
import java.util.Date;

public class Coupon implements Serializable{

	private static final long serialVersionUID = 1L;
	private long couponId;
	private String couponTitle;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;
	
	public Coupon() {}

	public Coupon(String title, Date startDate, Date endDate, int amount, CouponType type, String message, double price,
			String image) {
		super();
		this.setTitle(title);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setAmount(amount);
		this.setType(type);
		this.setMessage(message);
		this.setPrice(price);
		this.setImage(image);
	}

	
	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public long getId() {
		return couponId;
	}

	public String getTitle() {
		return couponTitle;
	}

	public void setTitle(String title) {
		this.couponTitle = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + couponId + ", title=" + couponTitle + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}
	
	
}
