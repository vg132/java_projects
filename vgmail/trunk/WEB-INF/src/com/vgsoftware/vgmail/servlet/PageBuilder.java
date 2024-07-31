/*
 * Created on: 2003-okt-25
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-25 Created by Viktor
 */
package com.vgsoftware.vgmail.servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.vgmail.misc.AttachmentData;
import com.vgsoftware.vgmail.misc.MailReader;
import com.vgsoftware.vgmail.misc.MailUser;

import dsv.pierre.Mixer;

/**
 * Main servlet.
 */
public class PageBuilder extends HttpServlet
{
	private String mainHTMLString=null;
	private String menuHTMLString=null;
	private String menuFolderString=null;
	private String contentMailListString=null;
	private String contentFlagsString=null;
	private String contentBrowseString=null;
	private String readString=null;
	private String headerString=null;
	private String composeString=null;
	private String foldereditString=null;

	/**
	 * Read the templates into memory for fast access.
	 * 
	 * @param config The servlet configuration object.
	 * 
	 * @throws ServletException
	 */
	public void init(ServletConfig config)
	throws ServletException
	{
		String dir=(String)((Map)((Map)config.getServletContext().getAttribute("config")).get("template")).get("directory");
		mainHTMLString=Mixer.getContent(new File(dir+ config.getInitParameter("main")));
		menuHTMLString=Mixer.getContent(new File(dir+ config.getInitParameter("folderlist")));
		menuFolderString=Mixer.getContent(new File(dir+ config.getInitParameter("folder")));
		contentMailListString=Mixer.getContent(new File(dir+ config.getInitParameter("maillist")));
		contentFlagsString=Mixer.getContent(new File(dir+ config.getInitParameter("flags")));
		contentBrowseString=Mixer.getContent(new File(dir+ config.getInitParameter("browse")));
		readString=Mixer.getContent(new File(dir+ config.getInitParameter("read")));
		composeString=Mixer.getContent(new File(dir+ config.getInitParameter("compose")));
		headerString=Mixer.getContent(new File(dir+ config.getInitParameter("header")));
		foldereditString=Mixer.getContent(new File(dir+ config.getInitParameter("folderedit")));
		super.init(config);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		Mixer mix=new Mixer(mainHTMLString);
		MailUser mu=(MailUser)request.getSession().getAttribute("mailuser");
		if(mu!=null)
		{
			try
			{
				Store store=mu.openConnection();
				String whattodo=request.getParameter("whattodo");
				if(whattodo!=null&&whattodo.equals("download"))
				{
					new MailReader().downloadAttachment(request,response,store);
					return;
				}
				mix.add("<!--header-->",buildHeader(request,store));
				mix.add("<!--content-->",buildContent(mu,request,store));
				mix.add("<!--menu-->",buildMenu(request,store));
				mu.closeConnection(store);

				mix.add("<!--context-->",request.getContextPath());
				if(request.getParameter("state")!=null)
					mix.add("<!--menu-state-->",request.getParameter("state"));
				else
					mix.add("<!--menu-state-->","INBOX|-|");
				response.setContentType("text/html");
				response.getOutputStream().println(mix.getMix().replaceAll("<!{1,2}--(.*?)-->",""));
			}
			catch(MessagingException me)
			{
				me.printStackTrace(System.err);
			}
		}
		else
		{
			response.sendRedirect(request.getContextPath()+"/index.html");
			return;
		}
	}

	/**
	 * Redirect request to <code>doPost</code>
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		doPost(request,response);
	}
	
	/**
	 * Build the compose page.
	 * 
	 * @param request The current request object.
	 * @param store The current store.
	 * 
	 * @return The compose page.
	 */
	private String buildCompose(HttpServletRequest request, Store store)
	{
		Mixer mix=new Mixer(composeString);
		mix.add("<!--context-->",request.getContextPath());
		return(mix.getMix());
	}
	
	/**
	 * Build the header field of the page, same in every page.
	 * 
	 * @param request
	 * @param store
	 * 
	 * @return The header page.
	 */
	private String buildHeader(HttpServletRequest request, Store store)
	{
		return(headerString);
	}

