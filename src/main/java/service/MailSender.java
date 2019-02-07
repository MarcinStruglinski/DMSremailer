package service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
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

import org.springframework.stereotype.Service;

import biz.ekspert.dao.DbOperations;

@Service
public class MailSender {

	public static boolean sendAttachmentEmail(String subject, String messageText, String sendTo, Integer iddokzew) throws IOException, SQLException{
		try{

		    final String username = "DmsPcbMailer@gmail.com";
		    final String password = "Wariatek3";
			DbOperations db = new DbOperations();	
		    Properties props = new Properties();
		    props.put("mail.smtp.auth", "true");
		    props.put("mail.smtp.starttls.enable", "true");
		    props.put("mail.smtp.host", "smtp.gmail.com");
		    props.put("mail.smtp.port", "587");

		    Authenticator authenticator =
		            new Authenticator() {
		              @Override
		              protected PasswordAuthentication getPasswordAuthentication() {
		                return new PasswordAuthentication(username, password);
		              }
		            };
		    Session session = Session.getInstance(props, authenticator);
			
	         MimeMessage msg = new MimeMessage(session);
	         msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		     msg.addHeader("format", "flowed");
		     msg.addHeader("Content-Transfer-Encoding", "8bit");
		     msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo)); 
		     msg.setFrom(new InternetAddress("prestiztest@onet.pl", "NoReply-JD"));
		     msg.setSubject(subject, "UTF-8");

		     msg.setSentDate(new Date(0));
             
		     //// save file 
		    
		     String filename =  db.saveFileOnDiski(iddokzew) ;
	         // Create the message body part
	         BodyPart messageBodyPart = new MimeBodyPart();

	         // Fill the message
	         messageBodyPart.setText(messageText);
	         
	         // Create a multipart message for attachment
	         Multipart multipart = new MimeMultipart();
             System.out.println(filename);
	         // Set text message part
	         multipart.addBodyPart(messageBodyPart);

	         // Second part is attachment
	         messageBodyPart = new MimeBodyPart();
	       
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(filename);
	         multipart.addBodyPart(messageBodyPart);

	         // Send the complete message parts
	         msg.setContent(multipart);

	         // Send message
	         Transport.send(msg);
	         System.out.println("EMail Sent Successfully with attachment!!");
	         return true ;
	      }catch (MessagingException e) {
	         e.printStackTrace();
	         return false;
	      } catch (UnsupportedEncodingException e) {
			 e.printStackTrace();
			   return false;
		}
	}
	
	
}

