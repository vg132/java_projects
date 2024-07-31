package com.vgsoftware.shop.init;

import java.util.Map;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-maj-04 - Document created.
 */
public class ShopConfigHandler extends DefaultHandler
{
	private StringBuffer sb=new StringBuffer();
	private Map<String,String> config=null;
	private String name=null;
	private String value=null;

	public ShopConfigHandler(Map<String,String> config)
	{
		this.config=config;
	}

	public void characters(char[] ch,int start,int length) throws SAXException
	{
		if(sb!=null)
			sb.append(ch,start,length);
	}

	public void endElement(String uri,String localName,String qName)
	throws SAXException
	{
		if(qName.equals("name"))
		{
			name=sb.toString().trim();
		}
		else if(qName.equals("value"))
		{
			value=sb.toString().trim();
		}
		else if(qName.equals("item"))
		{
			config.put(name,value);
		}
		sb=new StringBuffer();
	}
}
