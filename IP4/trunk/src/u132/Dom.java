/*
 * Created on 2005-maj-04
 */
package u132;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author vikto-ga
 */
public class Dom
{
	/**
	 * Use DOM to output a XML file. arg 1 has to be the XML file.
	 */
	public static void main(String[] args)
	{
		File file=new File(args[0]);
		try
		{
			DocumentBuilder builder=DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc=builder.parse(file);

			Node node=doc.getFirstChild();
			printNode(node,0);
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
		catch(SAXParseException spe)
		{
			spe.printStackTrace(System.err);
		}
		catch(SAXException se)
		{
			se.printStackTrace(System.err);
		}
		catch(ParserConfigurationException pce)
		{
			pce.printStackTrace(System.err);
		}
	}

	/**
	 * Recursive function that first checks if its a text node, if it is
	 * print out the text and return (we are at the end of the "tree").
	 * If its not write out the first part of the node and check arguments and print,
	 * print last part of tag and then call function again with any child nodes.
	 */
	private static void printNode(Node node, int tabs)
	{
		int i=0;
		if(node.getNodeType()==Node.TEXT_NODE)
		{
			if(node.getTextContent().trim().equals("")==false)
			{
				for(i=0;i<tabs;i++)
					System.out.print("\t");
				System.out.println(node.getTextContent().trim());
			}
			return;
		}
		for(i=0;i<tabs;i++)
			System.out.print("\t");
		System.out.print("<"+node.getNodeName());
		if(node.hasAttributes())
		{
			NamedNodeMap nnm=node.getAttributes();
			for(i=0;i<nnm.getLength();i++)
			{
				System.out.print(nnm.item(i));
			}
		}
		System.out.print(">\n");
		NodeList nodes=node.getChildNodes();
		for(i=0;i<nodes.getLength();i++)
		{
				printNode(nodes.item(i),tabs+1);
		}
		for(i=0;i<tabs;i++)
			System.out.print("\t");
		System.out.println("</"+node.getNodeName()+">");
	}
}
