package cn.lynx.ctripmall.model;

public abstract class Product {
	
	protected String exProductId;
	protected String exSubProductId;
	protected String productName;
	protected long quantity;
	protected double price;
	protected int experience;
	protected double settlePrice;
	protected String color;
	protected String size;
	
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

	public int getExp() {
		return experience;
	}

	public void setExp(int exp) {
		this.experience = exp;
	}

	public double getSettingPrice() {
		return settlePrice;
	}

	public void setSettingPrice(double settingPrice) {
		this.settlePrice = settingPrice;
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
