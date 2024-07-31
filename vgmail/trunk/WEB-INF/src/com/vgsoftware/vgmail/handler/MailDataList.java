/*
 * Created on: 2003-nov-09
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-nov-09 Created by Viktor
 */
package com.vgsoftware.vgmail.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vgsoftware.vgmail.misc.PartData;

/**
 * <code>MailData</code> collection class.
 */
public class MailDataList extends ArrayList
{
	/**
	 * Gets a new list with all inline attachments in.
	 * 
	 * @return List with all inline attachment <code>MailData</code> objects in.
	 */
	public List getInline()
	{
		return(getList(true));
	}

	/**
	 * Gets a new list with all none inline attachments in.
	 * 
	 * @return List with all none inline attachment <code>MailData</code> objects in.
	 */	
	public List getAttachment()
	{
		return(getList(false));
	}
	
	/**
	 * Gets a list with all inline or not inline <code>MailData</code> objects in.
	 * 
	 * @param inline True to get a list of inline object, otherwise false.
	 * 
	 * @return List with <code>MailData</code> objects.
	 */
	private List getList(boolean inline)
	{
		List list=new ArrayList();
		Iterator iter=iterator();
		while(iter.hasNext())
		{
			PartData md=(PartData)iter.next();
			if(md.isInline()==inline)
				list.add(md);
		}
		return(list);
	}
}
