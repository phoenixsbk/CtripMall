package cn.lynx.ctripmall.rest.model;

import com.ibm.json.java.JSONObject;

import cn.lynx.ctripmall.model.Receiver;

public class RestReceiver extends Receiver implements RestObj {
	
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
		this.contactName = (String) jo.get("contactname");
		this.mobilePhone = (String) jo.get("mobilephone");
		this.email = (String) jo.get("email");
		this.provinceName = (String) jo.get("provincename");
		this.cityName = (String) jo.get("cityname");
		this.zoneName = (String) jo.get("zonename");
		this.address = (String) jo.get("address");
		this.remark = (String) jo.get("remark");
	}
}
