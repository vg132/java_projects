/*
 * Created on 2003-sep-18
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-18 Created by Viktor.
 * 2004-jun-03 Updated by Viktor, moved from JDOM to JDK XML (no addon needed).
 */
package com.vgsoftware.sjrc.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The addressbook class represents a list of server addresses.<br/>
 * The class can write down the addresses to a XML file and read them
 * back up again from the XML file.
 */
public class AddressBook
{
	private Map addresses=new HashMap();

	/**
	 * Reads the addresses from the XML file and put them into the hashmap.
	 * 
	 * @param xmlFile path to the xml file.
	 * 
	 * @throws IOException
	 */
	public void loadAddresses(String xmlFile)
	throws IOException
	{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document=builder.parse(new File(xmlFile));
			NodeList nList=document.getElementsByTagName("server");
			for(int i=0;i<nList.getLength();i++)
			{
				Node n=nList.item(i);
				NamedNodeMap nnm=n.getAttributes();
				addresses.put(nnm.getNamedItem("name").getNodeValue(),new AddressData(nnm.getNamedItem("name").getNodeValue(),nnm.getNamedItem("address").getNodeValue(),Integer.parseInt(nnm.getNamedItem("port").getNodeValue()),nnm.getNamedItem("nick").getNodeValue(),nnm.getNamedItem("password").getNodeValue()));
			}
		}
		catch(SAXException sxe)
		{
			sxe.printStackTrace(System.err);
		}
		catch(ParserConfigurationException pce)
		{
			pce.printStackTrace(System.err);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace(System.err);
		}
	}

	/**
	 * Gets the specified address from the addressbook. 
	 * 
	 * @param name name of the address to be returned from the addressbook.
	 * 
	 * @return the address associated with the specified address name or null if none is found.
	 */
	public AddressData getAddress(String name)
	{
		return((AddressData)addresses.get(name));
	}

	/**
	 * Gets all address in the addressbook.
	 * 
	 * @return all addresses from the addressbook, null if the addressbook is empty.
	 */
	public Object[] getAddresses()
	{
		return(addresses.values().toArray());
	}

	/**
	 * Adds a new address, overwrite the old one if there is one already with the same name.
	 * 
	 * @param ad the {@link AddressBook} object to be added.
	 */
	public void addAddress(AddressData ad)
	{
		addresses.put(ad.getName(),ad);
	}

	/**
	 * Removes the specified address from the addressbook.
	 * 
	 * @param name the name of the address to be removed from the addressbook.
	 * 
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
	 * @see #removeAddress(String name)
	 * 
	 * @param ad the address to be removed from the addressbook.
	 * 
	 * @return true if the addressbook contained the specified address.
	 */
	public boolean removeAddress(AddressData ad)
	{
		return(removeAddress(ad.getName()));
	}

	/**
	 * Write all addresses in the addresses hashmap to a XML file.
	 * 
	 * @param xmlFile path to xml file. If a file excists it will be overwritten.
	 * 
	 * @throws IOException
	 */
	public void saveAddresses(String xmlFile)
	throws IOException
	{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document document = builder.newDocument();
			Element root=document.createElement("servers"); 
			document.appendChild(root);
			Iterator iter=addresses.values().iterator();
			while(iter.hasNext())
			{
				AddressData ad=(AddressData)iter.next();
				Element server=document.createElement("server");
				server.setAttribute("name",ad.getName());
				server.setAttribute("address",ad.getAddress());
				server.setAttribute("port",Integer.toString(ad.getPort()));
				server.setAttribute("nick",ad.getNick());
				server.setAttribute("password",ad.getPassword());
				root.appendChild(server);
			}

			TransformerFactory transformerFactory=TransformerFactory.newInstance();
			Transformer transformer=transformerFactory.newTransformer();
			FileOutputStream os = new FileOutputStream(xmlFile);
			StreamResult sr = new StreamResult(os);
			transformer.transform(new DOMSource(document),sr);
			OutputStream ostream=sr.getOutputStream(); 
			if(ostream!=os)
			{
				ostream.close();
			}
			os.close();
		}
		catch(TransformerConfigurationException tce)
		{
			tce.printStackTrace(System.err);
		}
		catch(TransformerException te)
		{
			te.printStackTrace(System.err);
		}
		catch(ParserConfigurationException pce)
		{
			pce.printStackTrace(System.err);
		}
	}
}