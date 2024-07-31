package com.vgsoftware.VMap.Model;

import com.vgsoftware.VMap.Util.Log;
import com.vgsoftware.VMap.Util.MathUtil;

public class Position
{
	private final int MaxZoom=17;
	private final int MinZoom=2;
	private final double MaxY=85.0511287798066;
	
	private double longitude;
	private double latitude;
	private int zoom;

	public Position(int zoom, double longitude, double latitude)
	{
		this.zoom = zoom;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return this.longitude;
	}
	
	public void setLongitude(double longitude)
	{
		this.longitude=longitude;
	}

	public double getLatitude()
	{
		return this.latitude;
	}
	
	public void setLatitude(double latitude)
	{
		this.latitude=latitude;
	}

	public int getZoom()
	{
		return this.zoom;
	}
	
	public void setZoom(int zoom)
	{
		this.zoom=zoom;
	}

	public void MoveDown()
	{
		Log.log("Move down: '"+getSpeedY()+"'");
		this.latitude -= getSpeedY();
	}

	public void MoveUp()
	{
		Log.log("Move up: '"+getSpeedY()+"'");
		this.latitude += getSpeedY();
	}

	public void MoveLeft()
	{
		Log.log("Move left: '"+getSpeedX()+"'");
		this.longitude -= getSpeedX();
	}

	public void MoveRight()
	{
		Log.log("Move right: '"+getSpeedX()+"'");
		this.longitude += getSpeedX();
	}

	public boolean ZoomIn()
	{
		if (this.zoom < MaxZoom)
		{
			this.zoom++;
			return true;
		}
		return false;
	}

	public boolean ZoomOut()
	{
		if (this.zoom > MinZoom)
		{
			this.zoom--;
			return true;
		}
		return false;
	}

	private double getSpeedX()
	{
		switch(this.zoom)
		{
		case 2:
			return	1.7578125;
		case 3:
			return	0.87890625;
		case 4:
			return	0.439453125;
		case 5:
			return	0.2197265625;
		case 6:
			return	0.10986328125;
		case 7:
			return	0.054931640625;
		case 8:
			return	0.0274658203125;
		case 9:
			return	0.01373291015625;
		case 10:
			return	0.006866455078125;
		case 11:
			return	0.0034332275390625;
		case 12:
			return	0.00171661376953125;
		case 13:
			return	0.000858306884765625;
		case 14:
			return	0.0004291534423828125;
		case 15:
			return	0.00021457672119140625;
		case 16:
			return	0.00010728836059570313;
		case 17:
			return 0.000053644180297851563;
		case 18:
			return 0.000026822090148925781;
		}
		return 0.0;
		
		/*
		Log.log("Zoom: '"+this.zoom+"'");
		Log.log("MathUtil.pow: '"+MathUtil.pow(2, this.zoom)+"'");
		Log.log("Part 2: '"+((double)5 / (double)MathUtil.pow(2, this.zoom) * (double)360)+"'");
		Log.log("Part 3: '"+(((double)5 / (double)MathUtil.pow(2, this.zoom) * (double)360) / (double)256)+"'");
		return ((double)5 / (double)MathUtil.pow(2, this.zoom) * (double)360) / (double)256;
		*/
	}

	private double getSpeedY()
	{
		double n = Math.PI - 2 * Math.PI * 5 / MathUtil.pow(2, this.zoom);
		return ((MaxY - (180 / Math.PI * MathUtil.atan(0.5 * (MathUtil.exp(n) - MathUtil.exp(-n))))) / 256) * 5;
	}
}
