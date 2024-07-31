package u222;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

public class PrimeClient
{
	/**
	 * Opens a XmlRpc connection to the server, calles a method and prints the
	 * result.
	 */
	public static void main(String args[])
	{
		try
		{
			XmlRpcClient client=new XmlRpcClient("http://compaq.vgsoftware.com:7777/");
			Vector v=new Vector();
			v.add("2isAnOddPrime");
			System.out.println("Prime: "+(String)client.execute("prime_handler.getPrime",v));
		}
		catch(MalformedURLException mue)
		{
			mue.printStackTrace(System.err);
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		catch(XmlRpcException xre)
		{
			xre.printStackTrace(System.err);
		}
	}
}
