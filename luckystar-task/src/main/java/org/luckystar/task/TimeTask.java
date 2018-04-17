package org.luckystar.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.luckystar.model.CacheInfo;
import org.luckystar.model.ChickenInfo;
import org.luckystar.model.EmailEntity;
import org.luckystar.model.LaborUnion;
import org.luckystar.service.DataBaseService;
import org.luckystar.util.MailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimeTask extends TimerTask {

	private static final Logger logger = LoggerFactory.getLogger(TimeTask.class);

	private String myName;
	
	private int mailRandom;
	
	private int mailFixed;
	
	private int maxSendNum;
	
	@Autowired
	private DataBaseService dataBaseService;

	public void init(int delay, int period, int mailRandom, int mailFixed, int maxSendNum) {
		myName = "time_task";
		this.mailRandom = mailRandom;
		this.mailFixed = mailFixed;
		this.maxSendNum = maxSendNum;
		new Timer(myName).schedule(this, delay * 1000, period * 1000);
	}
	
	@Override
	public void run() {
		try {
			Map<Integer, StringBuffer> messages = new HashMap<Integer, StringBuffer>();
			for(Entry<Long, EmailEntity> message : CacheInfo.emailContent.entrySet()) {
				ChickenInfo user = CacheInfo.chickenInfoCache.get(message.getKey());
				EmailEntity emailEntity = message.getValue();
				if(user != null) {
					LaborUnion laborUnion = CacheInfo.laborUnionCache.get(user.getlId());
					if(laborUnion != null) {
						if(emailEntity.getSendNum() > maxSendNum) {
							emailEntity.setContent("Cookie长时间未修复，系统自动关闭该用户<br>\r\n");
							dataBaseService.closeChicken(user.getId());
						} else {
							emailEntity.setSendNum(emailEntity.getSendNum() + 1);
						}
						StringBuffer sb = messages.get(user.getlId());
						if(sb == null) {
							sb = new StringBuffer();
							messages.put(user.getlId(), sb);
						}
						sb.append(laborUnion.getName()).append("/").append(user.getUserName()).append("(").append(user.getNickName()).append("):").append(emailEntity.getContent());
					} else {
						emailEntity.setSendNum(0);
					}
				} else {
					emailEntity.setSendNum(0);
				}
			}
//			CacheInfo.emailContent.clear();
			for(Entry<Integer, StringBuffer> message : messages.entrySet()) {
				try {
					StringBuffer sb = message.getValue();
					String address = CacheInfo.laborUnionCache.get(message.getKey()).getWorkerEmail();
					if(StringUtils.isNotEmpty(address) && sb != null) {
						String[] addrs = address.split(",");
						for(String addr : addrs) {
							MailUtils.sendMail("幸运星预警通知", sb.toString(), addr, null, mailRandom, mailFixed);											
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
