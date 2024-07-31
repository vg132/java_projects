package com.vgsoftware.shop.data;

public class InvoiceItemData
{
	private int id=-1;
	private int invoiceId=-1;
	private int itemId=-1;
	private int quantity=-1;
	private String name=null;
	private double price=-1;
	
	public InvoiceItemData(int id, int invoiceId, int itemId, int quantity, double price, String name)
	{
		this.id=id;
		this.invoiceId=invoiceId;
		this.itemId=itemId;
		this.quantity=quantity;
		this.price=price;
		this.name=name;
	}

	public int getId()
	{
		return (id);
	}

	public void setId(int id)
	{
		this.id=id;
	}

	public int getInvoiceId()
	{
		return (invoiceId);
	}

	public void setInvoiceId(int invoiceId)
	{
		this.invoiceId=invoiceId;
	}

	public int getItemId()
	{
		return (itemId);
	}

	public void setItemId(int itemId)
	{
		this.itemId=itemId;
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

	public String getName()
	{
		return (name);
	}

	public void setName(String name)
	{
		this.name=name;
	}

}
