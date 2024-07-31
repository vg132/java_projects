package com.vgsoftware.web.model.f1video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.web.data.f1video.SimpleData;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-okt-05 - Changed parameter order, datasource as last parameter.
 * 2005-apr-30 - Document created.
 */
public class ChannelModel
{
	public static SimpleData getChannel(int id, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, name, extra FROM f1video_channel WHERE id=? ORDER BY extra");
			ps.setInt(1,id);
			rs=ps.executeQuery();
			if(rs.next())
				return(new SimpleData(rs.getInt("id"),rs.getString("name"),rs.getInt("extra")));
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

	public static List<SimpleData> getChannels(DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<SimpleData> channels=new ArrayList<SimpleData>();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, name, extra FROM f1video_channel ORDER BY name_order");
			rs=ps.executeQuery();
			while(rs.next())
				channels.add(new SimpleData(rs.getInt("id"),rs.getString("name"),rs.getInt("extra")));
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
		return(channels);
	}
}
