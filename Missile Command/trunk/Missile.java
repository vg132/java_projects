/**
 * The missile object class
 * (C) Viktor Gars 2002
 */

import java.awt.*;

public class Missile implements Item
{
	private Pos startPos=null;
	private Pos endPos=null;
	private Pos curPos=null;
	private int speed=-1;
	private int curSpeedCount=-1;
	private int state=-1;
	private Explosion explosion=null;
	private boolean finished=false;

	//Constructor, set the init values for this missile
	Missile(Pos startPos, Pos endPos, int speed, int state)
	{
		this.startPos=startPos;
		this.curPos=new Pos(startPos);
		this.endPos=endPos;
		this.speed=speed;
		curSpeedCount=speed;
		this.state=state;
	}

	//Move line or expand explosion if it is hit, or create a new explosion if explotion=null
	public void move()
	{
		double length=getLength();
		curSpeedCount--;
		if(curPos.getY()<endPos.getY()&&curSpeedCount==0&&state==LINE)
		{
			curPos.setX(curPos.getX()+((endPos.getX()-startPos.getX())/length));
			curPos.setY(curPos.getY()+((endPos.getY()-startPos.getY())/length));
			curSpeedCount=speed;
		}
		else if(curPos.getY()>endPos.getY())
		{
			finished=true;
		}
		if(state==HIT)
		{
			if(explosion==null)
			{
				explosion=new Explosion(new Pos(curPos),4,50,HIT);
			}
			explosion.move();
		}
	}

	//Paint the line or explosion
	public void paint(Graphics g)
	{
		if(state!=HIT)
		{
			g.setColor(Color.red);
			g.drawLine(startPos.getIX(),startPos.getIY(),curPos.getIX(),curPos.getIY());
		}
		else if(state==HIT&&explosion!=null)
		{

			explosion.paint(g);
			if(explosion.finished())
				finished=true;
		}
	}

	//Calculate the length of the line to get the diff between x and y when moving it (how much to move x when y move 1 etc)
	private double getLength()
	{
		return(Math.sqrt((Math.pow(endPos.getX()-startPos.getX(),2)+Math.pow(endPos.getY()-startPos.getY(),2))));
	}

	//Check if missile is finished
	public boolean finished()
	{
		return(finished);
	}

	//Return current position of the line (top pixle)
	public Pos getCurPos()
	{
		return(new Pos(curPos));
	}

	//Set the current state of this missile, LINE (normal) or HIT (explotion)
	public void setState(int state)
	{
		this.state=state;
	}

	public int getState()
	{
		return(state);
	}
}