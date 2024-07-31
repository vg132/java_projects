/*
 * Created on 2004-okt-13 by viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 
 */
package com.vgsoftware.web.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Init implements ServletContextListener
{
	/**
	 * Load the access list and place it in the servlet context, "access-list".
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event)
	{
			List accessList=new ArrayList();
			DefaultHandler handler = new AccessListHandler(accessList);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			try
			{
				SAXParser saxParser = factory.newSAXParser();
				saxParser.parse(event.getServletContext().getRealPath("/")+"/WEB-INF/accesslist.xml",handler);
				event.getServletContext().setAttribute("access-list",accessList);
			}
			catch(SAXException sax)
			{
				sax.printStackTrace(System.err);
			}
			catch(IOException io)
			{
				io.printStackTrace(System.err);
			}
			catch(ParserConfigurationException pce)
			{
				pce.printStackTrace(System.err);
			}
	}

	public void contextDestroyed(ServletContextEvent event)
	{
	}
}

class AccessListHandler extends DefaultHandler
{
	private List accessList=null;
	
	public AccessListHandler(List accessList)
	{
		this.accessList=accessList;
	}

	public void startElement(String namespaceURI, String lName,String qName,Attributes attrs)
	throws SAXException
	{
		if(qName.equals("access"))
		{
			String directory=null;
			String level=null;
			for(int i=0;i<attrs.getLength();i++)
			{
				if(attrs.getQName(i).equals("directory"))
					directory=attrs.getValue(i);
				else if(attrs.getQName(i).equals("level"))
					level=attrs.getValue(i);
			}
			if((directory!=null)&&(level!=null))
			{
				accessList.add(new Directory(directory,Integer.parseInt(level)));
			}
		}
	}
}
