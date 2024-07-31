package u212;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class U212Server
{
	public static void main(String[] argv)
	{
		try
		{
			LocateRegistry.createRegistry(1099);
			Naming.rebind("BallServer", new U212BallServer(0,30,500,500,70));
			System.out.println("BallServer is ready.");
		}
		catch(MalformedURLException mue)
		{
			System.out.println("BallServer failed: "+mue);
		}
		catch(RemoteException re)
		{
			System.out.println("BallServer failed: "+re);
		}
	}
}
