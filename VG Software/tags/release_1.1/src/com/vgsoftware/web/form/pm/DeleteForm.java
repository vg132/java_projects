package com.vgsoftware.web.form.pm;

import org.apache.struts.action.ActionForm;

/**
 * @author Viktor
 * @version 1.0
 * 
 * History:
 * 2004-dec-28 - Class created.
 */
public class DeleteForm extends ActionForm
{
	private String[] messageIds={};

	public String[] getMessageIds()
	{ 
	  return(this.messageIds); 
	}

	public void setMessageIds(String[] messageIds)
	{ 
	  this.messageIds=messageIds; 
	}
}
