package org.luckystar.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.luckystar.model.CacheInfo;
import org.luckystar.model.ChickenInfo;
import org.luckystar.model.LaborUnion;
import org.luckystar.util.MailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TimeTask extends TimerTask {

	private static final Logger logger = LoggerFactory.getLogger(TimeTask.class);

	private String myName = "time_task";

	public void init() {
		new Timer(myName).schedule(this, 0, 10 * 60 * 1000);
	}
	
	@Override
	public void run() {
		try {
			Map<Integer, StringBuffer> messages = new HashMap<Integer, StringBuffer>();
			for(Entry<Long, String> message : CacheInfo.emailContent.entrySet()) {
				ChickenInfo user = CacheInfo.chickenInfoCache.get(message.getKey());
				if(user != null) {
					LaborUnion laborUnion = CacheInfo.laborUnionCache.get(user.getlId());
					if(laborUnion != null) {
						StringBuffer sb = messages.get(user.getlId());
						if(sb == null) {
							sb = new StringBuffer();
							messages.put(user.getlId(), sb);
						}
						sb.append(laborUnion.getName()).append("/").append(user.getUserName()).append("(").append(user.getNickName()).append("):").append(message.getValue());
					}
				}
			}
			CacheInfo.emailContent.clear();
			for(Entry<Integer, StringBuffer> message : messages.entrySet()) {
				try {
					StringBuffer sb = message.getValue();
					String address = CacheInfo.laborUnionCache.get(message.getKey()).getEmail();
					if(StringUtils.isNotEmpty(address) && sb != null) {
						String[] addrs = address.split(",");
						for(String addr : addrs) {
							Thread.sleep((long)(new Random().nextFloat() * 60000 + 60000));
							MailUtils.sendMail("幸运星预警短信", sb.toString(), addr);											
						}
					}
				} catch(Exception e) {
					logger.error("邮件发送异常：{} : ", myName, e);
				}
			}
		} catch(Exception e) {
			logger.error("{} : ", myName, e);
		} finally {
			logger.info("{} send email successful", myName);
		}
	}

}
