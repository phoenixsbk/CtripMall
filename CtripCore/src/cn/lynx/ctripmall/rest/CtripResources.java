package cn.lynx.ctripmall.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import cn.lynx.ctripmall.db.CtripDBMgr;
import cn.lynx.ctripmall.db.model.FlowInfo;
import cn.lynx.ctripmall.db.model.OrderInfo;
import cn.lynx.ctripmall.rest.model.RestOrderInfo;
import cn.lynx.ctripmall.rest.util.HMACMD5;

import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONObject;

import static cn.lynx.ctripmall.rest.util.RestUtil.*;

@Path("/ctrip")
public class CtripResources {
    private static final String CLASSNAME = CtripResources.class.getName();
    private static final String PKG = CtripResources.class.getPackage().getName();
    private static final Logger LOGGER = Logger.getLogger(PKG);

    private static final String CTRIP_ORDER_URI = "http://localhost:8080/mall-api/openapi/updateorderdelivery";
    //private static final String CTRIP_ORDER_URI = "https://api.mall.ctrip.com/mall-api/openapi/updateorderdelivery";

    @POST
    @Path("/orderinfo/{uuid}")
    public ResultMsg postOrderInfoToCtrip(@PathParam("uuid") String uuid, @FormParam("flow") String flow) {
	final String METHOD = "postOrderInfoToCtrip";
	if (LOGGER.isLoggable(Level.FINER)) {
	    LOGGER.entering(CLASSNAME, METHOD, "[TRACE ORDER FLOW] Update the order:" + uuid + " with flow:" + flow);
	}

	ResultMsg msg = new ResultMsg();

	if (flow == null || flow.isEmpty()) {
	    msg.setResult(1);
	    msg.setResultmessage("错误的本地数据提交");

	    if (LOGGER.isLoggable(Level.FINER)) {
		LOGGER.exiting(CLASSNAME, METHOD, "[TRACE ORDER FLOW] Wrong local data submission.");
	    }
	    return msg;
	}

	JSONObject jso = null;
	try {
	    jso = (JSONObject) JSON.parse(flow);
	} catch (Exception e) {
	    LOGGER.logp(Level.SEVERE, CLASSNAME, METHOD,
		    "[EXCEPTION ORDER FLOW] Exception occur when parse the flow json data.", e);
	}

	String flowCompanyName = (String) jso.get("flowCompanyName");
	String flowTicketNumber = (String) jso.get("flowTicketNumber");
	int flowStatus = (int) jso.get("flowStatus");
	String flowRemark = (String) jso.get("flowRemark");

	OrderInfo oi = CtripDBMgr.getInstance().queryEntity(OrderInfo.class, uuid);
	if (oi == null) {
	    LOGGER.logp(Level.SEVERE, CLASSNAME, METHOD,
		    "[EXCEPTION ORDER FLOW] Can't find the correct orderinfo data from uuid:" + uuid);

	    msg.setResult(1);
	    msg.setResultmessage("Can't find correct orderinfo from uuid:" + uuid);

	    if (LOGGER.isLoggable(Level.FINER)) {
		LOGGER.exiting(CLASSNAME, METHOD, "[TRACE ORDER FLOW] Wrong uuid submission.");
	    }
	    return msg;
	}

	FlowInfo fi = null;
	if (oi.getFlowInfo() != null) {
	    fi = oi.getFlowInfo();
	} else {
	    fi = new FlowInfo();
	    oi.setFlowInfo(fi);
	}

	fi.setFlowCompanyName(flowCompanyName);
	fi.setFlowTicketNumber(flowTicketNumber);
	fi.setFlowStatus(flowStatus);
	fi.setFlowRemark(flowRemark);

	CtripDBMgr.getInstance().saveEntity(oi);

	String jsondata = RestOrderInfo.toJSONCtrip(oi);
	String computeSign = HMACMD5.MD5("orderid=" + oi.getOrderId(), oi.getTimestamp());

	RestClient client = new RestClient();
	Resource resource = client.resource(CTRIP_ORDER_URI);
	resource = resource.contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(
		MediaType.APPLICATION_JSON_TYPE);
	resource = resource.header("Content-Type", "application/x-www-form-urlencoded");
	ClientResponse resp = resource.post("data=" + jsondata + "&sign=" + computeSign);
	if (resp.getStatusCode() != 200) {
	    msg.setResult(1);
	    msg.setResultmessage("Ctrip return error response code:" + resp.getStatusCode());

	    if (LOGGER.isLoggable(Level.FINER)) {
		LOGGER.exiting(CLASSNAME, METHOD,
			"[TRACE ORDER FLOW] Ctrip return error response code:" + resp.getStatusCode());
	    }
	    return msg;
	}

	String jsonmsg = resp.getEntity(String.class);
	try {
	    JSONObject objmsg = (JSONObject) JSON.parse(jsonmsg);
	    msg.setResult(getInt(objmsg, "result"));
	    msg.setResultmessage(getString(objmsg, "resultmessage"));
	} catch (Exception e) {
	    LOGGER.logp(Level.SEVERE, CLASSNAME, METHOD,
		    "[EXCEPTION FLOW] Exception when parse the response result message:" + jsonmsg, e);
	    msg.setResult(1);
	    msg.setResultmessage("Can't parse the ctrip response message into json.");
	}

	if (LOGGER.isLoggable(Level.FINER)) {
	    LOGGER.exiting(CLASSNAME, METHOD, "[TRACE ORDER FLOW] End of Ctrip response.");
	}
	return msg;
    }
}
