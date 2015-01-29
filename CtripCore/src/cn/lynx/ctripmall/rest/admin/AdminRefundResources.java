package cn.lynx.ctripmall.rest.admin;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cn.lynx.ctripmall.db.CtripDBMgr;
import cn.lynx.ctripmall.db.model.Refund;

@Path("/admin/refundinfos")
public class AdminRefundResources {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Refund> listRefund() {
		return CtripDBMgr.getInstance().queryEntitiesByProperties(Refund.class, null, -1);
	}
}
