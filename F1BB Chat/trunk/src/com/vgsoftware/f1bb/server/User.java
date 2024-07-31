package com.vgsoftware.f1bb.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.StringTokenizer;

import com.vgsoftware.f1bb.data.ChatServerRequest;
import com.vgsoftware.f1bb.data.ChatServerResponse;

public class User extends Thread
{
	private Socket socket=null;
	private String nick=null;
	private String userId=null;
	private ObjectOutputStream oos=null;
	private F1BBChatServer server=null;
	private boolean mod=false;

	public User(Socket socket, F1BBChatServer server)
	{
		try
		{
			this.socket=socket;
			this.server=server;
			oos=new ObjectOutputStream(socket.getOutputStream());
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}
	
	public boolean isMod()
	{
		return(mod);
	}
	
	public String getIP()
	{
		return(socket.getRemoteSocketAddress().toString());
	}

	public void setNick(String nick)
	{
		this.nick=nick;
	}
	
	public void quit()
	{
		try
		{
			if(!socket.isClosed())
				socket.close();
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}
	
	public String getNick()
	{
		return(nick);
	}
	
	public String getUserId()
	{
		return(userId);
	}
	
	public boolean validateUser()
	{
		try
		{
			URL url=new URL("http://www.f1bb.com/f1forum/chat/checkusr.php?username="+URLEncoder.encode(nick, "ISO-8859-1"));
			BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
			String line=null;
			while((line=br.readLine())!=null)
			{
				StringTokenizer st=new StringTokenizer(line.trim(),"|");
				if((st.countTokens()>0)&&(st.nextToken().equals("ok-f1bb-chat")))
				{
					int grp=Integer.parseInt(st.nextToken());
					nick=st.nextToken();
					String tmp=st.nextToken();
					userId=st.nextToken();
					if((grp==4)||(grp==6))
					{
						mod=true;
						return(true);
					}
					else if((grp==3)&&(tmp.equals("0")))
					{
						mod=false;
						return(true);
					}
					return(false);
				}
				else
				{
					return(false);
				}
			}
		}
		catch(MalformedURLException mue)
		{
			mue.printStackTrace(System.err);
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
		return(false);
	}

	public synchronized void send(ChatServerResponse data)
	{
		try
		{
			oos.writeObject(data);
		}
		catch(IOException io)
		{
			server.quit(this);
		}
	}

	public void run()
	{
		try
		{
			ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
			Object obj=null;
			while((obj=ois.readObject())!=null)
			{
				server.decodeMessage((ChatServerRequest)obj,this);
			}
			oos.close();
			ois.close();
			socket.close();
		}
		catch(IOException io)
		{
			server.quit(this);
		}
		catch(ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace(System.err);
		}
	}
}