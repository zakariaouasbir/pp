package controler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import bean.Email;
import java.io.Serializable;
import java.sql.Date;
import java.util.Properties;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import service.EmailFacade;
@Named("mail")
@ManagedBean
@RequestScoped
public class Mail implements Serializable {
   @EJB
   private EmailFacade emailFacade;
	private String from;
	private String to;
	private String subject;
	private String text;
        private String pw;

	public Mail(String from, String to, String subject, String text){
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.text = text;
	}

    public Mail() {
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
        
	public void send(){

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                
		Session mailSession = Session.getInstance(props, new javax.mail.Authenticator(){
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(
                from, pw);// Specify the Username and the PassWord
        }
});
		Message simpleMessage = new MimeMessage(mailSession);
                InternetAddress fromAddress = null;
		InternetAddress toAddress = null;
		try {
			fromAddress = new InternetAddress(from);
			toAddress = new InternetAddress(to);
                        System.out.println("peaceagain");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
                        System.out.println("ohoy1");
		}

		try {
			
                    simpleMessage.setFrom(fromAddress);
			simpleMessage.setRecipient(RecipientType.TO, toAddress);
			simpleMessage.setSubject(subject);
			simpleMessage.setText(text);
                    System.out.println("peaaaaaaaaaaaaaaaaaace");
//		  Transport transport=mailSession.getTransport("smtp");
//			transport.connect("smtp.gmail.com","zakariaouasbir@gmail.com","juaniturbe");
                    Transport.send(simpleMessage);
                    emailFacade.create(new Email(from, to, subject, text, new java.util.Date()));
                    
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
                         System.out.println("ohoy");
		}
	}
        public void sendfournisseur(){

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "645");
                props.put("mail.smtp.auth", "true");
props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", 
            "javax.net.ssl.SSLSocketFactory");
                
		Session mailSession = Session.getInstance(props, new javax.mail.Authenticator(){
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(
                "zakariaouasbir@gmail.com", "juaniturbe");// Specify the Username and the PassWord
        }
});
		Message simpleMessage = new MimeMessage(mailSession);
                InternetAddress fromAddress = null;
		InternetAddress toAddress = null;
		try {
//			fromAddress = new InternetAddress("zakariaouasbir@gmail.com");
			toAddress = new InternetAddress("ouasbirzakaria@gmail.com");
                        System.out.println("peaceagain");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
                        System.out.println("ohoy1");
		}

		try {
			
                    simpleMessage.setFrom(fromAddress);
			simpleMessage.setRecipient(RecipientType.TO, toAddress);
			simpleMessage.setSubject("hello");
			simpleMessage.setText("hello again");
                    System.out.println("peaaaaaaaaaaaaaaaaaace");
		  Transport transport=mailSession.getTransport("smtp");
//			transport.connect("smtp.gmail.com","zakariaouasbir@gmail.com","juaniturbe");
                    Transport.send(simpleMessage);
                    
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
                         System.out.println("ohoy");
		}
	}
}