/*
 * Created on: 2003-okt-30
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-30 Created by Viktor
 */
package com.vgsoftware.vgmail.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.Store;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.vgsoftware.vgmail.misc.AttachmentData;
import com.vgsoftware.vgmail.misc.MailReader;
import com.vgsoftware.vgmail.misc.MailUser;

import dsv.pierre.Mixer;

/**
 * Servlet that handles upload and download of attachments.
 */
public class Attachment extends HttpServlet
{
	private String popupString=null;
	
	/**
	 * Init popupString with data from template.
	 * 
	 * @param config Servlet configuration.
	 * 
	 * @throws ServletException
	 */
	public void init(ServletConfig config)
	throws ServletException
	{
		String dir=(String)((Map)((Map)config.getServletContext().getAttribute("config")).get("template")).get("directory");
		popupString=Mixer.getContent(new File(dir+ config.getInitParameter("attachment_popup")));
		super.init(config);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		MailUser mu=(MailUser)request.getSession().getAttribute("mailuser");
		if(mu!=null)
		{
			String uploadDirectory=(String)mu.getAttachment("upload-directory");
			Mixer mix=new Mixer(popupString);
			AttachmentData ad=null;
			Map map=(Map)request.getSession().getAttribute("attachments");
			if(map!=null)
			{
				Iterator iter=map.keySet().iterator();
				while(iter.hasNext())
				{
					ad=(AttachmentData)map.get(iter.next());
					mix.add("<!!--files-->","<!--value-->",ad.getTmpName());
					mix.add("<!!--files-->","<!--name-->",ad.getName());
				}
			}
			mix.add("<!--status-->","Ready for uploads.");
			mix.add("<!--total-size-->",""+(getSize(map,uploadDirectory)/1024));
			mix.add("<!--free-space-->",""+((Long.parseLong((String)mu.getAttachment("maximum-size"))*1024*1024)-getSize(map,uploadDirectory))/1024);
			mix.add("<!--context-->",request.getContextPath());
			response.getOutputStream().println(mix.getMix());
		}
	}
	
	/**
	 * Check if its a download or upload request.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		Store store=null;
		MailUser mu=(MailUser)request.getSession().getAttribute("mailuser");
		if(mu!=null)
		{
			try
			{
				if(request.getContentType().toLowerCase().startsWith("multipart"))
				{
					createPopup(request,response,mu);
				}
				else
				{
					store=mu.openConnection();
					new MailReader().downloadAttachment(request,response,store);
				}
			}
			catch(MessagingException me)
			{
			}
			finally
			{
				try
				{
					mu.closeConnection(store);
				}
				catch(Exception e)
				{
				}
			}
		}
	}

	/**
	 * Add or remove attachment files from the users session.
	 * The files are stored on the server until the user press send or the cleaning timeout
	 * happens.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws IOException
	 */
	private void createPopup(HttpServletRequest request, HttpServletResponse response,MailUser mu)
	throws IOException
	{
		Mixer mix=new Mixer(popupString);
		String uploadDirectory=(String)mu.getAttachment("upload-directory");
		Map map=(Map)request.getSession().getAttribute("attachments");
		if(map==null)
		{
			map=new HashMap();
			request.getSession().setAttribute("attachments",map);
		}

		try
		{
			MultipartRequest multi=new MultipartRequest(request,uploadDirectory,(int)((Long.parseLong((String)mu.getAttachment("maximum-size"))*1024*1024)-getSize(map,uploadDirectory)));
			if((multi.getParameter("whattodo")!=null)&&(multi.getParameter("whattodo").equals("remove")))
			{
				String file=multi.getParameter("files");
				AttachmentData ad=(AttachmentData)map.remove(file);
				if(ad!=null)
				{
					new File(uploadDirectory+"/"+ad.getTmpName()).delete();
				}
			}
			else
			{
				Enumeration files=multi.getFileNames();
				if(files.hasMoreElements())
				{
					File file=multi.getFile((String)files.nextElement());
					File newFile;
					AttachmentData ad=new AttachmentData(file.getName());
					while((newFile=new File(uploadDirectory+"/mail_"+Math.random()*100000)).exists());
					ad.setTmpName(newFile.getName());
					file.renameTo(newFile);
					map.put(ad.getTmpName(),ad);
					mix.add("<!--status-->","\""+ad.getName()+"\" uploaded.");
				}
			}
		}
		catch(IOException io)
		{
			mix.add("<!--status-->","File to large.");
		}
		Iterator iter=map.keySet().iterator();
		while(iter.hasNext())
		{
			AttachmentData data=(AttachmentData)map.get(iter.next());
			mix.add("<!!--files-->","<!--value-->",data.getTmpName());
			mix.add("<!!--files-->","<!--name-->",data.getName());
		}
		mix.add("<!--total-size-->",""+(getSize(map,uploadDirectory)/1024));
		mix.add("<!--free-space-->",""+((Long.parseLong((String)mu.getAttachment("maximum-size"))*1024*1024)-getSize(map,uploadDirectory))/1024);

		mix.add("<!--context-->",request.getContextPath());
		response.getOutputStream().println(mix.getMix());
	}
	
	private long getSize(Map map, String uploadDirectory)
	{
		if(map==null)
			return(0);
		long size=0;
		Iterator iter=map.keySet().iterator();
		while(iter.hasNext())
			size+=new File(uploadDirectory+"/"+((AttachmentData)map.get(iter.next())).getTmpName()).length();
		return(size);
	}
}