package cn.lynx.ctripmall.rest.admin;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.lynx.ctripmall.db.CtripDBMgr;
import cn.lynx.ctripmall.db.model.OrderInfo;

@Path("/admin/orderinfo")
public class AdminOrderResources {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrderInfo> listAllOrders() {
		return CtripDBMgr.getInstance().queryEntitiesByProperties(OrderInfo.class, null, -1);
	}
}
