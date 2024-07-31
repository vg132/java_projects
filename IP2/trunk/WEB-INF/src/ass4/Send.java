/*
 * Created on: 2003-okt-22
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-22 Created by Viktor
 */
package ass4;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mixer.Mixer;

/**
 * Simple email program.
 */
public class Send extends HttpServlet
{
	private String sentString=null;
	private String mailServer="localhost";
	private String pass=null;
	
	/**
	 * Load template, passoword and mailserver.
	 */
	public void init(ServletConfig config)
	throws ServletException
	{
		sentString=Mixer.getContent(new File(config.getInitParameter("template")));
		pass=config.getInitParameter("password");
		mailServer=config.getInitParameter("mailserver");
	}
	
	/**
	 * Get email information from parameters and send it.
	 * If somthing is wrong show a error message.
	 * 
	 * @param request Users request object.
	 * @param response USers response object.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		Mixer mix=new Mixer(sentString);
		String to=request.getParameter("to");
		String from=request.getParameter("from");
		String bcc=request.getParameter("bcc");
		String cc=request.getParameter("cc");
		String message=request.getParameter("message");
		String subject=request.getParameter("subject");
		String password=request.getParameter("password");
		if(pass.equals(password))
		{
			if((!to.equals(""))&&(!from.equals(""))&&(!subject.equals(""))&&(!message.equals("")))
			{
				message+="\n\nObservera! Detta meddelande är sänt från ett formulär på Internet och avsändaren kan vara felaktig!";
				if(sendMail(to,cc,bcc,from,subject,message))
				{
					mix.removeContext("<!!--error-->");
					mix.add("<!!--sent-->","<!--to-->",to);
					mix.add("<!!--sent-->","<!--message-->",message);
					mix.add("<!!--sent-->","<!--from-->",from);
					mix.add("<!!--sent-->","<!--subject-->",subject);
					mix.add("<!!--sent-->","<!--bcc-->",bcc);
					mix.add("<!!--sent-->","<!--cc-->",cc);
				}
				else
				{
					mix.removeContext("<!!--sent-->");
					mix.add("<!!--error-->","<!--error-message-->","Could not send email.");
				}
			}
			else
			{
				mix.removeContext("<!!--sent-->");
				mix.add("<!!--error-->","<!--error-message-->","You have to enter a to address, from address, subject and a message.");
			}
		}
		else
		{
			mix.removeContext("<!!--sent-->");
			mix.add("<!!--error-->","<!--error-message-->","Wrong password.");
		}
		response.getOutputStream().print(mix.getMix());
	}

	/**
	 * Send a email.
	 * 
	 * @param to The email address to the reciver.
	 * @param cc Cc reciver.
	 * @param bcc Bcc reciver.
	 * @param from The sendars email address.
	 * @param subject The subject of the email.
	 * @param text The body text of the email.
	 *
	 * @return true if successfull, otherwise false.
	 */
	public boolean sendMail(String to, String cc, String bcc, String from, String subject, String text)
	{
		try
		{
			Properties props = System.getProperties();
			props.put("mail.smtp.host", mailServer);
			Session session = Session.getDefaultInstance(props, null);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			if(!cc.equals(""))
				message.addRecipient(Message.RecipientType.CC,new InternetAddress(cc));
			if(!bcc.equals(""))
				message.addRecipient(Message.RecipientType.BCC,new InternetAddress(bcc));
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
			message.setSubject(subject);
			message.setText(text);
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
