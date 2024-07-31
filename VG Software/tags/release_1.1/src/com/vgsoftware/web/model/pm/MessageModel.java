package com.vgsoftware.web.model.pm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.web.data.pm.MessageData;

public class MessageModel
{
	public static List<MessageData> getMessages(int userId, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		MessageData md=null;
		List<MessageData> messages=new ArrayList<MessageData>();

		try
		{
			conn=dataSource.getConnection();			
			ps=conn.prepareStatement("SELECT mes.id, mes.title, mes.post_date, mes.sender_id, mem.name, mes.content FROM message mes, member mem WHERE mem.id=mes.sender_id AND mes.reciver_id=? ORDER BY post_date DESC");
			ps.setInt(1,userId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				md=new MessageData();
				md.setMessageId(rs.getInt("id"));
				md.setTitle(rs.getString("title"));
				md.setContent(rs.getString("content"));
				md.setPostDate(rs.getTimestamp("post_date"));
				md.setSenderId(rs.getInt("sender_id"));
				md.setSenderName(rs.getString("name"));
				messages.add(md);
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
		return(messages);
	}

	public static MessageData getMessage(int messageId, int userId, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		MessageData md=null;

		try
		{
			conn=dataSource.getConnection();			
			ps=conn.prepareStatement("SELECT mes.id, mes.title, mes.post_date, mes.sender_id, mem.name, mes.content FROM message mes, member mem WHERE mem.id=mes.sender_id AND mes.id=? AND reciver_id=? ORDER BY post_date DESC");
			ps.setInt(1,messageId);
			ps.setInt(2,userId);
			rs=ps.executeQuery();
			if(rs.next())
			{
				md=new MessageData();
				md.setMessageId(rs.getInt("id"));
				md.setTitle(rs.getString("title"));
				md.setContent(rs.getString("content"));
				md.setPostDate(rs.getTimestamp("post_date"));
				md.setSenderId(rs.getInt("sender_id"));
				md.setSenderName(rs.getString("name"));
				return(md);
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

	public static boolean addMessage(int userId, MessageData md, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO message(sender_id,reciver_id,title,content,post_date) VALUES(?,?,?,?,?)");
			ps.setInt(1,md.getSenderId());
			ps.setInt(2,userId);
			ps.setString(3,md.getTitle());
			ps.setString(4,md.getContent());
			ps.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
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

	public static boolean removeMessage(int id, int userId, DataSource dataSource)
 	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("DELETE FROM message WHERE id=? AND reciver_id=?");
			ps.setInt(1,id);
			ps.setInt(2,userId);
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
