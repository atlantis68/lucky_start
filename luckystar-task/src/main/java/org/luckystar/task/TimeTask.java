package org.luckystar.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.luckystar.model.CacheInfo;
import org.luckystar.model.ChickenInfo;
import org.luckystar.model.LaborUnion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TimeTask extends TimerTask {

	private static final Logger logger = LoggerFactory.getLogger(TimeTask.class);

	private String myName = "time_task";

	public void init() {
		new Timer(myName).schedule(this, 0, 12 * 60 * 1000);
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
				StringBuffer sb = message.getValue();
				String address = CacheInfo.laborUnionCache.get(message.getKey()).getEmail();
				if(StringUtils.isNotEmpty(address) && sb != null) {
					String[] addrs = address.split(",");
					for(String addr : addrs) {
						Thread.sleep((long)(new Random().nextFloat() * 10000 + 5000));
						sendMail("幸运星预警短信", sb.toString(), addr);											
					}
				}
			}
		} catch(Exception e) {
			logger.error("{} : ", myName, e);
		} finally {
			logger.info("{} send email successful", myName);
		}
	}
	
	private void sendMail(String subject, String content, String receiver) {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.qq.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "587");
			props.put("mail.user", "346411799@qq.com");
			props.put("mail.password", "gfzzbgwrkkhqbieb");
			Session ssn = Session.getInstance(props, new Authenticator() {

	            protected PasswordAuthentication getPasswordAuthentication() {
	                // 用户名、密码
	                String userName = props.getProperty("mail.user");
	                String password = props.getProperty("mail.password");
	                return new PasswordAuthentication(userName, password);
	            }
	        });
			
			MimeMessage message = new MimeMessage(ssn);
			InternetAddress fromAddress = new InternetAddress(props.getProperty("mail.user"), "幸运星平台");
			message.setFrom(fromAddress);
			InternetAddress toAddress = new InternetAddress(receiver);
			message.setRecipient(Message.RecipientType.TO, toAddress);
			message.setSubject(subject, "utf-8");
			message.setContent(content, "text/html;charset=utf-8");
 			Transport.send(message);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
