package com.vgsoftware.appengine.bokstavsjakten.controllers;

import com.vgsoftware.vaction.annotation.HttpGet;
import com.vgsoftware.vaction.controller.Controller;
import com.vgsoftware.vaction.result.ActionResult;

public class HomeController extends Controller
{
	@HttpGet
	public ActionResult index()
	{
		return view();
	}
}
