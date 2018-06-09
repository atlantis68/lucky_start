package org.luckystar.model;

import java.util.concurrent.ConcurrentHashMap;

public class CacheInfo {

	public static ConcurrentHashMap<Integer, LaborUnion> laborUnionCache;

	public static ConcurrentHashMap<Long, ChickenInfo> chickenInfoCache;

	public static ConcurrentHashMap<Long, EmailEntity> emailContent;
	
	public static int modNumber;
	
	public static int totalNumber;
	
	public static String role;

	static {
		laborUnionCache = new ConcurrentHashMap<Integer, LaborUnion>();
		chickenInfoCache = new ConcurrentHashMap<Long, ChickenInfo>();
		emailContent = new ConcurrentHashMap<Long, EmailEntity>();
	}
	
	public static void putContent(long starId, String content) {
		EmailEntity emailEntity = emailContent.get(starId);
		if(emailEntity != null) {
			emailEntity.setContent(content);
		} else {
			emailEntity = new EmailEntity();
			emailEntity.setContent(content);
			emailContent.put(starId, emailEntity);
		}
	}

}