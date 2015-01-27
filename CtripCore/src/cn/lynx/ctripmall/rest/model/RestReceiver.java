package cn.lynx.ctripmall.rest.model;

import com.ibm.json.java.JSONObject;

import cn.lynx.ctripmall.db.model.Receiver;
import static cn.lynx.ctripmall.rest.util.RestUtil.*;

public class RestReceiver {
	public static Receiver fromJSON(JSONObject jo) {
		Receiver r = new Receiver();
		r.setContactName(getString(jo, "contactname"));
		r.setMobilePhone(getString(jo, "mobilephone"));
		r.setEmail(getString(jo, "email"));
		r.setProvinceName(getString(jo, "provincename"));
		r.setCityName(getString(jo, "cityname"));
		r.setZoneName(getString(jo, "zonename"));
		r.setAddress(getString(jo, "address"));
		r.setRemark(getOptionalString(jo, "remark", ""));
		
		return r;
	}
}
