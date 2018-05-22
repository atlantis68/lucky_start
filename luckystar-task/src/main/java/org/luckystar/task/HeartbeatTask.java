package org.luckystar.task;

import java.util.Timer;
import java.util.TimerTask;

import org.luckystar.model.CacheInfo;
import org.luckystar.service.DataBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HeartbeatTask extends TimerTask {

	private static final Logger logger = LoggerFactory.getLogger(HeartbeatTask.class);

	@Autowired
	private DataBaseService dataBaseService;
	
	private String myName = "heartbeat_task";
	
	private String groupId;
	
	private int instanceId;
	
	private int threshold;
	
	private int heartbeatPeriod;

	public void init(String groupId, int instanceId, int heartbeatDelay, int heartbeatPeriod, int threshold) {
		this.groupId = groupId;
		this.instanceId = instanceId;
		this.threshold = threshold;
		this.heartbeatPeriod = heartbeatPeriod;
		new Timer(myName).schedule(this, heartbeatDelay, heartbeatPeriod * 1000);
	}
	
	@Override
	public void run() {
		try {
			CacheInfo.totalNumber = dataBaseService.updateHeartbeat(groupId, instanceId, threshold * heartbeatPeriod);
		} catch(Exception e) {
			logger.error("{} : ", myName, e);
		} finally {
			logger.info("{} synchronized from database", myName);
		}
	}
}
