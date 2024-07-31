package com.vgsoftware.rdc.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class TextPanel extends JPanel
{
	private DefaultStyledDocument doc=new DefaultStyledDocument();
	private JTextArea textArea=new JTextArea(doc);

	public TextPanel()
	{
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setMargin(new Insets(5,5,5,5));

		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(textArea),BorderLayout.CENTER);
	}

	public void setText(String text)
	{
		textArea.setText(text);
	}

	public void addLine(String line)
	{
		SimpleAttributeSet attr=new SimpleAttributeSet();
		StyleConstants.setForeground(attr,Color.BLACK);
		try
		{
			doc.insertString(doc.getLength(),line+"\n",attr);
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
		catch(BadLocationException ble)
		{
			ble.printStackTrace(System.err);
		}
	}
}
