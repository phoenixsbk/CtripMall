package cn.lynx.ctripmall.rest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
		ResultMsg msg = new ResultMsg();
		msg.setResult(0);
		msg.setResultmessage("hello world");
		return msg;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMsg postOrder(@FormParam("data") String data, @FormParam("sign") String sign) {
		if (LOGGER.isLoggable(Level.FINEST)) {
			LOGGER.logp(Level.FINEST, CLASSNAME, "postOrder", "Receive the post message with data:[" + data + "] and sign:[" + sign + "]");
		}
		try {
			JSONObject jo = (JSONObject)JSON.parse(data);
		} catch (Exception e) {
			LOGGER.logp(Level.SEVERE, CLASSNAME, "postOrder", "[Exception] When parsing the json object from ctrip data:[" + data + "]", e);
		}
		return new ResultMsg();
	}
}
