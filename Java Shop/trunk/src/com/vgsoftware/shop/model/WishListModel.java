package com.vgsoftware.shop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.shop.data.WishListData;

public class WishListModel
{
	public static boolean removeItem(DataSource dataSource, int id)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("DELETE FROM wishlist WHERE id=?");
			ps.setInt(1,id);
			if(ps.executeUpdate()==1)
				return(true);
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
	
	public static boolean addItem(DataSource dataSource, String idTable, int customerId, int itemId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		int id=DBCounter.getId(dataSource,idTable);

		if(id!=-1)
		{
			try
			{
				conn=dataSource.getConnection();
				ps=conn.prepareStatement("INSERT INTO wishlist(id,customer_id,item_id) VALUES(?,?,?)");
				ps.setInt(1,id);
				ps.setInt(2,customerId);
				ps.setInt(3,itemId);
				if(ps.executeUpdate()==1)
					return(true);
			}
			finally
			{
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
			}
		}
		return(false);
	}
	
	public static List<WishListData> getWishList(DataSource dataSource, int customerId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		WishListData wld=null;
		List<WishListData> wishList=new ArrayList<WishListData>();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT w.id, w.customer_id, i.name, w.item_id, ip.price, i.release_date FROM wishlist w, item i, item_price ip WHERE w.item_id=i.id AND ip.id=i.id AND w.customer_id=? AND ip.id=(SELECT id FROM item_price WHERE price_date=(SELECT max(price_date) FROM item_price WHERE item_id=i.id GROUP BY item_id))");
			ps.setInt(1,customerId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				wld=new WishListData();
				wld.setId(rs.getInt("id"));
				wld.setCustomerId(rs.getInt("customer_id"));
				wld.setName(rs.getString("name"));
				wld.setItemId(rs.getInt("item_id"));
				wld.setPrice(rs.getDouble("price"));
				wld.setRelease(rs.getTimestamp("release_date"));
				wishList.add(wld);
			}
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
		return(wishList);
	}
}
