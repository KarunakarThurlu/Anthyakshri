package com.megaroy.iservice;

import org.json.simple.JSONObject;

import com.megaroy.model.User;

public interface IUserService {
	public String saveUser(User user);
	public String profileVerivucation(JSONObject otp);
	public String forgotPassword(String emailOrPhno);
	public String verifyForgotPassword(JSONObject verifyForgotOTPpwd);
	public User findByUserEmail(String userEmail);
	public User findByUserEmailOrUserPhono(String emailOrPhno, String emailorPhno);
	public JSONObject resetUserPassword(JSONObject userDetails);

}
