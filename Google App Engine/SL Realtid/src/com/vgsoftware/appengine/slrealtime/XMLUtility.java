package com.vgsoftware.appengine.slrealtime;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class XMLUtility
{
	public static Element getRootElement(String feedUrl)
	{
		if (feedUrl != null && !feedUrl.isEmpty())
		{
			try
			{
				URLFetchService fetchService =  URLFetchServiceFactory.getURLFetchService();
				URL url = new URL(feedUrl);
				HTTPRequest request = new HTTPRequest(url);				
				HTTPResponse response = fetchService.fetch(request);

				InputStream stream = new ByteArrayInputStream(response.getContent());
				
				DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document document = documentBuilder.parse(stream);
				Element root = document.getDocumentElement();
				root.normalize();
				return root;
			}
			catch (Exception ex)
			{
				ex.printStackTrace(System.err);
			}
		}
		return null;
	}

	public static String getAttributeValue(Node node, String attributeName)
	{
		if (node != null && node.hasAttributes())
		{
			Node attribute = node.getAttributes().getNamedItem(attributeName);
			if (attribute != null)
			{
				return attribute.getNodeValue();
			}
		}
		return "";
	}

	public static boolean getAttributeValueBoolean(Node node, String attributeName)
	{
		String value = XMLUtility.getAttributeValue(node, attributeName);
		if (value != null && !value.isEmpty())
		{
			return new Boolean(value).booleanValue();
		}
		return false;
	}

	public static String getValue(Element root, String tagName)
	{
		if (root != null && tagName != null && !tagName.isEmpty())
		{
			NodeList elements = root.getElementsByTagName(tagName);
			if (elements != null && elements.getLength() > 0 && elements.item(0) instanceof Element)
			{
				Element element = (Element) elements.item(0);
				if (element.getFirstChild() != null)
				{
					return element.getFirstChild().getNodeValue();
				}
			}
		}
		return null;
	}

	public static String getValue(NodeList nodes, String tagName)
	{
		for (int i = 0; i < nodes.getLength(); i++)
		{
			Node node = nodes.item(i);
			if (node instanceof Element)
			{
				Element element = (Element) node;
				if (element.getNodeName().equals(tagName) && element.getFirstChild() != null)
				{
					return element.getFirstChild().getNodeValue();
				}
			}
		}
		return null;
	}

	public static int getValueInt(Element root, String tagName)
	{
		String value = XMLUtility.getValue(root, tagName);
		if (value != null && !value.isEmpty())
		{
			try
			{
				return Integer.parseInt(value);
			}
			catch (NumberFormatException nfe)
			{
			}
		}
		return 0;
	}

	public static double getValueDouble(Element root, String tagName)
	{
		String value = XMLUtility.getValue(root, tagName);
		if (value != null && !value.isEmpty())
		{
			try
			{
				return Double.parseDouble(value);
			}
			catch (NumberFormatException nfe)
			{
			}
		}
		return 0;
	}

	public static boolean getValueBoolean(Element root, String tagName)
	{
		String value = XMLUtility.getValue(root, tagName);
		if (value != null && !value.isEmpty())
		{
			return new Boolean(value).booleanValue();
		}
		return false;
	}

	public static Date getValueDate(Element root, String tagName)
	{
		return DateParser.parse(XMLUtility.getValue(root, tagName));
	}
}
