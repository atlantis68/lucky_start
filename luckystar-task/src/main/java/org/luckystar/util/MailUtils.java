package org.luckystar.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils {

	public static void sendMail(String subject, String content, String receiver) {
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
