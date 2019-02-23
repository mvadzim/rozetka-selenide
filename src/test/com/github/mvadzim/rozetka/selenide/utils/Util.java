package com.github.mvadzim.rozetka.selenide.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class Util {
    private final String configPopertiesPath = "./src/test/resources/config.properties";
    public final String tmpFilePath = getConfigPoperty("report.tmpfile.path");

    public void writeTextFile(String filePath, String text) {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath), "utf-8"));
            writer.write(text);
        } catch (IOException ex) {
            System.err.println("IOException!\n" + Arrays.toString(ex.getStackTrace()));
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                System.err.println("IOException!\n" + Arrays.toString(ex.getStackTrace()));
            }
        }
    }

    public String getConfigPoperty(String propertyName) {
        return getProperty(propertyName, configPopertiesPath);
    }

    public String getProperty(String propertyName, String propertyFilePath) {

        Properties prop = new Properties();
        InputStreamReader is = null;
        String propertyValue = null;
        try {
            is = new InputStreamReader(new FileInputStream(propertyFilePath), Charset.forName("UTF-8"));
        } catch (FileNotFoundException ex) {
            System.err.println("FileNotFoundException\n" + Arrays.toString(ex.getStackTrace()));
        }
        try {
            prop.load(is);
            propertyValue = prop.getProperty(propertyName);
        } catch (IOException ex) {
            System.err.println("IOException\n" + Arrays.toString(ex.getStackTrace()));
        } finally {
            try {
                is.close();
            } catch (Exception ex) {
                System.err.println("Exception\n" + Arrays.toString(ex.getStackTrace()));
            }
        }
        return propertyValue;
    }


    public void sendFileToEmails(String filePath) {

        String to = getConfigPoperty("mail.to");
        String from = getConfigPoperty("mail.from");
        String smtpHost = getConfigPoperty("mail.smtp.host");
        String smtpPort = getConfigPoperty("mail.smtp.port");
        String subject = getConfigPoperty("mail.smtp.subject");
        String body = getConfigPoperty("mail.smtp.body");
        final String username = getConfigPoperty("mail.username");
        final String password = getConfigPoperty("mail.password");

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", smtpPort);
        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filePath);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
