package com.vgsoftware.jbb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.jbb.data.TopicData;


public class TopicModel
{
	/**
	 * Gets all topics.
	 * 
	 * @param dataSource A datasource with database connections.
	 * 
	 * @return A list with all topics.
	 */
	public static List<TopicData> getTopics(DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		TopicData td=null;
		List<TopicData> topics=new ArrayList<TopicData>();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT t.id, t.title, t.post_date, t.replies, t.member_id, t.forum_id, c.id, f.name AS forum_name, f.category_id AS category_id, c.name AS category_name, m.username AS member_name FROM jbb_topics t, jbb_forums f, jbb_categorys c, jbb_members m WHERE c.id=f.category_id AND f.id=t.forum_id AND m.id=t.member_id ORDER BY post_date DESC");
			rs=ps.executeQuery();
			while(rs.next())
			{
				td=new TopicData();
				td.setId(rs.getInt("id"));
				td.setTitle(rs.getString("title"));
				td.setPostDate(rs.getTimestamp("post_date"));
				td.setReplies(rs.getInt("replies"));
				td.setMemberId(rs.getInt("member_id"));
				td.setForumId(rs.getInt("forum_id"));
				td.setCategoryId(rs.getInt("category_id"));
				td.setForumName(rs.getString("forum_name"));
				td.setCategoryName(rs.getString("category_name"));
				td.setMemberName(rs.getString("member_name"));
				topics.add(td);
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
		return(topics);
	}

	/**
	 * Gets all topics for a specified forum.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param fourmId The id of the forum.
	 * 
	 * @return A list with all topics in the specified forum.
	 */
	public static List<TopicData> getTopics(DataSource dataSource, int forumId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		TopicData td=null;
		List<TopicData> topics=new ArrayList<TopicData>();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT t.id, t.title, t.post_date, t.replies, t.member_id, t.forum_id, c.id, f.name AS forum_name, f.category_id AS category_id, c.name AS category_name, m.username AS member_name FROM jbb_topics t, jbb_forums f, jbb_categorys c, jbb_members m WHERE c.id=f.category_id AND f.id=t.forum_id AND m.id=t.member_id AND t.forum_id=? ORDER BY post_date DESC");
			ps.setInt(1,forumId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				td=new TopicData();
				td.setId(rs.getInt("id"));
				td.setTitle(rs.getString("title"));
				td.setPostDate(rs.getTimestamp("post_date"));
				td.setReplies(rs.getInt("replies"));
				td.setMemberId(rs.getInt("member_id"));
				td.setForumId(rs.getInt("forum_id"));
				td.setCategoryId(rs.getInt("category_id"));
				td.setForumName(rs.getString("forum_name"));
				td.setCategoryName(rs.getString("category_name"));
				td.setMemberName(rs.getString("member_name"));
				topics.add(td);
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
		return(topics);
	}

	/**
	 * Gets a topic.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param topicId The id of the topic.
	 * 
	 * @return The topic if successfull, otherwise null.
	 */
	public static TopicData getTopic(DataSource dataSource, int topicId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		TopicData td=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT t.id, t.title, t.post_date, t.replies, t.member_id, t.forum_id, c.id, f.name AS forum_name, f.category_id AS category_id, c.name AS category_name, m.username AS member_name FROM jbb_topics t, jbb_forums f, jbb_categorys c, jbb_members m WHERE c.id=f.category_id AND f.id=t.forum_id AND m.id=t.member_id AND t.id=?");
			ps.setInt(1,topicId);
			rs=ps.executeQuery();
			if(rs.next())
			{
				td=new TopicData();
				td.setId(rs.getInt("id"));
				td.setTitle(rs.getString("title"));
				td.setPostDate(rs.getTimestamp("post_date"));
				td.setReplies(rs.getInt("replies"));
				td.setMemberId(rs.getInt("member_id"));
				td.setForumId(rs.getInt("forum_id"));
				td.setCategoryId(rs.getInt("category_id"));
				td.setForumName(rs.getString("forum_name"));
				td.setCategoryName(rs.getString("category_name"));
				td.setMemberName(rs.getString("member_name"));
				return(td);
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
	 * Adds a topic.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param topicData A <code>TopicData</code> instance with information about the new topic.
	 * 
	 * @return The id of the inserted forum or -1 if unsuccessfull.
	 */
	public static int addTopic(DataSource dataSource, TopicData topicData)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		int id=-1;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO jbb_topics(id,title,post_date,replies,member_id,forum_id) VALUES(?,?,?,?,?,?)");
			id=IDGenerator.getNextId(dataSource,"jbb_topics");
			ps.setInt(1,id);
			ps.setString(2,topicData.getTitle());
			ps.setTimestamp(3,topicData.getPostDate());
			ps.setInt(4,topicData.getReplies());
			ps.setInt(5,topicData.getMemberId());
			ps.setInt(6,topicData.getForumId());
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
	 * Updates a topic.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param topicData Data for this topic.
	 * 
	 * @return True if successfull, otherwise false.
	 */
	public static boolean updateTopic(DataSource dataSource, TopicData topicData)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("UPDATE jbb_topics SET title=?, post_date=post_date, replies=?, forum_id=?, member_id=? WHERE id=?");
			ps.setString(1,topicData.getTitle());
			ps.setInt(2,topicData.getReplies());
			ps.setInt(3,topicData.getForumId());
			ps.setInt(4,topicData.getMemberId());
			ps.setInt(5,topicData.getId());
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
	 * Adds a reply to the reply counter
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param topicId The id of the topic.
	 * 
	 * @return True if successfull, otherwise false.
	 */
	public static synchronized boolean addReply(DataSource dataSource, int topicId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("UPDATE jbb_topics SET replies=replies+1, post_date=post_date WHERE id=?");
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
	
	/**
	 * Deletes a topic.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param topicId The id of the topic.
	 * 
	 * @return True if successfull, otherwise false.
	 */
	public static boolean deleteTopic(DataSource dataSource, int topicId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("DELETE FROM jbb_topics WHERE id=?");
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
