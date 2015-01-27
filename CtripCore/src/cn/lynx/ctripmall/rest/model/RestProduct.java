package cn.lynx.ctripmall.rest.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.json.java.JSONObject;

import cn.lynx.ctripmall.db.CtripDBMgr;
import cn.lynx.ctripmall.db.model.Product;
import static cn.lynx.ctripmall.rest.util.RestUtil.*;

public class RestProduct {

	public static Product fromJSON(JSONObject jo) {
		if (jo.containsKey("productname")) {
			Product p = new Product();
			
			p.setExProductId(getString(jo, "exproductid"));
			p.setExSubProductId(getString(jo, "exsubproductid"));
			p.setProductName(getString(jo, "productname"));
			p.setQuantity(getLong(jo, "quantity"));
			p.setPrice(getDouble(jo, "price"));
			p.setExperience(getInt(jo, "experience"));
			p.setSettlePrice(getDouble(jo, "settleprice"));
			p.setColor(getString(jo, "color"));
			p.setSize(getString(jo, "size"));
			
			return p;
		} else {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("exProductId", getString(jo, "exproductid"));
			param.put("exSubProductId", getString(jo, "exsubproductid"));
			List<Product> result = CtripDBMgr.getInstance().queryEntitiesByProperties(Product.class, param, 1);
			if (result == null || result.size() < 1) {
				throw new IllegalArgumentException("Can't find the corresponding Product entity from database.");
			}
			
			return result.get(0);
		}
	}
}
