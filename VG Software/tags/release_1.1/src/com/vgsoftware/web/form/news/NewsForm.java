package com.vgsoftware.web.form.news;

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
 * 2004-dec-18 - Class created.
 */
public class NewsForm extends ActionForm
{
	private String title=null;
	private String content=null;
	private boolean comment=false;

	/**
	 * @return Returns the content.
	 */
	public String getContent()
	{
		return(content);
	}

	/**
	 * @param content The content to set.
	 */
	public void setContent(String content)
	{
		this.content=content;
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
		this.title=title;
	}

	/**
	 * @return Returns the comment.
	 */
	public boolean getComment()
	{
		return(comment);
	}

	/**
	 * @param comment The comment to set.
	 */
	public void setComment(boolean comment)
	{
		this.comment=comment;
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		ActionErrors errors=new ActionErrors();
		if((title==null)||(title.trim().equals("")))
			errors.add("no_title",new ActionMessage("error.admin.notitle"));
		if((content==null)||(content.trim().equals("")))
			errors.add("no_content",new ActionMessage("error.admin.nocontent"));
		request.setAttribute("title",getTitle());
		request.setAttribute("content",getContent());
		return(errors);
	}
}
