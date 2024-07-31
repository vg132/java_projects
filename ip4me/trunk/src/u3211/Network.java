package u3211;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

public class Network extends Thread
{
	private SocketConnection sc=null;
	private DataOutputStream dos=null;
	private DataInputStream dis=null;
	private U3211Client client=null;
	private String port=null;
	private String server=null;
	private Listen listen=null;
	
	/**
	 * Default constructor with a referense to the client, server address
	 * and server port.
	 */
	public Network(U3211Client client, String server, String port)
	{
		this.client=client;
		this.server=server;
		this.port=port;
	}
	
	/**
	 * Connect to the server and start the listener thread.
	 */
	public void run()
	{
		try
		{
			sc=(SocketConnection)Connector.open("socket://"+server+":"+port);
			dos=sc.openDataOutputStream();
			dis=sc.openDataInputStream();

			listen=new Listen();
			listen.start();
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
	}
	
	/**
	 * Sends a message from the client to the server.
	 */
	public void sendMessage(String line)
	{
		try
		{
			line=line.trim()+"\n";
			dos.write(line.getBytes());
			dos.flush();
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
	}
	
	/**
	 * Listener thread that listen for incoming messages.
	 */
	class Listen extends Thread
	{
	  /**
	   * Listen for incoming messages and send them to the client
	   * when a full message has been recied.
	   */
		public void run()
		{
			try
			{
				String line="";
				int ch;
				while((ch=dis.read())!=-1)
				{
					if(ch=='\n')
					{
						client.addMessage(line);
						line="";
					}
					else
					{
						line+=(char)ch;
					}
	      }
			}
			catch(IOException io)
			{
				io.printStackTrace();
			}
		}
	}
}
