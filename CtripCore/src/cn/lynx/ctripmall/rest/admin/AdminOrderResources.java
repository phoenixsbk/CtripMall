package cn.lynx.ctripmall.rest.admin;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.lynx.ctripmall.db.CtripDBMgr;
import cn.lynx.ctripmall.db.model.OrderInfo;
import cn.lynx.ctripmall.db.model.Product;

@Path("/admin/orderinfo")
public class AdminOrderResources {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public List<OrderInfo> listAllOrders() {
		List<OrderInfo> result = CtripDBMgr.getInstance().queryEntitiesByProperties(OrderInfo.class, null, -1);
		for (OrderInfo oi : result) {
			List<Product> rr = oi.getProductList();
			if (rr == null)  continue;
			for (Product p : rr) {
				System.out.println(p.getProductName());
			}
		}
		return result;
	}
}
