package com.vgsoftware.web.model.f1video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.vgsoftware.web.data.f1video.VideoData;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-okt-05 - Changed parameter order, datasource as last parameter.
 * 2005-apr-30 - Document created.
 */
public class VideoModel
{
	public final static int RACE_VIDEOS=1;
	public final static int OTHER_VIDEOS=2;

	public static VideoData getVideo(int id, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("SELECT v.member_id,v.id,v.title, c.name as country, t.name as track, v.year, v.length, rt.name as racetype, ch.name as channel, ti.laps, lpad(ti.day,2,0) AS day, lpad(ti.month,2,0) AS month, v.filesize, m.name AS media, q.name AS quality, v.comment, v.added, v.videoid, l.name AS language, v.track_id, v.racetype_id, v.media_id, v.language_id, v.quality_id, v.channel_id FROM f1video_country c, f1video_language l, f1video_track t, f1video_video v, f1video_racetype rt, f1video_channel ch, f1video_track_info ti, f1video_media m, f1video_quality q WHERE v.track_id=t.id AND v.language_id=l.id AND t.extra=c.id AND m.id=v.media_id AND q.id=v.quality_id AND t.id=ti.track_id AND v.year=ti.year AND v.racetype_id=rt.id AND v.channel_id=ch.id AND v.id=? ORDER BY country, track, year");
			ps.setInt(1,id);
			rs=ps.executeQuery();
			if(rs.next())
			{
				VideoData vd=new VideoData();
				vd.setMemberId(rs.getInt("member_id"));
				vd.setId(rs.getInt("id"));
				vd.setTitle(rs.getString("title"));
				vd.setCountry(rs.getString("country"));
				vd.setTrack(rs.getString("track"));
				vd.setYear(rs.getInt("year"));
				vd.setMonth(rs.getString("month"));
				vd.setDay(rs.getString("day"));
				vd.setLaps(rs.getInt("laps"));
				vd.setLength(rs.getInt("length"));
				vd.setRacetype(rs.getString("racetype"));
				vd.setChannel(rs.getString("channel"));
				vd.setFilesize(rs.getInt("filesize"));
				vd.setMedia(rs.getString("media"));
				vd.setQuality(rs.getString("quality"));
				vd.setLanguage(rs.getString("language"));
				vd.setComment(rs.getString("comment"));
				vd.setAdded(rs.getTimestamp("added"));
				vd.setVideoid(rs.getString("videoid"));
				vd.setTrackId(rs.getInt("track_id"));
				vd.setChannelId(rs.getInt("channel_id"));
				vd.setQualityId(rs.getInt("quality_id"));
				vd.setMediaId(rs.getInt("media_id"));
				vd.setLanguageId(rs.getInt("language_id"));
				vd.setRaceTypeId(rs.getInt("racetype_id"));
				return(vd);
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

	public static List<VideoData> getVideos(int memberId, int type, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<VideoData> videos=new ArrayList<VideoData>();
		VideoData vd=null;
		
		try
		{
			conn=dataSource.getConnection();
			if(type==VideoModel.RACE_VIDEOS)
				ps=conn.prepareStatement("SELECT v.id,v.title, c.name as country, t.name as track, v.year, v.length, rt.name as racetype, ch.name as channel, ti.laps, lpad(ti.day,2,0) AS day, lpad(ti.month,2,0) AS month, v.filesize, m.name AS media, q.name AS quality, v.comment, v.added, v.videoid, l.name AS language, v.track_id, v.racetype_id, v.media_id, v.language_id, v.quality_id, v.channel_id FROM f1video_country c, f1video_language l, f1video_track t, f1video_video v, f1video_racetype rt, f1video_channel ch, f1video_track_info ti, f1video_media m, f1video_quality q WHERE v.track_id=t.id AND v.language_id=l.id AND t.extra=c.id AND m.id=v.media_id AND q.id=v.quality_id AND t.id=ti.track_id AND v.year=ti.year AND v.racetype_id=rt.id AND v.channel_id=ch.id AND member_id=? AND title is null ORDER BY country, track, year");
			else if(type==VideoModel.OTHER_VIDEOS)
				ps=conn.prepareStatement("SELECT v.id,v.title, c.name as country, t.name as track, v.year, v.length, rt.name as racetype, ch.name as channel, ti.laps, lpad(ti.day,2,0) AS day, lpad(ti.month,2,0) AS month, v.filesize, m.name AS media, q.name AS quality, v.comment, v.added, v.videoid, l.name AS language, v.track_id, v.racetype_id, v.media_id, v.language_id, v.quality_id, v.channel_id FROM f1video_country c, f1video_language l, f1video_track t, f1video_video v, f1video_racetype rt, f1video_channel ch, f1video_track_info ti, f1video_media m, f1video_quality q WHERE v.track_id=t.id AND v.language_id=l.id AND t.extra=c.id AND m.id=v.media_id AND q.id=v.quality_id AND t.id=ti.track_id AND v.year=ti.year AND v.racetype_id=rt.id AND v.channel_id=ch.id AND member_id=? AND title is not null ORDER BY title, year");
			ps.setInt(1,memberId);
			rs=ps.executeQuery();
			while(rs.next())
			{
				vd=new VideoData();
				vd.setMemberId(memberId);
				vd.setId(rs.getInt("id"));
				vd.setTitle(rs.getString("title"));
				vd.setCountry(rs.getString("country"));
				vd.setTrack(rs.getString("track"));
				vd.setYear(rs.getInt("year"));
				vd.setMonth(rs.getString("month"));
				vd.setDay(rs.getString("day"));
				vd.setLaps(rs.getInt("laps"));
				vd.setLength(rs.getInt("length"));
				vd.setRacetype(rs.getString("racetype"));
				vd.setChannel(rs.getString("channel"));
				vd.setFilesize(rs.getInt("filesize"));
				vd.setMedia(rs.getString("media"));
				vd.setQuality(rs.getString("quality"));
				vd.setLanguage(rs.getString("language"));
				vd.setComment(rs.getString("comment"));
				vd.setAdded(rs.getTimestamp("added"));
				vd.setVideoid(rs.getString("videoid"));
				vd.setTrackId(rs.getInt("track_id"));
				vd.setChannelId(rs.getInt("channel_id"));
				vd.setQualityId(rs.getInt("quality_id"));
				vd.setMediaId(rs.getInt("media_id"));
				vd.setLanguageId(rs.getInt("language_id"));
				vd.setRaceTypeId(rs.getInt("racetype_id"));
				videos.add(vd);
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
		return(videos);
	}
	
	public static boolean updateVideo(VideoData vd, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("UPDATE f1video_video SET added=added, track_id=?, year=?, racetype_id=?, length=?, language_id=?, media_id=?, channel_id=?, comment=?, title=?, quality_id=?, videoid=?, filesize=? WHERE id=? AND member_id=?");

			ps.setInt(1,vd.getTrackId());
			ps.setInt(2,vd.getYear());
			ps.setInt(3,vd.getRaceTypeId());
			ps.setInt(4,vd.getLength());
			ps.setInt(5,vd.getLanguageId());
			ps.setInt(6,vd.getMediaId());
			ps.setInt(7,vd.getChannelId());
			ps.setString(8,vd.getComment());
			ps.setString(9,vd.getTitle());
			ps.setInt(10,vd.getQualityId());
			ps.setString(11,vd.getVideoid());
			ps.setInt(12,vd.getFilesize());
			ps.setInt(13,vd.getId());
			ps.setInt(14,vd.getMemberId());
			
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
	
	public static boolean addVideo(VideoData vd, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("INSERT INTO f1video_video(member_id,track_id,year,racetype_id,length,language_id,media_id,channel_id,comment,title,added,quality_id,videoid,filesize) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1,vd.getMemberId());
			ps.setInt(2,vd.getTrackId());
			ps.setInt(3,vd.getYear());
			ps.setInt(4,vd.getRaceTypeId());
			ps.setInt(5,vd.getLength());
			ps.setInt(6,vd.getLanguageId());
			ps.setInt(7,vd.getMediaId());
			ps.setInt(8,vd.getChannelId());
			ps.setString(9,vd.getComment());
			ps.setString(10,vd.getTitle());
			ps.setTimestamp(11,vd.getAdded());
			ps.setInt(12,vd.getQualityId());
			ps.setString(13,vd.getVideoid());
			ps.setInt(14,vd.getFilesize());
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
	
	public static boolean removeVideo(int memberId, int videoId, DataSource dataSource)
	throws SQLException
	{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try
		{
			conn=dataSource.getConnection();
			ps=conn.prepareStatement("DELETE FROM f1video_video WHERE id=? AND member_id=?");
			ps.setInt(1,videoId);
			ps.setInt(2,memberId);
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

