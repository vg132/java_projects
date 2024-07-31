package com.vgsoftware.shop.init;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.vgsoftware.shop.data.Globals;

public class Init implements ServletContextListener
{
	public void contextInitialized(ServletContextEvent sce)
	{
		Map<String,String> config=new HashMap<String,String>();
		SortedMap<String,Double> currency=new TreeMap<String,Double>();

		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			//Read shop configuration file
			DefaultHandler handler=new ShopConfigHandler(config);
			saxParser.parse(sce.getServletContext().getRealPath("/")+"/WEB-INF/shop-config.xml",handler);
			sce.getServletContext().setAttribute(Globals.CONFIG,config);
			
			//Read the currency rates file, the url for this file is defined in the config file.
			handler=new CurrencyHandler(currency);
			saxParser.parse(config.get("currency_rates"),handler);
			sce.getServletContext().setAttribute(Globals.CURRENCY,currency);
			//add the base currency
			currency.put(config.get("currency"),1.0);
		}
		catch(SAXException sax)
		{
			sax.printStackTrace(System.err);
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		catch(ParserConfigurationException pce)
		{
			pce.printStackTrace(System.err);
		}
	}

	public void contextDestroyed(ServletContextEvent sce)
	{
	}
}
