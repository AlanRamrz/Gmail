package com.gmail.ui;

import java.io.Serializable;
import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Named
@RequestScoped
public class MailBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8883987679233837452L;
	private String message;

	public MailBean() {
		this.message = "This is a test message";
		sendMail();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void sendMail() {
		String host = "smtp.gmail.com";
		String from = "from@gmail.com";
		String pass = "pass";

		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true"); // added this line
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		// Get the default Session object.
		Session session = Session.getDefaultInstance(props);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set sender
			message.setFrom(new InternetAddress(from));

			// Set recipient
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("to@gmail.com"));

			// Set Subject: header field
			message.setSubject("SUBJECT");

			// set content and define type
			message.setContent("CONTENT", "text/html; charset=utf-8");

			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
