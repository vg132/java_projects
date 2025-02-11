package rmitest;

import java.rmi.Naming;

public class HelloServer
{
	/**
	 * Server program for the "Hello, world!" example.
	 * 
	 * @param argv
	 *          The command line arguments which are ignored.
	 */
	public static void main(String[] argv)
	{
		try
		{
			Naming.rebind("Hello", new Hello("Hello, world!"));
			System.out.println("Hello Server is ready.");
		}
		catch(Exception e)
		{
			System.out.println("Hello Server failed: "+e);
		}
	}
}
