/**
 * Document History
 * Created on 2004-sep-04
 * 
 * @author Viktor
 * @version 1.0
 */
package com.vgsoftware.f1bb.client.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.StringTokenizer;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;

import com.vgsoftware.f1bb.client.F1BBChatClient;
import com.vgsoftware.f1bb.data.ChatServerRequest;

public class AppletPanel extends BasePanel implements ActionListener
{
	private F1BBChatClient client=null;
	private JPopupMenu popupMenuUsers=null;
	private JPopupMenu popupMenuArea=null;

	public AppletPanel(F1BBChatClient client,Dimension size)
	{
		super();
		this.client=client;
		setLayout(new BorderLayout());
		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(new JLabel("Users"),BorderLayout.NORTH);
		p1.add(userScroll,BorderLayout.CENTER);
		p1.add(new JLabel(" "),BorderLayout.EAST);
		JSplitPane splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,textScroll,p1);
		splitPane.setOneTouchExpandable(true);
		
		splitPane.setDividerLocation((int)(size.getWidth()*0.85));
		add(splitPane,BorderLayout.CENTER);
		
		p1=new JPanel();
		p1.setLayout(new BorderLayout());
		
		txtField.addKeyListener(new ChannelKeyListener());
		p1.add(txtField,BorderLayout.CENTER);
		add(p1,BorderLayout.SOUTH);

		PopupMenuListenener pml=new PopupMenuListenener();

		if(client.getIsMod())
		{
			lstUsers.addMouseListener(pml);
			popupMenuUsers=createPopupMenu("Ban from session|Ban from chat");
		}

		textArea.addMouseListener(pml);
		popupMenuArea=createPopupMenu("Clear Screen");	
	}

	private JPopupMenu createPopupMenu(String items)
	{
		JPopupMenu popup=new JPopupMenu();
		StringTokenizer st=new StringTokenizer(items,"|");
		while(st.hasMoreTokens())
			popup.add(createMenuItem(st.nextToken()));
		return(popup);
	}

	private JMenuItem createMenuItem(String label)
	{
		JMenuItem item=new JMenuItem(label);
		item.addActionListener(this);
		return(item);
	}

	class PopupMenuListenener extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e)
		{
			if((e.getSource()==lstUsers)&&(e.isPopupTrigger())&&(lstUsers.getSelectedIndex()!=-1))
				popupMenuUsers.show(e.getComponent(), e.getX(), e.getY());
			else if((e.getSource()==textArea)&&(e.isPopupTrigger()))
				popupMenuArea.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	/**
	 * Listn for key input from the client.<br/>
	 * Every sent chat message is saved in a list so that the user can press up
	 * or down to go to the old messages in the text field.
	 */
	class ChannelKeyListener extends KeyAdapter
	{
		public void keyPressed(KeyEvent arg)
		{
			if(arg.getKeyCode()==KeyEvent.VK_ENTER)
			{
				if(!txtField.getText().trim().equals(""))
				{
					client.sendMessage(new ChatServerRequest(ChatServerRequest.CHAT,Color.BLACK.getRGB()+"|"+txtField.getText().trim()));
					history.add(txtField.getText().trim());
					historyPos=history.size();
				}
				txtField.setText("");
			}
			else if(arg.getKeyCode()==KeyEvent.VK_UP)
			{
				if(historyPos>0)
					txtField.setText((String)history.get(--historyPos));
			}
			else if((arg.getKeyCode()==KeyEvent.VK_L)&&(arg.isControlDown()))
			{
				textArea.setText("");
			}
			else if(arg.getKeyCode()==KeyEvent.VK_DOWN)
			{
				if(historyPos<history.size()-1)
					txtField.setText((String)history.get(++historyPos));
				else
					txtField.setText("");
			}
			else if(arg.getModifiers()==KeyEvent.CTRL_MASK)
			{
				if(arg.getKeyCode()==KeyEvent.VK_SPACE)
				{
					txtField.setText(txtField.getText()+getFullString(txtField.getText().substring((txtField.getText().lastIndexOf(" ")==-1)?0:txtField.getText().lastIndexOf(" "))));
				}
			}
		}
	}

	/**
	 * Listen for actions performed by the user, like menu selections.
	 */
	public void actionPerformed(ActionEvent arg)
	{
		if(arg.getSource() instanceof JMenuItem)
		{
			JMenuItem menu=(JMenuItem)arg.getSource();
			if(menu.getText().equals("Ban from session"))
				client.sendMessage(new ChatServerRequest(ChatServerRequest.BAN_SESSION,((String)lstUsers.getSelectedValue())));
			else if(menu.getText().equals("Ban from chat"))
				client.sendMessage(new ChatServerRequest(ChatServerRequest.BAN_CHAT,((String)lstUsers.getSelectedValue())));
			else if(menu.getText().equals("Clear Screen"))
				textArea.setText("");
		}
	}
}
