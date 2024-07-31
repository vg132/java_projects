/*
 * Created on 2003-sep-01
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-01 Created by Viktor.
 */

package ip1.ass8.a;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Server side of the RMI program.
 * Start the program and register it so that others can find it.
 *
 */
public class FibonacciServer
{
	public static void main(String args[])
	{
		try
		{
			FibonacciImpl f=new FibonacciImpl();
			Naming.rebind("fibonacci",f);
			System.out.println("Server running.");
		}
		catch(RemoteException re)
		{
			re.printStackTrace(System.err);
		}
		catch(MalformedURLException mue)
		{
			mue.printStackTrace(System.err);
		}
	}
}
