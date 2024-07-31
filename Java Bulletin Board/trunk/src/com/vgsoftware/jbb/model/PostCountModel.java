package com.vgsoftware.jbb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.vgsoftware.jbb.data.PostCountData;

public class PostCountModel
{
	/**
	 * Gets post count data for a member.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param memberId The member id to get post count data for.
	 * 
	 * @return A <code>PostCountData</code> object or null if unsuccessfull.
	 */
	public static PostCountData getPostCountData(DataSource dataSource, int memberId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT posts, member_id FROM jbb_post_counter WHERE member_id=?");
			ps.setInt(1,memberId);
			rs=ps.executeQuery();
			if(rs.next())
				return(new PostCountData(rs.getInt("member_id"),rs.getInt("posts")));
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
	 * Adds post count data.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param pd A <code>PostCountData</code> object.
	 * 
	 * @return True if successfull, otherwise false.
	 */
	public static boolean addPostCountData(DataSource dataSource, PostCountData pd)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO jbb_post_counter(name, member_id) VALUES(?,?)");
			ps.setInt(1,pd.getPosts());
			ps.setInt(2,pd.getMemberId());
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
	 * Updates post count data.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param pd A <code>PostCountData</code> object.
	 * 
	 * @return True if successfull, otherwise false.
	 */
	public static boolean updatePostCountData(DataSource dataSource, PostCountData pd)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("UPDATE jbb_post_counter SET posts=?, member_id=? WHERE member_id=?");
			ps.setInt(1,pd.getPosts());
			ps.setInt(2,pd.getMemberId());
			ps.setInt(3,pd.getMemberId());
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