	/**
	 * Create a folder list for this users mail folders.
	 * If a list has already been created for this user and saved to his context
	 * use that one, unless the refresh parameter is provided, then a new list
	 * will be created.
	 * 
	 * @param request Current request object.
	 * @param store The users store object.
	 * 
	 * @return The content html part.
	 */
	private String buildMenu(HttpServletRequest request, Store store)
	{
		String folderList=(String)request.getSession().getAttribute("folderlist");
		if((folderList==null)||(request.getParameter("refresh")!=null))
		{
			try
			{
				Folder folder=store.getFolder("INBOX");
				Mixer header=new Mixer(menuHTMLString);
				createList(folder,header,0);
				folderList=header.getMix();
				request.getSession().setAttribute("folderlist",folderList);
			}
			catch(NoSuchProviderException nspe)
			{
				nspe.printStackTrace(System.err);
			}
			catch(MessagingException me)
			{
				me.printStackTrace(System.err);
			}
		}
		return(folderList);
	}
	/**
	* Based on the whattodo parameter send the request to different
	* functions.
	* 
	* @param mu Current user.
	* @param request Current request object.
	* @param store The users store object.
	* 
	* @return The content html part.
	* 
	* @throws MessagingException
	* @throws IOException
	*/
	private String buildContent(MailUser mu, HttpServletRequest request, Store store)
	throws MessagingException, IOException
	{
		String whattodo=request.getParameter("whattodo");
		if((whattodo==null)||(!whattodo.equals("compose")))
			clearAttachments((Map)request.getSession().getAttribute("attachments"),mu.getAttachment("upload-directory"));
		if((whattodo==null)||(whattodo.equals("folder")))
			return(listFolder(mu, request,store));
		else if(whattodo.equals("read"))
			return(new MailReader().readMail(request,store,new Mixer(readString)));
		else if(whattodo.equals("compose"))
			return(buildCompose(request,store));
		else if(whattodo.equals("folderedit"))
			return(folderEdit(mu,request,store));
		else if(whattodo.equals("movemail"))
			return(moveMail(mu,request,store));
		else if(whattodo.equals("delete"))
			return(delete(mu,request,store));
		else if(whattodo.equals("undelete"))
			return(undelete(mu,request,store));
		else if(whattodo.equals("expung"))
			return(expung(mu,request,store));
		return(null);
	}

	/**
	 * Delete deleted messages from the server. Messages cant be undeleted.
	 * 
	 * @param mu Current user.
	 * @param request Current request object.
	 * @param store The users store object.
	 * 
	 * @return The content html part.
	 * 
	 * @throws MessagingException
	 * @throws IOException
	 */
	private String expung(MailUser mu, HttpServletRequest request, Store store)
	throws MessagingException, IOException
	{
		Folder folder=store.getFolder(request.getParameter("folder"));
		folder.open(Folder.READ_WRITE);
		folder.close(true);	
		return(listFolder(mu,request,store));
	}

	/**
	 * Delete selected messages, messages can still be undeleted.
	 * 
	 * @param mu Current user.
	 * @param request Current request object.
	 * @param store The users store object.
	 * 
	 * @return The content html part.
	 * 
	 * @throws MessagingException
	 * @throws IOException
	 */
	private String delete(MailUser mu, HttpServletRequest request, Store store)
	throws MessagingException, IOException
	{
		Folder folder=store.getFolder(request.getParameter("folder"));
		long[] msgId=getMessagesId(request);
		if(msgId.length>0)
		{
			folder.open(Folder.READ_WRITE);
			Message messages[]=((UIDFolder)folder).getMessagesByUID(msgId);
			folder.setFlags(messages,new Flags(Flags.Flag.DELETED),true);
			folder.close(false);
		}
		return(listFolder(mu,request,store));
	}
	
	/**
	 * Undelete deleted messages.
	 * 
	 * @param mu Current user.
	 * @param request Current request object.
	 * @param store The users store object.
	 * 
	 * @return The content html part.
	 * 
	 * @throws MessagingException
	 * @throws IOException
	 */
	private String undelete(MailUser mu, HttpServletRequest request, Store store)
	throws MessagingException, IOException
	{
		Folder folder=store.getFolder(request.getParameter("folder"));
		long[] msgId=getMessagesId(request);
		if(msgId.length>0)
		{
			folder.open(Folder.READ_WRITE);
			Message messages[]=((UIDFolder)folder).getMessagesByUID(msgId);
			folder.setFlags(messages,new Flags(Flags.Flag.DELETED),false);
			folder.close(false);
		}
		return(listFolder(mu,request,store));
	}

