package com.vgsoftware.jbb.data;


import java.sql.Timestamp;

/**
 * Holds information about a topic. Mapps agains the <code>topics</code> table
 * in the database.
 */
public class TopicData
{
	private int id=-1;
	private int forumId=-1;
	private int replies=-1;
	private int memberId=-1;
	private int categoryId=-1;
	private String title=null;
	private String forumName=null;
	private String memberName=null;
	private String categoryName=null;
	private Timestamp postDate=null;

	public TopicData()
	{
	}

	/**
	 * Gets the forumId.
	 * 
	 * @return Returns the forumId.
	 */
	public int getForumId()
	{
		return (forumId);
	}

	/**
	 * Sets the forumId.
	 * 
	 * @param forumId
	 *          The forumId to set.
	 */
	public void setForumId(int forumId)
	{
		this.forumId=forumId;
	}

	/**
	 * Gets the forumName.
	 * 
	 * @return Returns the forumName.
	 */
	public String getForumName()
	{
		return (forumName);
	}

	/**
	 * Sets the forumName.
	 * 
	 * @param forumName
	 *          The forumName to set.
	 */
	public void setForumName(String forumName)
	{
		this.forumName=forumName;
	}

	/**
	 * Gets the id.
	 * 
	 * @return Returns the id.
	 */
	public int getId()
	{
		return (id);
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *          The id to set.
	 */
	public void setId(int id)
	{
		this.id=id;
	}

	/**
	 * Gets the memberId.
	 * 
	 * @return Returns the memberId.
	 */
	public int getMemberId()
	{
		return (memberId);
	}

	/**
	 * Sets the memberId.
	 * 
	 * @param memberId
	 *          The memberId to set.
	 */
	public void setMemberId(int memberId)
	{
		this.memberId=memberId;
	}

	/**
	 * Gets the memberName.
	 * 
	 * @return Returns the memberName.
	 */
	public String getMemberName()
	{
		return (memberName);
	}

	/**
	 * Sets the memberName.
	 * 
	 * @param memberName
	 *          The memberName to set.
	 */
	public void setMemberName(String memberName)
	{
		this.memberName=memberName;
	}

	/**
	 * Gets the postDate.
	 * 
	 * @return Returns the postDate.
	 */
	public Timestamp getPostDate()
	{
		return (postDate);
	}

	/**
	 * Sets the postDate.
	 * 
	 * @param postDate
	 *          The postDate to set.
	 */
	public void setPostDate(Timestamp postDate)
	{
		this.postDate=postDate;
	}

	/**
	 * Gets the replies.
	 * 
	 * @return Returns the replies.
	 */
	public int getReplies()
	{
		return (replies);
	}

	/**
	 * Sets the replies.
	 * 
	 * @param replies
	 *          The replies to set.
	 */
	public void setReplies(int replies)
	{
		this.replies=replies;
	}

	/**
	 * Gets the title.
	 * 
	 * @return Returns the title.
	 */
	public String getTitle()
	{
		return (title);
	}

	/**
	 * Sets the title.
	 * 
	 * @param title
	 *          The title to set.
	 */
	public void setTitle(String title)
	{
		this.title=title;
	}

	/**
	 * Gets the categoryName.
	 * @return Returns the categoryName.
	 */
	public String getCategoryName()
	{
		return (categoryName);
	}

	/**
	 * Sets the categoryName.
	 * @param categoryName The categoryName to set.
	 */
	public void setCategoryName(String categoryName)
	{
		this.categoryName=categoryName;
	}

	/**
	 * Gets the categoryId.
	 * @return Returns the categoryId.
	 */
	public int getCategoryId()
	{
		return (categoryId);
	}

	/**
	 * Sets the categoryId.
	 * @param categoryId The categoryId to set.
	 */
	public void setCategoryId(int categoryId)
	{
		this.categoryId=categoryId;
	}

}
