package com.vgsoftware.web.model.f1video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.vgsoftware.web.data.f1video.TrackInfoData;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-okt-05 - Changed parameter order, datasource as last parameter.
 * 2005-apr-30 - Document created.
 */
public class TrackInfoModel
{
	public static TrackInfoData getTrackInfo(int trackId, int year,DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT ti.track_id, ti.country_id, ti.year, ti.month, ti.day, ti.laps, c.name AS country, t.name AS track FROM f1video_track_info ti, f1video_country c, f1video_track t WHERE t.id=ti.track_id AND c.id=ti.country_id AND t.id=? AND ti.year=?");
			ps.setInt(1,trackId);
			ps.setInt(2,year);
			rs=ps.executeQuery();
			if(rs.next())
				return(new TrackInfoData(rs.getInt("track_id"),rs.getString("track"),rs.getInt("country_id"),rs.getString("country"),rs.getInt("year"),rs.getInt("month"),rs.getInt("day"),rs.getInt("laps")));
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
}
