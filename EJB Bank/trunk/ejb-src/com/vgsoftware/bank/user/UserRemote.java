package com.vgsoftware.bank.user;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface UserRemote extends EJBObject
{
	public String getName()
	throws RemoteException;

	public Boolean isAdmin()
	throws RemoteException;

	public void setPassword(String password)
	throws RemoteException;
	
	public Boolean validate(String password)
	throws RemoteException;
}
