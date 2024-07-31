/**
 * Document History
 * Created on 2004-sep-04
 * 
 * @author Viktor
 * @version 1.0
 */
package com.vgsoftware.f1bb.client.panel;

import java.awt.Color;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public abstract class BasePanel extends JPanel
{
	private DefaultStyledDocument doc=new DefaultStyledDocument();
	protected JTextPane textArea=new JTextPane(doc);
	protected JScrollPane textScroll=new JScrollPane(textArea);
	protected JList lstUsers=new JList();
	protected JScrollPane userScroll=new JScrollPane(lstUsers);
	protected JTextField txtField=new JTextField();
	protected List history=new LinkedList();
	protected int historyPos=0;
	protected String channelName=null;
	protected List shorthand=new ArrayList();
	protected List driverList=new ArrayList();

	public BasePanel()
	{
		super();
		textArea.setEditable(false);
		textArea.setMargin(new Insets(5,5,5,5));
		buildDriverList();
	}

	public void setChannelName(String channelName)
	{
		this.channelName=channelName;
	}
	
	public String getChannelName()
	{
		return(channelName);
	}

	public void clearScreen()
	{
		textArea.setText("");
	}

	/**
	 * Sets the user list, a list with all users connected to this channel.
	 *
	 * @param users a string array containing the name of all users.
	 */
	public void setUsers(String[] users)
	{
		buildShorthandList(users);
		lstUsers.setListData(users);
	}

	/**
	 *
	 * @param message
	 */
	public void setMessage(String message)
	{
		setMessage(null,null,message);
	}

	/**
	 *
	 * @param color
	 * @param message
	 */
	public void setMessage(String color, String message)
	{
		setMessage(null,color,message);
	}

	/**
	 *
	 * @param user
	 * @param color
	 * @param message
	 */
	public void setMessage(String user, String color, String message)
	{
		SimpleAttributeSet attr=new SimpleAttributeSet();
		try
		{
			StyleConstants.setForeground(attr,new Color(Integer.parseInt(color)));
		}
		catch(NumberFormatException nfe)
		{
			StyleConstants.setForeground(attr,Color.BLACK);
		}
		try
		{
			if(user==null)
			{
				doc.insertString(doc.getLength(),message+"\n",attr);
			}
			else
			{
				doc.insertString(doc.getLength(),user+"> "+message+"\n",attr);
			}
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
		catch(BadLocationException ble)
		{
			ble.printStackTrace(System.err);
		}
	}

	private void buildShorthandList(String[] users)
	{
		shorthand.clear();
		for(int i=0;i<users.length;i++)
		{
			if(users[i].startsWith("*"))
				shorthand.add(users[i].substring(1));
			else
				shorthand.add(users[i]);
		}
		shorthand.addAll(driverList);
	}

	public String getFullString(String start)
	{
		start=start.trim().toLowerCase();
		String tmp=null;
		Iterator iter=shorthand.iterator();
		while(iter.hasNext())
		{
			String name=iter.next().toString().toLowerCase();
			if((name.startsWith(start))&&(tmp==null))
			{
				tmp=name;
			}
			else if((name.startsWith(start))&&(tmp!=null))
			{
				tmp=null;
				break;
			}
		}
		if(tmp==null)
			return("");
		else
			return(tmp.substring(start.length()));
	}

	private void buildDriverList()
	{
		driverList.add("Michael");
		driverList.add("Schumacher");
		driverList.add("Rubens");
		driverList.add("Barrichello");
		driverList.add("Jenson");
		driverList.add("Button");
		driverList.add("Takuma");
		driverList.add("Sato");
		driverList.add("Fernando");
		driverList.add("Alonso");
		driverList.add("Giancarlo");
		driverList.add("Fisichella");
		driverList.add("Mark");
		driverList.add("Webber");
		driverList.add("Nick");
		driverList.add("Heidfeld");
		driverList.add("Kimi");
		driverList.add("Räikkönen");
		driverList.add("Juan");
		driverList.add("Pablo");
		driverList.add("Montoya");
		driverList.add("Jacques");
		driverList.add("Villeneuve");
		driverList.add("Filipe");
		driverList.add("Massa");
		driverList.add("David");
		driverList.add("Coulthard");
		driverList.add("Christian");
		driverList.add("Klien");
		driverList.add("Jarno");
		driverList.add("Trulli");
		driverList.add("Ralf");
		driverList.add("Tiago");
		driverList.add("Monteiro");
		driverList.add("Narain");
		driverList.add("Karthikeyan");
		driverList.add("Christijan");
		driverList.add("Albers");
		driverList.add("Patrick");
		driverList.add("Friesacher");
		driverList.add("Wirdheim");
		driverList.add("Ekström");
		driverList.add("Anthony");
		driverList.add("Davidson");
		driverList.add("Nicolas");
		driverList.add("Kiesa");
		driverList.add("Doornbos");
		driverList.add("Robert");
	}
}
