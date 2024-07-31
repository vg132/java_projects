package u131;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class Sax
{
	/**
	 * Parse a XML file and check if its well-formed (arg 1)
	 *  and if arg 2 is true also check if its valid.
	 */
	public static void main(String args[])
	{
		try
		{
			SAXErrorHandler seh=new SAXErrorHandler();
			XMLReader parser=XMLReaderFactory.createXMLReader();
			parser.setErrorHandler(seh);
			
			if(args[1].equalsIgnoreCase("true"))
			{
				parser.setFeature("http://xml.org/sax/features/validation",true);
				parser.parse(new InputSource(new FileReader(args[0])));
				if(seh.hasErrors()==true)
				{
					System.out.println(args[0]+" is not valid.");
					System.out.println(seh);
				}
				else
				{
					System.out.println(args[0]+" is valid.");
				}
			}
			else
			{
				parser.parse(new InputSource(new FileReader(args[0])));
				if(seh.hasErrors()==true)
				{
					System.out.println(args[0]+" is not well-formed.");
					System.out.println(seh);
				}
				else
				{
					System.out.println(args[0]+" is well-formed.");
				}
			}
			System.exit(0);
		}
		catch(IOException io)
		{
			System.err.println("IO Error: "+io.getMessage());
		}
		catch(SAXNotSupportedException snse)
		{
			
			System.err.println("SAX Error: "+snse.getMessage());
			snse.printStackTrace(System.err);
		}
		catch(SAXException se)
		{
			System.err.println("SAX Error: "+se.getMessage());
			se.printStackTrace(System.err);
		}
	}
}

/**
 * Error handler class for the sax parser.
 */
class SAXErrorHandler implements ErrorHandler
{
	private List<SAXParseException> errors=new ArrayList<SAXParseException>();
	private List<SAXParseException> fatalErrors=new ArrayList<SAXParseException>();
	private List<SAXParseException> warnings=new ArrayList<SAXParseException>();
	
	public void error(SAXParseException exception)
	throws SAXException
	{
		errors.add(exception);
	}

	public void fatalError(SAXParseException exception)
	throws SAXException
	{
		fatalErrors.add(exception);
	}
	
	public void warning(SAXParseException exception)
	throws SAXException
	{
		warnings.add(exception);
	}

	public boolean hasErrors()
	{
		if((errors.size()==0)&&(fatalErrors.size()==0))
			return(false);
		return(true);
	}
	
	public List<SAXParseException> getErrors()
	{
		return(errors);
	}

	public List<SAXParseException> getFatalErrors()
	{
		return(fatalErrors);
	}

	public List<SAXParseException> getWarnings()
	{
		return(warnings);
	}
	
	/**
	 * Gets a string with a nice report of all errors, fatal errors and warnings
	 * from the parser.
	 * 
	 * @return A error report.
	 */
	public String toString()
	{
		StringBuffer buffer=new StringBuffer();
		if(fatalErrors.size()>0)
			buffer.append("\n-Fatal Errors-\n");
		for(SAXParseException spe : fatalErrors)
		{
			buffer.append(spe.getMessage()+"\n");
		}
		if(errors.size()>0)
			buffer.append("\n-Errors-\n");
		for(SAXParseException spe : errors)
		{
			buffer.append(spe.getMessage()+"\n");
		}
		if(warnings.size()>0)
			buffer.append("\n-Warnings-\n");
		for(SAXParseException spe : warnings)
		{
			buffer.append(spe.getMessage()+"\n");
		}
		return(buffer.toString());
	}
}