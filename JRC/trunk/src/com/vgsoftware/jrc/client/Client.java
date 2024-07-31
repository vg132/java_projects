/*
 * Created on 2003-sep-09
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-sep-09 Created by Viktor.
 */
package com.vgsoftware.jrc.client;

import javax.swing.UIManager;

/**
 * Start class for the JRC Client program.
 */
public class Client
{
	/**
	 * Creates and starts a new JRC client
	 * @param args commandline aguments.
	 */
	public static void main(String args[])
	{
		try
		{
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		}
		catch(Exception e)
		{
		}
		new ClientMessageCenter();
	}
}