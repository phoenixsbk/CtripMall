package cn.lynx.ctripmall.db.model;

import javax.persistence.Entity;

@Entity
public class Product extends CtripEntity {
	private String productId;
	private String subProductId;
	private String name;
	private long quantity;
	private double price;
	private int exp;
	private double settingPrice;
	private String color;
	private String size;
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSubProductId() {
		return subProductId;
	}

	public void setSubProductId(String subProductId) {
		this.subProductId = subProductId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public double getSettingPrice() {
		return settingPrice;
	}

	public void setSettingPrice(double settingPrice) {
		this.settingPrice = settingPrice;
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
}
