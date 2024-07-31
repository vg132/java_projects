/*
 * Created on 2004-sep-06 by Viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 
 */
package com.vgsoftware.f1bb.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.vgsoftware.f1bb.client.panel.AppletPanel;
import com.vgsoftware.f1bb.client.panel.BasePanel;
import com.vgsoftware.f1bb.client.panel.LoginPanel;
import com.vgsoftware.f1bb.data.ChatServerRequest;
import com.vgsoftware.f1bb.data.ChatServerResponse;

public class F1BBChatClient extends JApplet implements ActionListener
{
	private Listen l=null;
	private int port=-1;
	private String server=null;
	private ObjectOutputStream oos=null;
	private Socket socket=null;
	private LoginPanel loginPanel=null;
	private BasePanel chatPanel=null;
	private boolean isMod=false;
	private JPanel appletPanel=null;
	private JPanel windowPanel=null;
	private JButton btnWindow=new JButton("Show chat in window");

	private ObjectInputStream ois=null;

	public void init()
	{
		//GetDocumentBase!!
		server=getParameter("server");
		port=Integer.parseInt(getParameter("port"));
		connect();
		login();
	}

	public boolean getIsMod()
	{
		return(isMod);
	}

	public synchronized void decodeMessage(ChatServerResponse response)
	{
		if(response.getCommand()==ChatServerResponse.CHAT)
		{
			StringTokenizer st=new StringTokenizer(response.getValue(),"|");
			if(st.countTokens()==3)
				chatPanel.setMessage(st.nextToken(),st.nextToken(),st.nextToken());
		}
		else if(response.getCommand()==ChatServerResponse.CONNECTED)
		{

		}
		else if(response.getCommand()==ChatServerResponse.LOGGED_IN)
		{
			if(response.getValue().equals("0"))
				isMod=false;
			else
				isMod=true;
			chatPanel=new AppletPanel(this,getSize());
			getContentPane().remove(loginPanel);
			/*------*/
			windowPanel=new JPanel();
			windowPanel.setLayout(new BorderLayout());
			windowPanel.add(new JLabel("Dont close this page. If you do your connection with the chat will be lost."),BorderLayout.CENTER);
			appletPanel=new JPanel();
			btnWindow.addActionListener(this);
			appletPanel.setLayout(new BorderLayout());
			appletPanel.add(chatPanel,BorderLayout.CENTER);
			appletPanel.add(btnWindow,BorderLayout.NORTH);
			getContentPane().add(appletPanel);
			/*------*/
			//getContentPane().add(chatPanel);
			validate();
		}
		else if(response.getCommand()==ChatServerResponse.USER_LIST)
		{
			StringTokenizer st=new StringTokenizer(response.getValue(),"|");
			String[] users=new String[st.countTokens()];
			int i=0;
			while(st.hasMoreTokens())
				users[i++]=st.nextToken();
			chatPanel.setUsers(users);
		}
		else if(response.getCommand()==ChatServerResponse.INFO)
		{
			StringTokenizer st=new StringTokenizer(response.getValue(),"|");
			if(st.countTokens()==2)
				chatPanel.setMessage(st.nextToken(),st.nextToken());
		}
		else if(response.getCommand()==ChatServerResponse.ERROR)
		{
			JOptionPane.showMessageDialog(null,response.getValue());
		}
		else if(response.getCommand()==ChatServerResponse.SERVER_CLOSED)
		{
			JOptionPane.showMessageDialog(null,response.getValue());
			try
			{
				socket.close();
			}
			catch(IOException io)
			{
				io.printStackTrace(System.err);
			}
		}
	}

	public boolean connect()
	{
		try
		{
			socket=new Socket(server,port);
			oos=new ObjectOutputStream(socket.getOutputStream());
			l=new Listen();
			l.start();
			return(true);
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
			return(false);
		}
	}

	public void sendMessage(ChatServerRequest data)
	{
		try
		{
			oos.writeObject(data);
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}

	private void login()
	{
		loginPanel=new LoginPanel(this,getParameter("username"));
		getContentPane().add(loginPanel);
		validate();
	}

	class Listen extends Thread
	{
		public void run()
		{
			try
			{
				ois=new ObjectInputStream(socket.getInputStream());
				Object obj=null;
				while((obj=ois.readObject())!=null)
					decodeMessage((ChatServerResponse)obj);
			}
			catch(ClassNotFoundException cnfe)
			{
				System.err.println("Unknown object recived. Quiting.");
			}
			catch(IOException io)
			{
				chatPanel.setMessage("*** Disconnected from server.");
				System.err.println("Connection Closed.");
			}
		}
	}

	public class ChatWindow extends JFrame
	{
		public ChatWindow()
		{
			this.setTitle("F1BB Chat");
			this.setSize(750,400);
			this.addWindowListener(new WindowClose());
			this.setContentPane(chatPanel);
			this.validate();
			this.setVisible(true);
		}

		public void resetWindowLayout()
		{
			this.setContentPane(windowPanel);
			this.validate();
		}

		class WindowClose extends WindowAdapter
		{
			public void windowClosing(WindowEvent e)
			{
				super.windowClosing(e);
				resetWindowLayout();
				resetAppletLayout();
			}
		}
	}
	
	public void stop()
	{
		super.stop();
		try
		{
			socket.close();
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}

	public void resetAppletLayout()
	{
		this.setContentPane(appletPanel);
		this.validate();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==btnWindow)
		{
			this.setContentPane(windowPanel);
			this.validate();
			new ChatWindow();
		}
	}
}
