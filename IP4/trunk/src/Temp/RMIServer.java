package Temp;
/*
 * Created on 2005-maj-04
 */

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author vikto-ga
 * 
 */
public class RMIServer extends UnicastRemoteObject implements HelloServer
{
	
	public RMIServer()
	throws RemoteException
	{
		super();
	}

	public String getHello()
	throws RemoteException
	{
		return("Hello world!");
	}
	
/*	public void addBall() throws RemoteException
	{
	}

	public void pauseBalls() throws RemoteException
	{
	}

	public Vector getBalls() throws RemoteException
	{
		return (null);
	}
*/
	public static void main(String args[])
	{
		// Create and install a security manager
		if (System.getSecurityManager() == null)
		{
			System.setSecurityManager(new RMISecurityManager());
		}
		try
		{
			RMIServer obj=new RMIServer();
			Naming.rebind("//localhost/HelloServer", obj);
			System.out.println("HelloServer bound in registry");
		}
		catch(Exception e)
		{
			System.out.println("HelloImpl err: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
