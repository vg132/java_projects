package com.vgsoftware.shop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.shop.data.InvoiceData;

public class InvoiceModel
{
	public static boolean addInvoice(DataSource dataSource, String idTable,InvoiceData invoiceData)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		try
		{
			int id=DBCounter.getId(dataSource,idTable);
			if(id!=-1)
			{
				invoiceData.setId(id);
				conn=dataSource.getConnection();
				ps=conn.prepareStatement("INSERT INTO invoice(id, customer_id, order_date, total_price) VALUES(?,?,?,?)");
				ps.setInt(1,invoiceData.getId());
				ps.setInt(2,invoiceData.getCustomerId());
				ps.setTimestamp(3,invoiceData.getOrderDate());
				ps.setDouble(4,invoiceData.getTotalPrice());
				if(ps.executeUpdate()==1)
					return(true);
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
	
	public static InvoiceData getInvoice(DataSource dataSource, int invoiceId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT i.id, i.customer_id, i.order_date, i.total_price, c.name FROM invoice i, customer c WHERE c.id=i.customer_id AND i.id=?");
			ps.setInt(1,invoiceId);
			rs=ps.executeQuery();
			if(rs.next())
				return(new InvoiceData(rs.getInt("id"),rs.getInt("customer_id"),rs.getTimestamp("order_date"),rs.getDouble("total_price"),rs.getString("name")));
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
	
	public static List<InvoiceData> getInvoices(DataSource dataSource, int customerId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<InvoiceData> invoiceData=new ArrayList<InvoiceData>();
		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT i.id, i.customer_id, i.order_date, i.total_price, c.name FROM invoice i, customer c WHERE i.customer_id=c.id AND i.customer_id=? ORDER BY i.order_date DESC");
			ps.setInt(1,customerId);
			rs=ps.executeQuery();
			while(rs.next())
				invoiceData.add(new InvoiceData(rs.getInt("id"),rs.getInt("customer_id"),rs.getTimestamp("order_date"),rs.getDouble("total_price"),rs.getString("name")));
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
		return(invoiceData);
	}
}
