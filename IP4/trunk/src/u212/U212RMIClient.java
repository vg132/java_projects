package u212;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class U212RMIClient extends Thread
{
	private U212BallWindow bw=null;
	
	public U212RMIClient(U212BallWindow bw)
	{
		this.bw=bw;
	}

	public void run()
	{
		try
		{
			U212RemoteInterface rs=null;
			rs=(U212RemoteInterface)Naming.lookup("//compaq.vgsoftware.com:1099/BallServer");
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
