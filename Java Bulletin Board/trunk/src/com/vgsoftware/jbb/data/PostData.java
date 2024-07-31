package com.vgsoftware.jbb.data;


import java.sql.Timestamp;

/**
 * Holds information about a post. Mapps agains the <code>posts</code> table in the database.
 */
public class PostData
{
	private int id=-1;
	private int topicId=-1;
	private int memberId=-1;
	private String content=null;
	private String memberName=null;
	private String topicTitle=null;
	private Timestamp postDate=null;
	private Timestamp editDate=null;
	
	public PostData()
	{
	}
	
	public String getHtmlContent()
	{
		return(content.replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br/>"));
	}
	
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
	 * Gets the id.
	 * @return Returns the id.
	 */
	public int getId()
	{
		return (id);
	}
	/**
	 * Sets the id.
	 * @param id The id to set.
	 */
	public void setId(int id)
	{
		this.id=id;
	}
	/**
	 * Gets the memberId.
	 * @return Returns the memberId.
	 */
	public int getMemberId()
	{
		return(memberId);
	}
	/**
	 * Sets the memberId.
	 * @param memberId The memberId to set.
	 */
	public void setMemberId(int memberId)
	{
		this.memberId=memberId;
	}
	/**
	 * Gets the memberName.
	 * @return Returns the memberName.
	 */
	public String getMemberName()
	{
		return(memberName);
	}
	/**
	 * Sets the memberName.
	 * @param memberName The memberName to set.
	 */
	public void setMemberName(String memberName)
	{
		this.memberName=memberName;
	}
	/**
	 * Gets the postDate.
	 * @return Returns the postDate.
	 */
	public Timestamp getPostDate()
	{
		return(postDate);
	}

	/**
	 * Gets the postDate.
	 * @return Returns the postDate.
	 */
	public String getNicePostDate()
	{
		return(postDate.toString().substring(0,19));
	}
	
	/**
	 * Sets the postDate.
	 * @param postDate The postDate to set.
	 */
	public void setPostDate(Timestamp postDate)
	{
		this.postDate=postDate;
	}
	/**
	 * Gets the topicId.
	 * @return Returns the topicId.
	 */
	public int getTopicId()
	{
		return(topicId);
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
	 * Gets the topicTitle.
	 * @return Returns the topicTitle.
	 */
	public String getTopicTitle()
	{
		return(topicTitle);
	}
	/**
	 * Sets the topicTitle.
	 * @param topicTitle The topicTitle to set.
	 */
	public void setTopicTitle(String topicTitle)
	{
		this.topicTitle=topicTitle;
	}

	/**
	 * Gets the editDate.
	 * @return Returns the editDate.
	 */
	public Timestamp getEditDate()
	{
		return(editDate);
	}
	
	public String getNiceEditDate()
	{
		return(editDate.toString().substring(0,19));
	}

	/**
	 * Sets the editDate.
	 * @param editDate The editDate to set.
	 */
	public void setEditDate(Timestamp editDate)
	{
		this.editDate=editDate;
	}
}
