package com.vgsoftware.web.model.gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.web.data.gallery.AlbumData;

public class AlbumModel
{
	public static List<AlbumData> getAlbums(DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		AlbumData ad=null;
		List<AlbumData> albums=new ArrayList<AlbumData>();

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT id, name, description FROM gallery_album ORDER BY name");
			rs=ps.executeQuery();
			while(rs.next())
				albums.add(new AlbumData(rs.getInt("id"),rs.getString("name"),rs.getString("description")));
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
		return(albums);
	}

	public static boolean addAlbum(AlbumData ad, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO gallery_album(name, description) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1,ad.getName());
			ps.setString(2,ad.getDescription());
			if(ps.execute())
			{
				rs=ps.getGeneratedKeys();
				if(rs.next())
				{
					ad.setId(rs.getInt(1));
					return(true);
				}
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
		return(false);
	}
}
