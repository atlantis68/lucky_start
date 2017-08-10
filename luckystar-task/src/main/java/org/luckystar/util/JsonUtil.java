package org.luckystar.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;

public class JsonUtil {
	
	public static Map<String, String> parseJsonToMapWithString(String text) {
		return JSON.parseObject(text, Map.class);
	}
	
	public static Map<String, Object> parseJsonToMapWithObject(String text) {
		return JSON.parseObject(text, Map.class);
	}
}
