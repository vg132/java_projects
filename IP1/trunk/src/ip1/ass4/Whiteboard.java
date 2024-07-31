/*
 * Created on 2003-aug-29
 * Created by Viktor
 * 
 * Document History
 * 
 * 2003-aug-29 Created by Viktor.
 */
package ip1.ass4;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Whiteboard P2P program, everyting that is painted on one client is sent to the other client
 * and replicated on his screen.
 */
public class Whiteboard extends JFrame
{
	private Color[] colors=new Color[]{Color.BLACK, Color.GRAY, Color.RED, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.LIGHT_GRAY, Color.ORANGE, Color.PINK, Color.YELLOW, Color.WHITE};
	private JLabel drawArea=new JLabel();
	private String remoteHost=null;
	private int remotePort=0;
	private int localPort=0;
	private Mouse mouse=new Mouse();
	private JButton button=new JButton();
	private ColorPanel currentColor=new ColorPanel(Color.BLACK,20,20);
	private JComboBox pencilSize=null;
	private DatagramSocket dataSocket=null;
	
	private int lastX, lastY, otherLastX, otherLastY;
	private InetAddress reciver=null;

	public static void main(String[] arg)
	{
		if(arg.length==3)
		{
			new Whiteboard(arg);
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Wrong number of parameters.");
			System.exit(0);
		}	
	}

