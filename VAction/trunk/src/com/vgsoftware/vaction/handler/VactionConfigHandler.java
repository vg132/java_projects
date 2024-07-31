package com.vgsoftware.vaction.handler;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.vgsoftware.vaction.Action;
import com.vgsoftware.vaction.data.ActionData;
import com.vgsoftware.vaction.data.DataSourceData;
import com.vgsoftware.vaction.data.ViewData;


/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-maj-04 - Document created.
 */
public class VactionConfigHandler extends DefaultHandler
{
	private DataSourceData dataSourceData=null;
	private Map<String,String> globals=new HashMap<String,String>();
	private Action defaultAction=null;
	private Map<String,ActionData> actions=new HashMap<String,ActionData>();
	private String nameAttrib=null;
	private ActionData ad=null;
	private boolean redirect=false;
	private StringBuffer data=new StringBuffer();

	public VactionConfigHandler()
	{
	}

	public void endElement(String uri,String localName,String qName)
	throws SAXException
	{
		if(qName.equals("path"))
		{
			ad.setPath(data.toString().trim());
		}
		else if(qName.equals("action-class"))
		{
			ad.setAction(data.toString().trim());
		}
		else if(qName.equals("view"))
		{
			ad.addView(nameAttrib,new ViewData(data.toString().trim(),redirect));
		}
		else if(qName.equals("global-view"))
		{
			globals.put(nameAttrib,data.toString().trim());
			nameAttrib=null;
		}
		else if(qName.equals("action"))
		{
			actions.put(ad.getPath(),ad);
			nameAttrib=null;
			redirect=false;
		}
		else if(qName.equals("default-action"))
		{
			defaultAction=ad.getAction();
		}
		else if(qName.equals("driverClass"))
		{
			dataSourceData.setDriverClass(data.toString().trim());
		}
		else if(qName.equals("url"))
		{
			dataSourceData.setUrl(data.toString().trim());
		}
		else if(qName.equals("username"))
		{
			dataSourceData.setUsername(data.toString().trim());
		}
		else if(qName.equals("password"))
		{
			dataSourceData.setPassword(data.toString().trim());
		}
		data=new StringBuffer();
	}

	public void characters(char[] ch,int start,int length) throws SAXException
	{
		data.append(ch,start,length);
	}

	public void startElement(String uri,String localName,String qName, Attributes attributes)
	throws SAXException
	{
		if(qName.equals("global-view"))
		{
			nameAttrib=attributes.getValue("name");
		}
		else if(qName.equals("view"))
		{
			nameAttrib=attributes.getValue("name");
			redirect=Boolean.parseBoolean(attributes.getValue("redirect"));
		}
		else if(qName.equals("action"))
		{
			ad=new ActionData();
		}
		else if(qName.equals("default-action"))
		{
			ad=new ActionData();
		}
		else if(qName.equals("data-source"))
		{
			dataSourceData=new DataSourceData();
		}
	}

	public Map<String, ActionData> getActions()
	{
		return(actions);
	}

	public DataSourceData getDataSourceData()
	{
		return(dataSourceData);
	}

	public Action getDefaultAction()
	{
		return(defaultAction);
	}

	public Map<String, String> getGlobals()
	{
		return(globals);
	}
}
