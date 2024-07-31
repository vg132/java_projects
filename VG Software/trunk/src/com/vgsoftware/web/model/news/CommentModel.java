package com.vgsoftware.web.model.news;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.web.data.news.CommentData;

public class CommentModel
{
	public static List<CommentData> getComments(int newsId, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		CommentData cd=null;
		List<CommentData> comments=new ArrayList<CommentData>();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT c.id, c.member_id, m.name, c.comment, c.post_date,c.edit_date FROM news_comment c, member m WHERE c.member_id=m.id AND c.news_id=? ORDER BY post_date DESC");
			ps.setInt(1,newsId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				cd=new CommentData();
				cd.setId(rs.getInt("id"));
				cd.setMemberId(rs.getInt("member_id"));
				cd.setMemberName(rs.getString("name"));
				cd.setComment(rs.getString("comment"));
				cd.setPostDate(rs.getTimestamp("post_date"));
				cd.setEditDate(rs.getTimestamp("edit_date"));
				comments.add(cd);
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
		return(comments);
	}

	public static CommentData getComment(int commentId, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		CommentData cd=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT c.id, c.member_id, m.name, c.news_id, c.comment, c.post_date,c.edit_date FROM news_comment c, member m WHERE c.member_id=m.id AND c.id=? ORDER BY post_date DESC");
			ps.setInt(1,commentId);
			rs=ps.executeQuery();
			if(rs.next())
			{
				cd=new CommentData();
				cd.setId(rs.getInt("id"));
				cd.setMemberId(rs.getInt("member_id"));
				cd.setMemberName(rs.getString("name"));
				cd.setNewsId(rs.getInt("news_id"));
				cd.setComment(rs.getString("comment"));
				cd.setPostDate(rs.getTimestamp("post_date"));
				cd.setEditDate(rs.getTimestamp("edit_date"));
				return(cd);
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
	
	public static boolean updateComment(CommentData cd, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("UPDATE news_comment SET comment=?, edit_date=? WHERE id=?");
			ps.setString(1,cd.getComment());
			ps.setTimestamp(2,cd.getEditDate());
			ps.setInt(3,cd.getId());
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
	
	public static boolean addComment(CommentData cd, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO news_comment(member_id,news_id,comment,post_date,edit_date) VALUES(?,?,?,?,?)");
			ps.setInt(1,cd.getMemberId());
			ps.setInt(2,cd.getNewsId());
			ps.setString(3,cd.getComment());
			ps.setTimestamp(4,cd.getPostDate());
			ps.setTimestamp(5,cd.getEditDate());
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

	public static boolean removeComment(int commentId, int memberId, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("DELETE FROM news_comment WHERE id=? AND member_id=?");
			ps.setInt(1,commentId);
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
}
