/*
 * Created on: 2003-okt-23
 * Created by: Viktor
 * 
 * Document History
 * 
 * 2003-okt-23 Created by Viktor
 */
package ass8;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * XML->XSLT transformer
 */
public class XMLTransform extends HttpServlet
{
	/**
	 * Recive a xml and a xsl document and return the resulting document after the 
	 * transformation. Set default content type or a provieded content type.
	 * 
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		try
		{
			String contentType=request.getParameter("contenttype");
			if((contentType==null)||(contentType.equals("")))
				response.setContentType("text/html");
			else
				response.setContentType(contentType);
			TransformerFactory tff = TransformerFactory.newInstance();
			Transformer trans;
			StreamSource xslSource = new StreamSource(new StringReader(request.getParameter("xslt")));
			trans = tff.newTransformer(xslSource);
			StreamSource xmlSource = new StreamSource(new StringReader(request.getParameter("xml")));
			StreamResult output = new StreamResult(response.getOutputStream());
			trans.transform(xmlSource,output);
		}
		catch(TransformerConfigurationException tce)
		{
			tce.printStackTrace(System.err);
		}
		catch(TransformerException te)
		{
			te.printStackTrace(System.err);
		}
	}
}
