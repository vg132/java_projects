package com.vgsoftware.shop.action;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.vgsoftware.shop.data.CustomerData;
import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.shop.model.CountryModel;
import com.vgsoftware.shop.model.CustomerModel;
import com.vgsoftware.vaction.Action;

public class RegisterAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			Map<String,String> config=(Map<String,String>)request.getAttribute(Globals.CONFIG);
			CustomerData cd=new CustomerData();
			cd.setName(getParam(request,"name"));
			cd.setEmail(getParam(request,"email"));
			cd.setPassword(getParam(request,"password"));
			cd.setAddress(getParam(request,"address"));
			cd.setCity(getParam(request,"city"));
			cd.setState(getParam(request,"state"));
			cd.setPostCode(getParam(request,"post_code"));
			cd.setCountryId(getIntParam(request,"country"));
			cd.setCurrency(getParam(request,"currency"));
			cd.setCustomerType(1);
			String msg=cd.validate();
			if((msg==null)&&(CustomerModel.addCustomer(getDataSource(request),config.get("seq_customer"),cd)==true))
			{
				try
				{
					SimpleEmail se=new SimpleEmail();
					se.setHostName(config.get("smtp_host"));
					se.addTo(cd.getEmail());
					se.setFrom(config.get("email_from"));
					se.setSubject("Account Created");
	
					// Read the mail template
					String content="";
					String line="";
					BufferedReader br=new BufferedReader(new FileReader(request.getSession().getServletContext().getRealPath("/WEB-INF/template/mail/register.tpl")));
					while((line=br.readLine())!=null)
						content+=line+"\n";
					content=content.replace("{name}",config.get("pagename"));
					content=content.replace("{url}",config.get("baseurl"));
					content=content.replace("{email}",cd.getEmail());
					content=content.replace("{password}",cd.getPassword());
					se.setMsg(content);
					se.send();
				}
				catch(IOException io)
				{
				}
				catch(EmailException ee)
				{
				}
				return(Globals.SUCCESS);
			}
			else if(msg==null)
				request.setAttribute("errormsg","Email address already taken.");
			else
				request.setAttribute("errormsg",msg);
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		request.setAttribute("name",getParam(request,"name"));
		request.setAttribute("email",getParam(request,"email"));
		request.setAttribute("address",getParam(request,"address"));
		request.setAttribute("city",getParam(request,"city"));
		request.setAttribute("state",getParam(request,"state"));
		request.setAttribute("post_code",getParam(request,"post_code"));
		request.setAttribute("country",getIntParam(request,"country"));
		request.setAttribute("selectedCurrency",getParam(request,"currency"));

		try
		{
			request.setAttribute("countrys",CountryModel.getCountrys(getDataSource(request)));
		}
		catch(SQLException sql)
		{
			sql.printStackTrace(System.err);
		}
		return(Globals.FAILURE);
	}
}
