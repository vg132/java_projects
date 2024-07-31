/*
 * Created on 2003-aug-27
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-aug-27 Created by Viktor.
 */
package ip1.ass3.server;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Chat server. Keeps all connected clients in a array and when one sends a message it
 * replicates the message to all clients.
 * 
 * Default port number is 2000, to change this start the server with port number as 
 * a argument.
 * 
 * java -jar ChatServer.jar 2111
 * 
 * This will start the server on port 2111
 * 
 */
public class Server extends JFrame
{
	private ArrayList clients=new ArrayList();
	private JTextArea txtArea=new JTextArea();
	private ServerSocket serverSocket=null;
	private int myPort=0;
	
	public static void main(String[] arg)
	{
		new Server(arg);
	}
	
	/**
	 * Start the server and setup the server window.
	 * 
	 * @param arg Command line arguments, used to set the port for this server.
	 */
	public Server(String[] arg)
	{
		if(arg.length==0)
		{
			myPort=2000;
		}
		else if(arg.length==1)
		{
			myPort=Integer.parseInt(arg[0]);
		}
		else
		{
			JOptionPane.showMessageDialog(this,"Wrong number of arguments.");
			System.exit(1);
		}
		if(startServer())
		{
			setTitle("Server running on port: "+myPort);
			setSize(400,400);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			JPanel p1=new JPanel();
			p1.setLayout(new BorderLayout());
			JScrollPane scrollPane=new JScrollPane(txtArea);
			txtArea.setEditable(false);
			p1.add(scrollPane,BorderLayout.CENTER);
			getContentPane().add(p1);
			Listen l=new Listen(this);
			l.start();
			setVisible(true);
		}
		else
		{
			JOptionPane.showMessageDialog(this,"Unable to start server on port "+myPort+".");
			System.exit(1);
		}
	}
	
	/**
	 * start the server socket.
	 * 
	 * @return True is successfull, otherwise false.
	 */
	private boolean startServer()
	{
		try
		{
			serverSocket=new ServerSocket(myPort);
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
			return(false);
		}
		if(serverSocket!=null)
			return(true);
		else
			return(false);
	}
	
	/**
	 * Sends a message to all connected client.
	 * 
	 * @param message The message string to send
	 * @param s Socket to the client the sent the message.
	 */
	public synchronized void sendMessage(String message, Socket s)
	{
		txtArea.append("CLIENT: "+s.getInetAddress().getHostName()+" BROADCAST: "+message+"\n");
		for(int i=0;i<clients.size();i++)
			((Client)clients.get(i)).sendMessage(s.getInetAddress().getHostName() + ">" +message);
	}
	
	/**
	 * Removes a client from the list of clients.
	 * 
	 * @param c The client
	 */
	public void removeClient(Client c)
	{
		clients.remove(c);
	}
	
	/**
	 * The class that listens for new clients that want to connect.
	 */
	class Listen extends Thread
	{
		private Server server=null;
		
		public Listen(Server server)
		{
			this.server=server;
		}
		
		public void run()
		{
			try
			{
				Socket s=null;
				Client c=null;
				while(true)
				{
					s=serverSocket.accept();
					c=new Client(s,server);
					c.start();
					clients.add(c);
					txtArea.append("New Client\n");
				}
			}
			catch(IOException io)
			{
				io.printStackTrace(System.err);
			}
		}
	}
}

/**
 * A Class that represents a client. It listens for new messages from the client and 
 * send messages to the client when needed.
 */
class Client extends Thread
{
	private Socket mySocket=null;
	private Server server=null;
	private PrintWriter pw=null;
	
	public Client(Socket mySocket, Server server)
	{
		this.mySocket=mySocket;
		this.server=server;
		try
		{
			pw=new PrintWriter(mySocket.getOutputStream());
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		server.sendMessage("CLIENT CONNECTED: "+mySocket.getInetAddress().getHostName(),mySocket);
	}

	public void run()
	{
		try
		{
			BufferedReader br=new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
			String line=null;
			while((line=br.readLine())!=null)
			{
				server.sendMessage(line,mySocket);
			}
			server.removeClient(this);
			pw.close();
			br.close();
			mySocket.close();
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}
	
	public void sendMessage(String message)
	{
		pw.println(message);
		pw.flush();
	}
}