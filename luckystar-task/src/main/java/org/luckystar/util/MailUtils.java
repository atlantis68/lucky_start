package org.luckystar.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(MailUtils.class);

	private static int seq;
	
	private static int maxRetry;
	
	private static List<Properties> platformMail;
	
	static {
		seq = 0;
		maxRetry = 3;
		platformMail = new ArrayList<Properties>();
		Properties qqProps = new Properties();
		qqProps.put("mail.smtp.host", "smtp.qq.com");
		qqProps.put("mail.smtp.auth", "true");
		qqProps.put("mail.smtps.timeout", 10000);
		qqProps.put("mail.smtps.connectiontimeout", 10000);
		qqProps.put("mail.smtp.port", "587");
		qqProps.put("mail.user", "346411799@qq.com");
		qqProps.put("mail.password", "gfzzbgwrkkhqbieb");
		platformMail.add(qqProps);
//		Properties wyProps = new Properties();
//		wyProps.put("mail.smtp.host", "smtp.163.com");
//		wyProps.put("mail.smtp.auth", "true");
//		wyProps.put("mail.smtps.timeout", 10000);
//		wyProps.put("mail.smtps.connectiontimeout", 10000);
//		wyProps.put("mail.user", "18980868096@163.com");
//		wyProps.put("mail.password", "Atlantis68");
//		platformMail.add(wyProps);
//		Properties sinaProps = new Properties();
//		sinaProps.put("mail.smtp.host", "smtp.sina.com");
//		sinaProps.put("mail.smtp.auth", "true");
//		sinaProps.put("mail.smtps.timeout", 10000);
//		sinaProps.put("mail.smtps.connectiontimeout", 10000);
//		sinaProps.put("mail.user", "atlantis68@sina.com");
//		sinaProps.put("mail.password", "`1234567890-");
//		platformMail.add(sinaProps);
	}

	public static void sendMail(String subject, String content, String receiver, int mailRandom, int mailFixed) {
		int curSeq = 0;
		boolean isSuccess = false;
		while(curSeq++ < maxRetry && !isSuccess) {
			try {
				Thread.sleep((long)(new Random().nextFloat() * mailRandom * 1000 + mailFixed * 1000));
				Properties props = platformMail.get(seq++ % platformMail.size());
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
	 			isSuccess = true;
	 			logger.info("send mail to {} successful in {} times", curSeq, receiver);
			} catch(Exception e) {
				e.printStackTrace();
	 			logger.error("send mail to {} failed in {} times", curSeq, receiver);
			}	
		}

	}

}
