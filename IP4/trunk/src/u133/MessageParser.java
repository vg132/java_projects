package u133;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MessageParser
{
	/**
	 * Decode a XML message to a Message object. If the XML is invalid return null.
	 */
	public static Message getMessage(String xmlMessage)
	{
		Message msg=null;
		try
		{
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			factory.setValidating(true);
			DocumentBuilder builder=factory.newDocumentBuilder();
			SAXErrorHandler e=new SAXErrorHandler();
			builder.setErrorHandler(e);
			Document doc=builder.parse(new InputSource(new StringReader(xmlMessage)));
			if(!e.hasErrors())
			{
				msg=new Message();
				
				NodeList nl=doc.getElementsByTagName("name");
				msg.setName(nl.item(0).getTextContent().trim());
				nl=doc.getElementsByTagName("email");
				msg.setEmail(nl.item(0).getTextContent().trim());
				nl=doc.getElementsByTagName("homepage");
				msg.setHomepage(nl.item(0).getTextContent().trim());
				nl=doc.getElementsByTagName("host");
				msg.setHost(nl.item(0).getTextContent().trim());
				nl=doc.getElementsByTagName("body");
				msg.setBody(nl.item(0).getTextContent().trim());
			}
		}
		catch(ParserConfigurationException pce)
		{
		}
		catch(SAXParseException spe)
		{
		}
		catch(SAXException se)
		{
		}
		catch(IOException io)
		{
		}
		return(msg);
	}
}
