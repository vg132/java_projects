package u133;

/**
 * Holder class for a message in the system.
 */
public class Message
{
	public static final String TYPE="CTTP";
	public static final String VERSION="1.0";
	public static final String COMMAND="MESS";

	private String name=null;
	private String email=null;
	private String homepage=null;
	private String host=null;
	private String body=null;

	/**
	 * @return Returns the body.
	 */
	public String getBody()
	{
		return body;
	}

	/**
	 * @param body The body to set.
	 */
	public void setBody(String body)
	{
		this.body=body;
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email)
	{
		this.email=email;
	}

	/**
	 * @return Returns the homepage.
	 */
	public String getHomepage()
	{
		return homepage;
	}

	/**
	 * @param homepage The homepage to set.
	 */
	public void setHomepage(String homepage)
	{
		this.homepage=homepage;
	}

	/**
	 * @return Returns the host.
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * @param host The host to set.
	 */
	public void setHost(String host)
	{
		this.host=host;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name=name;
	}
}