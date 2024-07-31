/**
 * Copyright (C) VG Software 2003-apr-07
 *  
 * Document History
 * 
 * Created: 2003-apr-07 20:39:41 by Viktor
 * 
 */
package com.vgsoftware.dsvquest;

import java.util.List;

/**
 * @author Viktor
 * 
 */
public interface IBuilding
{
	public boolean isClosed();
	public void close(boolean close);
	public boolean canEnter(List keys);
	public List getNededKeys();
	public List getReward();
}
