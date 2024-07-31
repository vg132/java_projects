package com.vgsoftware.shop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.shop.data.CategoryData;
import com.vgsoftware.shop.data.ProductGroupData;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-maj-03 - Document created.
 */
public class CategoryModel
{
	public static CategoryData getCategory(DataSource dataSource, int categoryId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ProductGroupData pgd=null;
		List productGroups=new ArrayList();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT c.id, c.name, c.category_group_id, cg.name AS category_group_name FROM category c, category_group cg WHERE c.category_group_id=cg.id AND c.id=? ORDER BY c.name ASC");
			ps.setInt(1,categoryId);
			rs=ps.executeQuery();
			if(rs.next())
				return(new CategoryData(rs.getInt("id"),rs.getInt("category_group_id"),rs.getString("name"),rs.getString("category_group_name")));
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

	
	public static List<CategoryData> getCategorys(DataSource dataSource)
	throws SQLException
	{
		return(getCategorys(dataSource,-1));
	}
	
	public static List<CategoryData> getCategorys(DataSource dataSource, int categoryGroupId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<CategoryData> categorys=new ArrayList<CategoryData>();

		try
		{
			conn=dataSource.getConnection();
			if(categoryGroupId!=-1)
			{
				ps=conn.prepareStatement("SELECT c.id, c.name, c.category_group_id, cg.name AS category_group_name FROM category c, category_group cg WHERE c.category_group_id=cg.id AND cg.id=? ORDER BY c.name ASC");
				ps.setInt(1,categoryGroupId);
			}
			else
			{
				ps=conn.prepareStatement("SELECT c.id, c.name, c.category_group_id, cg.name AS category_group_name FROM category c, category_group cg WHERE c.category_group_id=cg.id ORDER BY c.name ASC");
			}
			rs=ps.executeQuery();
			while(rs.next())
				categorys.add(new CategoryData(rs.getInt("id"),rs.getInt("category_group_id"),rs.getString("name"),rs.getString("category_group_name")));
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
		return(categorys);
	}
}
