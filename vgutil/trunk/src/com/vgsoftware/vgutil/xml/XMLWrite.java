package com.vgsoftware.vgutil.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Stack;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * 
 * Document History
 *   Created on 2004-sep-27 by viktor
 * 
 * @author Viktor
 */
public class XMLWrite
{
	private Stack openElements=new Stack();
	private StringWriter out=new StringWriter();
	private TransformerHandler document=null;
	
	public void startDocument()
	throws SAXException
	{
		startDocument("ISO-8859-1");
	}

	public void startDocument(String encoding)
	throws SAXException
	{
		try
		{
			StreamResult streamResult=new StreamResult(out);
			SAXTransformerFactory tf=(SAXTransformerFactory)SAXTransformerFactory.newInstance();
			document=tf.newTransformerHandler();
			Transformer serializer=document.getTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING,encoding);
			serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			serializer.setOutputProperty(OutputKeys.INDENT,"yes");
			document.setResult(streamResult);
			document.startDocument();
		}
		catch(TransformerConfigurationException tce)
		{
			tce.printStackTrace(System.err);
		}
	}

	public void endDocument()
	throws SAXException
	{
		document.endDocument();
	}

	public String getDocumentContent()
	{
		return(out.getBuffer().toString());
	}

	public void startElement(String name)
	throws SAXException
	{
		startElement(name,new AttributesImpl());
	}

	public void startElement(String name, AttributesImpl atts)
	throws SAXException
	{
		document.startElement("","",name,atts);
		openElements.push(name);
	}

	public void addComment(String data)
	throws SAXException
	{
		document.comment(data.toCharArray(),0,data.length());
	}

	public void addCDATA(String data)
	throws SAXException
	{
		document.characters(data.toCharArray(),0,data.length());
	}

	public void endElement()
	throws SAXException
	{
		document.endElement("","",(String)openElements.pop());
	}

	public void addElement(String name,AttributesImpl atts, String CDATA)
	throws SAXException
	{
		startElement(name,atts);
		addCDATA(CDATA);
		endElement();
	}
	
	public void addElement(String name, String CDATA)
	throws SAXException
	{
		startElement(name);
		addCDATA(CDATA);
		endElement();
	}
	
	public boolean writeToFile(String filename)
	throws IOException
	{
		return(writeToFile(new File(filename)));
	}
	
	public boolean writeToFile(File file)
	throws IOException
	{
		FileWriter fw=new FileWriter(file);
		fw.write(getDocumentContent());
		fw.flush();
		fw.close();
		return(true);
	}
}
