package org.luckystar.task;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.luckystar.model.CacheInfo;
import org.luckystar.model.ChickenInfo;
import org.luckystar.model.LaborUnion;
import org.luckystar.service.DataBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CacheTask extends TimerTask {

	private static final Logger logger = LoggerFactory.getLogger(CacheTask.class);

	@Autowired
	private DataBaseService dataBaseService;
	
	private String myName = "cache_task";

	public void init() {
		new Timer(myName).schedule(this, 0, 5 * 60 * 1000);
	}
	
	@Override
	public void run() {
		try {
			ConcurrentHashMap<Integer, LaborUnion> newLaborUnionCache = new ConcurrentHashMap<Integer, LaborUnion>();
			List<Map<String, Object>> laborUnions = dataBaseService.getLaborUnion("ON");
			if(laborUnions != null) {
				for(Map<String, Object> lu : laborUnions) {
					LaborUnion laborUnion = new LaborUnion();
					laborUnion.setlId(Integer.parseInt(lu.get("l_id").toString()));
					laborUnion.setName(lu.get("name").toString());
					laborUnion.setRegDate(lu.get("reg_date").toString());
					laborUnion.setType(lu.get("jhi_type").toString());
					laborUnion.setEmail(lu.get("email").toString());
					laborUnion.setMinTask(lu.get("min_task") != null ? Integer.parseInt(lu.get("min_task").toString()) : -1);
					laborUnion.setMaxTask(lu.get("max_task") != null ? Integer.parseInt(lu.get("max_task").toString()) : -1);
					laborUnion.setBoundaryValue(lu.get("boundary_value") != null ? 
							Integer.parseInt(lu.get("boundary_value").toString()) : 240);
					laborUnion.setAutoExchange(Boolean.parseBoolean(lu.get("auto_exchange").toString()));
					newLaborUnionCache.put(Integer.parseInt(lu.get("id").toString()), laborUnion);
				}
				synchronized (CacheInfo.laborUnionCache) {
					CacheInfo.laborUnionCache = newLaborUnionCache;					
				}
			}
			ConcurrentHashMap<Long, ChickenInfo> newChickenInfoCache = new ConcurrentHashMap<Long, ChickenInfo>();
			List<Map<String, Object>> chickenInfos = dataBaseService.getChickenInfo("ON");
			if(chickenInfos != null) {
				for(Map<String, Object> ci : chickenInfos) {
					int lId = Integer.parseInt(ci.get("labor_union_id").toString());
					if(CacheInfo.laborUnionCache.containsKey(lId)) {
						ChickenInfo chickenInfo = new ChickenInfo();
						chickenInfo.setId(Long.parseLong(ci.get("id").toString()));
						long starId = Long.parseLong(ci.get("star_id").toString().trim());
						chickenInfo.setStarId(starId);
						chickenInfo.setlId(lId);
						chickenInfo.setUserName(ci.get("user_name").toString());
						chickenInfo.setNickName(ci.get("nick_name") != null ? ci.get("nick_name").toString() : null);
						chickenInfo.setRoomId(ci.get("room_id") != null ? ci.get("room_id").toString() : null);
						chickenInfo.setPhoneNumber(ci.get("phone_number") != null ? ci.get("phone_number").toString() : null);
						chickenInfo.setQQ(ci.get("qq") != null ? ci.get("qq").toString() : null);
						chickenInfo.setWeChat(ci.get("wei_chat") != null ? ci.get("wei_chat").toString() : null);
						chickenInfo.setRegDate(ci.get("reg_date").toString());
						chickenInfo.setCookie(ci.get("cookie") != null ? ci.get("cookie").toString() : null);
						chickenInfo.setLoginName(ci.get("login_name") != null ? ci.get("login_name").toString() : null);
						chickenInfo.setPassword(ci.get("jhi_password") != null ? ci.get("jhi_password").toString() : null);
						chickenInfo.setTimeRate(Float.parseFloat(ci.get("time_rate").toString()));
						chickenInfo.setBeanRate(Float.parseFloat(ci.get("bean_rate").toString()));
						newChickenInfoCache.put(starId, chickenInfo);
					} else {
//						logger.info("{} in labor union {} is not exists", ci.get("star_id"), lId);
					}
				}
				synchronized (CacheInfo.chickenInfoCache) {
					CacheInfo.chickenInfoCache = newChickenInfoCache;					
				}
			}
		} catch(Exception e) {
			logger.error("{} : ", myName, e);
		} finally {
			logger.info("{} synchronized from database", myName);
		}
	}
}
