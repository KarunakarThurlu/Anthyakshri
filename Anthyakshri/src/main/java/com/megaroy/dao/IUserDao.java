package com.megaroy.dao;

import org.json.simple.JSONObject;

public interface IUserDao {
	public String profileVerivucation(JSONObject otp);
	public String forgotPassword(String emailOrPhno);
	public String verifyForgotPassword(JSONObject verifyForgotOTPpwd);
	public JSONObject resetUserPassword(JSONObject obj);
}
