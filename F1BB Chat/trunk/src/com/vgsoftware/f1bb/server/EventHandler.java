/**
 * Document History
 * Created on 2004-sep-08
 * 
 * @author Viktor
 * @version 1.0
 */
package com.vgsoftware.f1bb.server;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class EventHandler extends Thread
{
	private List events=new ArrayList();
	private F1BBChatServer server=null;

	public EventHandler(F1BBChatServer server)
	{
		this.server=server;
		this.start();
	}

	public void loadEvents()
	{
		try
		{
			SAXParser parser=SAXParserFactory.newInstance().newSAXParser();
			parser.parse("http://www.f1bb.com/f1forum/chat/chat.xml",new EventXMLHandler(events));
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		catch(ParserConfigurationException pce)
		{
			pce.printStackTrace(System.err);
		}
		catch(SAXException se)
		{
			se.printStackTrace(System.err);
		}
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				loadEvents();

				boolean open=false;
				long timeToEvent=-1;
				long tmp=0;
				Event event=null;
				Iterator iter=events.iterator();
				Calendar rightNow=Calendar.getInstance();
				while(iter.hasNext())
				{
					Event e=(Event)iter.next();
					if(e.isOpen(rightNow))
					{
						open=true;
						event=e;
					}
					tmp=e.timeToEvent(rightNow);
					if((tmp>0)&&((timeToEvent>tmp)||(timeToEvent==-1)))
						timeToEvent=tmp;
				}
				server.setChatOpen(open,event);
				Thread.sleep(timeToEvent+1000);
			}
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace(System.err);
		}
	}
}

class EventXMLHandler extends DefaultHandler
{
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd kk:mm z");
	private List events=null;
	
	public EventXMLHandler(List events)
	{
		this.events=events;
	}
	
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
	throws SAXException
	{
		try
		{
			if(qName.equalsIgnoreCase("event"))
				events.add(new Event(atts.getValue("type"),atts.getValue("event"),atts.getValue("session"),sdf.parse(atts.getValue("open")).getTime(),sdf.parse(atts.getValue("close")).getTime()));
		}
		catch(ParseException pe)
		{
			pe.printStackTrace(System.err);
		}
	}
}
