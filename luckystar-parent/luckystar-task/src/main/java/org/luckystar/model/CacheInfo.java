package org.luckystar.model;

import java.util.HashMap;
import java.util.Map;

public class CacheInfo {

	public static Map<Integer, LaborUnion> laborUnionCache;
	
	public static Map<Long, ChickenInfo> chickenInfoCache;
	
	static {
		laborUnionCache = new HashMap<Integer, LaborUnion>();
		chickenInfoCache = new HashMap<Long, ChickenInfo>();
	}
	
}
