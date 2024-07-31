package com.vgsoftware.web.data.f1video;

import java.sql.Timestamp;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-30 - Document created.
 */
public class VideoData
{
	private int id=-1;
	private int year=-1;
	private int length=-1;
	private int laps=-1;
	private String day=null;
	private String month=null;
	private int filesize=-1;
	private int trackId=-1;
	private int memberId=-1;
	private int raceTypeId=-1;
	private int languageId=-1;
	private int mediaId=-1;
	private int channelId=-1;
	private int qualityId=-1;
	private String media=null;
	private String quality=null;
	private String comment=null;
	private String racetype=null;
	private String channel=null;
	private String title=null;
	private String country=null;
	private String track=null;
	private String videoid=null;
	private String language=null;
	private Timestamp added=null;
	
	public VideoData()
	{
	}
	/**
	 * @return Returns the added.
	 */
	public Timestamp getAdded()
	{
		return added;
	}
	/**
	 * @param added The added to set.
	 */
	public void setAdded(Timestamp added)
	{
		this.added=added;
	}
	/**
	 * @return Returns the channel.
	 */
	public String getChannel()
	{
		return channel;
	}
	/**
	 * @param channel The channel to set.
	 */
	public void setChannel(String channel)
	{
		this.channel=channel;
	}
	/**
	 * @return Returns the comment.
	 */
	public String getComment()
	{
		return comment;
	}
	/**
	 * @param comment The comment to set.
	 */
	public void setComment(String comment)
	{
		this.comment=comment;
	}
	/**
	 * @return Returns the country.
	 */
	public String getCountry()
	{
		return country;
	}
	/**
	 * @param country The country to set.
	 */
	public void setCountry(String country)
	{
		this.country=country;
	}
	/**
	 * @return Returns the day.
	 */
	public String getDay()
	{
		return day;
	}
	/**
	 * @param day The day to set.
	 */
	public void setDay(String day)
	{
		this.day=day;
	}
	/**
	 * @return Returns the filesize.
	 */
	public int getFilesize()
	{
		return filesize;
	}
	/**
	 * @param filesize The filesize to set.
	 */
	public void setFilesize(int filesize)
	{
		this.filesize=filesize;
	}
	/**
	 * @return Returns the id.
	 */
	public int getId()
	{
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id)
	{
		this.id=id;
	}
	/**
	 * @return Returns the language.
	 */
	public String getLanguage()
	{
		return language;
	}
	/**
	 * @param language The language to set.
	 */
	public void setLanguage(String language)
	{
		this.language=language;
	}
	/**
	 * @return Returns the laps.
	 */
	public int getLaps()
	{
		return laps;
	}
	/**
	 * @param laps The laps to set.
	 */
	public void setLaps(int laps)
	{
		this.laps=laps;
	}
	/**
	 * @return Returns the length.
	 */
	public int getLength()
	{
		return length;
	}
	/**
	 * @param length The length to set.
	 */
	public void setLength(int length)
	{
		this.length=length;
	}
	/**
	 * @return Returns the media.
	 */
	public String getMedia()
	{
		return media;
	}
	/**
	 * @param media The media to set.
	 */
	public void setMedia(String media)
	{
		this.media=media;
	}
	/**
	 * @return Returns the month.
	 */
	public String getMonth()
	{
		return month;
	}
	/**
	 * @param month The month to set.
	 */
	public void setMonth(String month)
	{
		this.month=month;
	}
	/**
	 * @return Returns the quality.
	 */
	public String getQuality()
	{
		return quality;
	}
	/**
	 * @param quality The quality to set.
	 */
	public void setQuality(String quality)
	{
		this.quality=quality;
	}
	/**
	 * @return Returns the racetype.
	 */
	public String getRacetype()
	{
		return racetype;
	}
	/**
	 * @param racetype The racetype to set.
	 */
	public void setRacetype(String racetype)
	{
		this.racetype=racetype;
	}
	/**
	 * @return Returns the title.
	 */
	public String getTitle()
	{
		return title;
	}
	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title)
	{
		this.title=title;
	}
	/**
	 * @return Returns the track.
	 */
	public String getTrack()
	{
		return track;
	}
	/**
	 * @param track The track to set.
	 */
	public void setTrack(String track)
	{
		this.track=track;
	}
	/**
	 * @return Returns the videoid.
	 */
	public String getVideoid()
	{
		return videoid;
	}
	/**
	 * @param videoid The videoid to set.
	 */
	public void setVideoid(String videoid)
	{
		this.videoid=videoid;
	}
	/**
	 * @return Returns the year.
	 */
	public int getYear()
	{
		return year;
	}
	/**
	 * @param year The year to set.
	 */
	public void setYear(int year)
	{
		this.year=year;
	}
	
	/**
	 * @return Returns the channelId.
	 */
	public int getChannelId()
	{
		return channelId;
	}
	/**
	 * @param channelId The channelId to set.
	 */
	public void setChannelId(int channelId)
	{
		this.channelId=channelId;
	}
	/**
	 * @return Returns the langaugeId.
	 */
	public int getLanguageId()
	{
		return languageId;
	}
	/**
	 * @param langaugeId The langaugeId to set.
	 */
	public void setLanguageId(int languageId)
	{
		this.languageId=languageId;
	}
	/**
	 * @return Returns the mediaId.
	 */
	public int getMediaId()
	{
		return mediaId;
	}
	/**
	 * @param mediaId The mediaId to set.
	 */
	public void setMediaId(int mediaId)
	{
		this.mediaId=mediaId;
	}
	/**
	 * @return Returns the memberId.
	 */
	public int getMemberId()
	{
		return memberId;
	}
	/**
	 * @param memberId The memberId to set.
	 */
	public void setMemberId(int memberId)
	{
		this.memberId=memberId;
	}
	/**
	 * @return Returns the qualityId.
	 */
	public int getQualityId()
	{
		return qualityId;
	}
	/**
	 * @param qualityId The qualityId to set.
	 */
	public void setQualityId(int qualityId)
	{
		this.qualityId=qualityId;
	}
	/**
	 * @return Returns the raceTypeId.
	 */
	public int getRaceTypeId()
	{
		return raceTypeId;
	}
	/**
	 * @param raceTypeId The raceTypeId to set.
	 */
	public void setRaceTypeId(int raceTypeId)
	{
		this.raceTypeId=raceTypeId;
	}
	/**
	 * @return Returns the trackId.
	 */
	public int getTrackId()
	{
		return trackId;
	}
	/**
	 * @param trackId The trackId to set.
	 */
	public void setTrackId(int trackId)
	{
		this.trackId=trackId;
	}
	
	public String getHtmlComment()
	{
		return(comment.replace("\n","<br/>"));
	}
	public String getTextTime()
	{
		return(added.toString().substring(0,16));
	}
}
