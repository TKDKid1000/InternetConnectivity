package io.github.tkdkid1000;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
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

import org.yaml.snakeyaml.Yaml;

public class Gmail {
	public static void sendEmail(String to, String sub, String body) throws FileNotFoundException {
		
		Yaml yaml = new Yaml();
		InputStream inputStream = new FileInputStream("config.yml");
		Map<String, Object> config = yaml.load(inputStream);
		String from = (String) config.get("email");
		String password = (String) config.get("password");
		Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, password);

            }

        });
        session.setDebug(true);

        try {
            MimeMessage email = new MimeMessage(session);
            email.setFrom(new InternetAddress(from));
            email.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            email.setSubject(sub);
            email.setText(body);
            Transport.send(email);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	}
	
	public static void sendEmailFile(String to, String sub, String body, File file) throws FileNotFoundException {
		Yaml yaml = new Yaml();
		InputStream inputStream = new FileInputStream("config.yml");
		Map<String, Object> config = yaml.load(inputStream);
		String from = (String) config.get("email");
		String password = (String) config.get("password");
		Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, password);

            }

        });
        session.setDebug(true);

        try {
        	MimeMessage email = new MimeMessage(session);

        	email.setFrom(new InternetAddress(from));
        	email.addRecipient(javax.mail.Message.RecipientType.TO,
        			new InternetAddress(to));
        	email.setSubject(sub);

        	MimeBodyPart mimeBodyPart = new MimeBodyPart();
        	mimeBodyPart.setContent(body, "text/plain");

        	Multipart multipart = new MimeMultipart();
        	multipart.addBodyPart(mimeBodyPart);

        	mimeBodyPart = new MimeBodyPart();
        	DataSource source = new FileDataSource(file);

        	mimeBodyPart.setDataHandler(new DataHandler(source));
        	mimeBodyPart.setFileName(file.getName());

        	multipart.addBodyPart(mimeBodyPart);
        	email.setContent(multipart);
            Transport.send(email);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	}
}