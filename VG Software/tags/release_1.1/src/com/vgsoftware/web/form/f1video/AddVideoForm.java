package com.vgsoftware.web.form.f1video;

import org.apache.struts.action.ActionForm;

/**
 * @author viktor
 * @version 1.0
 * 
 * History:
 * 2005-maj-01 - Document created.
 */
public class AddVideoForm extends ActionForm
{
	private int id=-1;
	private int year=-1;
	private int media=-1;
	private int track=-1;
	private int length=-1;
	private int channel=-1;
	private int quality=-1;
	private int racetype=-1;
	private int language=-1;
	private int filesize=-1;
	private String title=null;
	private String videoid=null;
	private String comment=null;
	
	public AddVideoForm()
	{
	}

	/**
	 * @return Returns the channel.
	 */
	public int getChannelId()
	{
		return(channel);
	}

	/**
	 * @param channel The channel to set.
	 */
	public void setChannel(String channel)
	{
		this.channel=Integer.parseInt(channel.substring(0,channel.indexOf("|")));
	}

	/**
	 * @return Returns the comment.
	 */
	public String getComment()
	{
		return(comment);
	}

	/**
	 * @param comment The comment to set.
	 */
	public void setComment(String comment)
	{
		this.comment=comment;
	}

	/**
	 * @return Returns the language.
	 */
	public int getLanguage()
	{
		return(language);
	}

	/**
	 * @param language The language to set.
	 */
	public void setLanguage(int language)
	{
		this.language=language;
	}

	/**
	 * @return Returns the length.
	 */
	public int getLength()
	{
		return(length);
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
	public int getMediaId()
	{
		return(media);
	}

	/**
	 * @param media The media to set.
	 */
	public void setMedia(String media)
	{
		this.media=Integer.parseInt(media.substring(0,media.indexOf("|")));
	}

	/**
	 * @return Returns the quality.
	 */
	public int getQuality()
	{
		return(quality);
	}

	/**
	 * @param quality The quality to set.
	 */
	public void setQuality(int quality)
	{
		this.quality=quality;
	}

	/**
	 * @return Returns the racetype.
	 */
	public int getRacetypeId()
	{
		return(racetype);
	}

	/**
	 * @param racetype The racetype to set.
	 */
	public void setRacetype(String racetype)
	{
		this.racetype=Integer.parseInt(racetype.substring(0,racetype.indexOf("|")));
	}

	/**
	 * @return Returns the track.
	 */
	public int getTrack()
	{
		return(track);
	}

	/**
	 * @param track The track to set.
	 */
	public void setTrack(int track)
	{
		this.track=track;
	}

	/**
	 * @return Returns the videoid.
	 */
	public String getVideoid()
	{
		return(videoid);
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
		return(year);
	}

	/**
	 * @param year The year to set.
	 */
	public void setYear(int year)
	{
		this.year=year;
	}
	
	/**
	 * @return Returns the title.
	 */
	public String getTitle()
	{
		return(title);
	}

	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title)
	{
		if(!title.trim().equals(""))
			this.title=title.trim();
	}

	/**
	 * @return Returns the filesize.
	 */
	public int getFilesize()
	{
		return(filesize);
	}
	/**
	 * @param filesize The filesize to set.
	 */
	public void setFilesize(int filesize)
	{
		this.filesize=filesize;
	}

	/**
	 * @return Returns the channel.
	 */
	public String getChannel()
	{
		return("");
	}
	/**
	 * @return Returns the media.
	 */
	public String getMedia()
	{
		return("");
	}
	/**
	 * @return Returns the racetype.
	 */
	public String getRacetype()
	{
		return("");
	}
	/**
	 * @return Returns the id.
	 */
	public int getId()
	{
		return(id);
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id)
	{
		this.id=id;
	}
}