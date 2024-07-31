package com.vgsoftware.web.model.gt4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.web.data.gt4.EventData;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-15 - Document created.
 */
public class EventModel
{
	public static EventData getEvent(int id, DataSource dataSource)
 	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		EventData ed=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, season_id, track, type, start,end FROM gt4_event WHERE id=?");
			ps.setInt(1,id);
			rs=ps.executeQuery();
			if(rs.next())
			{
				ed=new EventData();
				ed.setId(rs.getInt("id"));
				ed.setSeasonId(rs.getInt("season_id"));
				ed.setTrack(rs.getString("track"));
				ed.setType(rs.getString("type"));
				ed.setStart(rs.getTimestamp("start"));
				ed.setEnd(rs.getTimestamp("end"));
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
		return(ed);
	}

	/**
	 * Checks if the event is open, e.g the events start time is in the past.
	 * 
	 * @param eventId id of the event to check the status on.
	 * @param dataSource a datasource with a active connection.
	 * 
	 * @return true if the events start time is in the past, otherwise false.
	 * 
	 * @throws SQLException
	 */
	public static boolean isOpen(int eventId, DataSource dataSource)
 	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id FROM gt4_event WHERE start<? AND id=?");
			ps.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
			ps.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
			ps.setInt(3,eventId);
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
	
	/**
	 * Checks if the event is active, e.g. is open for new times, 
	 * start time has passed but not end time.
	 * 
	 * @param eventId id of the event to check the status on.
	 * @param dataSource a datasource with a active connection.
	 * 
	 * @return true if the events is active, otherwise false.
	 * 
	 * @throws SQLException
	 */
	public static boolean isActive(int eventId, DataSource dataSource)
 	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id FROM gt4_event WHERE start<? AND end>? AND event=?");
			ps.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
			ps.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
			ps.setInt(3,eventId);
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
	
	/**
	 * Gets a list of all currently active events.
	 * 
	 * @return a list of all active events.
	 * 
	 * @throws SQLException
	 */
	public static List getActiveEvents(DataSource dataSource)
 	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List events=new ArrayList();
		EventData ed=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, season_id, track, type, start,end FROM gt4_event WHERE start<? AND end>? ORDER BY start ASC");
			ps.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
			ps.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
			rs=ps.executeQuery();
			while(rs.next())
			{
				ed=new EventData();
				ed.setId(rs.getInt("id"));
				ed.setSeasonId(rs.getInt("season_id"));
				ed.setTrack(rs.getString("track"));
				ed.setType(rs.getString("type"));
				ed.setStart(rs.getTimestamp("start"));
				ed.setEnd(rs.getTimestamp("end"));
				events.add(ed);
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
		return(events);
	}

	
	/**
	 * Gets all events from the selected season.
	 * 
	 * @return a list with all events from the selected season.
	 * 
	 * @throws SQLException
	 */
	public static List getEvents(int seasonId, DataSource dataSource)
 	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List events=new ArrayList();
		EventData ed=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, season_id, track, type, start,end FROM gt4_event WHERE season_id=? ORDER BY start ASC");
			ps.setInt(1,seasonId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				ed=new EventData();
				ed.setId(rs.getInt("id"));
				ed.setSeasonId(rs.getInt("season_id"));
				ed.setTrack(rs.getString("track"));
				ed.setType(rs.getString("type"));
				ed.setStart(rs.getTimestamp("start"));
				ed.setEnd(rs.getTimestamp("end"));
				events.add(ed);
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
		return(events);
	}

	/**
	 * Gets all events that has a start time in the past.
	 * 
	 * @return a list with all events from the selected season.
	 * 
	 * @throws SQLException
	 */
	public static List getPastEvents(int seasonId, DataSource dataSource)
 	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List events=new ArrayList();
		EventData ed=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, season_id, track, type, start,end FROM gt4_event WHERE season_id=? AND start<? ORDER BY start ASC");
			ps.setInt(1,seasonId);
			ps.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
			rs=ps.executeQuery();
			while(rs.next())
			{
				ed=new EventData();
				ed.setId(rs.getInt("id"));
				ed.setSeasonId(rs.getInt("season_id"));
				ed.setTrack(rs.getString("track"));
				ed.setType(rs.getString("type"));
				ed.setStart(rs.getTimestamp("start"));
				ed.setEnd(rs.getTimestamp("end"));
				events.add(ed);
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
		return(events);
	}
	
	/**
	 * Gets all events that has a start time in the future.
	 * 
	 * @return a list with all events from the selected season.
	 * 
	 * @throws SQLException
	 */
	public static List getFutureEvents(int seasonId, DataSource dataSource)
 	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List events=new ArrayList();
		EventData ed=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, season_id, track, type, start,end FROM gt4_event WHERE season_id=? AND start>? ORDER BY start ASC");
			ps.setInt(1,seasonId);
			ps.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
			rs=ps.executeQuery();
			while(rs.next())
			{
				ed=new EventData();
				ed.setId(rs.getInt("id"));
				ed.setSeasonId(rs.getInt("season_id"));
				ed.setTrack(rs.getString("track"));
				ed.setType(rs.getString("type"));
				ed.setStart(rs.getTimestamp("start"));
				ed.setEnd(rs.getTimestamp("end"));
				events.add(ed);
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
		return(events);
	}
	
	public static boolean addEvent(EventData ed, DataSource dataSource)
 	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO gt4_event(season_id,track,type,start,end) VALUES(?,?,?,?,?)");
			ps.setInt(1,ed.getSeasonId());
			ps.setString(2,ed.getTrack());
			ps.setString(3,ed.getType());
			ps.setTimestamp(4,ed.getStart());
			ps.setTimestamp(5,ed.getEnd());
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

	public static boolean updateEvent(EventData ed, DataSource dataSource)
 	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("UPDATE gt4_event SET season_id=?,track=?,type=?,start=?,end=? WHERE id=?");
			ps.setInt(1,ed.getSeasonId());
			ps.setString(2,ed.getTrack());
			ps.setString(3,ed.getType());
			ps.setTimestamp(4,ed.getStart());
			ps.setTimestamp(5,ed.getEnd());
			ps.setInt(6,ed.getId());
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
	
	public static boolean removeEvent(int eventId,DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("DELETE FROM gt4_event WHERE id=?");
			ps.setInt(1,eventId);
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
/*
 	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		EventData ed=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("");
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
	}
*/
}
