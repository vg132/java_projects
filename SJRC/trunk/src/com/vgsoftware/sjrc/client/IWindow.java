/*
 * Created on 2004-jun-04
 * Created by Viktor
 *
 * Document History
 *
 * 2004-jun-04 Created by Viktor
 */

package com.vgsoftware.sjrc.client;

import java.awt.Dimension;

import com.vgsoftware.sjrc.client.panel.JRCPanel;

public interface IWindow
{
	public void addPanel(JRCPanel panel, String label, boolean selected);
	public void removePanel(JRCPanel panel);
	public Dimension getSize();
	public void showWindow();
}
