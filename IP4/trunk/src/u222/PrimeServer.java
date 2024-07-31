package u222;

import org.apache.xmlrpc.WebServer;

public class PrimeServer
{
	/**
	 * Start the XML-RPC server.
	 */
	public static void main(String args[])
	{
		WebServer server=new WebServer(7777);
		server.addHandler("prime_handler",new PrimeHandler());
		server.start();
	}
}
