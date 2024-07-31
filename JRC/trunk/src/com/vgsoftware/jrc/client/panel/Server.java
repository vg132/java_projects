/*
 * Created on 2003-sep-09
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-09 Created by Viktor.
 */
package com.vgsoftware.jrc.client.panel;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.StringTokenizer;

import com.vgsoftware.jrc.client.ClientMessageCenter;
/**
 * This is the server window where the user can enter commands and also all
 * status/information messages are shown.
 */
public class Server extends JRCPanel
{
	private ClientMessageCenter cmc=null;
	
	/**
	 * Constructs a new server window and sets its {@link ClientMessageCenter}.
	 * @param cmc
	 */
	public Server(ClientMessageCenter cmc)
	{
		super();
		setLayout(new BorderLayout());
		add(textScroll,BorderLayout.CENTER);
		txtField.addKeyListener(new ServerKeyListener());
		add(txtField,BorderLayout.SOUTH);
		this.cmc=cmc;
	}
	
	/**
	 * Read the commands enterd into the text field and act on them.
	 */
	class ServerKeyListener extends KeyAdapter
	{
		public void keyPressed(KeyEvent arg)
		{
			if(arg.getKeyCode()==KeyEvent.VK_ENTER)
			{
				history.add(txtField.getText());
				historyPos=history.size();
				StringTokenizer st=new StringTokenizer(txtField.getText()," ");
				String command=st.nextToken().toLowerCase();
								
				setMessage(null,null,txtField.getText());
				txtField.setText("");

				if(command.equals("/join"))
				{
					String name=st.nextToken();
					cmc.encodeMessage(ClientMessageCenter.JOIN_CHANNEL,name.trim());
				}
				else if(command.equals("cls"))
				{
					textArea.setText("");
				}
				else if(command.equals("/whois"))
				{
					cmc.encodeMessage(ClientMessageCenter.WHOIS,st.nextToken());
				}
				else if(command.equals("/list"))
				{
					String list=st.nextToken();
					if(list.equalsIgnoreCase("users"))
					{
						cmc.encodeMessage(ClientMessageCenter.LIST_USERS,null);
					}
					else if(list.equalsIgnoreCase("channels"))
					{
						cmc.encodeMessage(ClientMessageCenter.LIST_CHANNELS,null);
					}
				}
				else if(command.equals("/?"))
				{
					String info="JRC Server Commands:\n/join CHANNEL_NAME - Join the specified channel.\n/whois USER_NAME - Get information about a user.\n/list USERS|CHANNELS - Get a list of all users or channels connected to this server.\ncls - Clear this screen.";
					setMessage(info);
				}
				else
				{
					setMessage("Command not found.");
				}
			}
			else if(arg.getKeyCode()==KeyEvent.VK_UP)
			{
				if(historyPos>0)
					txtField.setText((String)history.get(--historyPos));
			}
			else if(arg.getKeyCode()==KeyEvent.VK_DOWN)
			{
				if(historyPos<history.size()-1)
					txtField.setText((String)history.get(++historyPos));
				else
					txtField.setText("");
			}
		}
	}
}
