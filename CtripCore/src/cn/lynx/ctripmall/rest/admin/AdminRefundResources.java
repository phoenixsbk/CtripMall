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
import cn.lynx.ctripmall.db.model.Refund;

@Path("/admin/refundinfo")
public class AdminRefundResources {

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public List<Refund> listRefund() {
		return CtripDBMgr.getInstance().queryEntitiesByProperties(Refund.class, null, -1);
	}
	
	@GET
	@Path("/uuid/{uuid}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Refund getRefundByUuid(@PathParam("uuid") String uuid) {
		return CtripDBMgr.getInstance().queryEntity(Refund.class, uuid);
	}
	
	@GET
	@Path("/type/{type}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public List<Refund> listRefundByType(@PathParam("type") String type) {
		if (type != null) {
			Map<String, Object> param = new HashMap<String, Object>();
			if (type.equalsIgnoreCase("refundnew")) {
				param.put("flowInfo", null);
			} else if (type.equalsIgnoreCase("refundflow")) {
				param.put("flowInfo", "$NONENULL$");
			} else {
				param = null;
			}
			return CtripDBMgr.getInstance().queryEntitiesByProperties(Refund.class, param, -1);
		} else {
			return CtripDBMgr.getInstance().queryEntitiesByProperties(Refund.class, null, -1);
		}
	}
}
