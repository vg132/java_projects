package com.vgsoftware.web.model.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.web.data.member.ServiceData;

public class MemberServiceModel
{
	public static List<ServiceData> getServices(int memberId, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<ServiceData> services=new ArrayList<ServiceData>();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT ms.service_id, s.name FROM member_service ms, service s WHERE ms.service_id=s.id AND member_id=?");
			ps.setInt(1,memberId);
			rs=ps.executeQuery();
			while(rs.next())
				services.add(new ServiceData(rs.getInt("service_id"),rs.getString("name")));
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
		return(services);
	}

	public static List<ServiceData> getServices(DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<ServiceData> services=new ArrayList<ServiceData>();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, name FROM service");
			rs=ps.executeQuery();
			while(rs.next())
				services.add(new ServiceData(rs.getInt("id"),rs.getString("name")));
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
		return(services);
	}
	
	public static boolean addService(String name, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO member_service(name) VALUES(?)");
			ps.setString(1,name);
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
