package com.vgsoftware.shop.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CartData
{
	private Map<Integer,CartItemData> items=new HashMap<Integer,CartItemData>();
	private double totalPrice=-1;

	public CartData()
	{
	}

	public void addItem(int id, double price, String name)
	{
		if(items.containsKey(id))
			items.get(id).addItem();
		else
			items.put(id,new CartItemData(id,1,price,name));
		calculateTotalPrice();
	}

	public void updateQuantity(int id, int quantity)
	{
		if((items.containsKey(id))&&(quantity>0))
			items.get(id).setQuantity(quantity);
		else if(quantity<=0)
			items.remove(id);
		calculateTotalPrice();
	}
	
	public Collection<CartItemData> getItems()
	{
		return(items.values());
	}
	
	private void calculateTotalPrice()
	{
		CartItemData cid=null;
		totalPrice=0;
		Iterator<CartItemData> iter=items.values().iterator();
		while(iter.hasNext())
		{
			cid=iter.next();
			totalPrice+=(cid.getPrice()*cid.getQuantity());
		}
	}
	
	public double getTotalPrice()
	{
		return(totalPrice);
	}
}
