/**
 * Copyright (C) VG Software 2003-mar-05
 *  
 * Document History
 * 
 * Created: 2003-mar-05 13:11:13 by Viktor
 * 
 */
package com.vgsoftware.dsvquest.components;

import java.awt.Dimension;
import javax.swing.JButton;

/**
 * An extension of the JButton. This button can have a fixed size.
 * 
 * @author Viktor
 * @see JButton
 * 
 */
public class JFixedButton extends JButton
{
	/**
	 * Creates a button with text
	 * 
	 * @param text the text of the button.
	 */
	public JFixedButton(String text)
	{
		super(text);
	}

	/**
	 * Creates a button with text and preferred size.
	 * 
	 * @param text the text of the button.
	 * @param size the size of the button.
	 */
	public JFixedButton(String text, Dimension size)
	{
		super(text);
		setPreferredSize(size);
	}
	
	/**
	 * Creates a button with text and preferred size.
	 * 
	 * @param text the text of the button.
	 * @param width the width of the button.
	 * @param height the height of the button.
	 */
	public JFixedButton(String text, int width, int height)
	{
		super(text);
		setPreferredSize(new Dimension(width,height));
	}

	/**
	 * Set the fixed size of this button.
	 * 
	 * @param size fixed size of the button.
	 */
	public void setSize(Dimension size)
	{
		setPreferredSize(size);
	}

	/**
	 * Set the fixed size of this button.
	 * 
	 * @param width the width of the button.
	 * @param height the height of the button.
	 */
	public void setSize(int width,int height)
	{
		setSize(new Dimension(width,height));
	}

	/**
	 * @return The minimum size of this component.
	 */
	public Dimension getMinimumSize()
	{
		return(getPreferredSize()); 
	}
	
	/**
	 * @return The maximum size of this component.
	 */
	public Dimension getMaximumSize()
	{
		return(getPreferredSize()); 
	}
}