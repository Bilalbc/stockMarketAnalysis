package stockMarketAnalysis;


import java.io.IOException;
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
	
	 public static void emailCode (ArrayList<String>followingStocks, ArrayList<String>emails, String date, String symbol, String close,String open,String high,String low, String vol) {
		  
		 //Creates an array from the passed in ArrayList. 
		  String [] emailArray = emails.toArray(new String[emails.size()]);
		  //Specifies the login information for our smartinvestofficial email account.
		  String myEmail = "smartinvestofficial@gmail.com";
		  String password = "SmartInvest@4567";
		  
		  //In a for loop, goes through all the emails in the email list and sends the following to all of them.
		 for (int i = 0; i < emailArray.length; i++) {  
		  String opponentEmail = emailArray[i];
		  //Sets some of the necessary properties for the email.
		  Properties pro = new Properties();
		  pro.put("mail.smtp.host", "smtp.gmail.com");
		  pro.put("mail.smtp.starttls.enable", "true");
		  pro.put("mail.smtp.auth", "true");
		  pro.put("mail.smtp.port", "587");
		  
		  //This part is mainly used as an authenticator to get into the smart invest email account through the program.
		  Session ss = Session.getInstance(pro, new javax.mail.Authenticator() {
		   @Override
		   protected PasswordAuthentication getPasswordAuthentication() {
		    return new PasswordAuthentication(myEmail, password);
		   }
		  });
		  
		  //This part creates the message as an smtp type.
		 
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

		        //Sends the files from the file location specified.
		        String file = "src//Email Files//status.txt";
		        String fileName = "daily report";
		        DataSource source = new FileDataSource(file);
		        messageBodyPart.setDataHandler(new DataHandler(source));
		        messageBodyPart.setFileName(fileName);
		        multipart.addBodyPart(messageBodyPart);
		        
		        //attaching files of all followed stocks 
		        for(int j=0;j<followingStocks.size();j++) {
		        	MimeBodyPart attachPart = new MimeBodyPart();

		        	file = "src//Email Files//"+followingStocks.get(j)+".png";
		        	  
		                try {
		                    attachPart.attachFile(file);
		                } catch (IOException ex) {
		                    ex.printStackTrace();
		                }
		 
		            multipart.addBodyPart(attachPart);
		        }

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
