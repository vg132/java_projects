package com.vgsoftware.rdc.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class MakeClient
{
	private Listen l=null;
	private boolean run=false;
	private Socket socket=null;
	private BufferedWriter bw=null;
	private BufferedReader br=null;
	private MakeServer server=null;

	public MakeClient(MakeServer server, Socket socket)
	{
		this.server=server;
		this.socket=socket;
		try
		{
			bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			l=new Listen();
			run=true;
			l.start();
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}

	private void executeCommand(String makeParam)
	{
		try
		{
			Process p=Runtime.getRuntime().exec("make "+makeParam);
			BufferedReader error=new BufferedReader(new InputStreamReader(p.getErrorStream()));
			CReader crError=new CReader(error,true);
			BufferedReader input=new BufferedReader(new InputStreamReader(p.getInputStream()));
			CReader crInput=new CReader(input,false);
			crError.start();
			crInput.start();
			p.waitFor();
			send("return|"+p.exitValue()+"");
			socket.close();
			server.remove(this);
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
	}

	class CReader extends Thread
	{
		private BufferedReader br=null;
		private boolean error=false;
		public CReader(BufferedReader br, boolean error)
		{
			this.error=error;
			this.br=br;
		}
		
		public void run()
		{
			String line=null;
			try
			{
				while((line=br.readLine())!=null)
				{
					if(error)
						send("error|"+line);
					else
						send("text|"+line);
				}
			}
			catch(IOException io)
			{
				io.printStackTrace(System.err);
			}
		}
	}
	
	private void decode(String line)
	{
		StringTokenizer st=new StringTokenizer(line,"|");
		if(st.nextToken().equals("command"))
		{
			executeCommand(st.nextToken());
		}
	}

	private void send(String line)
	{
		try
		{
			bw.write(line.trim()+"\n");
			bw.flush();
		}
		catch(IOException io)
		{
			io.printStackTrace(System.err);
		}
	}
	
	class Listen extends Thread
	{
		public void run()
		{
			String line=null;
			try
			{
				while((line=br.readLine())!=null)
					decode(line);
			}
			catch(IOException io)
			{
				//io.printStackTrace(System.err);
			}
		}
	}
}
