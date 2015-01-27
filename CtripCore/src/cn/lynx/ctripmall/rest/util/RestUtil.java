package cn.lynx.ctripmall.rest.util;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

public class RestUtil {
	
	@SuppressWarnings("unchecked")
	private static <T> T getVal(JSONObject jo, String key, Class<T> clazz) {
		if (jo.containsKey(key)) {
			Object val = jo.get(key);
			if ((val instanceof Long) && (clazz.isAssignableFrom(Double.class))) {
				String dstr = val.toString();
				return (T) Double.valueOf(dstr);
			}
			
			if ((val instanceof Long) && (clazz.isAssignableFrom(Integer.class))) {
				String istr = val.toString();
				return (T) Integer.valueOf(istr);
			}
			
			return (T) jo.get(key);
		} else {
			throw new IllegalArgumentException("Unsatisfied filed of [" + key + "] from json string:" + jo.toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T getVal(JSONObject jo, String key, T defaultValue) {
		if (jo.containsKey(key)) {
			return (T) jo.get(key);
		} else {
			return defaultValue;
		}
	}
	
	public static String getString(JSONObject jo, String key) {
		return getVal(jo, key, String.class);
	}
	
	public static String getOptionalString(JSONObject jo, String key, String defaultValue) {
		return getVal(jo, key, defaultValue);
	}
	
	public static Long getLong(JSONObject jo, String key) {
		return getVal(jo, key, Long.class);
	}
	
	public static Long getOptionalLong(JSONObject jo, String key, Long defaultValue) {
		return getVal(jo, key, defaultValue);
	}
	
	public static Double getDouble(JSONObject jo, String key) {
		return getVal(jo, key, Double.class);
	}
	
	public static Double getOptionalDouble(JSONObject jo, String key, Double defaultValue) {
		return getVal(jo, key, defaultValue);
	}
	
	public static Integer getInt(JSONObject jo, String key) {
		return getVal(jo, key, Integer.class);
	}
	
	public static Integer getOptionalInt(JSONObject jo, String key, Integer defaultValue) {
		return getVal(jo, key, defaultValue);
	}
	
	public static JSONArray getArray(JSONObject jo, String key) {
		return getVal(jo, key, JSONArray.class);
	}
	
	public static JSONObject getObject(JSONObject jo, String key) {
		return getVal(jo, key, JSONObject.class);
	}
}
