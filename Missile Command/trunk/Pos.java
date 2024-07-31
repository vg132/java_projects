/**
 * The pos object class
 * Basic Point class that use double insted of int
 * (C) Viktor Gars 2002
 */

public class Pos
{
	private double x=-1;
	private double y=-1;

	Pos(Pos pos)
	{
		this.x=pos.getX();
		this.y=pos.getY();
	}

	Pos(double x,double y)
	{
		this.x=x;
		this.y=y;
	}

	Pos(int x,int y)
	{
		this.x=x;
		this.y=y;
	}

	public void setX(double x)
	{
		this.x=x;
	}

	public void setY(double y)
	{
		this.y=y;
	}

	public double getX()
	{
		return(x);
	}

	public double getY()
	{
		return(y);
	}

	public int getIY()
	{
		return((int)y);
	}

	public int getIX()
	{
		return((int)x);
	}
}