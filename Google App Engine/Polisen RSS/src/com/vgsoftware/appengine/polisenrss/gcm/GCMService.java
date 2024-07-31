package com.vgsoftware.appengine.polisenrss.gcm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.vgsoftware.appengine.polisenrss.data.Recipient;

public class GCMService
{
	private static final int PageSize = 500;

	public static SendResult send(Message message, List<Recipient> recipients)
		throws IOException
	{
		List<String> deviceIds = new ArrayList<String>();
		for(Recipient recipient : recipients)
		{
			deviceIds.add(recipient.getDeviceId());
		}
		List<MessageResult> messageResultList = new ArrayList<MessageResult>();
		Sender sender = new Sender("AIzaSyBAbax1u_QvtLwnpvWW1_LR4x9uE0lP9R4");
		int pages = (int) Math.ceil((double) deviceIds.size() / (double) GCMService.PageSize);
		for (int i = 0; i < pages; i++)
		{
			int endIndex = (i * GCMService.PageSize) + GCMService.PageSize;
			endIndex = endIndex < deviceIds.size() ? endIndex : deviceIds.size();
			List<String> devices = deviceIds.subList(i * GCMService.PageSize, endIndex);
			if (!devices.isEmpty())
			{
				MulticastResult multicastResult = sender.sendNoRetry(message, devices);
				if (multicastResult != null)
				{
					List<Result> results = multicastResult.getResults();
					for (int resultId = 0; resultId < results.size(); resultId++)
					{
						Result result = results.get(resultId);
						String deviceId = deviceIds.get((i * GCMService.PageSize) + resultId);
						messageResultList.add(new MessageResult(result, deviceId));
					}
				}
			}
		}
		return new SendResult(messageResultList);
	}
}