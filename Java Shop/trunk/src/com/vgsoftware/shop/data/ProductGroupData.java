package com.vgsoftware.shop.data;

import java.util.List;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-maj-03 - Document created.
 */
public class ProductGroupData
{
	private int id=-1;
	private int categoryGroupId=-1;
	private int picWidth=-1;
	private int picHeight=-1;
	private int picWidthSmall=-1;
	private int picHeightSmall=-1;
	private String name=null;
	private String categoryGroupName=null;
	private List<ItemData> items=null;
	
	public ProductGroupData()
	{
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
	/**
	 * @return Returns the picHeight.
	 */
	public int getPicHeight()
	{
		return picHeight;
	}
	/**
	 * @param picHeight The picHeight to set.
	 */
	public void setPicHeight(int picHeight)
	{
		this.picHeight=picHeight;
	}
	/**
	 * @return Returns the picSmallHeight.
	 */
	public int getPicHeightSmall()
	{
		return picHeightSmall;
	}
	/**
	 * @param picSmallHeight The picSmallHeight to set.
	 */
	public void setPicHeightSmall(int picHeightSmall)
	{
		this.picHeightSmall=picHeightSmall;
	}
	/**
	 * @return Returns the picSmallWidth.
	 */
	public int getPicWidthSmall()
	{
		return picWidthSmall;
	}
	/**
	 * @param picSmallWidth The picSmallWidth to set.
	 */
	public void setPicWidthSmall(int picWidthSmall)
	{
		this.picWidthSmall=picWidthSmall;
	}
	/**
	 * @return Returns the picWidth.
	 */
	public int getPicWidth()
	{
		return picWidth;
	}
	/**
	 * @param picWidth The picWidth to set.
	 */
	public void setPicWidth(int picWidth)
	{
		this.picWidth=picWidth;
	}
	public List<ItemData> getItems()
	{
		return (items);
	}
	public void setItems(List<ItemData> items)
	{
		this.items=items;
	}
}
