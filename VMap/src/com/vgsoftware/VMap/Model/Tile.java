package com.vgsoftware.VMap.Model;

import com.vgsoftware.VMap.Util.Point2D;

public class Tile
{
	private int zoom;
	private Point2D point;

	public Tile(Point2D point, int zoom)
	{
		this.point=point;
		this.zoom=zoom;
	}
	
	public void setZoom(int zoom)
	{
		this.zoom=zoom;
	}
	
	public int getZoom()
	{
		return zoom;
	}
	
	public void setPoint(Point2D point)
	{
		this.point=point;
	}
	
	public Point2D getPoint()
	{
		return point;
	}
}
