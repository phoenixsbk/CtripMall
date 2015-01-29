package cn.lynx.ctripmall.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.lynx.ctripmall.db.CtripDBMgr;
import cn.lynx.ctripmall.db.model.OrderInfo;
import cn.lynx.ctripmall.db.model.Product;
import cn.lynx.ctripmall.db.model.Refund;
import cn.lynx.ctripmall.rest.model.RestProduct;
import cn.lynx.ctripmall.rest.model.RestRefund;
import cn.lynx.ctripmall.rest.util.HMACMD5;

import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

@Path("/refundinfo")
public class RefundResources {
	private static final String CLASSNAME = RefundResources.class.getName();
	private static final String PKG = RefundResources.class.getPackage().getName();
	private static final Logger LOGGER = Logger.getLogger(PKG);

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMsg postRefundInfo(@FormParam("data") String data, @FormParam("sign") String sign) {
		final String METHOD = "postRefundInfo";
		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.entering(CLASSNAME, METHOD, "[TRACE REFUND] Receive the post data:[" + data + "] with sign:[" + sign + "]");
		}
		
		ResultMsg msg = new ResultMsg();
		
		try {
			JSONObject jo = (JSONObject) JSON.parse(data);
			Refund r = RestRefund.fromJSON(jo);
			
			String refundstr = "refundapplyid=" + r.getRefundApplyId();
			String computeSign = HMACMD5.MD5(refundstr, r.getTimestamp());
			if (computeSign == null || !computeSign.equals(sign)) {
				msg.setResult(1);
				msg.setResultmessage("Incorrect sign");
				return msg;
			}
			
			/* Search for order */
			long orderId = (Long) jo.get("orderid");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orderId", orderId);
			List<OrderInfo> result = CtripDBMgr.getInstance().queryEntitiesByProperties(OrderInfo.class, param, 1);
			if (result == null || result.size() < 1) {
				throw new RuntimeException("Can't find the correct order from orderId:" + orderId);
			}
			
			OrderInfo oi = result.get(0);
			/* Found correct order */
			
			r.setOrderInfo(oi);
			
			/* Search for product list */
			List<Product> plist = new ArrayList<Product>();
			JSONArray ja = (JSONArray) jo.get("productlist");
			for (Object po : ja) {
				JSONObject pjo = (JSONObject) po;
				Product p = RestProduct.fromJSON(pjo);
				long refundQuantity = Long.valueOf(pjo.get("quantity").toString());
				if (p.getQuantity() != refundQuantity) {
					Product newp = p.clone();
					newp.setUuid(null);
					newp.setQuantity(refundQuantity);
					plist.add(newp);
				} else {
					plist.add(p);
				}
			}
			/* Found correct product list */
			
			r.setProductList(plist);
			
			CtripDBMgr.getInstance().saveEntity(r);
			msg.setResult(0);
			msg.setResultmessage("Correctly save the refund information.");
		} catch (Exception e) {
			LOGGER.logp(Level.SEVERE, CLASSNAME, METHOD, "[EXCEPTION REFUND] Exception when parsing the data from refund: [" + data + "]", e);
			msg.setResult(1);
			msg.setResultmessage("Exception when parse the refund info.");
		}
		
		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.exiting(CLASSNAME, METHOD, "[TRACE REFUND] Completed with msg:" + msg);
		}
		return msg;
	}
}
