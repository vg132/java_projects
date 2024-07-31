package com.vgsoftware.shop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.shop.data.CartItemData;
import com.vgsoftware.shop.data.InvoiceItemData;

public class InvoiceItemModel
{
	public static boolean addInvoiceItems(DataSource dataSource, String idTable, int invoiceId, Collection<CartItemData> items)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		try
		{
			int startId=DBCounter.getId(dataSource,idTable,items.size());
			if(startId!=-1)
			{
				conn=dataSource.getConnection();
				ps=conn.prepareStatement("INSERT INTO invoice_item(id,invoice_id,item_id,quantity,price) VALUES(?,?,?,?,?)");
				for(CartItemData cid : items)
				{
					ps.setInt(1,startId++);
					ps.setInt(2,invoiceId);
					ps.setInt(3,cid.getId());
					ps.setInt(4,cid.getQuantity());
					ps.setDouble(5,cid.getPrice());
					ps.addBatch();
				}
				int[] retVal=ps.executeBatch();
				boolean ok=true;
				for(int i : retVal)
				{
					if(i!=1)
					{
						ok=false;
						break;
					}
				}
				return(ok);
			}
		}
		finally
		{
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		}
		return(false);
	}
	
	public static InvoiceItemData getInvoiceItem(DataSource dataSource, int invoiceItemId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT ii.id, ii.invoice_id, ii.item_id, ii.quantity, ii.price, i.name FROM invoice_item ii, item i WHERE i.id=ii.item_id AND ii.id=?");
			ps.setInt(1,invoiceItemId);
			rs=ps.executeQuery();
			if(rs.next())
				return(new InvoiceItemData(rs.getInt("id"),rs.getInt("invoice_id"),rs.getInt("item_id"),rs.getInt("quantity"),rs.getDouble("price"),rs.getString("name")));
		}
		finally
		{
			if(rs!=null)
				rs.close();
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		}
		return(null);
	}

	public static List<InvoiceItemData> getInvoiceItems(DataSource dataSource, int invoiceId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<InvoiceItemData> invoiceItems=new ArrayList<InvoiceItemData>();
		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT ii.id, ii.invoice_id, ii.item_id, ii.quantity, ii.price, i.name FROM invoice_item ii, item i WHERE ii.item_id=i.id AND ii.invoice_id=?");
			ps.setInt(1,invoiceId);
			rs=ps.executeQuery();
			while(rs.next())
				invoiceItems.add(new InvoiceItemData(rs.getInt("id"),rs.getInt("invoice_id"),rs.getInt("item_id"),rs.getInt("quantity"),rs.getDouble("price"),rs.getString("name")));
		}
		finally
		{
			if(rs!=null)
				rs.close();
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		}
		return(invoiceItems);
	}

}
