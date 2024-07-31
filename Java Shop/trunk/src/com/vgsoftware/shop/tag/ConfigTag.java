package com.vgsoftware.shop.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.vgsoftware.shop.data.Globals;

public class ConfigTag extends TagSupport
{
	private String key=null;
	
	public int doEndTag()
	throws JspException
	{
		try
		{
			if(pageContext.getServletContext().getAttribute(Globals.CONFIG)!=null)
			{
				Map<String,String> config=(Map<String,String>)pageContext.getServletContext().getAttribute(Globals.CONFIG);
				pageContext.getOut().print(config.get(key));
			}
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		return(EVAL_PAGE);
	}

	public String getKey()
	{
		return(key);
	}

	public void setKey(String key)
	{
		this.key=key;
	}
}
