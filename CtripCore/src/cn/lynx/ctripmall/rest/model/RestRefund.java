package cn.lynx.ctripmall.rest.model;

import com.ibm.json.java.JSONObject;

import cn.lynx.ctripmall.db.model.Product;
import cn.lynx.ctripmall.db.model.Refund;
import static cn.lynx.ctripmall.rest.util.RestUtil.*;

public class RestRefund {
	
	public static Refund fromJSON(JSONObject jo) {
		Refund r = new Refund();
		r.setRefundApplyId(getLong(jo, "refundapplyid"));
		r.setTimestamp(getLong(jo, "timestamp"));
		r.setOperateType(getInt(jo, "operatetype"));
		r.setRemark(getOptionalString(jo, "remark", ""));
		
		return r;
	}

	public static String toJSONCtrip(Refund rf) {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		sb.append("\"refundapplyid\":" + rf.getRefundApplyId() + ",");
		sb.append("\"timestamp\":" + rf.getTimestamp() + ",");
		sb.append("\"flowcompanyname\":\"" + rf.getFlowInfo().getFlowCompanyName() + "\",");
		sb.append("\"flowticketnumber\":\"" + rf.getFlowInfo().getFlowTicketNumber() + "\",");
		sb.append("\"flowstatus\":" + rf.getFlowInfo().getFlowStatus() + ",");
		sb.append("\"flowremark\":\"" + rf.getFlowInfo().getFlowRemark() + "\",");
		sb.append("\"productlist\": [");
		
		int psize = rf.getProductList().size();
		for (int i = 0;i < psize;i ++) {
			if (i != 0) {
				sb.append(",");
			}
			
			Product p = rf.getProductList().get(i);
			
			sb.append("{ ");
			sb.append("\"exproductid\":\"" + p.getExProductId() + "\",");
			sb.append("\"exsubproductid\":\"" + p.getExSubProductId() + "\",");
			sb.append("\"quantity\":" + p.getQuantity());
			sb.append(" }");
		}
		
		sb.append("] }");
		
		return sb.toString();
	}
}
