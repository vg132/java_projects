/**
 * Copyright (C) VG Software 2003-apr-13
 * 
 * Document History
 * 
 * Created: 2003-apr-13 11:09:26 by Viktor
 */
package com.vgsoftware.vgutil.database;

import javax.servlet.jsp.PageContext;

/**
 * @author Viktor
 *
 */
public interface IInternetDBObject extends IDBObject
{
	public void setExtra(String extra);
	public boolean isTrue(String key, String data);
	public void setPageContext(PageContext pageContext);
	public PageContext getPageContext();
}
