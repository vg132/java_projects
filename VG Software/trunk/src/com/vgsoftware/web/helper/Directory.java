package com.vgsoftware.web.helper;

/**
 * @author Viktor
 * @version 1.0
 * 
 * History:
 * 2005-jan-12 - Class created.
 */
public class Directory
{
	private String path=null;
	private int level=99;
	
	public Directory(String path, int level)
	{
		this.path=path;
		this.level=level;
	}

	/**
	 * @return Returns the level.
	 */
	public int getLevel()
	{
		return(level);
	}

	/**
	 * @return Returns the path.
	 */
	public String getPath()
	{
		return(path);
	}
	
	public boolean hasAccess(String uri, int userLevel)
	{
		if((uri.startsWith(path))&&(userLevel<level))
			return(false);
		return(true);
	}
}
