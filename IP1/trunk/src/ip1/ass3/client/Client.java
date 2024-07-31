/*
 * Created on 2003-aug-27
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-aug-27 Created by Viktor.
 */
package ip1.ass3.client;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Chat client class
 * 
 * Connects to a chat server and send messages as clear text with no coding.
 * You can start the client in 3 ways,
 * - java Client
 * This will try to connect to a server on localhost on port 2000
 * - java Client www.vgsoftware.com
 * This will try to connect to a server running on www.vgsoftware.com on port 2000
 * - java Client www.vgsoftware.com 2001
 * This will try to connect to a server running on www.vgsoftware.com on port 2001
 */
public class Client extends JFrame
{
	private JTextArea txtChat=new JTextArea();
	private JTextField txtMessage=new JTextField();
	private Socket mySocket=null;
	private String myHost=null;
	private int myPort=-1;
	private Listen listen=null;
	
	public static void main(String[] arg)
	{
		new Client(arg);
	}
	
	/**
	 * Default constructor. Check commandline arguments, connect to server, create window and show window.
	 * 
	 * @param arg Commandline argument.
	 */
	public Client(String[] arg)
	{
		super("ChatClient");

		if(arg.length==0)
		{
			myHost="localhost";
			myPort=2000;
		}
		else if(arg.length==1)
		{
			myHost=arg[0];
			myPort=2000;
		}
		else if(arg.length==2)
		{
			myHost=arg[0];
			myPort=Integer.parseInt(arg[1]);
		}
		else
		{
			JOptionPane.showMessageDialog(this,"Wrong number of arguments.");
			System.exit(1);
		}
		if(connect())
		{					
			setTitle("Chat Client - Connected: "+myHost+" On Port: "+myPort);
			addWindowListener(new Close());
			setSize(400,400);

			JPanel p1=new JPanel();
			p1.setLayout(new BorderLayout());

			JPanel p2=new JPanel();
			p2.setLayout(new BoxLayout(p2,BoxLayout.X_AXIS));
			p2.add(new JLabel("Message:"));
			txtMessage.addKeyListener(new ClientKey());
			p2.add(txtMessage);

			p1.add(p2,BorderLayout.NORTH);
			JScrollPane scrollChat=new JScrollPane(txtChat);
			scrollChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			p1.add(scrollChat,BorderLayout.CENTER);
			getContentPane().add(p1);

			listen=new Listen(mySocket,txtChat);
			listen.start();
	
			setVisible(true);
		}
		else
		{
			JOptionPane.showMessageDialog(this,"Unable to connect to "+myHost+" on port "+myPort+".");
			System.exit(1);
		}
	}
	
	/**
	 * Try to connect to the selected host and port
	 * 
	 * @return True if successfull, otherwise false.
	 */
	private boolean connect()
	{
		try
		{
			mySocket=new Socket(myHost,myPort);
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
			return(false);
		}
		return(true);
	}

	/**
	 * Send the current text in txtMessage to the server.
	 * 
	 * @return True if the sending was successfull, otherwise false.
	 */
	private boolean send()
	{
		try
		{
			PrintWriter pw=new PrintWriter(mySocket.getOutputStream());
			pw.println(txtMessage.getText());
			pw.flush();
			txtMessage.setText("");
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
			return(false);
		}
		return(true);
	}

	/**
	 * Check if the user has pressed the return key in the message field. If that happens call the send function.
	 */
	class ClientKey extends KeyAdapter
	{
		public void keyPressed(KeyEvent arg0)
		{
			if(arg0.getKeyChar()==KeyEvent.VK_ENTER)
				send();
		}
	}

	/**
	 * When the user closes the chat window, also close the connection to the chat server and terminate the program.
	 */
	class Close extends WindowAdapter
	{
		public void windowClosing(WindowEvent arg0)
		{
			super.windowClosing(arg0);
			try
			{
				mySocket.close();
			}
			catch(IOException io)
			{
				io.printStackTrace(System.err);
			}
			System.exit(0);
		}
	}
}

class Listen extends Thread
{
	private Socket mySocket=null;
	private JTextArea txtArea=null;
	
	public Listen(Socket mySocket, JTextArea txtArea)
	{
		this.mySocket=mySocket;
		this.txtArea=txtArea;
	}
	
	/**
	 * Listen for messages from the server, if we get a message print it into the textarea.
	 */
	public void run()
	{
		try
		{
			BufferedReader br=new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
			String line=null;
			while((line=br.readLine())!=null)
			{
				txtArea.append(line+"\n");
			}
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}
}