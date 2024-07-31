package com.vgsoftware.appengine.polisenrss;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Utilities
{
	public static Element getRootElement(String feedUrl)
	{
		try
		{
			URL url = new URL(feedUrl);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				InputStream inputStream = connection.getInputStream();
				DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document document = documentBuilder.parse(inputStream);
				Element root=document.getDocumentElement();
				root.normalize();
				return root;
			}
		}
		catch(Exception ex)
		{
		}
		return null;
	}

	public static String getValue(Element root, String tagName)
	{
		if(root!=null && tagName!=null && !tagName.equals(""))
		{
			NodeList elements=root.getElementsByTagName(tagName);
			if(elements!=null && elements.getLength()>0 && elements.item(0) instanceof Element)
			{
				Element element=(Element)elements.item(0);
				if(element.getFirstChild()!=null)
				{
					return element.getFirstChild().getNodeValue();
				}
			}
		}
		return null;
	}

	public static String getValue(NodeList nodes, String tagName)
	{
		for(int i = 0;i < nodes.getLength();i++)
		{
			Node node = nodes.item(i);
			if(node instanceof Element)
			{
				Element element=(Element)node;
				if(element.getNodeName().equals(tagName) && element.getFirstChild()!=null)
				{
					return element.getFirstChild().getNodeValue();
				}
			}
		}
		return null;
	}
}
