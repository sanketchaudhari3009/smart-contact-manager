package com.smart.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
	public boolean sendEmail(String subject, String message, String to) {
		//rest of the code
		boolean f = false;
		
		String from = "smartcontactmanager4@gmail.com";
		String password = "Smart@8215";
		
		//var for gmail
		String host = "smtp.gmail.com";
		
		//get system properties
		Properties properties = System.getProperties();
		System.out.println("Properties: "+properties);
		
		//setting imp info to properties object
		
		//host
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.user", from);
		properties.put("mail.smtp.password", password);
		
		properties.put("mail.smtp.ssl.enable", "false");
		properties.put("mail.smtp.auth", "false");
		
		//Step 1: to get the session object..
				Session session=Session.getInstance(properties, new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {				
						return new PasswordAuthentication("smartcontactmanager4@gmail.com", password);
					}
					
					
					
				});
				
				session.setDebug(true);
				
				//Step 2 : compose the message [text,multi media]
				MimeMessage m = new MimeMessage(session);
				
				try {
				
				//from email
				m.setFrom(from);
				
				//adding recipient to message
				m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				
				//adding subject to message
				m.setSubject(subject);
			
				
				//adding text to message
				m.setText(message);
				
				//send 
				
				//Step 3 : send the message using Transport class
				Transport.send(m);
				
				System.out.println("Sent success...................");
				
				
				}catch (Exception e) {
					e.printStackTrace();
				}
				return f;
					
			}
}
