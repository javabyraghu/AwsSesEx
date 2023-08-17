package com.app.raghu;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;

public class SesSmtpSender {
    public static void main(String[] args) throws MessagingException, IOException {
        String sender = "javabyraghu@gmail.com";
        String recipient = "javabyraghu@gmail.com";
        String subject = "Hello from AWS SES SMTP";
        String bodyText = "This is a test email sent using AWS SES SMTP.";

        Region region = Region.AP_SOUTH_1; // Change to your desired region

        SesClient sesClient = SesClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(region)
                .build();

        // Create a JavaMail MIME message
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(sender));
        mimeMessage.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(recipient));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(bodyText);

        // Convert MimeMessage to raw email
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mimeMessage.writeTo(outputStream);
        ByteBuffer rawEmailByteBuffer = ByteBuffer.wrap(outputStream.toByteArray());

        // Send the raw email using SES
        SendRawEmailRequest emailRequest = SendRawEmailRequest.builder()
                .rawMessage(RawMessage.builder().data(SdkBytes.fromByteBuffer(rawEmailByteBuffer)).build())
                .build();

        sesClient.sendRawEmail(emailRequest);
        System.out.println("Email sent successfully!");

        sesClient.close();
    }
}
