package com.app.raghu;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SesSmtpJavaMailSender {
    public static void main(String[] args) {
        String sender = "javabyraghu@gmail.com";
        String recipient = "javabyraghu@gmail.com";
        String subject = "Hello from AWS SES SMTP with JavaMail";
        String bodyText = "This is a test email sent using AWS SES SMTP and JavaMail.";

        // SMTP server settings (Replace with your SES SMTP settings)
        String smtpHost = "email-smtp.ap-south-1.amazonaws.com";
        int smtpPort = 587;
        String smtpUsername = "AWS SDK - IAM USER ACCESS KEY";
        String smtpPassword = "AWS SDK - IAM USER SECRET KEY";

        // Setup JavaMail properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUsername, smtpPassword);
            }
        });

        try {
            // Create a new MimeMessage
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(bodyText);

            // Send the message using the Transport class
            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
