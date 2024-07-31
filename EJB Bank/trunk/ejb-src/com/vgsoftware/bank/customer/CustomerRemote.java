package com.vgsoftware.bank.customer;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.EJBObject;

import com.vgsoftware.bank.data.AccountData;

public interface CustomerRemote extends EJBObject
{
	public Boolean login(String username, String password)
	throws RemoteException;

	public Collection<AccountData> getAccounts()
	throws RemoteException;
	
	public Boolean transfer(Integer from, Integer to, Double amount)
	throws RemoteException;
	
	public Boolean deposit(Integer number, Double amount)
	throws RemoteException;
	
	public String getName()
	throws RemoteException;
}
