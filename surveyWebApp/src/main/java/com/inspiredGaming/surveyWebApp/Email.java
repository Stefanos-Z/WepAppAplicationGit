/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inspiredGaming.surveyWebApp;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Contains methods to send email notifications.
 * @author      Levi Roque-Nunes
 * @version     1
 * @since       10/03/2018
 */
public class Email {
        
    private String emailAddress;
    private String url;
    
    /**
     * Constructor to create an email object.
     * @param emailAddress
     * @param url 
     */
    public Email(String emailAddress, String url)
    {
        this.emailAddress = emailAddress;
        this.url = url;
    }
    
    /**
    * Sends an email to an address with link to a survey.
    * @throws AddressException
    * @throws MessagingException
    */
    public void send() throws AddressException, MessagingException
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
        //message.setFrom(new InternetAddress("levroque@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress));
        message.setSubject("Dallos Survey");

        // Set the text of the email message text.
        MimeBodyPart messagePart = new MimeBodyPart();
        messagePart.setText("You have been invited to complete the following survey:\n \n"+url);

        //Create the Email
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messagePart);
        message.setContent(multipart);

        Transport.send(message);

        //debug
        System.out.println("Email sent");

    }
    
    /**
    * Sends an email to an address with link to a survey.
    * @throws AddressException
    * @throws MessagingException
    */
    public void sendCompletionEmail(String surveyName) throws AddressException, MessagingException
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
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication("emailsenderbangor@gmail.com","Abc.123456");
                    }
                }
        );

        //Compose and Send Message
        Message message=new MimeMessage(session);
        //message.setFrom(new InternetAddress("levroque@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress));
        message.setSubject("Dallos: New response recieved for "+surveyName);

        // Set the text of the email message text.
        MimeBodyPart messagePart = new MimeBodyPart();
        messagePart.setText("A new repsonse has been received for survey "+surveyName+". You can view the results at "+url);

        //Create the Email
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messagePart);
        message.setContent(multipart);

        Transport.send(message);

        //debug
        System.out.println("Email sent");

    }
}
