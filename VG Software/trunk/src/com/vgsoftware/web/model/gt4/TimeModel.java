package com.vgsoftware.web.model.gt4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.web.data.gt4.TimeData;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-15 - Document created.
 */
public class TimeModel
{
	public static List getTimes(int eventId, DataSource dataSource)
 	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		TimeData td=null;
		List times=new ArrayList();

		try
		{
			conn=dataSource.getConnection();			
			
			ps=conn.prepareStatement("SELECT t.id, t.member_id, t.event_id, t.added, t.control_type_id, c.name AS control_type_name, m.name, ti.best_time FROM member m,gt4_control c, gt4_time t, (SELECT min(t.time) AS best_time FROM gt4_time t WHERE event_id=? GROUP BY member_id ORDER BY best_time) ti WHERE ti.best_time=t.time AND t.control_type_id=c.id AND t.member_id=m.id ORDER BY best_time");
			ps.setInt(1,eventId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				td=new TimeData();
				td.setId(rs.getInt("id"));
				td.setMemberId(rs.getInt("member_id"));
				td.setEventId(rs.getInt("event_id"));
				td.setTime(rs.getInt("best_time"));
				td.setAdded(rs.getTimestamp("added"));
				td.setControlTypeId(rs.getInt("control_type_id"));
				td.setControlTypeName(rs.getString("control_type_name"));
				td.setMemberName(rs.getString("name"));
				times.add(td);
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
		return(times);
	}

	public static List getTimes(int eventId, int driverId, DataSource dataSource)
 	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		TimeData td=null;
		List times=new ArrayList();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT t.id, t.member_id, t.event_id, t.time, t.added, t.control_type_id, m.name AS member_name, c.name AS control_type_name FROM gt4_time t, member m, gt4_control c WHERE c.id=t.control_type_id AND m.id=t.member_id AND t.event_id=? AND t.member_id=? ORDER BY t.time ASC");
			ps.setInt(1,eventId);
			ps.setInt(2,driverId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				td=new TimeData();
				td.setId(rs.getInt("id"));
				td.setMemberId(rs.getInt("member_id"));
				td.setEventId(rs.getInt("event_id"));
				td.setTime(rs.getInt("time"));
				td.setAdded(rs.getTimestamp("added"));
				td.setControlTypeId(rs.getInt("control_type_id"));
				td.setControlTypeName(rs.getString("control_type_name"));
				td.setMemberName(rs.getString("member_name"));
				times.add(td);
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
		return(times);
	}
	
	public static boolean addTime(TimeData td, DataSource dataSource)
 	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO gt4_time(member_id,event_id,time,added,control_type_id) VALUES(?,?,?,?,?)");
			ps.setInt(1,td.getMemberId());
			ps.setInt(2,td.getEventId());
			ps.setInt(3,td.getTime());
			ps.setTimestamp(4,td.getAdded());
			ps.setInt(5,td.getControlTypeId());
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
	
	public static boolean removeTime(int timeId, int driverId, DataSource dataSource)
 	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		TimeData td=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("DELETE FROM gt4_time WHERE id=? and member_id=?");
			ps.setInt(1,timeId);
			ps.setInt(2,driverId);
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
}
