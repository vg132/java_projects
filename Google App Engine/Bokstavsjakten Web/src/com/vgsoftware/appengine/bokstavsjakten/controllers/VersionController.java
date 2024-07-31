package com.vgsoftware.appengine.bokstavsjakten.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.appengine.api.utils.SystemProperty;
import com.vgsoftware.vaction.annotation.HttpGet;
import com.vgsoftware.vaction.controller.Controller;
import com.vgsoftware.vaction.result.ActionResult;

public class VersionController extends Controller
{
	@HttpGet
	public ActionResult index()
	{
		String applicationVersion = SystemProperty.applicationVersion.get();
		Date versionDate = new Date(Long.parseLong(applicationVersion.substring(applicationVersion.indexOf(".") + 1)) / (2 << 27) * 1000);
		getViewData().put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm zzz").format(versionDate));
		getViewData().put("version", applicationVersion);

		return view();
	}
}
