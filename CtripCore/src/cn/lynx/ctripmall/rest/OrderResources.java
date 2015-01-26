package cn.lynx.ctripmall.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/orderinfo")
public class OrderResources {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMsg postOrder(@FormParam("data") String data, @FormParam("sign") String sign) {
		System.out.println("Visited orderinfo");
		return new ResultMsg();
	}
}
