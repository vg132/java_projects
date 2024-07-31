package prime.server;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Holds the server configuration.
 */
public class ServerConfig
{
	private int port=1099;
	private int intervalSize=50;
	private int timeout=30;
	private String name="PrimeFinderServer";
	private String primeLogFile=null;
	private String stateFile=null;

	public ServerConfig()
	{
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
	
			//Load the mapping xml file
			ConfigHandler handler=new ConfigHandler();
			saxParser.parse("server.xml",handler);
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
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */
		public void startElement(String uri, String localName, String qName, Attributes attributes)
		throws SAXException
		{
			if(qName.equals("server"))
			{
				port=Integer.parseInt(attributes.getValue("port"));
				name=attributes.getValue("name");
			}
			else if(qName.equals("interval-size"))
			{
				timeout=Integer.parseInt(attributes.getValue("timeout"));
			}
		}

		/**
		 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
		 */
		public void characters(char[] ch, int start, int length)
		throws SAXException
		{
			sb.append(ch,start,length);
		}

		/**
		 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
		 */
		public void endElement(String uri, String localName, String qName)
		throws SAXException
		{
			if(qName.equals("interval-size"))
				intervalSize=Integer.parseInt(sb.toString().trim());
			else if(qName.equals("state-file"))
				stateFile=sb.toString().trim();
			else if(qName.equals("prime-log-file"))
				primeLogFile=sb.toString().trim();
			sb=new StringBuffer();
		}
	}

	/**
	 * @return Returns the intervalSize.
	 */
	public int getIntervalSize()
	{
		return intervalSize;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return Returns the port.
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * @return Returns the timeout.
	 */
	public int getTimeout()
	{
		return timeout;
	}

	/**
	 * @return Returns the stateFile.
	 */
	public String getStateFile()
	{
		return stateFile;
	}

	/**
	 * @return Returns the primeLogFile.
	 */
	public String getPrimeLogFile()
	{
		return primeLogFile;
	}
}