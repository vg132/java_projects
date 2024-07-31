package com.vgsoftware.web.model.news;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.web.data.news.NewsData;

public class NewsModel
{
	public static NewsData getNews(int newsId, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		NewsData nd=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT n.id, n.title, n.content, n.author_id, m.name AS author_name, n.comment, n.comments, n.news_date FROM news n, member m WHERE m.id=n.author_id AND n.id=? ORDER BY news_date DESC");
			ps.setInt(1,newsId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				nd=new NewsData();
				nd.setId(rs.getInt("id"));
				nd.setTitle(rs.getString("title"));
				nd.setContent(rs.getString("content"));
				nd.setAuthorId(rs.getInt("author_id"));
				nd.setAuthorName(rs.getString("author_name"));
				nd.setDate(rs.getTimestamp("news_date"));
				nd.setComments(rs.getInt("comments"));
				nd.setComment(rs.getBoolean("comment"));
				return(nd);
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
		return(nd);
	}

	public static List<NewsData> getNews(DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		NewsData nd=null;
		List<NewsData> news=new ArrayList<NewsData>();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT n.id, n.title, n.content, n.author_id, m.name AS author_name, n.comment, n.comments, n.news_date FROM news n, member m WHERE m.id=n.author_id ORDER BY news_date DESC");
			rs=ps.executeQuery();
			while(rs.next())
			{
				nd=new NewsData();
				nd.setId(rs.getInt("id"));
				nd.setTitle(rs.getString("title"));
				nd.setContent(rs.getString("content"));
				nd.setAuthorId(rs.getInt("author_id"));
				nd.setAuthorName(rs.getString("author_name"));
				nd.setDate(rs.getTimestamp("news_date"));
				nd.setComments(rs.getInt("comments"));
				nd.setComment(rs.getBoolean("comment"));
				news.add(nd);
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
		return(news);
	}

	public static boolean updateNews(NewsData nd, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("UPDATE news SET title=?, content=?, comment=?, comments=? WHERE id=?");
			ps.setString(1,nd.getTitle());
			ps.setString(2,nd.getContent());
			ps.setBoolean(3,nd.canComment());
			ps.setInt(4,nd.getComments());
			ps.setInt(5,nd.getId());
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
	
	public static boolean addNews(NewsData nd, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO news(title,content,author_id,comment,comments,news_date) VALUES(?,?,?,?,?,?)");
			ps.setString(1,nd.getTitle());
			ps.setString(2,nd.getContent());
			ps.setInt(3,nd.getAuthorId());
			ps.setBoolean(4,nd.canComment());
			ps.setInt(5,nd.getComments());
			ps.setTimestamp(6,nd.getDate());
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
