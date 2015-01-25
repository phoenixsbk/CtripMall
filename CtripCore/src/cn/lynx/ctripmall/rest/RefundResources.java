package cn.lynx.ctripmall.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/refundinfo")
public class RefundResources {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMsg postRefundInfo(@FormParam("data") String data, @FormParam("sign") String sign) {
		return new ResultMsg();
	}
}
