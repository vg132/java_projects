package com.vgsoftware.shop.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ColorSwitchTag extends TagSupport
{
	private String even=null;
	private String odd=null;

	public int doEndTag() throws JspException
	{
		try
		{
			int number=0;
			if(pageContext.getAttribute("switch_number")!=null)
				number=((Integer)pageContext.getAttribute("switch_number")).intValue();
			number++;
			if(number%2==0)
				pageContext.getOut().print(even);
			else
				pageContext.getOut().print(odd);
			pageContext.setAttribute("switch_number",number);
		}
		catch(IOException io)
		{
		}
		catch(NumberFormatException nfe)
		{
		}
		return(EVAL_PAGE);
	}

	public String getEven()
	{
		return (even);
	}

	public void setEven(String even)
	{
		this.even=even;
	}

	public String getOdd()
	{
		return (odd);
	}

	public void setOdd(String odd)
	{
		this.odd=odd;
	}
}
