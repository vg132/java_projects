package com.vgsoftware.shop.data;

import java.sql.Timestamp;
import java.util.List;

public class InvoiceData
{
	private int id=-1;
	private int customerId=-1;
	private double totalPrice=-1;
	private Timestamp orderDate=null;
	private String customerName=null;
	private List<InvoiceItemData> items=null;
	
	public InvoiceData()
	{
	}
	
	public InvoiceData(int id, int customerId, Timestamp orderDate, double totalPrice, String customerName)
	{
		this.id=id;
		this.customerId=customerId;
		this.orderDate=orderDate;
		this.totalPrice=totalPrice;
		this.customerName=customerName;
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

	public List<InvoiceItemData> getItems()
	{
		return (items);
	}

	public void setItems(List<InvoiceItemData> items)
	{
		this.items=items;
	}

	public Timestamp getOrderDate()
	{
		return (orderDate);
	}

	public void setOrderDate(Timestamp orderDate)
	{
		this.orderDate=orderDate;
	}

	public double getTotalPrice()
	{
		return (totalPrice);
	}

	public void setTotalPrice(double totalPrice)
	{
		this.totalPrice=totalPrice;
	}

	public String getCustomerName()
	{
		return (customerName);
	}

	public void setCustomerName(String customerName)
	{
		this.customerName=customerName;
	}
}
