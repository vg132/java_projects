package com.vgsoftware.shop.data;

import java.sql.Timestamp;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-maj-03 - Document created.
 */
public class ItemData
{
	private int id=-1;
	private int categoryId=-1;
	private int productGroupId=-1;
	private int regionId=-1;
	private double rrp=-1;
	private double price=-1;
	private String name=null;
	private String description=null;
	private String picture=null;
	private String smallPicture=null;
	private String regionName=null;
	private String categoryName=null;
	private String productGroupName=null;
	private Timestamp release=null;
	
	public ItemData()
	{
	}

	/**
	 * @return Returns the categoryId.
	 */
	public int getCategoryId()
	{
		return categoryId;
	}
	/**
	 * @param categoryId The categoryId to set.
	 */
	public void setCategoryId(int categoryId)
	{
		this.categoryId=categoryId;
	}
	/**
	 * @return Returns the categoryName.
	 */
	public String getCategoryName()
	{
		return categoryName;
	}
	/**
	 * @param categoryName The categoryName to set.
	 */
	public void setCategoryName(String categoryName)
	{
		this.categoryName=categoryName;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription()
	{
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description)
	{
		this.description=description;
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
	 * @return Returns the picture.
	 */
	public String getPicture()
	{
		return picture;
	}
	/**
	 * @param picture The picture to set.
	 */
	public void setPicture(String picture)
	{
		this.picture=picture;
	}
	/**
	 * @return Returns the price.
	 */
	public double getPrice()
	{
		return price;
	}
	/**
	 * @param price The price to set.
	 */
	public void setPrice(double price)
	{
		this.price=price;
	}
	/**
	 * @return Returns the productGroupId.
	 */
	public int getProductGroupId()
	{
		return productGroupId;
	}
	/**
	 * @param productGroupId The productGroupId to set.
	 */
	public void setProductGroupId(int productGroupId)
	{
		this.productGroupId=productGroupId;
	}
	/**
	 * @return Returns the productGroupName.
	 */
	public String getProductGroupName()
	{
		return productGroupName;
	}
	/**
	 * @param productGroupName The productGroupName to set.
	 */
	public void setProductGroupName(String productGroupName)
	{
		this.productGroupName=productGroupName;
	}
	/**
	 * @return Returns the regionId.
	 */
	public int getRegionId()
	{
		return regionId;
	}
	/**
	 * @param regionId The regionId to set.
	 */
	public void setRegionId(int regionId)
	{
		this.regionId=regionId;
	}
	/**
	 * @return Returns the regionName.
	 */
	public String getRegionName()
	{
		return regionName;
	}
	/**
	 * @param regionName The regionName to set.
	 */
	public void setRegionName(String regionName)
	{
		this.regionName=regionName;
	}
	/**
	 * @return Returns the release.
	 */
	public String getRelease()
	{
		return(release.toString().substring(0,10));
	}
	/**
	 * @param release The release to set.
	 */
	public void setRelease(Timestamp release)
	{
		this.release=release;
	}
	/**
	 * @return Returns the rrp.
	 */
	public double getRrp()
	{
		return rrp;
	}
	/**
	 * @param rrp The rrp to set.
	 */
	public void setRrp(double rrp)
	{
		this.rrp=rrp;
	}
	/**
	 * @return Returns the smallPicture.
	 */
	public String getSmallPicture()
	{
		return smallPicture;
	}
	/**
	 * @param smallPicture The smallPicture to set.
	 */
	public void setSmallPicture(String smallPicture)
	{
		this.smallPicture=smallPicture;
	}
}
