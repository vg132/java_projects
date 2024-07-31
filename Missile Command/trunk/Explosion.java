/**
 * The explosion object class
 * (C) Viktor Gars 2002
 */

import java.awt.*;

public class Explosion implements Item
{
	private Pos center=null;
	private int speed=-1;
	private int speedCount=-1;
	private int curSize=1;
	private int size=-1;
	private int state=-1;
	private boolean finished=false;

	public Explosion(Pos center, int speed, int size, int state)
	{
		this.center=center;
		this.speed=speed;
		speedCount=speed;
		this.size=size;
		this.curSize=1;
		this.state=state;
	}

	//Expand the explosion
	public void move()
	{
		speedCount--;
		if(speedCount<=0&&size>=curSize)
		{
			curSize++;
			speedCount=speed;
		}
		else if(size<curSize)
			finished=true;
	}

	//Check if explosion is finished
	public boolean finished()
	{
		return(finished);
	}

	//Paint a different explosion if it is a click or a hit
	public void paint(Graphics g)
	{
		if(state==CLICK)
		{
			g.setXORMode(new Color(200-curSize*3,200-curSize*3,curSize*3));
			g.fillOval(center.getIX()-(curSize/2),center.getIY()-(curSize/2),curSize,curSize);
			g.setPaintMode();
		}
		else if(state==HIT)
		{
			g.setXORMode(new Color(curSize*3,200-curSize*3,200-curSize*3));
			g.fillOval(center.getIX()-(curSize/2),center.getIY()-(curSize/2),curSize,curSize);
			g.setPaintMode();
		}
	}

	//Check if point is inside of the explosion
	public boolean hitTest(int x, int y)
	{
		int height=0;
		int width=0;

		if(x>center.getX())
			width=x-center.getIX();
		else
			width=center.getIX()-x;
		if(y>center.getY())
			height=y-center.getIY();
		else
			height=center.getIY()-y;

		//Calculate the distense from the point to the center of the explosion,
		//if it is greater then the size of the explosion, the point is outside
		//of the explosion
		if(Math.sqrt(Math.pow(height,2)+Math.pow(width,2))<=(curSize/2))
			return(true);
		else
			return(false);
	}

	public boolean hitTest(Pos xy)
	{
		if(xy!=null)
			return(hitTest(xy.getIX(),xy.getIY()));
		else
			return(false);
	}

	public Pos getCurPos()
	{
		return(new Pos(center));
	}

	public void setState(int state)
	{
		this.state=state;
	}

	public int getState()
	{
		return(state);
	}
}