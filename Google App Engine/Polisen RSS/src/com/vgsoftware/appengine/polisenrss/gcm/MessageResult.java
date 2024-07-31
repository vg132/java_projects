package com.vgsoftware.appengine.polisenrss.gcm;

import org.apache.commons.lang3.StringUtils;

import com.google.android.gcm.server.Result;

public class MessageResult
{
	private Result _result = null;
	private String _deviceId = null;

	MessageResult(Result result, String deviceId)
	{
		_result = result;
		_deviceId = deviceId;
	}

	public Result getResult()
	{
		return _result;
	}

	public String getDeviceId()
	{
		return _deviceId;
	}

	public String getErrorName()
	{
		return _result.getErrorCodeName() != null ? _result.getErrorCodeName() : StringUtils.EMPTY;
	}

	public boolean isError()
	{
		return !StringUtils.isEmpty(getResult().getErrorCodeName());
	}
}
