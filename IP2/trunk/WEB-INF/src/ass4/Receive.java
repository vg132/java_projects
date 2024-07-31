/*
 * Created on: 2003-okt-23
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-23 Created by Viktor
 */
package ass4;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mixer.Mixer;

/**
 * Email reciver, prints out all messages from the inbox onto the page.
 */
public class Receive extends HttpServlet
{
	private String htmlString=null;
	
	/**
	 * Load templates and upload directory.
	 * 
	 * @param config
	 * 
	 * @throws ServletException
	 */
	public void init(ServletConfig config)
	throws ServletException
	{
		htmlString=Mixer.getContent(new File(config.getInitParameter("template")));
		super.init(config);
	}
	
	/**
	 * Connect to the mail server and return all messages from the inbox.
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
		Mixer mix=new Mixer(htmlString);
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String server=request.getParameter("server");
		int port=Integer.parseInt(request.getParameter("port"));
		response.setContentType("text/html");
		try
		{
			Session session=Session.getDefaultInstance(new Properties(),null);
			Store store=session.getStore("pop3");
			store.connect(server,port,username,password);
			Folder inbox=store.getFolder("INBOX");
			if(inbox==null)
			{
				response.setContentType("text/plain");
				response.getOutputStream().println("Inbox not found!");
				return;
			}
			inbox.open(Folder.READ_ONLY);
			Message[] messages=inbox.getMessages();
			for(int i=0;i<messages.length;i++)
			{
				if(messages[i].getFrom()!=null)
				{
					String address=null;
					for(int from=0;from<messages[i].getFrom().length;from++)
					{
						if(address==null)
							address=messages[i].getFrom()[from].toString();
						else
							address+=", "+messages[i].getFrom()[from].toString();
					}
					mix.add("<!!--message-block-->","<!--from-->",address);
				}
				if(messages[i].getRecipients(Message.RecipientType.TO)!=null)
				{
					String address=null;
					for(int to=0;to<messages[i].getRecipients(Message.RecipientType.TO).length;to++)
					{
						if(address==null)
							address=messages[i].getRecipients(Message.RecipientType.TO)[to].toString();
						else
							address+=", "+messages[i].getRecipients(Message.RecipientType.TO)[to].toString();
					}
					mix.add("<!!--message-block-->","<!--to-->",address);
				}
				mix.add("<!!--message-block-->","<!--subject-->",messages[i].getSubject());
				mix.add("<!!--message-block-->","<!--message-->",toHTML((String)messages[i].getContent()));
			}
			inbox.close(false);
			store.close();
		}
		catch(MessagingException me)
		{
			me.printStackTrace(System.err);
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		response.getOutputStream().println(mix.getMix());
	}
	
	/**
	 * Replace special characters with html code.
	 * 
	 * @param str Ascii string.
	 * 
	 * @return HTML string.
	 * 
	 */
	public String toHTML(String str)
	{
		String tmp=str;
		tmp=tmp.replaceAll("\n","<br/>");
		tmp=tmp.replaceAll("\t","&nbsp;&nbsp;&nbsp;");
		return(tmp);
	}
}
