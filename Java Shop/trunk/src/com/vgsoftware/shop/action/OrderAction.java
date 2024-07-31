package com.vgsoftware.shop.action;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.vgsoftware.shop.data.CartData;
import com.vgsoftware.shop.data.CartItemData;
import com.vgsoftware.shop.data.CustomerData;
import com.vgsoftware.shop.data.Globals;
import com.vgsoftware.shop.data.InvoiceData;
import com.vgsoftware.shop.model.InvoiceItemModel;
import com.vgsoftware.shop.model.InvoiceModel;
import com.vgsoftware.vaction.Action;

public class OrderAction extends Action
{
	public String execute(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String,String> config=(Map<String,String>)request.getSession().getServletContext().getAttribute(Globals.CONFIG);
		CustomerData cd=null;
		if(request.getSession().getAttribute("customer")!=null)
			cd=((CustomerData)request.getSession().getAttribute("customer"));
		CartData cart=null;
		if(request.getSession().getAttribute("cart")!=null)
			cart=(CartData)request.getSession().getAttribute("cart");
		if((cd!=null)&&(cart!=null))
		{
			try
			{
				InvoiceData id=new InvoiceData();
				id.setCustomerId(cd.getId());
				id.setOrderDate(new Timestamp(System.currentTimeMillis()));
				id.setTotalPrice(cart.getTotalPrice());
				if(InvoiceModel.addInvoice(getDataSource(request),config.get("seq_invoice"),id)==true)
				{
					if(InvoiceItemModel.addInvoiceItems(getDataSource(request),config.get("seq_invoice_item"),id.getId(),cart.getItems()))
					{
						try
						{
							SimpleEmail se=new SimpleEmail();
							se.setHostName(config.get("smtp_host"));
							se.addTo(cd.getEmail());
							se.setFrom(config.get("email_from"));
							se.setSubject("New Order");
			
							// Read the mail template
							String content="";
							String line="";
							BufferedReader br=new BufferedReader(new FileReader(request.getSession().getServletContext().getRealPath("/WEB-INF/template/mail/order.tpl")));
							while((line=br.readLine())!=null)
								content+=line+"\n";
							content=content.replace("{name}",config.get("pagename"));
							String items="";
							for(CartItemData cid : cart.getItems())
								items+=cid.getName()+" - "+cid.getQuantity()+" - "+(cid.getPrice()*cid.getQuantity())+" ("+cid.getPrice()+")\n"; 
							content=content.replace("{items}",items);
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
				}
			}
			catch(SQLException sql)
			{
			}
		}
		return(Globals.FAILURE);
	}
}
