package com.vgsoftware.jbb.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class TopicForm extends ActionForm
{
	private int forumId=-1;
	private String title=null;
	private String content=null;

	/**
	 * Gets the content.
	 * @return Returns the content.
	 */
	public String getContent()
	{
		return (content);
	}

	/**
	 * Sets the content.
	 * @param content The content to set.
	 */
	public void setContent(String content)
	{
		this.content=content;
	}

	/**
	 * Gets the forumId.
	 * @return Returns the forumId.
	 */
	public int getForumId()
	{
		return (forumId);
	}

	/**
	 * Sets the forumId.
	 * @param forumId The forumId to set.
	 */
	public void setForumId(int forumId)
	{
		this.forumId=forumId;
	}

	/**
	 * Gets the title.
	 * @return Returns the title.
	 */
	public String getTitle()
	{
		return (title);
	}

	/**
	 * Sets the title.
	 * @param title The title to set.
	 */
	public void setTitle(String title)
	{
		this.title=title;
	}

	/**
	 * Validate the values of this form.
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors=new ActionErrors();
		if((content==null)||(content.equals("")))
			errors.add("nocontent_error",new ActionMessage("errors.post.nocontent"));
		else if(forumId==-1)
			errors.add("noforum_error", new ActionMessage("errors.post.noforum"));
		return(errors);
	}
}
