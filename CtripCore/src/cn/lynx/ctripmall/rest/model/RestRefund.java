package cn.lynx.ctripmall.rest.model;

import com.ibm.json.java.JSONObject;

import cn.lynx.ctripmall.model.Refund;

public class RestRefund extends Refund implements RestObj {
	
	private String uuid;

	@Override
	public String getUuid() {
		return this.uuid;
	}

	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public void fromJSON(JSONObject jo) {
		this.refundApplyId = (Long) jo.get("refundapplyid");
		this.timestamp = (Long) jo.get("timestamp");
		this.operateType = (Integer) jo.get("operatetype");
	}

}
