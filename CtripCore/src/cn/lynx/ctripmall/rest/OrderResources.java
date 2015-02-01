package cn.lynx.ctripmall.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.lynx.ctripmall.db.CtripDBMgr;
import cn.lynx.ctripmall.db.model.OrderInfo;
import cn.lynx.ctripmall.rest.model.RestOrderInfo;
import cn.lynx.ctripmall.rest.util.HMACMD5;

import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONObject;

@Path("/orderinfo")
public class OrderResources {
    private static final String CLASSNAME = OrderResources.class.getName();
    private static final String PKG = OrderResources.class.getPackage().getName();
    private static final Logger LOGGER = Logger.getLogger(PKG);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResultMsg getOrder() {
	String encode = HMACMD5.MD5("orderid=1234567", System.currentTimeMillis());
	ResultMsg msg = new ResultMsg();
	msg.setResult(0);
	msg.setResultmessage("hello world:" + encode);
	return msg;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public ResultMsg postOrder(@FormParam("data") String data, @FormParam("sign") String sign) {
	final String METHOD = "postOrder";
	if (LOGGER.isLoggable(Level.FINER)) {
	    LOGGER.entering(CLASSNAME, METHOD, "[TRACE ORDER] Receive the post message with data:[" + data
		    + "] and sign:[" + sign + "]");
	}

	ResultMsg msg = new ResultMsg();

	try {
	    JSONObject jo = (JSONObject) JSON.parse(data);
	    OrderInfo oi = RestOrderInfo.fromJSON(jo);
	    String orderstr = "orderid=" + oi.getOrderId();
	    String computeSign = HMACMD5.MD5(orderstr, oi.getTimestamp());
	    // if (computeSign == null || !computeSign.equals(sign)) {
	    // msg.setResult(1);
	    // msg.setResultmessage("Incorrect sign data, please verify");
	    // } else {
	    CtripDBMgr.getInstance().saveEntity(oi);
	    msg.setResult(0);
	    msg.setResultmessage("Correctly update the order info");
	    // }
	} catch (Exception e) {
	    LOGGER.logp(Level.SEVERE, CLASSNAME, METHOD,
		    "[EXCEPTION ORDER] When parsing the json object from ctrip data:[" + data + "]", e);
	    msg.setResult(1);
	    msg.setResultmessage("Exception when parse the order info.");
	}

	if (LOGGER.isLoggable(Level.FINER)) {
	    LOGGER.exiting(CLASSNAME, METHOD, "[TRACE ORDER] Complete with result status:" + msg);
	}
	return msg;
    }
}
