package com.vgsoftware.web.model.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.vgutil.misc.MD5;
import com.vgsoftware.web.data.member.ExtraData;
import com.vgsoftware.web.data.member.MemberData;
import com.vgsoftware.web.data.member.ServiceData;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-okt-05 - Added two new methods, getMembersWithExtra and getMembersWithService
 * 2005-sep-27 - Added getMember by name and changed the parameter order.
 * 2005-maj-01 - Document created.
 */
public class MemberModel
{
	public static boolean checkPassword(int memberId, String password, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id FROM member WHERE id=? AND password=?");
			ps.setInt(1,memberId);
			ps.setString(2,MD5.parse(password));
			rs=ps.executeQuery();
			if(rs.next())
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

	public static boolean updatePassword(int memberId, String password, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("UPDATE member SET password=? WHERE id=?");
			ps.setString(1,MD5.parse(password));
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

	public static boolean updateMember(MemberData ud, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("UPDATE member SET name=?, email=?, group_id=? WHERE id=?");
			ps.setString(1,ud.getName());
			ps.setString(2,ud.getEmail());
			ps.setInt(3,ud.getGroupId());
			ps.setInt(4,ud.getId());
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

	public static MemberData getMember(String name, String password, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT m.id, m.name, m.email, m.join_date, m.group_id, mg.name AS group_name FROM member m, member_group mg WHERE m.group_id=mg.id AND m.name=? AND password=?");
			ps.setString(1,name);
			ps.setString(2,MD5.parse(password));
			rs=ps.executeQuery();
			if(rs.next())
			{
				MemberData ud=new MemberData();
				ud.setId(rs.getInt("id"));
				ud.setName(rs.getString("name"));
				ud.setEmail(rs.getString("email"));
				ud.setJoinDate(rs.getTimestamp("join_date"));
				ud.setGroupName(rs.getString("group_name"));
				ud.setGroupId(rs.getInt("group_id"));
				return(ud);
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

	public static MemberData getMember(String name, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT m.id, m.name, m.email, m.join_date, m.group_id, mg.name AS group_name FROM member m, member_group mg WHERE m.group_id=mg.id AND m.name=?");
			ps.setString(1,name);
			rs=ps.executeQuery();
			if(rs.next())
			{
				MemberData ud=new MemberData();
				ud.setId(rs.getInt("id"));
				ud.setName(rs.getString("name"));
				ud.setEmail(rs.getString("email"));
				ud.setJoinDate(rs.getTimestamp("join_date"));
				ud.setGroupName(rs.getString("group_name"));
				ud.setGroupId(rs.getInt("group_id"));
				return(ud);
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

	public static boolean addMember(MemberData md, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO member(name,password,email,join_date,group_id) VALUES(?,?,?,?,?)");
			ps.setString(1,md.getName());
			ps.setString(2,MD5.parse(md.getPassword()));
			ps.setString(3,md.getEmail());
			ps.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
			ps.setInt(5,md.getGroupId());
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

	public static MemberData getMember(int memberId, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT m.id, m.name, m.email, m.join_date, m.group_id, mg.name AS group_name FROM member m, member_group mg WHERE m.group_id=mg.id AND m.id=?");
			ps.setInt(1,memberId);
			rs=ps.executeQuery();
			if(rs.next())
			{
				MemberData ud=new MemberData();
				ud.setId(rs.getInt("id"));
				ud.setName(rs.getString("name"));
				ud.setEmail(rs.getString("email"));
				ud.setJoinDate(rs.getTimestamp("join_date"));
				ud.setGroupName(rs.getString("group_name"));
				ud.setGroupId(rs.getInt("group_id"));
				return(ud);
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

	public static List<MemberData> getMembers(DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<MemberData> members=new ArrayList<MemberData>();
		MemberData ud=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT m.id, m.name, m.email, m.join_date, m.group_id, mg.name as group_name FROM member m, member_group mg WHERE m.group_id=mg.id ORDER BY m.name");
			rs=ps.executeQuery();
			while(rs.next())
			{
				ud=new MemberData();
				ud.setId(rs.getInt("id"));
				ud.setName(rs.getString("name"));
				ud.setEmail(rs.getString("email"));
				ud.setJoinDate(rs.getTimestamp("join_date"));
				ud.setGroupName(rs.getString("group_name"));
				ud.setGroupId(rs.getInt("group_id"));
				members.add(ud);
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

	public static List<MemberData> getMembersWithExtra(String extra, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<MemberData> members=new ArrayList<MemberData>();
		MemberData md=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT me.member_id, me.id AS extra_id, me.data, m.name, m.email, m.join_date, m.group_id, mg.name AS group_name FROM member_extra me, member m, member_group mg WHERE me.name=? AND m.id=me.member_id AND mg.id=m.group_id ORDER BY m.name");
			ps.setString(1,extra);
			rs=ps.executeQuery();
			while(rs.next())
			{
				md=new MemberData();
				md.setId(rs.getInt("member_id"));
				md.setName(rs.getString("name"));
				md.addExtra(new ExtraData(rs.getInt("extra_id"),rs.getInt("member_id"),extra,rs.getString("data")));
				md.setEmail(rs.getString("email"));
				md.setJoinDate(rs.getTimestamp("join_date"));
				md.setGroupName(rs.getString("group_name"));
				md.setGroupId(rs.getInt("group_id"));
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

	public static List<MemberData> getMembersWithService(String service, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<MemberData> members=new ArrayList<MemberData>();
		MemberData md=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT s.id AS service_id, s.name AS service_name, ms.member_id, m.name, m.email, m.join_date, m.group_id, mg.name AS group_name FROM member m, member_group mg, member_service ms, service s WHERE s.name=? AND s.id=ms.service_id AND ms.member_id=m.id AND mg.id=m.group_id ORDER BY name");
			ps.setString(1,service);
			rs=ps.executeQuery();
			while(rs.next())
			{
				md=new MemberData();
				md.setId(rs.getInt("member_id"));
				md.setName(rs.getString("name"));
				md.addService(new ServiceData(rs.getInt("service_id"),rs.getString("service_name")));
				md.setEmail(rs.getString("email"));
				md.setJoinDate(rs.getTimestamp("join_date"));
				md.setGroupName(rs.getString("group_name"));
				md.setGroupId(rs.getInt("group_id"));
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
	
}
