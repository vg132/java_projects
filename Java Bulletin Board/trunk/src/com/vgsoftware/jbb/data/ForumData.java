package com.vgsoftware.jbb.data;


/**
 * Holds information about a forum. Mapps agains the <code>forums</code> table in the database.
 */
public class ForumData
{
	private int id=-1;
	private int order=-1;
	private int topics=-1;
	private int replies=-1;
	private int categoryId=-1;
	private String name=null;
	private String description=null;
	private String categoryName=null;

	public ForumData()
	{
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
	 * Gets the description.
	 * @return Returns the description.
	 */
	public String getDescription()
	{
		return (description);
	}

	/**
	 * Sets the description.
	 * @param description The description to set.
	 */
	public void setDescription(String description)
	{
		this.description=description;
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
	 * Gets the name.
	 * @return Returns the name.
	 */
	public String getName()
	{
		return (name);
	}

	/**
	 * Sets the name.
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name=name;
	}

	/**
	 * Gets the order.
	 * @return Returns the order.
	 */
	public int getOrder()
	{
		return (order);
	}

	/**
	 * Sets the order.
	 * @param order The order to set.
	 */
	public void setOrder(int order)
	{
		this.order=order;
	}

	/**
	 * Gets the replies.
	 * @return Returns the replies.
	 */
	public int getReplies()
	{
		return (replies);
	}

	/**
	 * Sets the replies.
	 * @param replies The replies to set.
	 */
	public void setReplies(int replies)
	{
		this.replies=replies;
	}

	/**
	 * Gets the topics.
	 * @return Returns the topics.
	 */
	public int getTopics()
	{
		return (topics);
	}

	/**
	 * Sets the topics.
	 * @param topics The topics to set.
	 */
	public void setTopics(int topics)
	{
		this.topics=topics;
	}
}
