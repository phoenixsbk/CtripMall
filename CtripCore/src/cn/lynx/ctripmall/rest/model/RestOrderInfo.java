package cn.lynx.ctripmall.rest.model;

import java.util.ArrayList;
import java.util.List;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

import cn.lynx.ctripmall.model.OrderInfo;

public class RestOrderInfo extends OrderInfo implements RestObj {
	
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
		this.orderId = (Long) jo.get("orderid");
		this.timestamp = (Long) jo.get("timestamp");
		this.bookingDate = (Long) jo.get("bookingdate");
		this.invoicePrice = (Double) jo.get("invoiceprice");
		this.invoiceContent = (String) jo.get("invoicecontent");
		this.invoiceHead = (String) jo.get("invoicehead");
		
		JSONArray productAry = (JSONArray) jo.get("productlist");
		List<RestProduct> productList = new ArrayList<RestProduct>();
		for (Object po : productAry) {
			JSONObject productjo = (JSONObject) po;
			RestProduct rp = new RestProduct();
			rp.fromJSON(productjo);
			productList.add(rp);
		}
		
		JSONObject ro = (JSONObject) jo.get("receiverinfo");
		RestReceiver rr = new RestReceiver();
		rr.fromJSON(ro);
		this.receiver = rr;
	}
}
