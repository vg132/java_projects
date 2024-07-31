/**
 * Copyright (C) VG Software 2003-mar-14
 *  
 * Document History
 * 
 * Created: 2003-mar-14 14:44:24 by Viktor
 * 
 */
package com.vgsoftware.vgutil.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;

/**
 * This interface must be implemented by all dbobject classes that want to be used
 * with dblib tags.
 * 
 * @author Viktor
 * 
 */
public interface IDBObject
{
	public Map getData();
	public void setData(Object key, Object data);
	public Object getData(Object key);
	public Object getFormattedData(Object key, String format);
	public Object getFirst(Connection conn) throws SQLException;
	public Object getFirst(Connection conn, String whereClause) throws SQLException;
	public List getAll(Connection conn) throws SQLException;
	public List getAll(Connection conn, String whereClause) throws SQLException;
	public List executeQuery(Connection conn) throws SQLException;
	public List executeQuery(Connection conn, String where) throws SQLException;
}