	/**
	 * Move selected mails from one folder to another.
	 * 
	 * @param mu Current user.
	 * @param request Current request object.
	 * @param store The users store object.
	 * 
	 * @return The content html part.
	 * 
	 * @throws MessagingException
	 * @throws IOException
	 */
	private String moveMail(MailUser mu, HttpServletRequest request, Store store)
	throws MessagingException, IOException
	{
		Folder moveTo=store.getFolder(request.getParameter("moveto"));
		Folder from=store.getFolder(request.getParameter("folder"));
		long[] msgId=getMessagesId(request);
		if(msgId.length>0)
		{
			moveTo.open(Folder.READ_WRITE);
			from.open(Folder.READ_WRITE);
			
			Message messages[]=((UIDFolder)from).getMessagesByUID(msgId);
			from.copyMessages(messages,moveTo);
			from.setFlags(messages,new Flags(Flags.Flag.DELETED),true);
			from.close(false);
			moveTo.close(false);
		}
		return(listFolder(mu,request,store));
	}

	/**
	 * Returns the ids of the selected messages.
	 * 
	 * @param request The request that contains the message parameters.
	 * 
	 * @return The ids of the selected messages.
	 */
	private long[] getMessagesId(HttpServletRequest request)
	{
		Map map=request.getParameterMap();
		Iterator iter=map.keySet().iterator();
		long[] tmpId=new long[map.size()];
		int i=0;
		while(iter.hasNext())
		{
			String name=(String)iter.next();
			if(name.startsWith("msg["))
				tmpId[i++]=Long.parseLong(request.getParameter(name));
		}
		long[] msgId=new long[i];
		for(i=0;i<msgId.length;i++)
			msgId[i]=tmpId[i];
		return(msgId);
	}

	/**
	 * Add a new folder to this users store.
	 * 
	 * @param mu
	 * @param request
	 * @param store
	 * 
	 * @return
	 * 
	 * @throws MessagingException
	 */
	private String folderEdit(MailUser mu, HttpServletRequest request, Store store)
	throws MessagingException
	{
		Mixer mix=new Mixer(foldereditString);
		String folderInfo=request.getParameter("folderinfo");
		if((folderInfo!=null)&&(folderInfo.equals("newfolder")))
		{
			String newFolder=request.getParameter("newfolder");
			String root=request.getParameter("root");
			if((root!=null)&&(newFolder!=null))
			{
				Folder folder=store.getFolder(root+"."+newFolder);
				if(folder.create(Folder.HOLDS_MESSAGES|Folder.HOLDS_FOLDERS))
				{
					folder.setSubscribed(true);
				}
			}
		}

		Folder folder=store.getFolder(mu.getPostOffice("root-folder"));
		List list=new ArrayList();

		folderList(folder,list);
		for(int i=0;i<list.size();i++)
			mix.add("<!!--folders-->","<!--folder-->",(String)list.get(i));
		return(mix.getMix());
	}

	/**
	 * Create a list of all subfolders of folder.
	 * 
	 * @param folder The root folder.
	 * @param list The list of folders.
	 * 
	 * @return A list with all subfolders and the root folder.
	 */
	private synchronized void folderList(Folder folder, List list)
	throws MessagingException
	{
		list.add(folder.getFullName());
		Folder[] folders=folder.list();
		for(int i=0;i<folders.length;i++)
			folderList(folders[i],list);
	}

