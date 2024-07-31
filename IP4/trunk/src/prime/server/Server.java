package prime.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server
{
	public static void main(String args[])
	{
		ServerConfig sc=new ServerConfig();
		try
		{
			LocateRegistry.createRegistry(sc.getPort());
			Naming.rebind(sc.getName(), new PrimeFinderServer(sc));
			System.out.println(sc.getName()+" is ready.");
		}
		catch(MalformedURLException mue)
		{
			System.out.println(sc.getName()+" failed: "+mue);
		}
		catch(RemoteException re)
		{
			System.out.println(sc.getName()+" failed: "+re);
		}
	}
}
