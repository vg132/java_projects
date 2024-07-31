package com.vgsoftware.shop.data;

import java.sql.Timestamp;

public class WishListData
{
	private int id=-1;
	private int customerId=-1;
	private int itemId=-1;
	private String name=null;
	private double price=-1;
	private Timestamp release=null;

	public WishListData()
	{
	}

	public int getCustomerId()
	{
		return (customerId);
	}

	public void setCustomerId(int customerId)
	{
		this.customerId=customerId;
	}

	public int getId()
	{
		return (id);
	}

	public void setId(int id)
	{
		this.id=id;
	}

	public int getItemId()
	{
		return (itemId);
	}

	public void setItemId(int itemId)
	{
		this.itemId=itemId;
	}

	public String getName()
	{
		return (name);
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public double getPrice()
	{
		return (price);
	}

	public void setPrice(double price)
	{
		this.price=price;
	}

	public Timestamp getRelease()
	{
		return (release);
	}

	public void setRelease(Timestamp release)
	{
		this.release=release;
	}
}
