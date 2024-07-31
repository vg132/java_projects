package com.vgsoftware.vgutil.internet.mail;

import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.MessagingException;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import java.util.Properties;
import java.util.StringTokenizer;

/**
 * A simple class that sends a email
 *
 * @author 	Viktor Gars
 * @version 1.1
 */
public class Mail
{
	private String host="";

	/**
	 * Default constructor
	 */
	public Mail()
	{

	}

	/**
	 * Constructor that takes the mail host that will be used when sending mails as a parameter
	 *
	 * @param host the SMTP mail server that will be used for sending the email.
	 */
	public Mail(String host)
	{
		this.host=host;
	}

	/**
	 * Send a email
	 *
	 * @param from The sendars email address.
	 * @param to The email addresses to the reciver(s), seperate the list of recivers with ";".
	 * @param cc The email address to the cc reciver(s), seperate the list of recivers with ";".
	 * @param bcc The email address to the bcc reciver(s), seperate the list of recivers with ";".
	 * @param subject The subject of the email.
	 * @param text The body text of the email.
	 *
	 * @return true if successfull, false if somthing went wrong
	 */
	public boolean sendMail(String from, String to, String cc, String bcc, String subject, String text)
	{
		return(sendMail(host, from, to, cc, bcc, subject, text));
	}

	/**
	 * Send a email
	 *
	 * @param host the SMTP mail server that will be used for sending the email.
	 * @param from The sendars email address.
	 * @param to The email addresses to the reciver(s), seperate the list of recivers with ";".
	 * @param cc The email address to the cc reciver(s), seperate the list of recivers with ";".
	 * @param bcc The email address to the bcc reciver(s), seperate the list of recivers with ";".
	 * @param subject The subject of the email.
	 * @param text The body text of the email.
	 *
	 * @return true if successfull, false if somthing went wrong
	 */
	public boolean sendMail(String host, String from, String to, String cc, String bcc, String subject, String text)
	{
		try
		{
			// Get system properties
			Properties props = System.getProperties();
			// Setup mail server
			props.put("mail.smtp.host", host);
			// Get session
			Session session = Session.getDefaultInstance(props, null);
			// Define message
			MimeMessage message = new MimeMessage(session);
			// Set the from address
			if(from!=null)
				message.setFrom(new InternetAddress(from));
			// Set the to addresses
			StringTokenizer st=null;
			if(to!=null)
			{
				st=new StringTokenizer(to,";");
				while(st.hasMoreTokens())
					message.addRecipient(Message.RecipientType.TO,new InternetAddress(st.nextToken()));
			}
			// Set the cc addresses
			if(cc!=null)
			{
				st=new StringTokenizer(cc,";");
				while(st.hasMoreTokens())
					message.addRecipient(Message.RecipientType.CC,new InternetAddress(st.nextToken()));
			}
			// Set the bcc addresses
			if(bcc!=null)
			{
				st=new StringTokenizer(bcc,";");
				while(st.hasMoreTokens())
					message.addRecipient(Message.RecipientType.BCC,new InternetAddress(st.nextToken()));
			}
			// Set the subject
			message.setSubject(subject);
			// Set the content
			message.setText(text);
			// Send message
			Transport.send(message);
		}
		catch(AddressException e)
		{
			e.printStackTrace(System.err);
			return(false);
		}
		catch(MessagingException e)
		{
			e.printStackTrace(System.err);
			return(false);
		}
		return(true);
	}
}