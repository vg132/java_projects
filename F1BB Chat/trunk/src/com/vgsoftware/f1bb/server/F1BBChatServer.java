/*
 * Created on 2004-sep-06 by Viktor
 * 
 * Version 1.0
 * 
 * Document history:
 * 
 */
package com.vgsoftware.f1bb.server;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.vgsoftware.f1bb.data.ChatServerRequest;
import com.vgsoftware.f1bb.data.ChatServerResponse;

public class F1BBChatServer extends Thread
{
	private ServerSocket serverSocket=null;
	private List bannedFromSession=new ArrayList();
	private List bannedFromChat=new ArrayList();
	private Map onlineUsers=Collections.synchronizedMap(new HashMap());
	private List waitingUsers=new LinkedList();
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd kk:mm");
	private SimpleDateFormat sdf2=new SimpleDateFormat("kk:mm.ss");
	private boolean chatOpen=false;
	private EventHandler eh=null;
	private Event currentEvent=null;

	public static void main(String args[])
	{
		new F1BBChatServer(args);
	}

	public F1BBChatServer(String args[])
	{
		try
		{
			int port=39999;
			for(int i=0;i<args.length;i++)
			{
				if(args[i].equals("-port"))
					port=Integer.parseInt(args[i+1]);
			}
			eh=new EventHandler(this);
			serverSocket=new ServerSocket(port);
			start();
			System.out.println("F1BBChatServer running on port: "+port);
		}
		catch(IOException io)
		{

		}
		catch(NumberFormatException nfe)
		{
			System.out.println("VG Software F1BBChatServer. Default port: 2000");
			System.out.println("Usage: java -jar Server.jar [-port [port]]");
		}
	}

