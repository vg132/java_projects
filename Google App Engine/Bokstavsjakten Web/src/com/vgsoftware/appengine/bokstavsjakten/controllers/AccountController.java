package com.vgsoftware.appengine.bokstavsjakten.controllers;

import com.google.gson.Gson;
import com.vgsoftware.appengine.bokstavsjakten.models.UserModel;
import com.vgsoftware.vaction.annotation.HttpPost;
import com.vgsoftware.vaction.controller.Controller;
import com.vgsoftware.vaction.result.JsonResult;

public class AccountController extends Controller
{
	@HttpPost
	public JsonResult createUser()
	{
		Gson gson = new Gson();
		UserModel user = gson.fromJson(getRequest().getParameter("usermodel"), UserModel.class);
		user.save();
		return json("ok");
	}
}
