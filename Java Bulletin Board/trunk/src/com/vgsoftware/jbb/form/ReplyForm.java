package com.vgsoftware.jbb.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class ReplyForm extends ActionForm
{
	private int topicId=-1;
	private int forumId=-1;
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
		this.content=content.trim();
	}

	/**
	 * Gets the topicId.
	 * @return Returns the topicId.
	 */
	public int getTopicId()
	{
		return (topicId);
	}

	/**
	 * Sets the topicId.
	 * @param topicId The topicId to set.
	 */
	public void setTopicId(int topicId)
	{
		this.topicId=topicId;
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
	 * Validate the values of this form.
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors=new ActionErrors();
		if((content==null)||(content.equals("")))
			errors.add("nocontent_error",new ActionMessage("errors.post.nocontent"));
		else if((topicId==-1)||(forumId==-1))
			errors.add("notopic_error", new ActionMessage("errors.post.notopic"));
		return(errors);
	}
}
