package com.vgsoftware.jbb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.jbb.data.CategoryData;


public class CategoryModel
{
	/**
	 * Gets all categorys.
	 * 
	 * @param dataSource A datasource with database connections.
	 * 
	 * @return A list with all categorys.
	 */
	public static List<CategoryData> getCategorys(DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<CategoryData> categorys=new ArrayList<CategoryData>();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, name FROM jbb_categorys");
			rs=ps.executeQuery();
			while(rs.next())
				categorys.add(new CategoryData(rs.getInt("id"),rs.getString("name")));
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
	
	/**
	 * Gets a category.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param id The id of the category.
	 * 
	 * @return The category, or null if no category was found.
	 */
	public static CategoryData getCategory(DataSource dataSource, int id)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, name FROM jbb_categorys WHERE id=?");
			ps.setInt(1,id);
			rs=ps.executeQuery();
			if(rs.next())
				return(new CategoryData(rs.getInt("id"),rs.getString("name")));
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
	
	/**
	 * Adds a new category.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param name The name of the category.
	 * 
	 * @return The id of the inserted category or -1 if unsuccessfull.
	 */
	public static int addCategory(DataSource dataSource, String name)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		int id=-1;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO jbb_categorys(id,name) VALUES(?,?)");
			id=IDGenerator.getNextId(dataSource,"jbb_categorys");
			ps.setInt(1,id);
			ps.setString(2,name);
			if(ps.executeUpdate()==1)
				return(id);
		}
		finally
		{
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		}
		return(id);
	}
	
	/**
	 * Adds a new category.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param name The name of the category.
	 * 
	 * @return True if successfull, otherwise false.
	 */
	public static boolean deleteCategory(DataSource dataSource, int id)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("DELETE FROM jbb_categorys WHERE id=?");
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
}
