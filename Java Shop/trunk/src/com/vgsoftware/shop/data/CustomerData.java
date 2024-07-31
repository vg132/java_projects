package com.vgsoftware.shop.data;

public class CustomerData
{
	private int id=-1;
	private int countryId=-1;
	private int customerType=-1;
	private String email=null;
	private String password=null;
	private String name=null;
	private String address=null;
	private String city=null;
	private String state=null;
	private String postCode=null;
	private String currency=null;
	private String country=null;
	
	public CustomerData()
	{
	}

	public String getAddress()
	{
		return (address);
	}

	public void setAddress(String address)
	{
		this.address=address;
	}

	public String getCity()
	{
		return (city);
	}

	public void setCity(String city)
	{
		this.city=city;
	}

	public String getCountry()
	{
		return (country);
	}

	public void setCountry(String country)
	{
		this.country=country;
	}

	public int getCountryId()
	{
		return (countryId);
	}

	public void setCountryId(int countryId)
	{
		this.countryId=countryId;
	}

	public String getCurrency()
	{
		return (currency);
	}

	public void setCurrency(String currency)
	{
		this.currency=currency;
	}

	public int getCustomerType()
	{
		return (customerType);
	}

	public void setCustomerType(int customerType)
	{
		this.customerType=customerType;
	}

	public String getEmail()
	{
		return (email);
	}

	public void setEmail(String email)
	{
		this.email=email;
	}

	public int getId()
	{
		return (id);
	}

	public void setId(int id)
	{
		this.id=id;
	}

	public String getName()
	{
		return (name);
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public String getPassword()
	{
		return (password);
	}

	public void setPassword(String password)
	{
		this.password=password;
	}

	public String getPostCode()
	{
		return (postCode);
	}

	public void setPostCode(String postCode)
	{
		this.postCode=postCode;
	}

	public String getState()
	{
		return (state);
	}

	public void setState(String state)
	{
		this.state=state;
	}
	
	public String validate()
	{
		if(password.length()<6)
			return("Password is to short, minimum 6 characters");
		else if(email.length()<8)
			return("Invalid email address");
		return(null);
	}
}
