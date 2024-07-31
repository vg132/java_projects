/*
 * Created on 2003-sep-09
 * Created by Viktor
 *
 * Document History
 *
 * 2003-sep-09 Created by Viktor.
 * 2004-jun-03 Updated by Viktor, xor text color of button.
 */
package com.vgsoftware.sjrc.client.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;

import com.vgsoftware.sjrc.client.ClientMessageCenter;
import com.vgsoftware.sjrc.data.ClientData;

/**
 * This is the chat window, with a user list, chat text field and chat text area.
 */
public class Channel extends JRCPanel implements ActionListener
{
	private JList users=new JList();
	private String name=null;
	private ClientMessageCenter cmc=null;
	private JPopupMenu popupMenu=null;
	private JColorChooser tcc=new JColorChooser();
	private JButton btnColor=new JButton("Text Color");

	/**
	 * Constructs a new channel window and initialize all values.
	 *
	 * @param cmc the {@link ClientMessageCenter} that this channel communicates with.
	 * @param name the name of this channel.
	 */
	public Channel(ClientMessageCenter cmc, String name)
	{
		super();
		setLayout(new BorderLayout());

		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(new JLabel("Users"),BorderLayout.NORTH);
		p1.add(users,BorderLayout.CENTER);

		JSplitPane splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,textScroll,p1);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation((int)(cmc.getWindowDimension().getWidth()*0.85));

		add(splitPane,BorderLayout.CENTER);
		p1=new JPanel();
		p1.setLayout(new BorderLayout());
		txtField.addKeyListener(new ChannelKeyListener());
		p1.add(txtField,BorderLayout.CENTER);
		btnColor.addActionListener(this);
		p1.add(btnColor,BorderLayout.EAST);
		add(p1,BorderLayout.SOUTH);
		this.name=name;
		this.cmc=cmc;
		tcc.setColor(Color.BLACK);
		btnColor.setBackground(Color.BLACK);
		btnColor.setForeground(new Color(btnColor.getBackground().getRGB()^0xFFFFFF));

		users.addMouseListener(new MenuMouseListenener());
		popupMenu=createPopupMenu();
	}

	/**
	 * Simple menu create function.
	 *
	 * @return popupmenu used for the member list.
	 */
	private JPopupMenu createPopupMenu()
	{
		JPopupMenu popup=new JPopupMenu();
		popup.add(createMenuItem("Whois"));
		return(popup);
	}

	/**
	 * Creates a menuitem with action listener and a label.
	 *
	 * @param label The label of this menuitem.
	 * @return JMenuItem the created menuitem.
	 */
	private JMenuItem createMenuItem(String label)
	{
		JMenuItem item=new JMenuItem(label);
		item.addActionListener(this);
		return(item);
	}

	/**
	 * Sets the user list, a list with all users connected to this channel.
	 *
	 * @param users a string array containing the name of all users.
	 */
	public void setUsers(String[] users)
	{
		this.users.setListData(users);
	}

	/**
	 * Gets the name of this channel.
	 *
	 * @return the name of this channel.
	 */
	public String getName()
	{
		return(name);
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
				ClientData cd=new ClientData();
				cd.setCommand(ClientData.CHAT);
				cmc.encodeMessage(cd,txtField.getText(),name,""+btnColor.getBackground().getRGB());
				history.add(txtField.getText());
				historyPos=history.size();
				txtField.setText("");
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

	/**
	 * Listen for a mouse click, if it is the right one show the popup menu.
	 */
	class MenuMouseListenener extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e)
		{
			if((e.isPopupTrigger())&&(users.getSelectedIndex()!=-1))
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	/**
	 * Listen for actions performed by the user, like menu selections.
	 */
	public void actionPerformed(ActionEvent arg)
	{
		if(arg.getSource() instanceof JMenuItem)
		{
			ClientData cd=new ClientData();
			cd.setCommand(ClientData.WHOIS);
			JMenuItem menu=(JMenuItem)arg.getSource();
			if(menu.getLabel().equals("Whois"))
				cmc.encodeMessage(cd,((String)users.getSelectedValue()));
		}
		else if(arg.getSource()==btnColor)
		{
			Color c=JColorChooser.showDialog(this, "Choose Background Color", btnColor.getBackground());
			if(c!=null)
			{
				btnColor.setBackground(c);
				btnColor.setForeground(new Color(btnColor.getBackground().getRGB()^0xFFFFFF));
			}
		}
	}
}
