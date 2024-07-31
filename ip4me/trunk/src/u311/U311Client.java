package u311;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class U311Client extends MIDlet implements CommandListener
{
	private Form frmNetwork=new Form("Network Setup");
	private Form frmChat=new Form("Chat");
	private TextField txtPort=new TextField("Port:","",4,TextField.NUMERIC);
	private TextField txtAddress=new TextField("Server:","",64,TextField.ANY);
	private TextField txtMessage=new TextField("Msg:","",150,TextField.ANY);
	private TextField txtMessages=new TextField("Messages","",999,TextField.ANY|TextField.UNEDITABLE);

	private Command btnExit=new Command("Exit",Command.EXIT,1);
	private Command btnConnect=new Command("Connect",Command.OK,1);
	private Command btnSend=new Command("Send",Command.OK,1);

	private Display display=null;
	
	/**
	 * Build the two screens we need in the application.
	 */
	public U311Client()
	{
		display=Display.getDisplay(this);
		
		//build network form
		frmNetwork.addCommand(btnExit);
		frmNetwork.addCommand(btnConnect);
		frmNetwork.append(txtAddress);
		frmNetwork.append(txtPort);
		frmNetwork.setCommandListener(this);
		
		//build chat form
		frmChat.addCommand(btnExit);
		frmChat.addCommand(btnSend);
		frmChat.append(txtMessages);
		frmChat.append(txtMessage);
		frmChat.setCommandListener(this);
	}
	
	protected void destroyApp(boolean unconditional)
	throws MIDletStateChangeException
	{
	}

	protected void pauseApp()
	{
	}

	protected void startApp()
	throws MIDletStateChangeException
	{
		display.setCurrent(frmNetwork);
	}

	/**
	 * Act on the users input. 
	 */
	public void commandAction(Command c, Displayable d)
	{
		if(c==btnConnect)
		{
			display.setCurrent(frmChat);
		}
		else if(c==btnSend)
		{
			if(txtMessage.equals(""))
				return;
			if(txtMessages.getString().equals(""))
				txtMessages.setString(txtMessages.getString()+txtMessage.getString());
			else
				txtMessages.setString(txtMessages.getString()+"\n"+txtMessage.getString());
			txtMessage.setString("");
		}
		else if(c==btnExit)
		{
			try
			{
				this.destroyApp(false);
			}
			catch(MIDletStateChangeException mse)
			{
				mse.printStackTrace();
			}
		}
	}
}
