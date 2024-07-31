package com.vgsoftware.jdownloader.gui.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.JFrame;

import com.vgsoftware.jdownloader.engine.IJDEngine;
import com.vgsoftware.jdownloader.gui.IGui;

public class WindowGui extends JFrame implements IGui, ActionListener
{
	private IJDEngine engine=null;

	public WindowGui(IJDEngine engine)
	{
		this.engine=engine;
		this.engine.setGui(this);
	}

	/**
	 * @see com.vgsoftware.jdownloader.gui.IGui#eventDownloadList()
	 */
	public void eventDownloadList()
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see com.vgsoftware.jdownloader.gui.IGui#eventFatalError(java.lang.String)
	 */
	public void eventFatalError(String text)
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see com.vgsoftware.jdownloader.gui.IGui#eventFinished(int)
	 */
	public void eventFinished(int id)
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see com.vgsoftware.jdownloader.gui.IGui#eventResumeRestartCancel(java.io.File, java.net.URL)
	 */
	public int eventResumeRestartCancel(File file, URL url)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see com.vgsoftware.jdownloader.gui.IGui#eventWarning(java.lang.String)
	 */
	public void eventWarning(String text)
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}
}
