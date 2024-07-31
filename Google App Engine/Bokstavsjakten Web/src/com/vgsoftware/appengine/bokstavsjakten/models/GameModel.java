package com.vgsoftware.appengine.bokstavsjakten.models;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class GameModel implements Serializable
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.SEQUENCE)
	private long _id;
	@Persistent
	private int _status = 0;
	@Persistent
	private UserModel _player1 = null;
	@Persistent
	private UserModel _player2 = null;
	@Persistent
	private Date _created = null;
	@Persistent
	private Date _started = null;
	@Persistent
	private Date _lastMove = null;
}
