package cn.lynx.ctripmall.rest.model;

import com.ibm.json.java.JSONObject;

import cn.lynx.ctripmall.model.Product;

public class RestProduct extends Product implements RestObj {
	
	private String uuid;

	@Override
	public String getUuid() {
		return this.uuid;
	}

	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public void fromJSON(JSONObject jo) {
		this.exProductId = (String) jo.get("exproductid");
		this.exSubProductId = (String) jo.get("exsubproductid");
		this.productName = (String) jo.get("productname");
		this.quantity = (Long) jo.get("quantity");
		this.price = (Double) jo.get("price");
		this.experience = (Integer) jo.get("experience");
		this.settlePrice = (Double) jo.get("settleprice");
		this.color = (String) jo.get("color");
		this.size = (String) jo.get("size");
	}
}
