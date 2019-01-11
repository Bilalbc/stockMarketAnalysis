package stockMarketAnalysis;


import java.util.ArrayList;
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

public class sendEmails {

	public static void main(String[]args) {
		emailCode(null, null, "SLV", null, null,null,null,null);
	}
	
	 public static void emailCode (ArrayList<String>emails, String date, String symbol, String close,String open,String high,String low, String vol) {
		  
		  String [] emailArray = emails.toArray(new String[emails.size()]);
		  String myEmail = "smartinvestofficial@gmail.com";
		  String password = "SmartInvest@4567";
		  
		 for (int i = 0; i < emailArray.length; i++) {  
		  String opponentEmail = emailArray[i];
		  Properties pro = new Properties();
		  pro.put("mail.smtp.host", "smtp.gmail.com");
		  pro.put("mail.smtp.starttls.enable", "true");
		  pro.put("mail.smtp.auth", "true");
		  pro.put("mail.smtp.port", "587");
		  Session ss = Session.getInstance(pro, new javax.mail.Authenticator() {
		   @Override
		   protected PasswordAuthentication getPasswordAuthentication() {
		    return new PasswordAuthentication(myEmail, password);
		   }
		  });
		  try {
		        Message message = new MimeMessage(ss);
		        message.setFrom(new InternetAddress(myEmail));
		        message.setRecipients(Message.RecipientType.TO,
		                InternetAddress.parse(opponentEmail));
		        message.setSubject("Testing Subject");
		        message.setText("PFA");

		        MimeBodyPart messageBodyPart = new MimeBodyPart();

		        Multipart multipart = new MimeMultipart();

		        messageBodyPart = new MimeBodyPart();
		        String file = "src//Email Files//test.txt";
		        String fileName = "daily report";
		        DataSource source = new FileDataSource(file);
		        messageBodyPart.setDataHandler(new DataHandler(source));
		        messageBodyPart.setFileName(fileName);
		        multipart.addBodyPart(messageBodyPart);

		        message.setContent(multipart);

		        System.out.println("Sending");

		        Transport.send(message);

		        System.out.println("Done");

		    } catch (MessagingException e) {
		        e.printStackTrace();
		    }
		  }
	 }
	 
	 
	
	
	
}
