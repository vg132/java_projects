package u212;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class U212BallServer extends UnicastRemoteObject implements U212RemoteInterface
{
	private List<Ball> balls=new ArrayList<Ball>();
	private Random rnd=new Random();
	private int startX=0;
	private int startY=0;
	private int endX=0;
	private int endY=0;
	private int maxRadius=0;
	private boolean pause=false;

	public U212BallServer(int startX, int startY, int endX, int endY, int maxRadius)
	throws RemoteException
	{
		this.startX=startX;
		this.startY=startY;
		this.endX=endX;
		this.endY=endY;
		this.maxRadius=maxRadius;
	}

	public void addBall()
	throws RemoteException
	{
		Ball b=new Ball();
		b.start();
		balls.add(b);
	}

	public Vector getBalls()
	throws RemoteException
	{
		Vector v=new Vector();
		for(Ball b : balls)
		{
			v.add(b.getX());
			v.add(b.getY());
			v.add(b.getRadius());
		}
		return(v);
	}

	public void pauseBalls()
	throws RemoteException
	{
		pause=!pause;
	}
	
	class Ball extends Thread
	{
		private int x=0;
		private int y=0;
		private int radius=0;
		
		public Ball()
		{
			x=rnd.nextInt(endX-startX);
			y=rnd.nextInt(endY-startY);
			radius=rnd.nextInt(maxRadius);
		}
		
		public void run()
		{
			while(true)
			{
				try
				{
					radius--;
					if(rnd.nextBoolean())
						x-=rnd.nextInt(2);
					else
						x+=rnd.nextInt(2);
					if(rnd.nextBoolean())
						y-=rnd.nextInt(2);
					else
						y+=rnd.nextInt(2);
					Thread.sleep(250);
				}
				catch(InterruptedException ie)
				{
				}
			}
		}

		public int getRadius()
		{
			return radius;
		}

		public int getX()
		{
			return x;
		}

		public int getY()
		{
			return y;
		}
	}
}





