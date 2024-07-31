package com.vgsoftware.web.data.f1video;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-apr-30 - Document created.
 */
public class TrackInfoData
{
	private int year=-1;
	private int month=-1;
	private int day=-1;
	private int laps=-1;
	private int countryId=-1;
	private int trackId=-1;
	private String country=null;
	private String track=null;
	
	public TrackInfoData()
	{
	}
	
	public TrackInfoData(int trackId, String track, int countryId, String country, int year, int month, int day, int laps)
	{
		this.trackId=trackId;
		this.track=track;
		this.countryId=countryId;
		this.country=country;
		this.year=year;
		this.month=month;
		this.day=day;
		this.laps=laps;
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
	 * @return Returns the countryId.
	 */
	public int getCountryId()
	{
		return countryId;
	}
	/**
	 * @param countryId The countryId to set.
	 */
	public void setCountryId(int countryId)
	{
		this.countryId=countryId;
	}
	/**
	 * @return Returns the day.
	 */
	public int getDay()
	{
		return day;
	}
	/**
	 * @param day The day to set.
	 */
	public void setDay(int day)
	{
		this.day=day;
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
	 * @return Returns the month.
	 */
	public int getMonth()
	{
		return month;
	}
	/**
	 * @param month The month to set.
	 */
	public void setMonth(int month)
	{
		this.month=month;
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
}
