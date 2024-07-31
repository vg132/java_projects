/*
 * Created on 2003-sep-09
 * Created by Viktor
 *
 * Document History
 *
 * 2003-sep-09 Created by Viktor.
 */
package com.vgsoftware.sjrc.client.panel;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.StringTokenizer;

import com.vgsoftware.sjrc.client.ClientMessageCenter;
import com.vgsoftware.sjrc.data.ClientData;
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
				else if(command.equals("/list"))
				{
					String list=st.nextToken();
					if(list.equalsIgnoreCase("users"))
					{
						cd.setCommand(ClientData.LIST_USERS);
						cmc.encodeMessage(cd,null);
					}
					else if(list.equalsIgnoreCase("channels"))
					{
						cd.setCommand(ClientData.LIST_CHANNELS);
						cmc.encodeMessage(cd,null);
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
