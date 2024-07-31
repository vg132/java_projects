package com.vgsoftware.web.data.pm;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author Viktor
 * @version 1.0
 * 
 * History:
 * 2004-dec-19 - Class created.
 */
public class MessageData
{
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private int messageId=-1;
	private int senderId=-1;
	private String senderName=null;
	private String title=null;
	private String content=null;
	private Timestamp postDate=null;

	public MessageData()
	{
	}

	public MessageData(int messageId, int senderId, String senderName, String title, String content, Timestamp postDate)
	{
		this.messageId=messageId;
		this.senderId=senderId;
		this.senderName=senderName;
		this.title=title;
		this.content=content;
		this.postDate=postDate;
	}

	/**
	 * @return Returns the messageId.
	 */
	public int getMessageId()
	{
		return(messageId);
	}

	/**
	 * @param messageId The messageId to set.
	 */
	public void setMessageId(int messageId)
	{
		this.messageId=messageId;
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
	 * @return Returns the postDate.
	 */
	public String getPostDate()
	{
		return(sdf.format(postDate));
	}

	/**
	 * @param postDate The postDate to set.
	 */
	public void setPostDate(Timestamp postDate)
	{
		this.postDate=postDate;
	}

	/**
	 * @return Returns the senderId.
	 */
	public int getSenderId()
	{
		return(senderId);
	}

	/**
	 * @param senderId The senderId to set.
	 */
	public void setSenderId(int senderId)
	{
		this.senderId=senderId;
	}

	/**
	 * @return Returns the senderName.
	 */
	public String getSenderName()
	{
		return(senderName);
	}

	/**
	 * @param senderName The senderName to set.
	 */
	public void setSenderName(String senderName)
	{
		this.senderName=senderName;
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
}
