package stockAnalysisProgram;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class sendEmails {

	
	 public static void emailCode (ArrayList<String>emails, String date, String symbol, String close,String open,String high,String low, String vol) {

		  
		  String [] emailArray = emails.toArray(new String[emails.size()]);
		  String myEmail = "smartinvestofficial@gmail.com";
		  String password = "SmartInvest@4567";
		  
		  for (int i = 0; i <= emailArray.length; i++) {
		  
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
		   Message msg = new MimeMessage(ss);
		   msg.setFrom(new InternetAddress(myEmail));
		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(opponentEmail));
		   msg.setSubject("Stock " + symbol + " on the date " + date );
		   msg.setText("Closing Price: " + close
				   	+ "\nOpening Price: " + open
				   	+ "\nHigh Price " + high
				   	+ "\nLowest Price " + low
		   			+ "\nVolume " + vol);
		   Transport trans = ss.getTransport("smtp");
		   Transport.send(msg);
		   System.out.println("message sent");
		  } catch (Exception e) {
		   System.out.println(e.getMessage());
		  
		  }
		 }
	 }
	 
	 
	
	
	
}
