package com.vgsoftware.shop.data;

public class CartItemData
{
	private int id=-1;
	private int quantity=-1;
	private double price=-1;
	private String name=null;
	
	public CartItemData(int id, int quantity, double price, String name)
	{
		this.id=id;
		this.quantity=quantity;
		this.price=price;
		this.name=name;
	}

	public void addItem()
	{
		quantity++;
	}
	
	public int getId()
	{
		return (id);
	}

	public void setId(int id)
	{
		this.id=id;
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

	public int getQuantity()
	{
		return (quantity);
	}

	public void setQuantity(int quantity)
	{
		this.quantity=quantity;
	}
}
