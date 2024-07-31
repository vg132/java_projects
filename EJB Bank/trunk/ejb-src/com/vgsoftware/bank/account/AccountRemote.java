package com.vgsoftware.bank.account;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface AccountRemote extends EJBObject
{
	public boolean deposit(Double amount)
	throws RemoteException;

	public boolean withdraw(Double amount)
	throws RemoteException;

	public Double getBalance()
	throws RemoteException;

	public Integer getNumber()
	throws RemoteException;
	
	public String getUser()
	throws RemoteException;
}
