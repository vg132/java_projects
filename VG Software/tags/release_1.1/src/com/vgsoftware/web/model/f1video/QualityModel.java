package com.vgsoftware.web.model.f1video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.web.data.f1video.QualityData;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-okt-05 - Changed parameter order, datasource as last parameter.
 * 2005-apr-30 - Document created.
 */
public class QualityModel
{
	public static QualityData getQuality(int id, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, name, sign, info FROM f1video_quality WHERE id=?");
			ps.setInt(1,id);
			rs=ps.executeQuery();
			if(rs.next())
				return(new QualityData(rs.getInt("id"),rs.getString("name"),rs.getString("sign"),rs.getString("info")));
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

	public static List<QualityData> getQualities(DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<QualityData> qualities=new ArrayList<QualityData>();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, name, sign,info FROM f1video_quality ORDER BY name_order");
			rs=ps.executeQuery();
			while(rs.next())
				qualities.add(new QualityData(rs.getInt("id"),rs.getString("name"),rs.getString("sign"),rs.getString("info")));
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
		return(qualities);
	}
}
