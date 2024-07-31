/*
 * Created on: 2003-okt-24
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-24 Created by Viktor
 */
package com.vgsoftware.vgmail.misc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Loads the configuration at startup and saves the configuration in
 * the servlet context.
 */
public class vgMailConfig implements ServletContextListener
{
	private UploadCleaner uc=null;
	
	/**
	 * Parse vgmail xml configuration file and set resulting properties objects
	 * in the servlet context.
	 * 
	 * @param sce ServletContextEvent
	 *
	 */
	public void contextInitialized(ServletContextEvent sce)
	{
		try
		{
			Map hm=new HashMap();
			SAXParser parser=SAXParserFactory.newInstance().newSAXParser();
			parser.parse(sce.getServletContext().getRealPath("/WEB-INF/config/configuration.xml"),new ConfigHandler(hm));
			sce.getServletContext().setAttribute("config",hm);
			uc=new UploadCleaner((String)((Map)hm.get("attachment")).get("upload-directory"),Integer.parseInt((String)((Map)hm.get("attachment")).get("clean-interval")),Integer.parseInt((String)((Map)hm.get("attachment")).get("maximum-file-age")));
			uc.start();
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		catch(ParserConfigurationException pce)
		{
			pce.printStackTrace(System.err);
		}
		catch(FactoryConfigurationError fce)
		{
			fce.printStackTrace(System.err);
		}
		catch(SAXException se)
		{
			se.printStackTrace(System.err);
		}
	}

	/**
	 * @param sce ServletContextEvent
	 */
	public void contextDestroyed(ServletContextEvent sce)
	{
		if(uc!=null)
		{
			uc.stopThread();
			uc.interrupt();
		}
	}
}

/**
 * Handler class used when parsing xml configuration file.
 */
class ConfigHandler extends DefaultHandler
{
	private Map config=null;
	private Map values=null;
	private int level=0;
	private StringBuffer data=null;
	
	public ConfigHandler(Map map)
	{
		config=map;
	}
	
	/**
	 * Reset character data and create a new properties object if its on the second level
	 * in the file and add the properties object to the map.
	 * 
	 * @param namespaceURI
	 * @param localName
	 * @param qName Element name.
	 * @param atts Element attributes.
	 */
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
	throws SAXException
	{
		level++;
		if(level==2)
		{
			values=new HashMap();
			config.put(qName,values);
		}
		data=new StringBuffer();
	}

	/**
	 * If we are in a level higher then 1 then add data to the properties object. 
	 * 
	 * @param namespaceURI
	 * @param localName
	 * @param qName Element name
	 * 
	 * @throws SAXException
	 */
	public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException
	{
		level--;
		if(level>1)
			values.put(qName,data.toString().trim());
		else
			data=new StringBuffer();
	}

	/**
	 * Add characters to data string.
	 * 
	 * @param ch Character array.
	 * @param start Indext of the first character in the array.
	 * @param length The length of the array.
	 * 
	 * @throws SAXException
	 */
	public void characters(char ch[], int start, int length)
	throws SAXException
	{
		data.append(ch,start,length);
	}
}