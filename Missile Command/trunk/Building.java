/**
 * The building object class
 * (C) Viktor Gars 2002
 */

import java.awt.*;

public class Building
{
	private Pos center=null;
	private Polygon building=null;

	//Create object and set the center position of the building
	Building(int x, int y)
	{
		this.center=new Pos(x,y);
		build();
	}

	//Paint building on the graphic object recived
	public void paint(Graphics g)
	{
		g.setColor(Color.green);
		g.fillPolygon(building);
	}

	public boolean hitTest(Pos xy)
	{
		return(hitTest(xy.getIX(),xy.getIY()));
	}

	public boolean hitTest(int x, int y)
	{
		//Fist check if it's not a hit,
		if((x>(center.getIX()+20))||
			(x<(center.getIX()-20))||
			(y<(center.getIY()-10))||
			(y>(center.getIY()+10)))
		{
			return(false);
		}
		//Check if it is a hit between the towers (e.g. no hit)
		if((x>(center.getIX()-12))&&(x<(center.getIX()-4))&&(y<(center.getIY()-10))&&(y>(center.getIY()-5)))
		{
			return(false);
		}
		if((x>(center.getIX()+12))&&(x<(center.getIX()+4))&&(y<(center.getIY()+10))&&(y>(center.getIY()+5)))
		{
			return(false);
		}
		//and if it is a hit, return true :)
		return(true);
	}

	//Build the building from the center point.
	private void build()
	{
		building=new Polygon();
		building.addPoint(center.getIX()-20,center.getIY()+10);
		building.addPoint(center.getIX()-20,center.getIY()-10);
		building.addPoint(center.getIX()-12,center.getIY()-10);
		building.addPoint(center.getIX()-12,center.getIY()-5);
		building.addPoint(center.getIX()-4,center.getIY()-5);
		building.addPoint(center.getIX()-4,center.getIY()-10);
		building.addPoint(center.getIX()+4,center.getIY()-10);
		building.addPoint(center.getIX()+4,center.getIY()-5);
		building.addPoint(center.getIX()+12,center.getIY()-5);
		building.addPoint(center.getIX()+12,center.getIY()-10);
		building.addPoint(center.getIX()+20,center.getIY()-10);
		building.addPoint(center.getIX()+20,center.getIY()+10);
	}
}