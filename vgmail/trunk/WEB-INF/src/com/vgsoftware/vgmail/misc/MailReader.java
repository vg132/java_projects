/*
 * Created on: 2003-okt-30
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-30 Created by Viktor
 */
package com.vgsoftware.vgmail.misc;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vgsoftware.vgmail.handler.*;

import dsv.pierre.Mixer;

public class MailReader
{
	
	private long uid=-1;
	private String level=null;
	private Message message=null; 
	
	public void downloadAttachment(HttpServletRequest request, HttpServletResponse response, Store store)
	throws MessagingException, IOException
	{
		Folder folder=store.getFolder(request.getParameter("folder"));
		if(folder!=null)
		{
			folder.open(Folder.READ_ONLY);
			message=((UIDFolder)folder).getMessageByUID(Long.parseLong(request.getParameter("uid")));
			if((message!=null)&&(message.getContent() instanceof Multipart))
			{
				String pid=request.getParameter("pid");
				Multipart mp=(Multipart)message.getContent();
				
				Part part=new PartHandler().getPart(mp,pid);
				if(part!=null)
				{
					response.setHeader("Content-Disposition","inline; filename="+part.getFileName());
					response.setContentType("application/download; name="+part.getFileName());
					ServletOutputStream out=response.getOutputStream();
					InputStream in=new BufferedInputStream(part.getInputStream());
					int b;
					while((b=in.read())!=-1)
						out.write(b);
					out.flush();
					in.close();
				}
			}
			folder.close(false);
		}
	}
	
	public String readMail(HttpServletRequest request, Store store, Mixer mix)
	throws MessagingException, IOException
	{
		uid=Long.parseLong(request.getParameter("uid"));
		Folder folder=store.getFolder(request.getParameter("folder"));
		if(folder!=null)
		{
			folder.open(Folder.READ_WRITE);
			message=((UIDFolder)folder).getMessageByUID(uid);
			if(message!=null)
			{
				message.setFlag(Flags.Flag.SEEN,true);
				String strData=null;
				int i=0;
				mix.add("<!!--header-->","<!--subject-->",message.getSubject());
				Address[] addresses=message.getFrom();
				for(i=0;i<addresses.length;i++)
				{
					if(strData==null)
						strData=addresses[i].toString().replaceAll("<","&lt;").replaceAll(">","&gt;");
					else
					strData+=";"+addresses[i].toString().replaceAll("<","&lt;").replaceAll(">","&gt;");
				}
				mix.add("<!!--header-->","<!--from-->",strData);
				strData=null;
				addresses=message.getRecipients(Message.RecipientType.TO);
				for(i=0;i<addresses.length;i++)
				{
					if(strData==null)
						strData=addresses[i].toString().replaceAll("<","&lt;").replaceAll(">","&gt;");
					else
						strData+=";"+addresses[i].toString().replaceAll("<","&lt;").replaceAll(">","&gt;");
				}
				mix.add("<!!--header-->","<!--to-->",strData);
				strData=null;
				addresses=message.getRecipients(Message.RecipientType.CC);
				if(addresses!=null)
				{
					for(i=0;i<addresses.length;i++)
					{
						if(strData==null)
							strData=addresses[i].toString().replaceAll("<","&lt;").replaceAll(">","&gt;");
						else
							strData+=";"+addresses[i].toString().replaceAll("<","&lt;").replaceAll(">","&gt;");
					}
					mix.add("<!!--header-->","<!--cc-->",strData);
				}
				mix.add("<!!--header-->","<!--date-->",new SimpleDateFormat("MMM dd yyyy HH:mm:ss").format(message.getReceivedDate()));
	
				if(message.getContent() instanceof Multipart)
				{
					String mail="";
					MailDataList list=(MailDataList)new PartHandler().getParts((Multipart)message.getContent());
					
					//First get all inline documents
					//Only display one inline document, add the others as attachments
					boolean inlineSet=false;
					List inline=list.getInline();
					
					if(inline.size()>1)
					{
						for(i=0;i<inline.size();i++)
						{
							PartData md=(PartData)inline.get(i);
							if((!inlineSet)&&(md.getContentType().toLowerCase().startsWith("text/plain")))
							{
								mail+=toHTML(md.getData());
								inlineSet=true;
							}
							else
							{
								if((inlineSet)||(false))//check if html mail is ok
								{
									list.remove(md);
									md.setInline(false);
									list.add(md);
								}
								else
								{
									mail=toHTML(md.getData());
									inlineSet=true;
								}
							}
						}
					}
					else if(inline.size()==1)
					{
						PartData md=(PartData)inline.get(0);
						if(md.getContentType().toLowerCase().startsWith("text/plain"))
						{
							mail=toHTML(md.getData());
						}
						else
						{
							//check if HTML mail is OK
							if(false)
							{
								list.remove(md);
								md.setInline(false);
								list.add(md);
							}
							else
							{
								mail=md.getData();
							}
						}
					}
					
					List attachment=list.getAttachment();
					if((attachment!=null)&&(attachment.size()>0))
					{
						Iterator iter=attachment.iterator();
						while(iter.hasNext())
						{
							PartData md=(PartData)iter.next();
							mix.add("<!!--download-->","<!--folder-path-->",message.getFolder().getFullName());
							mix.add("<!!--download-->","<!--uid-->",""+uid);
							mix.add("<!!--download-->","<!--pid-->",md.getId());
							mix.add("<!!--download-->","<!--file-name-->",md.getFormattedFileName());
							mix.add("<!!--download-->","<!--size-->",""+(md.getSize()/1024));
							mix.add("<!!--download-->","<!--type-->",md.getFormattedContentType());
						}
					}
					else
					{
						mix.removeContext("<!!--attachements-->");
					}
					mix.add("<!--mail-->",mail);
				}
				else if(message.getContent() instanceof String)
				{
					mix.add("<!--mail-->",toHTML((String)message.getContent()));
					mix.removeContext("<!!--attachements-->");
				}
				else
				{
					System.out.println(message.getContent().getClass());
				}
				folder.close(false);
				return(mix.getMix());
			}
			folder.close(false);
		}
		return(null);
	}

	/**
	 * Replace special characters with html code. Also make all linkes clickable in
	 * the text.
	 * 
	 * @param str Ascii string.
	 * 
	 * @return HTML string.
	 * 
	 */
	private String toHTML(String str)
	{
		String tmp=str;
		tmp=tmp.replaceAll("\n","<br/>");
		tmp=tmp.replaceAll("\t","&nbsp;&nbsp;&nbsp;");

		Pattern p=Pattern.compile("(\\p{Space}{1}|^)(http|ftp)s?://(\\w*\\p{Punct}*)++");
		Matcher m=p.matcher(tmp);
		StringBuffer sb=new StringBuffer();
		boolean result;
		while(result=m.find())
		{
			m.appendReplacement(sb," <a href=\""+m.group().trim()+"\">"+m.group().trim()+"</a>");
		}
		m.appendTail(sb);
		return(sb.toString());
	}
}
