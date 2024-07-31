package u212;
/*
 * Created on 2005-maj-04
 */
import java.rmi.Remote;
import java.util.Vector;
import java.rmi.RemoteException;

/**
 * @author vikto-ga
 */
public interface U212RemoteInterface extends Remote
{
  public void addBall() throws RemoteException;
  public void pauseBalls() throws RemoteException;
  public Vector getBalls() throws RemoteException;
}
