package com.vgsoftware.shop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.shop.data.ItemData;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-maj-03 - Document created.
 */
public class ItemModel
{
	public static ItemData getItem(DataSource dataSource, int itemId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ItemData id=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT i.id, c.name as category, pg.name as product, r.name as region, i.rrp, i.name, i.description, i.picture, i.small_picture, i.release_date, ip.price FROM item i, category c, product_group pg, region r, item_price ip WHERE i.category_id=c.id AND i.product_group_id=pg.id AND i.region_id=r.id AND i.id=? AND ip.id=(SELECT id FROM item_price WHERE price_date=(SELECT max(price_date) FROM item_price WHERE item_id=i.id GROUP BY item_id))");
			ps.setInt(1,itemId);
			rs=ps.executeQuery();
			if(rs.next())
			{
				id=new ItemData();
				id.setId(rs.getInt("id"));
				id.setCategoryName(rs.getString("category"));
				id.setProductGroupName(rs.getString("product"));
				id.setRegionName(rs.getString("region"));
				id.setRrp(rs.getDouble("rrp"));
				id.setPrice(rs.getDouble("price"));
				id.setName(rs.getString("name"));
				id.setDescription(rs.getString("description"));
				id.setPicture(rs.getString("picture"));
				id.setSmallPicture(rs.getString("small_picture"));
				id.setRelease(rs.getTimestamp("release_date"));
				return(id);
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
		return(null);
	}

	public static List findItems(DataSource dataSource, String find)
	throws SQLException
	{
		return(findItems(dataSource,find,-1));
	}
	
	public static List findItems(DataSource dataSource, String find, int productGroupId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ItemData id=null;
		List<ItemData> items=new ArrayList<ItemData>();
		try
		{
			conn=dataSource.getConnection();
			if(productGroupId!=-1)
			{
				ps=conn.prepareStatement("SELECT i.id, c.name as category, pg.name as product, r.name as region, i.rrp, i.name, i.description, i.picture, i.small_picture, i.release_date, ip.price FROM item i, category c, product_group pg, region r, item_price ip WHERE i.category_id=c.id AND i.product_group_id=pg.id AND i.region_id=r.id AND (i.name LIKE ? OR i.description LIKE ?) AND i.product_group_id=? AND ip.id=(SELECT id FROM item_price WHERE price_date=(SELECT max(price_date) FROM item_price WHERE item_id=i.id GROUP BY item_id)) ORDER BY c.name");
				ps.setString(1,"%"+find+"%");
				ps.setString(2,"%"+find+"%");
				ps.setInt(3,productGroupId);
			}
			else
			{
				ps=conn.prepareStatement("SELECT i.id, c.name as category, pg.name as product, r.name as region, i.rrp, i.name, i.description, i.picture, i.small_picture, i.release_date, ip.price FROM item i, category c, product_group pg, region r, item_price ip WHERE i.category_id=c.id AND i.product_group_id=pg.id AND i.region_id=r.id AND (i.name LIKE ? OR i.description LIKE ?) AND ip.id=(SELECT id FROM item_price WHERE price_date=(SELECT max(price_date) FROM item_price WHERE item_id=i.id GROUP BY item_id))");
				ps.setString(1,"%"+find+"%");
				ps.setString(2,"%"+find+"%");
			}
			rs=ps.executeQuery();
			while(rs.next())
			{
				id=new ItemData();
				id.setId(rs.getInt("id"));
				id.setCategoryName(rs.getString("category"));
				id.setProductGroupName(rs.getString("product"));
				id.setRegionName(rs.getString("region"));
				id.setRrp(rs.getDouble("rrp"));
				id.setName(rs.getString("name"));
				id.setDescription(rs.getString("description"));
				id.setPicture(rs.getString("picture"));
				id.setSmallPicture(rs.getString("small_picture"));
				id.setRelease(rs.getTimestamp("release_date"));
				id.setPrice(rs.getDouble("price"));
				items.add(id);
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
		return(items);
	}

	public static List getProductGroupItems(DataSource dataSource, int productGroupId) 
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ItemData id=null;
		List<ItemData> items=new ArrayList<ItemData>();
		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT i.id, c.name as category, pg.name as product, r.name as region, i.rrp, i.name, i.description, i.picture, i.small_picture, i.release_date, ip.price FROM item i, category c, product_group pg, region r, item_price ip WHERE i.category_id=c.id AND i.product_group_id=pg.id AND i.region_id=r.id AND i.product_group_id=? AND ip.id=(SELECT id FROM item_price WHERE price_date=(SELECT max(price_date) FROM item_price WHERE item_id=i.id GROUP BY item_id)) ORDER BY i.name");
			ps.setInt(1,productGroupId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				id=new ItemData();
				id.setId(rs.getInt("id"));
				id.setCategoryName(rs.getString("category"));
				id.setProductGroupName(rs.getString("product"));
				id.setRegionName(rs.getString("region"));
				id.setRrp(rs.getDouble("rrp"));
				id.setName(rs.getString("name"));
				id.setDescription(rs.getString("description"));
				id.setPicture(rs.getString("picture"));
				id.setSmallPicture(rs.getString("small_picture"));
				id.setRelease(rs.getTimestamp("release_date"));
				id.setPrice(rs.getDouble("price"));
				items.add(id);
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
		return(items);
	}
	
	
	public static List getCategoryItems(DataSource dataSource, int productGroupId, int categoryId) 
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ItemData id=null;
		List<ItemData> items=new ArrayList<ItemData>();
		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT i.id, c.name as category, pg.name as product, r.name as region, i.rrp, i.name, i.description, i.picture, i.small_picture, i.release_date, ip.price FROM item i, category c, product_group pg, region r, item_price ip WHERE i.category_id=c.id AND i.product_group_id=pg.id AND i.region_id=r.id AND i.product_group_id=? AND i.category_id=? AND ip.id=(SELECT id FROM item_price WHERE price_date=(SELECT max(price_date) FROM item_price WHERE item_id=i.id GROUP BY item_id)) ORDER BY i.name");
			ps.setInt(1,productGroupId);
			ps.setLong(2,categoryId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				id=new ItemData();
				id.setId(rs.getInt("id"));
				id.setCategoryName(rs.getString("category"));
				id.setProductGroupName(rs.getString("product"));
				id.setRegionName(rs.getString("region"));
				id.setRrp(rs.getDouble("rrp"));
				id.setName(rs.getString("name"));
				id.setDescription(rs.getString("description"));
				id.setPicture(rs.getString("picture"));
				id.setSmallPicture(rs.getString("small_picture"));
				id.setRelease(rs.getTimestamp("release_date"));
				id.setPrice(rs.getDouble("price"));
				items.add(id);
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
		return(items);
	}
	
	public static List getFourItems(DataSource dataSource, int productGroupId) 
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ItemData id=null;
		List<ItemData> items=new ArrayList<ItemData>();
		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT i.id, c.name as category, pg.name as product, r.name as region, i.rrp, i.name, i.description, i.picture, i.small_picture, i.release_date, ip.price FROM item i, category c, product_group pg, region r, item_price ip WHERE i.category_id=c.id AND i.product_group_id=pg.id AND i.region_id=r.id AND i.product_group_id=? AND ip.id=(SELECT id FROM item_price WHERE price_date=(SELECT max(price_date) FROM item_price WHERE item_id=i.id GROUP BY item_id)) ORDER BY rand() LIMIT 4");
			ps.setInt(1,productGroupId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				id=new ItemData();
				id.setId(rs.getInt("id"));
				id.setCategoryName(rs.getString("category"));
				id.setProductGroupName(rs.getString("product"));
				id.setRegionName(rs.getString("region"));
				id.setRrp(rs.getDouble("rrp"));
				id.setName(rs.getString("name"));
				id.setDescription(rs.getString("description"));
				id.setPicture(rs.getString("picture"));
				id.setSmallPicture(rs.getString("small_picture"));
				id.setRelease(rs.getTimestamp("release_date"));
				id.setPrice(rs.getDouble("price"));
				items.add(id);
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
		return(items);
	}
}
