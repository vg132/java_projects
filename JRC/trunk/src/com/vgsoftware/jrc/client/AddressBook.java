/*
 * Created on 2003-sep-18
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-18 Created by Viktor.
 */
package com.vgsoftware.jrc.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


/**
 * The addressbook class represents a list of server addresses.<br/>
 * The class can write down the addresses to a XML file using <a href="http://www.jdom.org" target="_new">JDOM</a> and read them
 * back up again from the XML file.
 */
public class AddressBook
{
	private Map addresses=new HashMap();

	/**
	 * Reads the addresses from the XML file and put them into the hashmap.
	 * 
	 * @param xmlFile path to the xml file.
	 * @throws IOException
	 */
	public void loadAddresses(String xmlFile)
	throws IOException
	{
		try
		{
			SAXBuilder builder=new SAXBuilder(false);
			Document doc=builder.build(new File(xmlFile));
			Element root=doc.getRootElement();
			List servers=root.getChildren();
			Iterator iter=servers.iterator();
			while(iter.hasNext())
			{
				Element server=(Element)iter.next();
				AddressData ad=new AddressData();
				ad.setName(server.getAttributeValue("name"));
				ad.setAddress(server.getAttributeValue("address"));
				try
				{
					ad.setPort(Integer.parseInt(server.getAttributeValue("port")));
				}
				catch(NumberFormatException nfe)
				{
				}
				ad.setNick(server.getAttributeValue("nick"));
				ad.setPassword(server.getAttributeValue("password"));
				addresses.put(ad.getName(),ad);
			}
		}
		catch(JDOMException je)
		{
			je.printStackTrace(System.err);
		}
	}

	/**
	 * Gets all address in the addressbook.
	 * @return all addresses in from the addressbook, null if the addressbook is empty.
	 */
	public Object[] getAddresses()
	{
		return(addresses.values().toArray());
	}

	/**
	 * Adds a new address, overwrite the old one if there is one already with the same name.
	 * @param ad the {@link AddressBook} object to be added.
	 */
	public void addAddress(AddressData ad)
	{
		addresses.put(ad.getName(),ad);
	}

	/**
	 * @see #removeAddress(String name)
	 * @param ad the address to be removed from the addressbook.
	 * @return true if the addressbook contained the specified address.
	 */
	public boolean removeAddress(AddressData ad)
	{
		return(removeAddress(ad.getName()));
	}

	/**
	 * Gets the specified address from the addressbook. 
	 * @param name address to be returned from the addressbook.
	 * @return the address associated with the specified address name.
	 */
	public AddressData getAddress(String name)
	{
		return((AddressData)addresses.get(name));
	}

	/**
	 * Removes the specified address from the addressbook.
	 * @param name the name of the address to be removed from the addressbook.
	 * @return true if addressbook contained the specified address.
	 */
	public boolean removeAddress(String name)
	{
		if(addresses.remove(name)==null)
			return(true);
		else
			return(false);
	}

	/**
	 * Write all addresses in the addresses hashmap to a XML file.
	 * @param xmlFile path to xml file. If a file excists it will be overwritten.
	 * @throws IOException
	 */
	public void saveAddresses(String xmlFile)
	throws IOException
	{
		Element root=new Element("servers");
		Document doc=new Document(root);

		Iterator iter=addresses.values().iterator();
		while(iter.hasNext())
		{
			AddressData ad=(AddressData)iter.next();

			Element element=new Element("server");
			element.setAttribute("name",ad.getName());
			element.setAttribute("address",ad.getAddress());
			element.setAttribute("port",Integer.toString(ad.getPort()));
			element.setAttribute("nick",ad.getNick());
			element.setAttribute("password",ad.getPassword());
			root.addContent(element);			
		}
		XMLOutputter outputter= new XMLOutputter(Format.getRawFormat());
		FileOutputStream output= new FileOutputStream(xmlFile);
		outputter.output(doc, output);
	}
}