package com.vgsoftware.jbb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.jbb.data.MemberData;
import com.vgsoftware.vgutil.misc.MD5;

public class MemberModel
{
	/**
	 * Gets all members.
	 * 
	 * @param dataSource A datasource with database connections.
	 * 
	 * @return A list with all members.
	 */
	public static List<MemberData> getMembers(DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		MemberData md=null;
		List<MemberData> members=new ArrayList<MemberData>();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT m.id, m.username, m.email, m.join_date, m.member_group, p.posts FROM jbb_members m, jbb_post_counter p WHERE p.member_id=m.id ORDER BY m.username DESC");
			rs=ps.executeQuery();
			while(rs.next())
			{
				md=new MemberData();
				md.setId(rs.getInt("id"));
				md.setUsername(rs.getString("username"));
				md.setEmail(rs.getString("email"));
				md.setJoinDate(rs.getTimestamp("join_date"));
				md.setMemberGroup(rs.getInt("member_group"));
				md.setPosts(rs.getInt("posts"));
				members.add(md);
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
		return(members);
	}

	/**
	 * Gets a member.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param memberId The id of the member.
	 * 
	 * @return The member or null.
	 */
	public static MemberData getMember(DataSource dataSource, int memberId)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		MemberData md=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT m.id, m.username, m.email, m.join_date, m.member_group, p.posts FROM jbb_members m LEFT OUTER JOIN jbb_post_counter p ON p.member_id=m.id WHERE m.id=?");
			ps.setInt(1,memberId);
			rs=ps.executeQuery();
			if(rs.next())
			{
				md=new MemberData();
				md.setId(rs.getInt("id"));
				md.setUsername(rs.getString("username"));
				md.setEmail(rs.getString("email"));
				md.setJoinDate(rs.getTimestamp("join_date"));
				md.setMemberGroup(rs.getInt("member_group"));
				md.setPosts(rs.getInt("posts"));
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
		return(md);
	}

	/**
	 * Gets a member.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param memberId The id of the member.
	 * 
	 * @return The member or null.
	 */
	public static MemberData getMember(DataSource dataSource, String memberName)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		MemberData md=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT m.id, m.username, m.email, m.join_date, m.member_group, p.posts FROM jbb_members m LEFT OUTER JOIN jbb_post_counter p ON p.member_id=m.id WHERE m.username=?");
			ps.setString(1,memberName);
			rs=ps.executeQuery();
			if(rs.next())
			{
				md=new MemberData();
				md.setId(rs.getInt("id"));
				md.setUsername(rs.getString("username"));
				md.setEmail(rs.getString("email"));
				md.setJoinDate(rs.getTimestamp("join_date"));
				md.setMemberGroup(rs.getInt("member_group"));
				md.setPosts(rs.getInt("posts"));
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
		return(md);
	}

	/**
	 * Login as a member.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param username A username.
	 * @param password A password.
	 * 
	 * @return The member data or null if the login fail.
	 */
	public static MemberData login(DataSource dataSource, String username, String password)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		MemberData md=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, username, email, join_date, member_group FROM jbb_members WHERE username=? AND password=?");
			ps.setString(1,username);
			ps.setString(2,MD5.parse(password));
			rs=ps.executeQuery();
			if(rs.next())
			{
				md=new MemberData();
				md.setId(rs.getInt("id"));
				md.setUsername(rs.getString("username"));
				md.setEmail(rs.getString("email"));
				md.setJoinDate(rs.getTimestamp("join_date"));
				md.setMemberGroup(rs.getInt("member_group"));
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
		return(md);
	}
	
	/**
	 * Adds a member.
	 * 
	 * @param dataSource A datasource with database connections.
	 * @param md Member data about the new member.
	 * 
	 * @return The ID of the new member or -1 if unsuccessfull.
	 */
	public static int addMember(DataSource dataSource, MemberData md)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		int id=-1;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO jbb_members(id,username,password,email,join_date,member_group) VALUES(?,?,?,?,?,?)");
			id=IDGenerator.getNextId(dataSource,"jbb_members");
			ps.setInt(1,id);
			ps.setString(2,md.getUsername());
			ps.setString(3,MD5.parse(md.getPassword()));
			ps.setString(4,md.getEmail());
			ps.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
			ps.setInt(6,md.getMemberGroup());
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
}
