package cn.lynx.ctripmall.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.wink.client.ClientConfig;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

import cn.lynx.ctripmall.db.CtripDBMgr;
import cn.lynx.ctripmall.db.model.FlowInfo;
import cn.lynx.ctripmall.db.model.OrderInfo;
import cn.lynx.ctripmall.db.model.Refund;
import cn.lynx.ctripmall.rest.model.RestOrderInfo;
import cn.lynx.ctripmall.rest.model.RestRefund;
import cn.lynx.ctripmall.rest.util.HMACMD5;

import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONObject;

import static cn.lynx.ctripmall.rest.util.RestUtil.*;

@Path("/ctrip")
public class CtripResources {
	private static final String CLASSNAME = CtripResources.class.getName();
	private static final String PKG = CtripResources.class.getPackage().getName();
	private static final Logger LOGGER = Logger.getLogger(PKG);

	private static final String CTRIPURL_JNDI_KEY = "ctripurl";
	private static final String CTRIP_ORDER_URL_KEY = "ctriporderurl";
	private static final String CTRIP_REFUND_URL_KEY = "ctriprefundurl";

	private static final String CTRIP_ORDER_URL_DEFAULT = "https://api.mall.ctrip.com/mall-api/openapi/updateorderdelivery";
	private static final String CTRIP_REFUND_URL_DEFAULT = "https://api.mall.ctrip.com/mall-api/openapi/updateorderrefundinfo";

	private static String CTRIP_ORDER_URL = CTRIP_ORDER_URL_DEFAULT;
	private static String CTRIP_REFUND_URL = CTRIP_REFUND_URL_DEFAULT;

	static {
		try {
			String urlPath = new InitialContext().lookup(CTRIPURL_JNDI_KEY).toString();
			Properties p = new Properties();
			p.load(new FileInputStream(new File(urlPath)));
			if (p.containsKey(CTRIP_ORDER_URL_KEY)) {
				CTRIP_ORDER_URL = p.getProperty(CTRIP_ORDER_URL_KEY);
			}
			if (p.containsKey(CTRIP_REFUND_URL_KEY)) {
				CTRIP_REFUND_URL = p.getProperty(CTRIP_REFUND_URL_KEY);
			}
		} catch (NamingException | IOException e) {
			e.printStackTrace();
		}
	}

	@POST
	@Path("/orderinfo/{uuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMsg postOrderInfoToCtrip(@PathParam("uuid") String uuid,
			@FormParam("flowcompanyname") String flowCompanyName,
			@FormParam("flowticketnumber") String flowTicketNumber, @FormParam("flowstatus") int flowStatus,
			@FormParam("flowremark") String flowRemark) {
		final String METHOD = "postOrderInfoToCtrip";
		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.entering(CLASSNAME, METHOD, "[TRACE ORDER FLOW] Update the order:" + uuid + " with flow:["
					+ flowCompanyName + "] [" + flowTicketNumber + "] [" + flowStatus + "] [" + flowRemark + "]");
		}

		ResultMsg msg = new ResultMsg();

