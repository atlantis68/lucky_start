package org.luckystar.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
			Map<Integer, LaborUnion> newLaborUnionCache = new HashMap<Integer, LaborUnion>();
			List<Map<String, Object>> laborUnions = dataBaseService.getLaborUnion(1);
			if(laborUnions != null) {
				for(Map<String, Object> lu : laborUnions) {
					LaborUnion laborUnion = new LaborUnion();
					int lId = Integer.parseInt(lu.get("l_id").toString());
					laborUnion.setlId(lId);
					laborUnion.setName(lu.get("name").toString());
					laborUnion.setRegDate(lu.get("reg_date").toString());
					newLaborUnionCache.put(lId, laborUnion);
				}
				synchronized (CacheInfo.laborUnionCache) {
					CacheInfo.laborUnionCache = newLaborUnionCache;					
				}
			}
			Map<Long, ChickenInfo> newChickenInfoCache = new HashMap<Long, ChickenInfo>();
			List<Map<String, Object>> chickenInfos = dataBaseService.getChickenInfo(1);
			if(chickenInfos != null) {
				for(Map<String, Object> ci : chickenInfos) {
					ChickenInfo chickenInfo = new ChickenInfo();
					long starId = Long.parseLong(ci.get("star_id").toString());
					chickenInfo.setStarId(starId);
					chickenInfo.setlId(Integer.parseInt(ci.get("l_id").toString()));
					chickenInfo.setUserName(ci.get("user_name").toString());
					chickenInfo.setRegDate(ci.get("reg_date").toString());
					chickenInfo.setCookie(ci.get("cookie").toString());
					chickenInfo.setTimeRate(Float.parseFloat(ci.get("time_rate").toString()));
					chickenInfo.setBeanRate(Float.parseFloat(ci.get("bean_rate").toString()));
					newChickenInfoCache.put(starId, chickenInfo);
				}
				synchronized (CacheInfo.chickenInfoCache) {
					CacheInfo.chickenInfoCache = newChickenInfoCache;					
				}
			}
		} catch(Exception e) {
			logger.error("{} : ", myName, e);
		} finally {
			logger.info("{}从数据库同步完成", myName);
		}
	}
}
