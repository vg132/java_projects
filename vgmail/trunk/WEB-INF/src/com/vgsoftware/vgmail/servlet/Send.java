/*
 * Created on: 2003-nov-13
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-nov-13 Created by Viktor
 */
package com.vgsoftware.vgmail.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.vgmail.misc.AttachmentData;
import com.vgsoftware.vgmail.misc.MailUser;

/**
 * Send the mail.
 */
public class Send extends HttpServlet
{
	private String errorString=null;
	
	public void init(ServletConfig config)
	throws ServletException
	{
		
		super.init(config);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		MailUser mu=(MailUser)request.getSession().getAttribute("mailuser");
		if(mu!=null)
		{
			String to=request.getParameter("to");
			String cc=request.getParameter("cc");
			String bcc=request.getParameter("bcc");
			String subject=request.getParameter("subject");
			String msg=request.getParameter("message");
			Map map=(Map)request.getSession().getAttribute("attachments");
	
			try
			{
				Properties props = System.getProperties();
				props.put("mail."+mu.getMTA("protocol")+".host", mu.getMTA("host"));
				if(!mu.getMTA("port").equals("-1"))
					props.put("mail."+mu.getMTA("protocol")+".port",mu.getMTA("port"));
				props.put("mail.transport.protocol",mu.getMTA("protocol"));			
				Session session = Session.getInstance(props, null);
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(mu.getUsername()));
				StringTokenizer st=null;
				if((to!=null)&&(!to.equals("")))
				{
					st=new StringTokenizer(to,";");
					while(st.hasMoreTokens())
						message.addRecipient(Message.RecipientType.TO,new InternetAddress(st.nextToken()));
				}
				if((cc!=null)&&(!cc.equals("")))
				{
					st=new StringTokenizer(cc,";");
					while(st.hasMoreTokens())
						message.addRecipient(Message.RecipientType.CC,new InternetAddress(st.nextToken()));
				}
				if((bcc!=null)&&(!bcc.equals("")))
				{
					st=new StringTokenizer(bcc,";");
					while(st.hasMoreTokens())
						message.addRecipient(Message.RecipientType.BCC,new InternetAddress(st.nextToken()));
				}
				message.setSubject(subject);
				if(map!=null)
				{
					Multipart mp=new MimeMultipart();
					MimeBodyPart mbp=new MimeBodyPart();
					mbp.setText(msg);
					mbp.setDisposition(BodyPart.INLINE);
					mp.addBodyPart(mbp);
					Iterator iter=map.keySet().iterator();
					while(iter.hasNext())
					{
						AttachmentData ad=(AttachmentData)map.get(iter.next());
						mbp=new MimeBodyPart();
						FileDataSource fds=new FileDataSource(mu.getAttachment("upload-directory")+"/"+ad.getTmpName());
						mbp.setDataHandler(new DataHandler(fds));
						mbp.setFileName(ad.getName());
						mp.addBodyPart(mbp);
					}
					message.setContent(mp);
				}
				else
				{
					message.setText(msg);
				}
				Transport.send(message);
			}
			catch(AddressException e)
			{
				e.printStackTrace(System.err);
			}
			catch(MessagingException e)
			{
				e.printStackTrace(System.err);
			}
			response.sendRedirect(request.getContextPath()+"/servlet/pagebuilder");
		}
		else
		{
			response.sendRedirect(request.getContextPath());
		}
	}
}