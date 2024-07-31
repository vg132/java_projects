package com.vgsoftware.web.form.news;

import org.apache.struts.action.ActionForm;

/**
 * @author Viktor
 * @version 1.0
 * 
 * History:
 * 2005-sep-28 - Removed whattodo.
 * 2004-dec-18 - Class created.
 */
public class CommentForm extends ActionForm
{
	private int newsId=-1;
	private int commentId=-1;
	private String comment=null;

	public String getComment()
	{
		return(comment);
	}

	public void setComment(String comment)
	{
		this.comment=comment;
	}

	/**
	 * @return Returns the newsId.
	 */
	public int getNewsId()
	{
		return(newsId);
	}

	/**
	 * @param newsId The newsId to set.
	 */
	public void setNewsId(int newsId)
	{
		this.newsId=newsId;
	}

	/**
	 * @return Returns the commentId.
	 */
	public int getCommentId()
	{
		return(commentId);
	}

	/**
	 * @param commentId The commentId to set.
	 */
	public void setCommentId(int commentId)
	{
		this.commentId=commentId;
	}
}
