package u133;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

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
