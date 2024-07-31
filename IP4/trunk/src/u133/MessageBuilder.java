package u133;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MessageBuilder
{
	private static DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();

	/**
	 * Creates a XML string from the Message object recived with DOM.
	 */
	public static String getXMLMessage(Message msg)
	{
		try
		{
			DocumentBuilder builder = factory.newDocumentBuilder();			
			Document doc=builder.newDocument();

			Element root=doc.createElement("message");
			Element header=doc.createElement("header");
			//create protocol subtree
			Element protocol=doc.createElement("protocol");
			Element tmp=doc.createElement("type");
			tmp.appendChild(doc.createTextNode(Message.TYPE));
			protocol.appendChild(tmp);
			tmp=doc.createElement("version");
			tmp.appendChild(doc.createTextNode(Message.VERSION));
			protocol.appendChild(tmp);
			tmp=doc.createElement("command");
			tmp.appendChild(doc.createTextNode(Message.COMMAND));
			protocol.appendChild(tmp);
			
			
			//create ID subtree
			Element id=doc.createElement("id");
			tmp=doc.createElement("name");
			tmp.appendChild(doc.createTextNode(msg.getName()));
			id.appendChild(tmp);
			tmp=doc.createElement("email");
			tmp.appendChild(doc.createTextNode(msg.getEmail()));
			id.appendChild(tmp);
			tmp=doc.createElement("homepage");
			tmp.appendChild(doc.createTextNode(msg.getHomepage()));
			id.appendChild(tmp);
			tmp=doc.createElement("host");
			tmp.appendChild(doc.createTextNode(msg.getHost()));
			id.appendChild(tmp);
			
			header.appendChild(protocol);
			header.appendChild(id);

			Element body=(Element)doc.createElement("body");
			body.appendChild(doc.createTextNode(msg.getBody()));
			root.appendChild(header);
			root.appendChild(body);
			doc.appendChild(root);

			Source source=new DOMSource(doc);
			StringWriter sw=new StringWriter();
      Result result = new StreamResult(sw);

      // Write the DOM document to the file
      // Get Transformer
      Transformer xformer = TransformerFactory.newInstance().newTransformer();
      xformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"http://atlas.dsv.su.se/~pierre/i/05_ass/ip4/a_1_3_3/message.dtd");
      xformer.setOutputProperty(OutputKeys.METHOD,"xml");
      xformer.setOutputProperty(OutputKeys.INDENT,"yes");
      xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
      // Write to a file
      xformer.transform(source, result);
      return(sw.toString());
		}
		catch(TransformerException te)
		{
		}
		catch(ParserConfigurationException pce)
		{
		}		
		return("");
	}
}