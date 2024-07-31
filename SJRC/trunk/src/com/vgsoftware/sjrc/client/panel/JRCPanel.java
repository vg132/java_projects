/*
 * Created on 2003-sep-17
 * Created by Viktor
 *
 * Document History
 *
 * 2003-sep-17 Created by Viktor.
 */
package com.vgsoftware.sjrc.client.panel;

import java.awt.Color;
import java.awt.Insets;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * Abstract super class for all window components used by the SJRC Client.
 */
public abstract class JRCPanel extends JPanel
{
	private DefaultStyledDocument doc=new DefaultStyledDocument();
	protected JTextPane textArea=new JTextPane(doc);
	protected JScrollPane textScroll=new JScrollPane(textArea);

	protected JTextField txtField=new JTextField();
	protected List history=new LinkedList();
	protected int historyPos=0;

	public JRCPanel()
	{
		super();
		textArea.setEditable(false);
		textArea.setMargin(new Insets(5,5,5,5));
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
		}
		catch(BadLocationException ble)
		{
			ble.printStackTrace(System.err);
		}
	}
}
