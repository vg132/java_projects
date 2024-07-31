package com.vgsoftware.shop.init;

import java.util.SortedMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CurrencyHandler extends DefaultHandler
{
	private SortedMap<String,Double> currency=null;
	
	public CurrencyHandler(SortedMap<String,Double> currency)
	{
		this.currency=currency;
	}

	public void startElement(String uri,String localName,String qName, Attributes attributes)
	throws SAXException
	{
		if((qName.equals("Cube")==true)&&(attributes.getValue("currency")!=null))
			currency.put(attributes.getValue("currency"),Double.parseDouble(attributes.getValue("rate")));
	}
}
