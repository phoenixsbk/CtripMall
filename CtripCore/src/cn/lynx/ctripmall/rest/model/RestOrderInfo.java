package cn.lynx.ctripmall.rest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

import cn.lynx.ctripmall.db.model.OrderInfo;
import cn.lynx.ctripmall.db.model.Product;
import cn.lynx.ctripmall.db.model.Receiver;
import static cn.lynx.ctripmall.rest.util.RestUtil.*;

public class RestOrderInfo {

	public static OrderInfo fromJSON(JSONObject jo) {
		OrderInfo doi = new OrderInfo();
		
		doi.setOrderId(getLong(jo, "orderid"));
		doi.setTimestamp(getLong(jo, "timestamp"));
		doi.setBookingDate(getLong(jo, "bookingdate"));
		doi.setInvoicePrice(getDouble(jo, "invoiceprice"));
		doi.setInvoiceContent(getOptionalString(jo, "invoicecontent", ""));
		doi.setInvoiceHead(getOptionalString(jo, "invoicehead", ""));
		
		JSONArray productAry = getArray(jo, "productlist");
		List<Product> productList = new ArrayList<Product>();
		for (Object po : productAry) {
			JSONObject productjo = (JSONObject) po;
			Product rp = RestProduct.fromJSON(productjo);
			String uuid = UUID.randomUUID().toString();
			rp.setUuid(uuid);
			productList.add(rp);
		}
		doi.setProductList(productList);
		
		JSONObject ro = getObject(jo, "receiverinfo");
		Receiver rr = RestReceiver.fromJSON(ro);
		String uuid = UUID.randomUUID().toString();
		rr.setUuid(uuid);
		doi.setReceiver(rr);
		
		return doi;
	}
	
	public static String toJSONCtrip(OrderInfo oi) {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		sb.append("\"orderid\":" + oi.getOrderId() + ",");
		sb.append("\"timestamp\":" + oi.getTimestamp() + ",");
		sb.append("\"flowcompanyname\":\"" + oi.getFlowInfo().getFlowCompanyName() + "\",");
		sb.append("\"flowticketnumber\":\"" + oi.getFlowInfo().getFlowTicketNumber() + "\",");
		sb.append("\"flowstatus\":" + oi.getFlowInfo().getFlowStatus() + ",");
		sb.append("\"flowremark\":\"" + oi.getFlowInfo().getFlowRemark() + "\",");
		sb.append("\"productlist\": [");
		
		int psize = oi.getProductList().size();
		for (int i = 0;i < psize;i ++) {
			if (i != 0) {
				sb.append(",");
			}
			
			Product p = oi.getProductList().get(i);
			
			sb.append("{ ");
			sb.append("\"exproductid\":\"" + p.getExProductId() + "\",");
			sb.append("\"exsubproductid\":\"" + p.getExSubProductId() + "\",");
			sb.append("\"quantity\":" + p.getQuantity());
			sb.append(" }");
		}
		
		sb.append("] }");
		
		return sb.toString();
	}
}