	/**
	 * Create the folder list with all folders in the current store.
	 * 
	 * @param request
	 * @param store
	 * 
	 * @throws MessagingException
	 * @throws IOException
	 * 
	 * @return the folder list ready to be displayed.
	 */
	private String listFolder(MailUser mu, HttpServletRequest request, Store store)
	throws MessagingException, IOException
	{
		Mixer mix=new Mixer(contentMailListString);
		Mixer flagMix=new Mixer(contentFlagsString);
		SimpleDateFormat sdf=new SimpleDateFormat("MMM dd yyyy");
		String tmpData="";
		Folder folder=store.getFolder(mu.getPostOffice("root-folder"));
		List list=new ArrayList();

		folderList(folder,list);
		for(int i=0;i<list.size();i++)
			mix.add("<!!--folders-->","<!--folder-->",(String)list.get(i));

		if(request.getParameter("folder")==null)
			folder=store.getFolder(mu.getPostOffice("root-folder"));
		else
			folder=store.getFolder(request.getParameter("folder"));

		mix.add("<!--folder-path-->",folder.getFullName().replaceAll("\\."," > "));
		mix.add("<!!--move-->","<!--real-folder-path-->",folder.getFullName());
		
		
		folder.open(Folder.READ_ONLY);
		Message[] messages=pageBrowser(request,folder,mix);
		for(int i=messages.length-1;i>=0;i--)
		{
			mix.add("<!!--mail-->","<!--class-->","color"+(i%2==0?1:2));
			Address[] addresses=messages[i].getFrom();
			if(addresses!=null)
			{
				tmpData=MimeUtility.decodeText(addresses[0].toString());
				if(tmpData.length()>35)
					mix.add("<!!--mail-->","<!--from-->",tmpData.substring(0,32)+"...");
				else
					mix.add("<!!--mail-->","<!--from-->",tmpData);
			}
			else
			{
				mix.add("<!!--mail-->","<!--from-->","");
			}
			tmpData=messages[i].getSubject();
			if((tmpData!=null)&&(!tmpData.trim().equals("")))
			{
				if(tmpData.length()>50)
					mix.add("<!!--mail-->","<!--subject-->",tmpData.substring(0,47)+"...");
				else
					mix.add("<!!--mail-->","<!--subject-->",tmpData);
			}
			else
				mix.add("<!!--mail-->","<!--subject-->","(no subject)");
			mix.add("<!!--mail-->","<!--real-folder-path-->",folder.getFullName());
			mix.add("<!!--mail-->","<!--uid-->",""+((UIDFolder)folder).getUID(messages[i]));
			tmpData=sdf.format(messages[i].getReceivedDate());
			tmpData=tmpData.substring(0,1).toUpperCase()+tmpData.substring(1);
			mix.add("<!!--mail-->","<!--recived-->",tmpData);

			mix.add("<!!--mail-->","<!--flags-->",flags(messages[i],flagMix));
			flagMix.clearAll();
		}
		if(messages.length==0)
			mix.removeContext("<!!--mail-->");
		else
			mix.removeContext("<!!--no-mail-->");
		folder.close(false);
		return(mix.getMix());
	}

	/**
	 * Sets the page browsing information in the mixer (html template)
	 * and gets the messages to display for this request.
	 * 
	 * @param request The current Servlet Request object.
	 * @param folder Current opend folder.
	 * @param mix The mixer to add the browsing information to.
	 * 
	 * @throws MessagingException
	 * 
	 */
	private Message[] pageBrowser(HttpServletRequest request, Folder folder, Mixer mix)
	throws MessagingException
	{
		//Setup init values
		Mixer bMix=new Mixer(contentBrowseString);
		int startPage=1;
		int pages=1;
		int messages=folder.getMessageCount();
		int pageSize=100;
		if((request.getParameter("startpage")!=null)&&(!request.getParameter("startpage").equals("")))
			startPage=Integer.parseInt(request.getParameter("startpage"));
		if(startPage<1)
			startPage=1;
		int startMessage=messages-(pageSize*startPage);
		int endMessage=startMessage+pageSize;

		if(pageSize<messages)
		{
			pages=(messages/pageSize)+1;
			if(endMessage>messages)
				endMessage=messages;
			if(startMessage>messages)
				startMessage-=pageSize;
			if((endMessage<0))
			{
				endMessage=pageSize;
				startMessage=0;
			}
			else if(startMessage<0)
			{
				startMessage=0;
			}

			startMessage++;
			bMix.add("<!!--page-control-->","<!--real-folder-path-->",folder.getFullName());
			bMix.add("<!!--page-control-->","<!--next-page-->",""+((startPage+1)>pages?startPage:startPage+1));
			bMix.add("<!!--page-control-->","<!--prev-page-->",""+((startPage-1)<1?startPage:startPage-1));
			bMix.add("<!!--page-control-->","<!--page-->",""+startPage);

			int i=0;
			for(i=1;i<pages+1;i++)
			{
				bMix.add("<!!--pages-->","<!--page-->",""+i);
				bMix.add("<!!--pages-->","<!--real-folder-path-->",folder.getFullName());
			}
		}
		else
		{
			startMessage=1;
			endMessage=messages;
			bMix.removeContext("<!!--page-control-->");
			bMix.removeContext("<!!--pages-->");
		}
		bMix.add("<!--first-message-->",""+(messages-endMessage+1));
		bMix.add("<!--last-message-->",""+(messages-startMessage+1));
		bMix.add("<!--messages-->",""+folder.getMessageCount());
		bMix.add("<!--unread-->",""+folder.getUnreadMessageCount());
		mix.add("<!--page-browser-->",bMix.getMix());
		return(folder.getMessages(startMessage,endMessage));
	}

