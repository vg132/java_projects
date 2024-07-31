package com.vgsoftware.jbb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.jbb.data.ForumData;


public class ForumModel
{
	/**
	 * Gets all forums.
	 * 
	 * @param dataSource A datasource with database connections.
	 * 
	 * @return A list with all forums.
	 */
	public static List<ForumData> getForums(DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ForumData fd=null;
		List<ForumData> forums=new ArrayList<ForumData>();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT f.id, f.name, f.description, f.topics, f.replies, f.order_value, f.category_id, c.name AS category_name FROM jbb_forums f, jbb_categorys c WHERE c.id=f.category_id");
			rs=ps.executeQuery();
			while(rs.next())
			{
				fd=new ForumData();
				fd.setId(rs.getInt("id"));
				fd.setName(rs.getString("name"));
				fd.setDescription(rs.getString("description"));
				fd.setTopics(rs.getInt("topics"));
				fd.setReplies(rs.getInt("replies"));
				fd.setOrder(rs.getInt("order_value"));
				fd.setCategoryId(rs.getInt("category_id"));
				fd.setCategoryName(rs.getString("category_name"));
				forums.add(fd);
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
		return(forums);
	}
	
	/**
	 * Gets all forums in a category.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param category The id for the category.
	 * 
	 * @return A list with all forums.
	 */
	public static List<ForumData> getForums(DataSource dataSource, int categoryId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ForumData fd=null;
		List<ForumData> forums=new ArrayList<ForumData>();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT f.id, f.name, f.description, f.topics, f.replies, f.order_value, f.category_id, c.name AS category_name FROM jbb_forums f, jbb_categorys c WHERE c.id=f.category_id AND f.category_id=? ORDER BY order_value ASC");
			ps.setInt(1,categoryId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				fd=new ForumData();
				fd.setId(rs.getInt("id"));
				fd.setName(rs.getString("name"));
				fd.setDescription(rs.getString("description"));
				fd.setTopics(rs.getInt("topics"));
				fd.setReplies(rs.getInt("replies"));
				fd.setOrder(rs.getInt("order_value"));
				fd.setCategoryId(rs.getInt("category_id"));
				fd.setCategoryName(rs.getString("category_name"));
				forums.add(fd);
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
		return(forums);
	}
	
	/**
	 * Gets a forum.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param forumId The id of the forum.
	 * 
	 * @return The forum or null.
	 */
	public static ForumData getForum(DataSource dataSource, int forumId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ForumData fd=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT f.id, f.name, f.description, f.topics, f.replies, f.order_value, f.category_id, c.name AS category_name FROM jbb_forums f, jbb_categorys c WHERE c.id=f.category_id AND f.id=?");
			ps.setInt(1,forumId);
			rs=ps.executeQuery();
			if(rs.next())
			{
				fd=new ForumData();
				fd.setId(rs.getInt("id"));
				fd.setName(rs.getString("name"));
				fd.setDescription(rs.getString("description"));
				fd.setTopics(rs.getInt("topics"));
				fd.setReplies(rs.getInt("replies"));
				fd.setOrder(rs.getInt("order_value"));
				fd.setCategoryId(rs.getInt("category_id"));
				fd.setCategoryName(rs.getString("category_name"));
				return(fd);
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
	
	/**
	 * Adds a forum.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param forumData A <code>ForumData</code> instance with information about the new forum.
	 * 
	 * @return The id of the inserted forum or -1 if unsuccessfull.
	 */
	public static int addForum(DataSource dataSource, ForumData forumData)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		int id=-1;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO jbb_forums(id,name,description,topics,replies,order_value,category_id) VALUES(?,?,?,?,?,?,?)");
			id=IDGenerator.getNextId(dataSource,"jbb_forums");
			ps.setInt(1,id);
			ps.setString(2,forumData.getName());
			ps.setString(3,forumData.getDescription());
			ps.setInt(4,forumData.getTopics());
			ps.setInt(5,forumData.getReplies());
			ps.setInt(6,forumData.getOrder());
			ps.setInt(7,forumData.getCategoryId());
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
		return(-1);
	}

	/**
	 * Adds a reply to the reply counter
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param forumId The id of the forum.
	 * 
	 * @return True if successfull, otherwise false.
	 */
	public static synchronized boolean addReply(DataSource dataSource, int forumId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("UPDATE jbb_forums SET replies=replies+1 WHERE id=?");
			ps.setInt(1,forumId);
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

	/**
	 * Adds a topic to the topics counter
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param forumId The id of the forum.
	 * 
	 * @return True if successfull, otherwise false.
	 */
	public static synchronized boolean addTopic(DataSource dataSource, int forumId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("UPDATE jbb_forums SET topics=topics+1 WHERE id=?");
			ps.setInt(1,forumId);
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
	
	/**
	 * Deletes a forum.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param forumId The id of the forum.
	 * 
	 * @return True if successfull, otherwise false.
	 */
	public static boolean deleteForum(DataSource dataSource, int forumId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("DELETE FROM jbb_forums WHERE id=?");
			ps.setInt(1,forumId);
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
