package com.vgsoftware.VMap.Model;

import javax.microedition.lcdui.Image;

import com.vgsoftware.VMap.Util.Log;

public class TileManager
{
	private TileLoader tileLoader;
	
	public TileManager()
	{
		this.tileLoader=new TileLoader();
	}
	
	public Image getTile(String path)
	{
		if(tileLoader.isReady())
		{
			Log.log("Get Tile: '"+path+"'");
			return tileLoader.getTile(path);
		}
		return null;
	}
	
	public boolean isReady()
	{
		return tileLoader.isReady();
	}
}
