package org.luckystar.model;

import java.util.concurrent.ConcurrentHashMap;

public class CacheInfo {

	public static ConcurrentHashMap<Integer, LaborUnion> laborUnionCache;
	
	public static ConcurrentHashMap<Long, ChickenInfo> chickenInfoCache;
	
	public static ConcurrentHashMap<Long, String> emailContent;
	
	static {
		laborUnionCache = new ConcurrentHashMap<Integer, LaborUnion>();
		chickenInfoCache = new ConcurrentHashMap<Long, ChickenInfo>();
		emailContent = new ConcurrentHashMap<Long, String>();
	}
	
}
