package com.vgsoftware.appengine.polisenrss.gcm;

import java.util.ArrayList;
import java.util.List;

public class SendResult
{
	private List<MessageResult> _messageResult = null;

	SendResult(List<MessageResult> messageResults)
	{
		_messageResult = messageResults;
	}

	public List<MessageResult> getMessageResult()
	{
		return _messageResult != null ? _messageResult : new ArrayList<MessageResult>();
	}

	public List<MessageResult> getErrorResults()
	{
		List<MessageResult> results = new ArrayList<MessageResult>();
		for (MessageResult result : getMessageResult())
		{
			if (result.isError())
			{
				results.add(result);
			}
		}
		return results;
	}

	public boolean hasErrors()
	{
		for (MessageResult result : getMessageResult())
		{
			if (result.isError())
			{
				return true;
			}
		}
		return false;
	}
}
