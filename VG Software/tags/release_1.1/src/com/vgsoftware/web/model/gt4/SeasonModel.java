package com.vgsoftware.web.model.gt4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.web.data.gt4.SeasonData;
import com.vgsoftware.web.data.gt4.SeasonResultData;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-15 - Document created.
 */
public class SeasonModel
{
	public static SeasonData getSeason(int seasonId,DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		SeasonData sd=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, name, point_rounds FROM gt4_season WHERE id=?");
			ps.setInt(1,seasonId);
			rs=ps.executeQuery();
			if(rs.next())
				sd=new SeasonData(rs.getInt("id"),rs.getString("name"),rs.getInt("point_rounds"));
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
		return(sd);
	}
	
	public static List getSeasons(DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List seasons=new ArrayList();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, name, point_rounds FROM gt4_season ORDER BY name");
			rs=ps.executeQuery();
			while(rs.next())
				seasons.add(new SeasonData(rs.getInt("id"),rs.getString("name"),rs.getInt("point_rounds")));
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
		return(seasons);
	}
	
	public static boolean removeSeason(int seasonId, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("DELETE FROM gt4_season WHERE id=?");
			ps.setInt(1,seasonId);
			if(ps.executeUpdate()==1)
				return(true);
			else
				return(false);
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
	
	public static boolean addSeason(SeasonData seasonData, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO gt4_season(name,point_rounds) VALUES(?,?)");
			ps.setString(1,seasonData.getName());
			ps.setInt(2,seasonData.getPointRounds());
			if(ps.executeUpdate()==1)
				return(true);
			else
				return(false);
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

	public static boolean updateSeason(SeasonData seasonData, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("UPDATE gt4_season SET name=?, point_rounds=? WHERE id=?");
			ps.setString(1,seasonData.getName());
			ps.setInt(2,seasonData.getPointRounds());
			ps.setInt(3,seasonData.getId());
			if(ps.executeUpdate()==1)
				return(true);
			else
				return(false);
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
	
	public static List getSeasonResults(int seasonId,DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List results=new ArrayList();
		SeasonResultData srd=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT t.member_id, m.username, t.event_id, MIN(time) AS best_time FROM gt4_time t, gt4_event e, member m WHERE m.id=t.member_id AND e.season_id=? AND t.event_id=e.id GROUP BY t.event_id, t.member_id ORDER BY t.event_id, best_time");
			ps.setInt(1,seasonId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				srd=new SeasonResultData();
				srd.setEventId(rs.getInt("event_id"));
				srd.setBestTime(rs.getInt("best_time"));
				srd.setMemberId(rs.getInt("member_id"));
				srd.setUsername(rs.getString("username"));
				results.add(srd);
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
		return(results);
	}
}
