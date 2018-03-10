/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import java.util.Properties;
import javax.mail.*;

/**
 *
 * @author Levi
 */
public class Email {
    /**
	 * Sends an email to an address with an image attachment.
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void emailSender() throws AddressException, MessagingException
	{
		//Set up SMTP details
		Properties emailServer = new Properties();
		emailServer.put("mail.smtp.host","smtp.gmail.com");
		emailServer.put("mail.smtp.socketFactory.port", "465");
		emailServer.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		emailServer.put("mail.smtp.auth", "true");
		emailServer.put("mail.smtp.port", "465");
		
		//Supply gmail authentication details. 
		Session session = Session.getDefaultInstance(emailServer,
				new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication("emailsenderbangor@gmail.com","Abc.123456");
				}
			}
		);
		
		//Compose and Send Message
		Message message=new MimeMessage(session);
		message.setFrom(new InternetAddress("levroque@gmail.com"));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("levroque@gmail.com"));
		message.setSubject("Visitor Alert");
        
		// Set the text of the email message text.
        MimeBodyPart messagePart = new MimeBodyPart();
        messagePart.setText("Your house was visited on "+getDate()+". Please see the attached image");
        
        // Set the email attachment file
        FileDataSource fileDataSource = new FileDataSource("C:\\Users\\Levi\\workspace\\ArduinoProject\\Visitor.jpg");
        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.setDataHandler(new DataHandler(fileDataSource));
        attachmentPart.setFileName(fileDataSource.getName());
		
        //Create the Email
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messagePart);
        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);

		Transport.send(message);
		System.out.println("Email sent");
		
		
	}
}
