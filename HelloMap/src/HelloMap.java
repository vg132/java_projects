import com.eightmotions.map.MapDisplay;
import com.eightmotions.util.UtilMidp;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class HelloMap extends MIDlet
{
	MapDisplay m_map;
	protected void destroyApp(boolean unconditional)
	throws MIDletStateChangeException
	{
	}

	protected void pauseApp()
	{		
	}

	protected void startApp() throws MIDletStateChangeException
	{
    UtilMidp.checkMIDP(this);  //Initialise the utility library...
    m_map=new MapDisplay();
    Display.getDisplay(this).setCurrent(m_map.getCanvas());
	}
}
