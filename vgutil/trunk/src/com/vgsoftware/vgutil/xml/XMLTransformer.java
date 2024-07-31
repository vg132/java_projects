/**
 * 
 * Document History
 *   Created on 2004-sep-27 by viktor
 * 
 * @author viktor
 */
package com.vgsoftware.vgutil.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


public class XMLTransformer
{
	public static void xsltTransformer(File xml, File xslt, File result)
	throws IOException
	{
		FileWriter fw=new FileWriter(result);
		fw.write(xsltTransformer(xml,xslt));
		fw.flush();
		fw.close();
	}
	
	public static void xsltTransformer(String xml, String xslt, File result)
	throws IOException
	{
		FileWriter fw=new FileWriter(result);
		fw.write(xsltTransformer(xml,xslt));
		fw.flush();
		fw.close();
	}

	public static String xsltTransformer(File xml, File xslt)
	throws IOException
	{
		BufferedReader brXML=new BufferedReader(new FileReader(xml));
		BufferedReader brXSLT=new BufferedReader(new FileReader(xslt));
		String sXML="";
		String sXSLT="";
		String line="";
		while((line=brXML.readLine())!=null)
			sXML+=line;
		while((line=brXSLT.readLine())!=null)
			sXSLT+=line;
		return(xsltTransformer(sXML,sXSLT));
	}

	public static String xsltTransformer(String xml, String xslt)
	{
		StringWriter out=new StringWriter();
		try
		{
			TransformerFactory tff = TransformerFactory.newInstance();
			StreamSource xslSource = new StreamSource(new StringReader(xslt));
			Transformer trans = tff.newTransformer(xslSource);
			StreamSource xmlSource = new StreamSource(new StringReader(xml));
			StreamResult output = new StreamResult(out);
			trans.transform(xmlSource,output);
		}
		catch(TransformerConfigurationException tce)
		{
			tce.printStackTrace(System.err);
		}
		catch(TransformerException te)
		{
			te.printStackTrace(System.err);
		}
		return(out.getBuffer().toString());
	}
}