	/**
	 * Sets the correct flag icons.
	 * 
	 * @param message The current message with the flags.
	 * @param flagMix The mixer to add the flags to.
	 * 
	 * @throws MessagingException
	 * @throws IOException
	 */
	private String flags(Message message, Mixer flagMix)
	throws MessagingException, IOException
	{
		if(message.isSet(Flags.Flag.FLAGGED))
			flagMix.add("<!!--flags-->","<!--flag-->","flagged.gif");
		else
			flagMix.add("<!!--flags-->","<!--flag-->","flagged_transparent.gif");
		String[] prio=message.getHeader("X-Priority");
		if((prio!=null)&&(prio[0].trim().matches("[12](.*?)")))
			flagMix.add("<!!--flags-->","<!--flag-->","prio_high.gif");
		else if((prio!=null)&&(prio[0].trim().matches("[45](.*?)")))
			flagMix.add("<!!--flags-->","<!--flag-->","prio_low.gif");
		else
			flagMix.add("<!!--flags-->","<!--flag-->","prio_transparent.gif");

		String fileName="";
		if(message.isSet(Flags.Flag.SEEN))
			fileName="msg_read";
		else
			fileName="msg_new";
		if(message.isSet(Flags.Flag.DELETED))
			fileName+="_deleted";
		if(message.isSet(Flags.Flag.ANSWERED))
			fileName+="_reply";
		flagMix.add("<!!--flags-->","<!--flag-->",fileName+".gif");
		
		if(message.getContent() instanceof Multipart)
			flagMix.add("<!!--flags-->","<!--flag-->","attach.gif");
		else
			flagMix.add("<!!--flags-->","<!--flag-->","attach_transparent.gif");
		
		return(flagMix.getMix());
	}

	/**
	 * Loop all folders and subfolders and add information about the 
	 * folders to the templates.
	 * 
	 * @param folder The current folder.
	 * @param header The mixer to whitch the menu will be added.
	 * @param indentSize The indent size, 3 spaces/indent.
	 * 
	 * @throws MessagingException
	 */
	private synchronized void createList(Folder folder, Mixer header, int indentSize)
	{
		Mixer mix=new Mixer(menuFolderString);
		String indent="";
		int i=0;

		try
		{
			for(i=0;i<indentSize;i++)
				indent+="&nbsp;";
			mix.add("<!--indent-->",indent);
			mix.add("<!--folder-name-->",folder.getName());
			mix.add("<!--folder-path-->",folder.getFullName().replaceAll(" ","_|_"));
			mix.add("<!--real-folder-path-->",folder.getFullName());

			if(folder.getUnreadMessageCount()!=0)
				mix.add("<!--unread-->","("+folder.getUnreadMessageCount()+")");
			else
				mix.add("<!--unread-->","&nbsp;");

			Folder[] folders=folder.list();

			if(folders.length==0)
				mix.removeContext("<!!--subfolders-->");
			else
				mix.removeContext("<!!--no-subfolders-->");
			for(i=0;i<folders.length;i++)
				createList(folders[i],mix,indentSize+1);
		}
		catch(MessagingException me)
		{
			mix.removeContext("<!!--subfolders-->");
			me.printStackTrace(System.err);
		}
		header.add("<!!--menu-items-->","<!--folder-->",mix.getMix());
	}
	
	/**
	 * Remove all attachments from the upload directory if we move from
	 * compose mode to any  other mode.
	 * 
	 * @param map Map with all attachments.
	 * @param uploadDirectory The directory where all uploaded files are saved.
	 */
	private void clearAttachments(Map map, String uploadDirectory)
	{
		if(map!=null)
		{
			Iterator iter=map.keySet().iterator();
			while(iter.hasNext())
			{
				AttachmentData ad=(AttachmentData)map.get(iter.next());
				new File(uploadDirectory+"/"+ad.getTmpName()).delete();
				iter.remove();
			}
		}
	}
}
