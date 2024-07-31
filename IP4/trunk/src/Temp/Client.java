package Temp;
/*
 * Created on 2005-maj-04
 */
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;

/**
 * @author vikto-ga
 */
public class Client
{
	public static void main(String arg[])
	{
		try
		{
			HelloServer rs=null;
			rs=(HelloServer)Naming.lookup("//localhost/HelloServer");
			System.out.println(rs.getHello());
		}
		catch(NotBoundException nbe)
		{
			nbe.printStackTrace(System.err);
		}
		catch(RemoteException re)
		{
			re.printStackTrace(System.err);
		}
		catch(MalformedURLException mue)
		{
			mue.printStackTrace(System.err);
		}
		
		
		/*
		
		BallWindow bw=new BallWindow();
		bw.updateBalls(null);
		RMIClient client=new RMIClient(bw);
		client.start();*/
	}
}

class RMIClient extends Thread
{
	private BallWindow bw=null;
	
	public RMIClient(BallWindow bw)
	{
		this.bw=bw;
	}

	public void run()
	{
		try
		{
			RemoteServer rs=null;
			rs=(RemoteServer)Naming.lookup("//atlas.dsv.su.se:1099/server");
			rs.addBall();
			rs.addBall();
			rs.addBall();
			while(true)
			{
				bw.updateBalls(rs.getBalls());
				Thread.sleep(10);
			}
		}
		catch(NotBoundException nbe)
		{
			nbe.printStackTrace(System.err);
		}
		catch(RemoteException re)
		{
			re.printStackTrace(System.err);
		}
		catch(MalformedURLException mue)
		{
			mue.printStackTrace(System.err);
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace(System.err);
		}
	}
}
