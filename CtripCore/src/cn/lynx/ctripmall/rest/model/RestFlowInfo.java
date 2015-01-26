package cn.lynx.ctripmall.rest.model;

import com.ibm.json.java.JSONObject;

import cn.lynx.ctripmall.model.FlowInfo;

public class RestFlowInfo extends FlowInfo implements RestObj {
	
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
		// TODO Auto-generated method stub
	}
}
