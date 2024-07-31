package com.vgsoftware.web.model.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.vgsoftware.web.data.member.ExtraData;

public class MemberExtraModel
{
	public static Map<String,ExtraData> getExtra(int memberId, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Map<String,ExtraData> extra=new HashMap<String,ExtraData>();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, name, data, member_id FROM member_extra WHERE member_id=?");
			ps.setInt(1,memberId);
			rs=ps.executeQuery();
			while(rs.next())
				extra.put(rs.getString("name"),new ExtraData(rs.getInt("id"),rs.getInt("member_id"),rs.getString("name"),rs.getString("data")));
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
		return(extra);
	}
	
	public static boolean addExtra(ExtraData ed, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO member_extra(member_id,name,data) VALUES(?,?,?)");
			ps.setInt(1,ed.getMemberId());
			ps.setString(2,ed.getName());
			ps.setString(3,ed.getData());
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
	
	public static boolean updateExtra(ExtraData ed, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("UPDATE member_extra SET data=? WHERE id=?");
			ps.setString(1,ed.getData());
			ps.setInt(2,ed.getId());
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
