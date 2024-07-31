package com.vgsoftware.web.data.news;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author Viktor
 * @version 1.0
 * 
 * History:
 * 2005-sep-27 - Updated for use with model classes.
 * 2004-dec-18 - Class created.
 */
public class CommentData
{
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private int id=-1;
	private int memberId=-1;
	private int newsId=-1;
	private String memberName=null;
	private String comment=null;
	private Timestamp postDate=null;
	private Timestamp editDate=null;

	public CommentData()
	{
	}
	
	/**
	 * @return Returns the comment.
	 */
	public String getComment()
	{
		return(comment);
	}

	/**
	 * @param comment The comment to set.
	 */
	public void setComment(String comment)
	{
		this.comment=comment;
	}

	/**
	 * @return Returns the commentId.
	 */
	public int getId()
	{
		return(id);
	}

	/**
	 * @param commentId The commentId to set.
	 */
	public void setId(int id)
	{
		this.id=id;
	}

	/**
	 * @return Returns the editDate.
	 */
	public Timestamp getEditDate()
	{
		return(editDate);
	}

	/**
	 * @param editDate The editDate to set.
	 */
	public void setEditDate(Timestamp editDate)
	{
		this.editDate=editDate;
	}

	public String getNiceEditDate()
	{
		return(sdf.format(editDate));
	}
	
	/**
	 * @return Returns the postDate.
	 */
	public Timestamp getPostDate()
	{
		return(postDate);
	}

	/**
	 * @param postDate The postDate to set.
	 */
	public void setPostDate(Timestamp postDate)
	{
		this.postDate=postDate;
	}

	public String getNicePostDate()
	{
		return(sdf.format(postDate));
	}
	
	/**
	 * @return Returns the userId.
	 */
	public int getMemberId()
	{
		return(memberId);
	}

	/**
	 * @param userId The userId to set.
	 */
	public void setMemberId(int memberId)
	{
		this.memberId=memberId;
	}

	/**
	 * @return Returns the username.
	 */
	public String getMemberName()
	{
		return(memberName);
	}

	/**
	 * @param username The username to set.
	 */
	public void setMemberName(String name)
	{
		this.memberName=name;
	}

	/**
	 * @return Returns the newsId.
	 */
	public int getNewsId()
	{
		return newsId;
	}

	/**
	 * @param newsId The newsId to set.
	 */
	public void setNewsId(int newsId)
	{
		this.newsId=newsId;
	}
}
