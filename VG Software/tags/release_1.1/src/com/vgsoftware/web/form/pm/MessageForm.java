package com.vgsoftware.web.form.pm;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * @author Viktor
 * @version 1.0
 * 
 * History:
 * 2004-dec-19 - Class created.
 */
public class MessageForm extends ActionForm
{
	private String recipient=null;
	private String title=null;
	private String message=null;

	/**
	 * @return Returns the content.
	 */
	public String getMessage()
	{
		return(message);
	}

	/**
	 * @param content The content to set.
	 */
	public void setMessage(String message)
	{
		this.message=message.trim();
	}

	/**
	 * @return Returns the reciverName.
	 */
	public String getRecipient()
	{
		return(recipient);
	}

	/**
	 * @param reciverName The reciverName to set.
	 */
	public void setRecipient(String recipient)
	{
		this.recipient=recipient.trim();
	}

	/**
	 * @return Returns the title.
	 */
	public String getTitle()
	{
		return(title);
	}

	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title)
	{
		this.title=title.trim();
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors=new ActionErrors();
		if((recipient==null)||(recipient.equals("")))
			errors.add("norecipient",new ActionMessage("error.pm.norecipient"));
		if((title==null)||(title.equals("")))
			errors.add("notitle",new ActionMessage("error.pm.notitle"));
		if((message==null)||(message.equals("")))
			errors.add("nomessage",new ActionMessage("error.pm.nomessage"));
		request.setAttribute("message",getMessage());
		request.setAttribute("recipient",getRecipient());
		request.setAttribute("title",getTitle());
		return(errors);
	}
}
