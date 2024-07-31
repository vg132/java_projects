package com.vgsoftware.jbb.data;

import java.sql.Timestamp;

public class MemberData
{
	private int id=-1;
	private String username=null;
	private String email=null;
	private String password=null;
	private Timestamp joinDate=null;
	private int memberGroup=-1;
	private int posts=-1;

	public MemberData()
	{
	}

	/**
	 * Gets the email.
	 * @return Returns the email.
	 */
	public String getEmail()
	{
		return (email);
	}

	/**
	 * Sets the email.
	 * @param email The email to set.
	 */
	public void setEmail(String email)
	{
		this.email=email;
	}

	/**
	 * Gets the id.
	 * @return Returns the id.
	 */
	public int getId()
	{
		return (id);
	}

	/**
	 * Sets the id.
	 * @param id The id to set.
	 */
	public void setId(int id)
	{
		this.id=id;
	}

	/**
	 * Gets the joinDate.
	 * @return Returns the joinDate.
	 */
	public Timestamp getJoinDate()
	{
		return (joinDate);
	}

	/**
	 * Sets the joinDate.
	 * @param joinDate The joinDate to set.
	 */
	public void setJoinDate(Timestamp joinDate)
	{
		this.joinDate=joinDate;
	}

	/**
	 * Gets the memberGroup.
	 * @return Returns the memberGroup.
	 */
	public int getMemberGroup()
	{
		return (memberGroup);
	}

	/**
	 * Sets the memberGroup.
	 * @param memberGroup The memberGroup to set.
	 */
	public void setMemberGroup(int memberGroup)
	{
		this.memberGroup=memberGroup;
	}

	/**
	 * Gets the password.
	 * @return Returns the password.
	 */
	public String getPassword()
	{
		return (password);
	}

	/**
	 * Sets the password.
	 * @param password The password to set.
	 */
	public void setPassword(String password)
	{
		this.password=password;
	}

	/**
	 * Gets the username.
	 * @return Returns the username.
	 */
	public String getUsername()
	{
		return (username);
	}

	/**
	 * Sets the username.
	 * @param username The username to set.
	 */
	public void setUsername(String username)
	{
		this.username=username;
	}

	/**
	 * Gets the posts.
	 * @return Returns the posts.
	 */
	public int getPosts()
	{
		return (posts);
	}

	/**
	 * Sets the posts.
	 * @param posts The posts to set.
	 */
	public void setPosts(int posts)
	{
		this.posts=posts;
	}

}
