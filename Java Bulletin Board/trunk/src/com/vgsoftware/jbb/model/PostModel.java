package com.vgsoftware.jbb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.jbb.data.PostData;


public class PostModel
{
	/**
	 * Gets all posts in a topic.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param topicId The id of the topic.
	 * 
	 * @return A list with all topics.
	 */
	public static List<PostData> getPosts(DataSource dataSource, int topicId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		PostData pd=null;
		List<PostData> posts=new ArrayList<PostData>();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT p.id, p.content, p.post_date, p.edit_date, p.topic_id, p.member_id, m.username AS member_name FROM jbb_posts p, jbb_members m WHERE m.id=p.member_id AND p.topic_id=? ORDER BY post_date ASC");
			ps.setInt(1,topicId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				pd=new PostData();
				pd.setId(rs.getInt("id"));
				pd.setContent(rs.getString("content"));
				pd.setPostDate(rs.getTimestamp("post_date"));
				pd.setEditDate(rs.getTimestamp("edit_date"));
				pd.setTopicId(rs.getInt("topic_id"));
				pd.setMemberId(rs.getInt("member_id"));
				pd.setMemberName(rs.getString("member_name"));
				posts.add(pd);
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
		return(posts);
	}
	
	/**
	 * Gets a post.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param postId The id of the post.
	 * 
	 * @return The post or null if its not found.
	 */
	public static PostData getPost(DataSource dataSource, int postId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT p.id, p.content, p.post_date, p.edit_date, p.topic_id, p.member_id, m.username AS member_name FROM jbb_posts p, jbb_members m WHERE m.id=p.member_id AND p.id=? ORDER BY post_date ASC");
			ps.setInt(1,postId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				PostData pd=new PostData();
				pd.setId(rs.getInt("id"));
				pd.setContent(rs.getString("content"));
				pd.setPostDate(rs.getTimestamp("post_date"));
				pd.setEditDate(rs.getTimestamp("edit_date"));
				pd.setTopicId(rs.getInt("topic_id"));
				pd.setMemberId(rs.getInt("member_id"));
				pd.setMemberName(rs.getString("member_name"));
				return(pd);
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
	 * Adds a post.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param post The post to be added.
	 * 
	 * @return The id of the inserted forum or -1 if unsuccessfull.
	 */
	public static int addPost(DataSource dataSource, PostData post)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int id=-1;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO jbb_posts(id,content,post_date,edit_date,topic_id,member_id) VALUES(?,?,?,?,?,?)");
			id=IDGenerator.getNextId(dataSource,"jbb_posts");
			ps.setInt(1,id);
			ps.setString(2,post.getContent());
			ps.setTimestamp(3,post.getPostDate());
			ps.setTimestamp(4,post.getPostDate());
			ps.setInt(5,post.getTopicId());
			ps.setInt(6,post.getMemberId());
			if(ps.executeUpdate()==1)
				return(id);
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
		return(-1);
	}
	
	/**
	 * Updates a post.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param post The post to be updated.
	 * 
	 * @return True if successfull, otherwise false.
	 */
	public static boolean updatePost(DataSource dataSource, PostData post)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("UPDATE jbb_posts SET content=?,post_date=post_date,edit_date=?,topic_id=?,member_id=? WHERE id=?");
			ps.setString(1,post.getContent());
			ps.setTimestamp(2,post.getEditDate());
			ps.setInt(3,post.getTopicId());
			ps.setInt(4,post.getMemberId());
			ps.setInt(5,post.getId());
			if(ps.executeUpdate()==1)
				return(true);
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
		return(false);
	}
	
	/**
	 * Deletes a post.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param postId The id of the post.
	 * 
	 * @return True if successfull, otherwise false.
	 */
	public static boolean deletePost(DataSource dataSource, int postId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("DELETE FROM jbb_posts WHERE id=?");
			ps.setInt(1,postId);
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
	 * Deletes a post.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param memberId The if of the member who posted the post.
	 * @param postId The id of the post.
	 * 
	 * @return True if successfull, otherwise false.
	 */
	public static boolean deletePost(DataSource dataSource, int memberId, int postId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("DELETE FROM jbb_posts WHERE id=? AND member_id=?");
			ps.setInt(1,postId);
			ps.setInt(2,memberId);
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
	 * Deletes post in a specified topic.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param topicId The id of the topic.
	 * 
	 * @return True if successfull, otherwise false.
	 */
	public static boolean deletePosts(DataSource dataSource, int topicId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("DELETE FROM jbb_posts WHERE topic_id=?");
			ps.setInt(1,topicId);
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
