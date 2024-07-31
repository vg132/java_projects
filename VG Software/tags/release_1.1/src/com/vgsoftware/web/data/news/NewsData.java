package com.vgsoftware.web.data.news;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author Viktor
 * @version 1.0
 * 
 * History:
 * 2004-dec-18 - Class created.
 */
public class NewsData
{
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private int id=-1;
	private int authorId=-1;
	private int comments=-1;
	private String title=null;
	private String content=null;
	private String authorName=null;
	private boolean comment=false;
	private Timestamp date=null;

	public NewsData()
	{
	}
	
	/**
	 * @return Returns the authorId.
	 */
	public int getAuthorId()
	{
		return(authorId);
	}

	/**
	 * @param authorId The authorId to set.
	 */
	public void setAuthorId(int authorId)
	{
		this.authorId=authorId;
	}

	/**
	 * @return Returns the comment.
	 */
	public boolean canComment()
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

	/**
	 * @return Returns the comments.
	 */
	public int getComments()
	{
		return(comments);
	}

	/**
	 * @param comments The comments to set.
	 */
	public void setComments(int comments)
	{
		this.comments=comments;
	}

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
	 * @return Returns the newsDate.
	 */
	public Timestamp getDate()
	{
		return(date);
	}

	/**
	 * @param newsDate The newsDate to set.
	 */
	public void setDate(Timestamp date)
	{
		this.date=date;
	}

	public String getNiceDate()
	{
		return(sdf.format(date));
	}
	
	/**
	 * @return Returns the newsId.
	 */
	public int getId()
	{
		return(id);
	}

	/**
	 * @param newsId The newsId to set.
	 */
	public void setId(int id)
	{
		this.id=id;
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
	 * @return Returns the author.
	 */
	public String getAuthorName()
	{
		return(authorName);
	}

	/**
	 * @param author The author to set.
	 */
	public void setAuthorName(String authorName)
	{
		this.authorName=authorName;
	}
}
