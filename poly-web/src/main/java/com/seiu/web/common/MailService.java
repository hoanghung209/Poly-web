package com.seiu.web.common;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {
	public static void sendMessage(String recipient, String subject, String message)
		    throws MessagingException
		  {
		    if (theService == null) {
		      theService = new MailService();
		    }
		    MimeMessage mimeMessage = new MimeMessage(mailSession);
		    
		    mimeMessage.setFrom(new InternetAddress(FROM));
		    mimeMessage.setSender(new InternetAddress(FROM));
		    mimeMessage.setSubject(subject, "utf-8");
		    mimeMessage.setContent(message, "text/html; charset=utf-8");
		    
		    mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		    
		    Transport transport = mailSession.getTransport("smtps");
		    transport.connect(HOST, PORT, USER, PASSWORD);
		    
		    transport.sendMessage(mimeMessage, mimeMessage.getRecipients(Message.RecipientType.TO));
		    transport.close();
		  }
		  
		  private MailService()
		  {
		    Properties props = new Properties();
		    
		    props.put("mail.transport.protocol", "smtps");
		    props.put("mail.smtps.host", HOST);
		    props.put("mail.smtps.auth", "true");
		    props.put("mail.smtp.from", FROM);
		    props.put("mail.smtps.quitwait", "false");
		    
		    mailSession = Session.getDefaultInstance(props);
		    mailSession.setDebug(true);
		  }
		  
		  private static MailService theService = null;
		  private static Session mailSession;
		  private static final String HOST = Config.host;
		  private static final int PORT = Config.port;
		  private static final String USER = Config.user;
		  private static final String PASSWORD = Config.password;
		  private static final String FROM = Config.from;
}
