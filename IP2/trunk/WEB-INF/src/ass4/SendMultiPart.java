/*
 * Created on: 2003-okt-24
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-24 Created by Viktor
 */
package ass4;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
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
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import mixer.Mixer;


public class SendMultiPart extends HttpServlet
{
	private String sentString=null;
	private String htmlString=null;
	private String maildir=null;
	private String mailServer="localhost";
	private String pass=null;
	
	/**
	 * Load template, passoword and mailserver.
	 */
	public void init(ServletConfig config)
	throws ServletException
	{
		sentString=Mixer.getContent(new File(config.getInitParameter("template")));
		htmlString=Mixer.getContent(new File(config.getInitParameter("htmltemplate")));
		maildir=config.getInitParameter("maildir");
		pass=config.getInitParameter("password");
		mailServer=config.getInitParameter("mailserver");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		Mixer mix=new Mixer(htmlString);
		mix.removeContext("<!!--files-->");
		mix.add("<!--to-->","");
		mix.add("<!--cc-->","");
		mix.add("<!--bcc-->","");
		mix.add("<!--from-->","");
		mix.add("<!--subject-->","");
		mix.add("<!--message-->","");
		response.getOutputStream().print(mix.getMix());
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
		MultipartRequest multi=new MultipartRequest(request,maildir,3*1024*1024);
		Mixer mix=null;
		String to=multi.getParameter("to");
		String from=multi.getParameter("from");
		String bcc=multi.getParameter("bcc");
		String cc=multi.getParameter("cc");
		String message=multi.getParameter("message");
		String subject=multi.getParameter("subject");
		String whattodo=multi.getParameter("whattodo");

		if(whattodo.equals("add"))
		{
			mix=new Mixer(htmlString);
			Enumeration fileNames=multi.getFileNames();
			if(fileNames.hasMoreElements())
			{
				File file=multi.getFile((String)fileNames.nextElement());
				Attachment attachment=new Attachment(file.getName());
				File newFile;
				while((newFile=new File(maildir+"\\mail_"+Math.random()*100000)).exists());
				attachment.setTmpName(newFile.getName());
				file.renameTo(newFile);
				HttpSession session=request.getSession();
				List l=(List)session.getAttribute("attachments");
				if(l==null)
				{
					l=new LinkedList();
					session.setAttribute("attachments",l);
				}
				l.add(attachment);
				Iterator iter=l.iterator();
				while(iter.hasNext())
				{
					attachment=(Attachment)iter.next();
					mix.add("<!!--files-->","<!--value-->",attachment.getTmpName());
					mix.add("<!!--files-->","<!--name-->",attachment.getName());
				}
			}
			mix.add("<!--to-->",to);
			mix.add("<!--cc-->",cc);
			mix.add("<!--bcc-->",bcc);
			mix.add("<!--from-->",from);
			mix.add("<!--subject-->",subject);
			mix.add("<!--message-->",message);
			response.getOutputStream().print(mix.getMix());
		}
		else if(whattodo.equals("remove"))
		{
			mix=new Mixer(htmlString);
			
			String filename=multi.getParameter("filename");
			List l=(List)request.getSession().getAttribute("attachments");
			if(l!=null)
			{
				Iterator iter=l.iterator();
				while(iter.hasNext())
				{
					Attachment attachment=(Attachment)iter.next();
					if(attachment.getTmpName().equals(filename))
					{
						File f=new File(maildir+"\\"+attachment.getTmpName());
						f.delete();
						iter.remove();
					}
					else
					{
						mix.add("<!!--files-->","<!--value-->",attachment.getTmpName());
						mix.add("<!!--files-->","<!--name-->",attachment.getName());
					}
				}
			}
			else
			{
				mix.removeContext("<!!--files-->");	
			}
			mix.add("<!--to-->",to);
			mix.add("<!--cc-->",cc);
			mix.add("<!--bcc-->",bcc);
			mix.add("<!--from-->",from);
			mix.add("<!--subject-->",subject);
			mix.add("<!--message-->",message);
			response.getOutputStream().print(mix.getMix());			
		}
		else if(whattodo.equals("send"))
		{
			mix=new Mixer(sentString);
			String password=multi.getParameter("password");
			if(pass.equals(password))
			{
				if((!to.equals(""))&&(!from.equals(""))&&(!subject.equals(""))&&(!message.equals("")))
				{
					message+="\n\nObservera! Detta meddelande är sänt från ett formulär på Internet och avsändaren kan vara felaktig!";
					if(sendMail(to,cc,bcc,from,subject,message,(List)request.getSession().getAttribute("attachments")))
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
	public synchronized boolean sendMail(String to, String cc, String bcc, String from, String subject, String text, List attachments)
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
			if(attachments!=null)
			{
				Multipart mp = new MimeMultipart();
				for(int i=0;i<attachments.size();i++)
				{
					Attachment a=(Attachment)attachments.get(i);
					MimeBodyPart mbp=new MimeBodyPart();
					
					FileDataSource fds=new FileDataSource(maildir+"\\"+a.getTmpName());
					mbp.setDataHandler(new DataHandler(fds));
					mbp.setFileName(a.getName());
					mp.addBodyPart(mbp);
				}
				message.setContent(mp);
			}
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

class Attachment
{
	private String name=null;
	private String tmpName=null;

	public Attachment()
	{
	}

	public Attachment(String name)
	{
		this.name=name;
	}

	public Attachment(String name, String tmpName)
	{
		this.name=name;
		this.tmpName=tmpName;
	}

	public String getName()
	{
		return(name);
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public String getTmpName()
	{
		return(tmpName);
	}
	
	public void setTmpName(String tmpName)
	{
		this.tmpName=tmpName;
	}
}