package cn.lynx.ctripmall.rest.model;

import com.ibm.json.java.JSONObject;

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

}
