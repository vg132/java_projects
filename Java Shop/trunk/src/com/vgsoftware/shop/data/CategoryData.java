package com.vgsoftware.shop.data;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-maj-03 - Document created.
 */
public class CategoryData
{
	private int id=-1;
	private int categoryGroupId=-1;
	private String name=null;
	private String categoryGroupName=null;

	public CategoryData(int id, int categoryGroupId, String name, String categoryGroupName)
	{
		this.id=id;
		this.categoryGroupId=categoryGroupId;
		this.name=name;
		this.categoryGroupName=categoryGroupName;
	}

	/**
	 * @return Returns the categoryGroupId.
	 */
	public int getCategoryGroupId()
	{
		return categoryGroupId;
	}
	/**
	 * @param categoryGroupId The categoryGroupId to set.
	 */
	public void setCategoryGroupId(int categoryGroupId)
	{
		this.categoryGroupId=categoryGroupId;
	}
	/**
	 * @return Returns the categoryGroupName.
	 */
	public String getCategoryGroupName()
	{
		return categoryGroupName;
	}
	/**
	 * @param categoryGroupName The categoryGroupName to set.
	 */
	public void setCategoryGroupName(String categoryGroupName)
	{
		this.categoryGroupName=categoryGroupName;
	}
	/**
	 * @return Returns the id.
	 */
	public int getId()
	{
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id)
	{
		this.id=id;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name=name;
	}
}