	public void run()
	{
		try
		{
			Socket s=null;
			while(true)
			{
				s=serverSocket.accept();
				this.addUser(s);
			}
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}

	public void addUser(Socket socket)
	{
		User u=new User(socket,this);
		u.start();
		waitingUsers.add(u);
		if(chatOpen)
			u.send(new ChatServerResponse(ChatServerResponse.CONNECTED,null));
		else
			u.send(new ChatServerResponse(ChatServerResponse.SERVER_CLOSED,"Chat server closed. Please check back later."));
	}

	public synchronized void decodeMessage(ChatServerRequest request, User user)
	{
		if(!chatOpen)
			return;
		StringTokenizer st=new StringTokenizer(request.getValue(),"|");
		if(request.getCommand()==ChatServerRequest.CHAT)
		{
			toAll(new ChatServerResponse(ChatServerResponse.CHAT,user.getNick()+"|"+request.getValue()));
			st.nextToken();
			sendTranscript(user.getNick(),st.nextToken());
		}
		else if(request.getCommand()==ChatServerRequest.LOGIN)
		{
			user.setNick(st.nextToken());
			if(user.validateUser()&&(!onlineUsers.containsKey(user.getNick().toUpperCase()))&&(!bannedFromChat.contains(user.getNick().toUpperCase()))&&(!bannedFromSession.contains(user.getNick().toUpperCase())))
			{
				waitingUsers.remove(user);
				synchronized(onlineUsers)
				{
					onlineUsers.put(user.getNick().toUpperCase(),user);
				}
				user.send(new ChatServerResponse(ChatServerResponse.LOGGED_IN,(user.isMod()?"1":"0")));
				sendUserlist();
				toAll(new ChatServerResponse(ChatServerResponse.INFO,Color.BLUE.getRGB()+"|*** "+ sdf2.format(new Timestamp(System.currentTimeMillis()))+" - "+user.getNick()+" has joined this session."));
			}
			else
			{
				if(bannedFromChat.contains(user.getNick().toUpperCase()))
					user.send(new ChatServerResponse(ChatServerResponse.ERROR,"Login Error: You have been banned from this chat."));
				else if(bannedFromSession.contains(user.getNick().toUpperCase()))
					user.send(new ChatServerResponse(ChatServerResponse.ERROR,"Login Error: You have been banned from this session."));
				else
					user.send(new ChatServerResponse(ChatServerResponse.ERROR,"Login Error, please try again later."));
			}
		}
		else if((request.getCommand()==ChatServerRequest.BAN_SESSION)||(request.getCommand()==ChatServerRequest.BAN_CHAT))
		{
			if(user.isMod())
			{
				User u=(User)onlineUsers.get(request.getValue().toUpperCase());
				if((u!=null)&&(!u.isMod()))
				{
					if(request.getCommand()==ChatServerRequest.BAN_SESSION)
					{
						bannedFromSession.add(u.getNick().toUpperCase());
						u.send(new ChatServerResponse(ChatServerResponse.INFO,Color.RED.getRGB()+"|*** "+ sdf2.format(new Timestamp(System.currentTimeMillis()))+" - You have been banned from this session."));
					}
					else
					{
						bannedFromChat.add(u.getNick().toUpperCase());
						u.send(new ChatServerResponse(ChatServerResponse.INFO,Color.RED.getRGB()+"|*** "+ sdf2.format(new Timestamp(System.currentTimeMillis()))+" - You have been banned from this chat."));
					}
					u.quit();
				}
			}
		}
	}
	
	public synchronized void sendUserlist()
	{
		User u=null;
		String users="";
		synchronized(onlineUsers)
		{
			Iterator iter=onlineUsers.values().iterator();
			while(iter.hasNext())
			{
				u=(User)iter.next();
				if(u.isMod())
					users+="|*"+u.getNick();
				else
					users+="|"+u.getNick();
			}
		}
		toAll(new ChatServerResponse(ChatServerResponse.USER_LIST,(users.length()>0?users.substring(1):users)));
	}

	public synchronized void quit(User user)
	{
		if((user.getNick()!=null)&&(onlineUsers.containsKey(user.getNick().toUpperCase())))
		{
			synchronized(onlineUsers)
			{
				onlineUsers.remove(user.getNick().toUpperCase());
			}
			sendUserlist();
			toAll((new ChatServerResponse(ChatServerResponse.INFO,Color.BLUE.getRGB()+"|*** "+ sdf2.format(new Timestamp(System.currentTimeMillis()))+" - "+user.getNick()+" has quit this session.")));
		}
		else
		{
			waitingUsers.remove(user);
		}
	}
	
	private synchronized void toAll(ChatServerResponse response)
	{
		synchronized(onlineUsers)
		{
			Iterator iter=onlineUsers.values().iterator();
			while(iter.hasNext())
				((User)iter.next()).send(response);
		}
	}
	
	public void setChatOpen(boolean open, Event event)
	{
		if((open==true)&&(chatOpen==false))
		{
			chatOpen=true;
			currentEvent=event;
		}
		else if((open==false)&&(chatOpen==true))
		{
			chatOpen=false;
			bannedFromSession.clear();
			toAll(new ChatServerResponse(ChatServerResponse.INFO,Color.BLUE.getRGB()+"|*** "+ sdf2.format(new Timestamp(System.currentTimeMillis()))+" - This chat session is over."));
		}
	}
	
	private void sendTranscript(String user, String message)
	{
		try
		{
			String data=URLEncoder.encode("start", "ISO-8859-1")+"="+URLEncoder.encode(currentEvent.getOpen(), "ISO-8859-1");
			data+="&"+URLEncoder.encode("end", "ISO-8859-1")+"="+URLEncoder.encode(currentEvent.getClose(), "ISO-8859-1");
			data+="&"+URLEncoder.encode("session", "ISO-8859-1")+"="+URLEncoder.encode(currentEvent.getSession(), "ISO-8859-1");
			data+="&"+URLEncoder.encode("event", "ISO-8859-1")+"="+URLEncoder.encode(currentEvent.getEvent(), "ISO-8859-1");
			data+="&"+URLEncoder.encode("type", "ISO-8859-1")+"="+URLEncoder.encode(currentEvent.getType(), "ISO-8859-1");
			data+="&"+URLEncoder.encode("message", "ISO-8859-1")+"="+URLEncoder.encode(user+"|"+sdf.format(new Date(System.currentTimeMillis()))+"|"+message, "ISO-8859-1");
			data+="&"+URLEncoder.encode("password", "ISO-8859-1")+"="+URLEncoder.encode("f1BbChAtUrLUp", "ISO-8859-1");
			data+="&"+URLEncoder.encode("filename", "ISO-8859-1")+"="+URLEncoder.encode(""+currentEvent.getEventCode(), "ISO-8859-1");
			// Send data
			URL url=new URL("http://www.f1bb.com/f1forum/index.php?act=transcript&appendtranscript=true");
			URLConnection conn=url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr=new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while((line=rd.readLine())!=null)
			{
				System.out.println(line);
			}
			wr.close();
			rd.close();
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
	}
}