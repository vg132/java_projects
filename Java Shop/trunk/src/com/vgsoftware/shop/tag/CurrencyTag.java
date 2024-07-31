package com.vgsoftware.shop.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.vgsoftware.shop.data.CustomerData;
import com.vgsoftware.shop.data.Globals;

public class CurrencyTag extends TagSupport
{
	private double price=-1;

	public int doEndTag() throws JspException
	{
		try
		{
			if(pageContext.getSession().getAttribute("customer")!=null)
			{
				CustomerData cd=(CustomerData)pageContext.getSession().getAttribute("customer");
				pageContext.getOut().print(price*((Map<String,Double>)pageContext.getServletContext().getAttribute(Globals.CURRENCY)).get(cd.getCurrency()));
			}
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		return(EVAL_PAGE);
	}

	public double getPrice()
	{
		return (price);
	}

	public void setPrice(double price)
	{
		this.price=price;
	}	
}
