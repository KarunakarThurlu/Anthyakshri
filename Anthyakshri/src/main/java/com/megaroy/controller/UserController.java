package com.megaroy.controller;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.megaroy.iservice.IUserService;
import com.megaroy.jwtutil.JwtUtil;
import com.megaroy.model.User;

@RestController
@RequestMapping("/anthyakshri")
@CrossOrigin(origins="http://localhost:4200")
public class UserController {

	private static final Logger log=LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	JSONParser parser=new JSONParser();

	@SuppressWarnings("unchecked")
	@RequestMapping("/signup")
	public String saveUser(@RequestBody User userdata,HttpSession session) {
		log.info("enter into save method of "+UserController.class.getName());
		String userEmail=userdata.getUserEmail();
		String userPwd=userdata.getUserPwd();
		String response=service.saveUser(userdata);
		JSONObject responseObject=new JSONObject();
		if(response.equals("Email is Already Existed!")) {
			responseObject.put("message",response);
		}else {
			final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( userEmail, userPwd));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final String token = jwtTokenUtil.generateToken(authentication);
			session.setAttribute("userEmail",userEmail);
			responseObject.put("message",response);
			responseObject.put("token",token);
		}
		return responseObject.toJSONString(); 
	}
	@RequestMapping("/secure")
	public String name() {
		log.info("enter into secure method of "+UserController.class.getName());
		return "secure source";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/profileverification")
	public String profileVerification(@RequestBody String otp,HttpSession session) {
		String userEmail=(String) session.getAttribute("userEmail");
		String response=null;
		try {
			JSONObject otpp=(JSONObject) parser.parse(otp);
			JSONObject obj=new JSONObject();
			obj.put("userEmail", userEmail);
			obj.put("OTP",otpp.get("OTP"));
			response=service.profileVerivucation(obj);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject obj=new JSONObject();
		obj.put("message", response);
		return obj.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/forgotpassword")
	public String forgotPassword(@RequestBody String userEmailorPhno,HttpSession session) {
		String response=null;
		JSONObject finalResponseObject=new JSONObject();
		try {
			JSONObject obj=(JSONObject) parser.parse(userEmailorPhno);
			String emailOrPhno=(String) obj.get("userEmailorPhno");
			response=service.forgotPassword(emailOrPhno);
			JSONObject resObj=(JSONObject) parser.parse(response);
			String userEmail=(String) resObj.get("userEmail");
			String message=(String) resObj.get("message");
			String otp=(String) resObj.get("OTP");
			if(!message.contains(" is not found in our database")) {
				service.resetUserPassword(resObj);
				final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( userEmail, userEmail+otp));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				final String token = jwtTokenUtil.generateToken(authentication);
				session.setAttribute("userEmail",userEmail);
				finalResponseObject.put("token", token);
				finalResponseObject.put("message", message);
			}else {
				finalResponseObject.put("message", message);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return finalResponseObject.toJSONString();
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/verifyforgotpassword")
	public String verifyForgotPassword(@RequestBody String forgotpwdotp,HttpSession session) {
		String response=null;
		try {
			JSONObject obj=new JSONObject();
			JSONObject otp=(JSONObject) parser.parse(forgotpwdotp);
			String email=(String) session.getAttribute("userEmail");
			obj.put("userEmail",email);
			obj.put("OTP",otp.get("forgotpwdotp"));
			response=service.verifyForgotPassword(obj);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/resetpassword")
	public String resetPassword(@RequestBody String userPwd,HttpSession session) {
		String userEmail=(String) session.getAttribute("userEmail");
		JSONObject responseObject=new JSONObject();
		try {
			JSONObject object=(JSONObject) parser.parse(userPwd);
			object.put("userEmail",userEmail);
			responseObject= service.resetUserPassword(object);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return responseObject.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/profile")
	public String getProfileByName(HttpSession session) {
		String userEmail=(String) session.getAttribute("userEmail");
		User user=service.findByUserEmail(userEmail);
		JSONObject jsonobj=new JSONObject();
		jsonobj.put("userName", user.getUserName());
		jsonobj.put("userPhono", user.getUserPhono());
		jsonobj.put("userDob", user.getUserDob());
		jsonobj.put("userEmail", user.getUserEmail());
		jsonobj.put("userLanguage", user.getUserLanguage());
		jsonobj.put("userGender", user.getUserGender());
		return jsonobj.toJSONString();
	}
}
