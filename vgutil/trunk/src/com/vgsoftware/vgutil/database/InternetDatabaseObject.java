/**
 * Copyright (C) VG Software 2003-apr-13
 * 
 * Document History
 * 
 * Created: 2003-apr-13 10:56:18 by Viktor
 */
package com.vgsoftware.vgutil.database;

import javax.servlet.jsp.PageContext;

/**
 * @author Viktor
 *
 */
public abstract class InternetDatabaseObject extends DatabaseObject implements IInternetDBObject
{
	protected String extra=null;
	protected PageContext pageContext=null;

	public boolean isTrue(String key, String data)
	{
		if((key!=null)&&(this.data.get(key)!=null)&&(data!=null))
		{
			if(this.data.get(key).toString().equals(data))
				return(true);
		}
		return(false);
	}

	public void setExtra(String extra)
	{
		this.extra=extra;
	}
	
	public void setPageContext(PageContext pageContext)
	{
		this.pageContext=pageContext;		
	}
	
	public PageContext getPageContext()
	{
		return(pageContext);
	}
}
