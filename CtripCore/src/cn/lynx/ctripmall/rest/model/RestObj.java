package cn.lynx.ctripmall.rest.model;

import com.ibm.json.java.JSONObject;

public interface RestObj {
	public String getUuid();
	
	public void setUuid(String uuid);
	
	public void fromJSON(JSONObject jo);
}
