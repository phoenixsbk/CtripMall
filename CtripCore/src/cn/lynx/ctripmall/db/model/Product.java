package cn.lynx.ctripmall.db.model;

import javax.persistence.Entity;

@Entity
public class Product extends CtripEntity implements Cloneable {
	
	private String exProductId;
	private String exSubProductId;
	private String productName;
	private long quantity;
	private double price;
	private int experience;
	private double settlePrice;
	private String color;
	private String size;
	
	public String getExProductId() {
		return exProductId;
	}

	public void setExProductId(String exProductId) {
		this.exProductId = exProductId;
	}

	public String getExSubProductId() {
		return exSubProductId;
	}

	public void setExSubProductId(String exSubProductId) {
		this.exSubProductId = exSubProductId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public double getSettlePrice() {
		return settlePrice;
	}

	public void setSettlePrice(double settlePrice) {
		this.settlePrice = settlePrice;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	@Override
	public Product clone() {
		Product p = new Product();
		p.exProductId = this.exProductId;
		p.exSubProductId = this.exSubProductId;
		p.productName = this.productName;
		p.quantity = this.quantity;
		p.price = this.price;
		p.experience = this.experience;
		p.settlePrice = this.settlePrice;
		p.color = this.color;
		p.size = this.size;
		return p;
	}
}
