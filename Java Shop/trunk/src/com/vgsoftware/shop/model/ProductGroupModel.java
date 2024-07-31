package com.vgsoftware.shop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.shop.data.ProductGroupData;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-maj-03 - Document created.
 */
public class ProductGroupModel
{
	public static List getProductGropus(DataSource dataSource)
	throws SQLException
	{
		return(getProductGroups(dataSource,-1));
	}

	public static List getProductGroups(DataSource dataSource, int categoryGroupId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ProductGroupData pgd=null;
		List<ProductGroupData> productGroups=new ArrayList<ProductGroupData>();

		try
		{
			conn=dataSource.getConnection();
			if(categoryGroupId!=-1)
			{
				ps=conn.prepareStatement("SELECT pg.id, pg.name, pg.category_group_id,cg.name AS category_group_name, pg.pic_width, pg.pic_height, pg.pic_width_small, pg.pic_height_small FROM product_group pg, category_group cg WHERE cg.id=pg.category_group_id AND pg.category_group_id=? ORDER BY name ASC");
				ps.setInt(1,categoryGroupId);
			}
			else
			{
				ps=conn.prepareStatement("SELECT pg.id, pg.name, pg.category_group_id,cg.name AS category_group_name, pg.pic_width, pg.pic_height, pg.pic_width_small, pg.pic_height_small FROM product_group pg, category_group cg WHERE cg.id=pg.category_group_id ORDER BY cg.name, pg.name ASC");
			}
			rs=ps.executeQuery();
			while(rs.next())
			{
				pgd=new ProductGroupData();
				pgd.setId(rs.getInt("id"));
				pgd.setName(rs.getString("name"));
				pgd.setCategoryGroupId(rs.getInt("category_group_id"));
				pgd.setCategoryGroupName(rs.getString("category_group_name"));
				pgd.setPicWidth(rs.getInt("pic_width"));
				pgd.setPicHeight(rs.getInt("pic_height"));
				pgd.setPicWidthSmall(rs.getInt("pic_width_small"));
				pgd.setPicHeightSmall(rs.getInt("pic_height_small"));
				productGroups.add(pgd);
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
		return(productGroups);
	}
	
	public static ProductGroupData getProductGroupData(DataSource dataSource, int productGroupId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ProductGroupData pgd=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT pg.id, pg.name, pg.category_group_id,cg.name AS category_group_name, pg.pic_width, pg.pic_height, pg.pic_width_small, pg.pic_height_small FROM product_group pg, category_group cg WHERE cg.id=pg.category_group_id AND pg.id=? ORDER BY name ASC");
			ps.setInt(1,productGroupId);
			rs=ps.executeQuery();
			if(rs.next())
			{
				pgd=new ProductGroupData();
				pgd.setId(rs.getInt("id"));
				pgd.setName(rs.getString("name"));
				pgd.setCategoryGroupId(rs.getInt("category_group_id"));
				pgd.setCategoryGroupName(rs.getString("category_group_name"));
				pgd.setPicWidth(rs.getInt("pic_width"));
				pgd.setPicHeight(rs.getInt("pic_height"));
				pgd.setPicWidthSmall(rs.getInt("pic_width_small"));
				pgd.setPicHeightSmall(rs.getInt("pic_height_small"));
				return(pgd);
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
}