		if (flowCompanyName == null || flowCompanyName.isEmpty() || flowTicketNumber == null
				|| flowTicketNumber.isEmpty() || flowRemark == null) {
			msg.setResult(1);
			msg.setResultmessage("错误的本地数据提交");

			if (LOGGER.isLoggable(Level.FINER)) {
				LOGGER.exiting(CLASSNAME, METHOD, "[TRACE ORDER FLOW] Wrong local data submission.");
			}
			return msg;
		}

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
			fi.setUuid(UUID.randomUUID().toString());
			oi.setFlowInfo(fi);
		}

		fi.setFlowCompanyName(flowCompanyName);
		fi.setFlowTicketNumber(flowTicketNumber);
		fi.setFlowStatus(flowStatus);
		fi.setFlowRemark(flowRemark);

		CtripDBMgr.getInstance().saveEntity(oi);

		oi = CtripDBMgr.getInstance().queryEntity(OrderInfo.class, oi.getUuid());

		String jsondata = RestOrderInfo.toJSONCtrip(oi);
		String computeSign = HMACMD5.MD5("orderid=" + oi.getOrderId(), oi.getTimestamp());

		ClientConfig conf = new ClientConfig();
		conf.setBypassHostnameVerification(true);

		RestClient client = new RestClient(conf);
		System.out.println("orderurl:" + CTRIP_ORDER_URL);
		Resource resource = client.resource(CTRIP_ORDER_URL);
		resource = resource.contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(
				MediaType.APPLICATION_JSON_TYPE);
		resource = resource.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
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
	
	@POST
	@Path("/refundinfo/{uuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMsg postRefundToCtrip(@PathParam("uuid") String uuid,
			@FormParam("flowcompanyname") String flowCompanyName,
			@FormParam("flowticketnumber") String flowTicketNumber, @FormParam("flowstatus") int flowStatus,
			@FormParam("flowremark") String flowRemark) {
		final String METHOD = "postRefundToCtrip";
		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.entering(CLASSNAME, METHOD, "[TRACE REFUND FLOW] Update the refund:" + uuid + " with flow:["
					+ flowCompanyName + "] [" + flowTicketNumber + "] [" + flowStatus + "] [" + flowRemark + "]");
		}

		ResultMsg msg = new ResultMsg();

		if (flowCompanyName == null || flowCompanyName.isEmpty() || flowTicketNumber == null
				|| flowTicketNumber.isEmpty() || flowRemark == null) {
			msg.setResult(1);
			msg.setResultmessage("错误的本地数据提交");

			if (LOGGER.isLoggable(Level.FINER)) {
				LOGGER.exiting(CLASSNAME, METHOD, "[TRACE REFUND FLOW] Wrong local data submission.");
			}
			return msg;
		}

		Refund rf = CtripDBMgr.getInstance().queryEntity(Refund.class, uuid);
		if (rf == null) {
			LOGGER.logp(Level.SEVERE, CLASSNAME, METHOD,
					"[EXCEPTION REFUND FLOW] Can't find the correct refund data from uuid:" + uuid);

			msg.setResult(1);
			msg.setResultmessage("Can't find correct refund from uuid:" + uuid);

			if (LOGGER.isLoggable(Level.FINER)) {
				LOGGER.exiting(CLASSNAME, METHOD, "[TRACE REFUND FLOW] Wrong uuid submission.");
			}
			return msg;
		}

		FlowInfo fi = null;
		if (rf.getFlowInfo() != null) {
			fi = rf.getFlowInfo();
		} else {
			fi = new FlowInfo();
			fi.setUuid(UUID.randomUUID().toString());
			rf.setFlowInfo(fi);
		}

		fi.setFlowCompanyName(flowCompanyName);
		fi.setFlowTicketNumber(flowTicketNumber);
		fi.setFlowStatus(flowStatus);
		fi.setFlowRemark(flowRemark);

		CtripDBMgr.getInstance().saveEntity(rf);

		rf = CtripDBMgr.getInstance().queryEntity(Refund.class, rf.getUuid());

		String jsondata = RestRefund.toJSONCtrip(rf);
		String computeSign = HMACMD5.MD5("refundapplyid=" + rf.getRefundApplyId(), rf.getTimestamp());

		ClientConfig conf = new ClientConfig();
		conf.setBypassHostnameVerification(true);

		RestClient client = new RestClient(conf);
		Resource resource = client.resource(CTRIP_REFUND_URL);
		resource = resource.contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(
				MediaType.APPLICATION_JSON_TYPE);
		resource = resource.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		ClientResponse resp = resource.post("data=" + jsondata + "&sign=" + computeSign);
		if (resp.getStatusCode() != 200) {
			msg.setResult(1);
			msg.setResultmessage("Ctrip return error response code:" + resp.getStatusCode());

			if (LOGGER.isLoggable(Level.FINER)) {
				LOGGER.exiting(CLASSNAME, METHOD,
						"[TRACE REFUND FLOW] Ctrip return error response code:" + resp.getStatusCode());
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
					"[EXCEPTION REFUND FLOW] Exception when parse the response result message:" + jsonmsg, e);
			msg.setResult(1);
			msg.setResultmessage("Can't parse the ctrip response message into json.");
		}

		if (LOGGER.isLoggable(Level.FINER)) {
			LOGGER.exiting(CLASSNAME, METHOD, "[TRACE REFUND FLOW] End of Ctrip response.");
		}
		return msg;
	}
}
