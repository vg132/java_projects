package prime.client;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Holds the server configuration.
 */
public class ClientConfig
{
	private String serverUrl="//www.vgsoftware.com:1099/PrimeFinderServer";
	private String statFile=null;
	
	public ClientConfig()
	{
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
	
			//Load the mapping xml file
			ConfigHandler handler=new ConfigHandler();
			saxParser.parse("bin/client.xml",handler);
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
	}
	
	/**
	 * Read the config xml file.
	 */
	class ConfigHandler extends DefaultHandler
	{
		private StringBuffer sb=new StringBuffer();

		/**
		 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
		 */
		public void characters(char[] ch, int start, int length) throws SAXException
		{
			sb.append(ch,start,length);
		}

		/**
		 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
		 */
		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if(qName.equals("server-url"))
				serverUrl=sb.toString().trim();
			else if(qName.equals("stat-file"))
				statFile=sb.toString().trim();
		}

		/**
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			sb=new StringBuffer();
		}
	}

	/**
	 * @return Returns the statFile.
	 */
	public String getStatFile()
	{
		return statFile;
	}

	/**
	 * @return Returns the server url.
	 */
	public String getServerUrl()
	{
		return(serverUrl);
	}
}