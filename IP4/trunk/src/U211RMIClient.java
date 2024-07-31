

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class U211RMIClient extends Thread
{
	private U211BallWindow bw=null;
	
	public U211RMIClient(U211BallWindow bw)
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
