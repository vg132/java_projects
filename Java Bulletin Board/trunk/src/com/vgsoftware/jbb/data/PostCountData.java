package com.vgsoftware.jbb.data;


/**
 * Holds information about the nr of posts a member has done. Mapps agains the
 * <code>post_counter</code> table in the database.
 */
public class PostCountData
{
	private int memberId=-1;
	private int posts=-1;
	
	public PostCountData()
	{
	}
	
	public PostCountData(int memberId, int posts)
	{
		this.memberId=memberId;
		this.posts=posts;
	}

	/**
	 * Gets the memberId.
	 * @return Returns the memberId.
	 */
	public int getMemberId()
	{
		return (memberId);
	}

	/**
	 * Sets the memberId.
	 * @param memberId The memberId to set.
	 */
	public void setMemberId(int memberId)
	{
		this.memberId=memberId;
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
