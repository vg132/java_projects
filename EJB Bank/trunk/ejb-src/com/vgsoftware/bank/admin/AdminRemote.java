package com.vgsoftware.bank.admin;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.EJBObject;

import com.vgsoftware.bank.data.AccountData;

public interface AdminRemote extends EJBObject
{
	public Boolean login(String name, String password)
	throws RemoteException;

	public Boolean addCustomer(String name, String password)
	throws RemoteException;

	public Boolean addAccount(String customer, Integer number)
	throws RemoteException;

	public Double getBankBalance()
	throws RemoteException;

	public Collection<String> getCustomers()
	throws RemoteException;
	
	public Boolean removeCustomer(String name)
	throws RemoteException;
	
	public Collection<AccountData> getCustomerAccounts(String customer)
	throws RemoteException;
	
	public Boolean removeCustomerAccount(Integer number)
	throws RemoteException;
}
