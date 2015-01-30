package cn.lynx.ctripmall.rest.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.lynx.ctripmall.db.CtripDBMgr;
import cn.lynx.ctripmall.db.model.OrderInfo;

@Path("/admin/orderinfo")
public class AdminOrderResources {
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public List<OrderInfo> listAllOrders() {
		return CtripDBMgr.getInstance().queryEntitiesByProperties(OrderInfo.class, null, -1);
	}
	
	@GET
	@Path("/uuid/{uuid}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public OrderInfo getOrderById(@PathParam("uuid") String uuid) {
		return CtripDBMgr.getInstance().queryEntity(OrderInfo.class, uuid);
	}
	
	@GET
	@Path("/type/{type}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public List<OrderInfo> listOrderByType(@PathParam("type") String type) {
		if (type != null) {
			Map<String, Object> param = new HashMap<String, Object>();
			if (type.equalsIgnoreCase("ordernew")) {
				param.put("flowInfo", null);
			} else if (type.equalsIgnoreCase("orderflow")) {
				param.put("flowInfo", "$NONENULL$");
			} else {
				param = null;
			}
			
			return CtripDBMgr.getInstance().queryEntitiesByProperties(OrderInfo.class, param, -1);
		} else {
			return CtripDBMgr.getInstance().queryEntitiesByProperties(OrderInfo.class, null, -1);
		}
	}
}
