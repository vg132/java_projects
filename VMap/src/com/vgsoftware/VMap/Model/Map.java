package com.vgsoftware.VMap.Model;

import com.vgsoftware.VMap.Util.MathUtil;
import com.vgsoftware.VMap.Util.Point;
import com.vgsoftware.VMap.Util.Point2D;

public class Map
{
	// Offsets for the map tile indexes in the vertical direction
	private final int[] mapOffsetX = new int[] { -1, 0, 1, -1, 0, 1, -1, 0, 1 };
	private final int[] mapOffsetY = new int[] { -1, -1, -1, 0, 0, 0, 1, 1, 1 };

	private Position currentPosition;
	private Point mapPosition;
	
	public String[] getMapFiles()
	{
		String[] files=new String[9];
		if (getCurrentTile() != null)
		{
			for (int imageIndex = 0; imageIndex < 9; imageIndex++)
			{
				files[imageIndex]=getCurrentTile().getZoom()+"\\"+(int)(getCurrentTile().getPoint().getX()+ mapOffsetX[imageIndex])+"\\"+(int)(getCurrentTile().getPoint().getY() + mapOffsetY[imageIndex]);
			}
		}
		return files;
	}

	public Point getMapPosition()
	{
		return this.mapPosition;
	}
	
	public void setMapPosition(Point mapPosition)
	{
		this.mapPosition=mapPosition;
	}

	public Tile getCurrentTile()
	{
		if (getCurrentPosition() != null)
		{
			float xtile = (float)(getCurrentPosition().getLongitude() + 180) / 360 * (1 << getCurrentPosition().getZoom());
			float ytile = (float)(1 - MathUtil.log(Math.tan(getCurrentPosition().getLatitude() * Math.PI / 180) + 1 / Math.cos(getCurrentPosition().getLatitude() * Math.PI / 180)) / Math.PI) / 2 * (1 << getCurrentPosition().getZoom());

			Tile tile = new Tile(new Point2D(xtile, ytile), getCurrentPosition().getZoom());
			setMapPosition( new Point((int)Math.floor((256 / 2) - ((xtile - Math.floor(xtile)) * 256)), (int)Math.floor((256 / 2) - ((ytile - Math.floor(ytile)) * 256))));

			return tile;
		}
		return null;
	}

	public Position getCurrentPosition()
	{
		return this.currentPosition;
	}
	
	public void setCurrentPosition(Position currentPosition)
	{
		this.currentPosition=currentPosition;
	}
}
