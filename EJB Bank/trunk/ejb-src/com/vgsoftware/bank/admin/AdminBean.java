package com.vgsoftware.bank.admin;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.vgsoftware.bank.account.AccountHome;
import com.vgsoftware.bank.account.AccountRemote;
import com.vgsoftware.bank.data.AccountData;
import com.vgsoftware.bank.user.UserHome;
import com.vgsoftware.bank.user.UserRemote;

public class AdminBean implements SessionBean
{
	private UserRemote user=null;
	private UserHome userHome=null;
	private AccountHome accountHome=null;

	public void ejbCreate()
	{
		try
		{
			InitialContext jndiContext=new InitialContext();
			Object ref=jndiContext.lookup("java:comp/env/UserEJB");
			userHome=(UserHome)PortableRemoteObject.narrow(ref,UserHome.class);

			ref=jndiContext.lookup("java:comp/env/AccountEJB");
			accountHome=(AccountHome)PortableRemoteObject.narrow(ref,AccountHome.class);
		}
		catch(NamingException ne)
		{
			ne.printStackTrace(System.err);
		}
	}

	public Boolean login(String username, String password)
	throws RemoteException
	{
		try
		{
			UserRemote remote=userHome.findByPrimaryKey(username);
			if((remote.validate(password))&&(remote.isAdmin()))
			{
				user=remote;
				return(true);
			}
		}
		catch(FinderException fe)
		{
			fe.printStackTrace(System.err);
		}
		catch(RemoteException re)
		{
			re.printStackTrace(System.err);
		}
		return(false);
	}

	public Boolean addCustomer(String name, String password)
	throws RemoteException
	{
		if(user!=null)
		{
			try
			{
				userHome.create(name,password);
				return(true);
			}
			catch(CreateException ce)
			{
				ce.printStackTrace(System.err);
			}
			catch(RemoteException re)
			{
				re.printStackTrace(System.err);
			}
		}
		return(false);
	}

	public Boolean addAccount(String customer, Integer number)
	throws RemoteException
	{
		if(user!=null)
		{
			try
			{
				accountHome.create(customer,number);
				return(true);
			}
			catch(CreateException ce)
			{
				ce.printStackTrace(System.err);
			}
			catch(RemoteException re)
			{
				re.printStackTrace(System.err);
			}
		}
		return(false);
	}

	public Double getBankBalance()
	throws RemoteException
	{
		double balance=0.0;
		if(user!=null)
		{
			try
			{
				Collection<AccountRemote> accounts=accountHome.findAll();
				for(AccountRemote ar : accounts)
					balance+=ar.getBalance();
			}
			catch(FinderException fe)
			{
				fe.printStackTrace(System.err);
			}
			catch(RemoteException re)
			{
				re.printStackTrace(System.err);
			}
		}
		return(balance);
	}
	
	public Collection<String> getCustomers()
	{
		List<String> userNames=new ArrayList<String>();
		if(user!=null)
		{
			try
			{
				Collection<UserRemote> users=userHome.findAllByType(/*UserHome.CUSTOMER_TYPE*/0);
				for(UserRemote ur : users)
					userNames.add(ur.getName());
			}
			catch(FinderException fe)
			{
				fe.printStackTrace(System.err);
			}
			catch(RemoteException re)
			{
				re.printStackTrace(System.err);
			}
		}
		return(userNames);
	}

	public Boolean removeCustomer(String name)
	{
		if(user!=null)
		{
			try
			{
				UserRemote customer=userHome.findByPrimaryKey(name);
				Collection<AccountRemote> accounts=accountHome.findByOwner(name);
				for(AccountRemote account : accounts)
					account.remove();
				customer.remove();
				return(true);
			}
			catch(FinderException fe)
			{
				fe.printStackTrace(System.err);
			}
			catch(RemoveException re)
			{
				re.printStackTrace(System.err);
			}
			catch(RemoteException ree)
			{
				ree.printStackTrace(System.err);
			}
		}
		return(false);
	}
	
	public Collection<AccountData> getCustomerAccounts(String customer)
	{
		List<AccountData> accounts=new ArrayList<AccountData>();
		if(user!=null)
		{
			try
			{
				Collection<AccountRemote> accountRemotes=accountHome.findByOwner(customer);
				for(AccountRemote ar : accountRemotes)
					accounts.add(new AccountData(ar.getNumber(),ar.getBalance()));
			}
			catch(RemoteException re)
			{
			}
			catch(FinderException fe)
			{
			}
		}
		return(accounts);
	}
	
	public Boolean removeCustomerAccount(Integer number)
	{
		if(user!=null)
		{
			try
			{
				AccountRemote remote=accountHome.findByPrimaryKey(number);
				remote.remove();
				return(true);
			}
			catch(FinderException fe)
			{
			}
			catch(RemoteException re)
			{
			}
			catch(RemoveException ree)
			{
			}
		}
		return(false);
	}
	
	public void ejbActivate()
	throws EJBException, RemoteException
	{
	}

	public void ejbPassivate()
	throws EJBException, RemoteException
	{
	}

	public void ejbRemove()
	throws EJBException, RemoteException
	{
	}

	public void setSessionContext(SessionContext ctx)
	throws EJBException, RemoteException
	{
	}
}
