/**
 * Document History
 * Created on 2004-sep-09
 * 
 * @author Viktor
 * @version 1.0
 */
package com.vgsoftware.f1bb.client.panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.vgsoftware.f1bb.client.F1BBChatClient;
import com.vgsoftware.f1bb.data.ChatServerRequest;

public class LoginPanel extends JPanel implements Runnable
{
	private F1BBChatClient client=null;
	private String username=null;
	private Thread loginThread=null;
	
	public LoginPanel(F1BBChatClient client, String username)
	{
		this.client=client;
		this.username=username;

		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());

		JLabel lblInfo=new JLabel("Copyright (C) VG Software 2004-2005 - F1BB Chat - Istanbul 2005-08-16");
		lblInfo.setForeground(Color.GRAY);
		lblInfo.setBackground(Color.WHITE);

		this.add(lblInfo,BorderLayout.SOUTH);

		loginThread=new Thread(this);
		loginThread.start();
	}
	
	public void run()
	{
		try
		{
			Thread.sleep(1500);
		}
		catch(InterruptedException ie)
		{
			
		}
		client.sendMessage(new ChatServerRequest(ChatServerRequest.LOGIN,username));
	}
}