	/**
	 * Setup main window and communications with UDP sockets.
	 * 
	 * @param arg The commandline arguments
	 */
	public Whiteboard(String[] arg)
	{
		localPort=Integer.parseInt(arg[0]);
		remoteHost=arg[1];
		remotePort=Integer.parseInt(arg[2]);
		try
		{
			dataSocket=new DatagramSocket(localPort);
		}
		catch(SocketException se)
		{
		}
		try
		{
			reciver=InetAddress.getByName(remoteHost);
		}
		catch(UnknownHostException uhe)
		{
			uhe.printStackTrace(System.err);
		}
	
		Server s=new Server(dataSocket);
		s.start();
		setSize(400,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		drawArea.addMouseListener(mouse);
		drawArea.addMouseMotionListener(mouse);
		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(buildControlArea(),BorderLayout.SOUTH);
		p1.add(drawArea,BorderLayout.CENTER);
		getContentPane().add(p1);
		setVisible(true);
	}
	
	/**
	 * Build the control area for this program, colors, pencil size etc.
	 * 
	 * @return A JPanel with all controles for this program.
	 */
	private JPanel buildControlArea()
	{
		int i=0;
		JPanel p1=new JPanel();
		p1.setLayout(new BorderLayout());
		JPanel p2=new JPanel();
		p2.setLayout(new BoxLayout(p2,BoxLayout.Y_AXIS));
		pencilSize=new JComboBox();
		for(i=1;i<=10;i++)
			pencilSize.addItem(""+i);
		p2.add(pencilSize);
		p2.add(currentColor);

		JPanel p3=new JPanel();
		p3.setLayout(new GridLayout(2,6,2,2));
		for(i=0;i<colors.length;i++)
		{
			ColorPanel cp=new ColorPanel(colors[i],20,20);
			cp.addMouseListener(mouse);
			p3.add(cp);
		}
		p1.add(p2,BorderLayout.WEST);
		p1.add(p3,BorderLayout.CENTER);
		return(p1);
	}
	
	/**
	 * Send the last move made by this client in a specified protocol language.
	 * 
	 * 1. Byte=What to do, 0=set a point, 1=draw a line
	 * 2. Byte=Pencil size for current operation
	 * 3. Int=Color used for current operation
	 * 4. Int=X pos for current operation
	 * 5. Int=Y pos for current operation
	 * 
	 * @param whatTodo Type of operation.
	 */
	private void send(int whatTodo)
	{
		try
		{
			byte[] data;
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			DataOutputStream dos=new DataOutputStream(baos);
			dos.writeByte(whatTodo);
			dos.writeByte(pencilSize.getSelectedIndex()+1);
			dos.writeInt(currentColor.getBackground().getRGB());
			dos.writeInt(lastX);
			dos.writeInt(lastY);
			data=baos.toByteArray();
			DatagramPacket dp=new DatagramPacket(data,data.length,reciver,remotePort);
			dataSocket.send(dp);
		}
		catch(IOException io)
		{
			
		}
	}

	public void drawLine(int color, int size, int x1, int y1, int x2, int y2)
	{
		Graphics2D g2d=(Graphics2D)drawArea.getGraphics();
		g2d.setStroke(new BasicStroke(size));
		g2d.setColor(new Color(color));
		g2d.drawLine(x1,y1,x2,y2);
	}
	
	/**
	 * Act on the different mouse movements and buttons
	 */
	class Mouse extends MouseAdapter implements MouseMotionListener
	{
		public void mousePressed(MouseEvent arg)
		{
			if(arg.getSource()==drawArea)
			{
				super.mousePressed(arg);
				lastX=arg.getX();
				lastY=arg.getY();
				send(0);
			}
		}

		public void mouseReleased(MouseEvent arg)
		{
			super.mouseReleased(arg);
			if(arg.getSource()==drawArea)
			{
				Graphics2D g2d=(Graphics2D)drawArea.getGraphics();
				g2d.setStroke(new BasicStroke(pencilSize.getSelectedIndex()+1));
				g2d.setColor(currentColor.getBackground());
				g2d.drawLine(lastX,lastY,arg.getX(),arg.getY());
				lastX=arg.getX();
				lastY=arg.getY();
				send(1);				
			}
		}

		public void mouseDragged(MouseEvent arg)
		{
			if(arg.getSource()==drawArea)
			{
				Graphics2D g2d=(Graphics2D)drawArea.getGraphics();
				g2d.setStroke(new BasicStroke(pencilSize.getSelectedIndex()+1));
				g2d.setColor(currentColor.getBackground());
				g2d.drawLine(lastX,lastY,arg.getX(),arg.getY());
				lastX=arg.getX();
				lastY=arg.getY();
				send(1);
			}
		}

		public void mouseMoved(MouseEvent arg)
		{
		}

		public void mouseClicked(MouseEvent arg)
		{
			super.mouseClicked(arg);
			if(arg.getSource() instanceof ColorPanel)
			{
				currentColor.setBackground(((ColorPanel)arg.getSource()).getBackground());
			}
		}
	}
	
	/**
	 * The server class listen for incoming data from the client, when it recives a 
	 * datagram it uses the data to draw a line or update a point on the servers whiteboard.
	 */
	class Server extends Thread
	{
		private DatagramSocket mySocket=null;
		
		public Server(DatagramSocket mySocket)
		{
			this.mySocket=mySocket;
		}
		
		public void run()
		{
			byte[] data=new byte[14];
			int x,y,size,color,whatTodo;
			DatagramPacket dp=new DatagramPacket(data,data.length);
			try
			{
				while(true)
				{
					mySocket.receive(dp);
					DataInputStream dis=new DataInputStream(new ByteArrayInputStream(dp.getData()));
					whatTodo=dis.readByte();
					size=dis.readByte();
					color=dis.readInt();
					x=dis.readInt();
					y=dis.readInt();
					if(whatTodo==1)
						drawLine(color,size,otherLastX,otherLastY,x,y);
					otherLastX=x;
					otherLastY=y;
					dis.close();
				}
			}
			catch(IOException io)
			{
			}
		}
	}
}

/**
 * This is a class used for the color pallet at the bottom of the screen.
 * It extends a normal JPanel but addes some new futures to it.
 */
class ColorPanel extends JPanel
{
	public ColorPanel(Color color)
	{
		super();
		this.setBackground(color);
		this.paintBorder(this.getGraphics());
	}

	public ColorPanel(Color color, Dimension size)
	{
		super();
		this.setBackground(color);
		this.paintBorder(this.getGraphics());
		this.setPreferredSize(size);		
	}
	
	public ColorPanel(Color color, int width, int height)
	{
		super();
		this.setBackground(color);
		this.paintBorder(this.getGraphics());
		this.setPreferredSize(new Dimension(width,height));
	}

	public Dimension getMaximumSize()
	{
		return(super.getPreferredSize());
	}

	public Dimension getMinimumSize()
	{
		return(super.getPreferredSize());
	}
}