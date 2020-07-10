package com.megaroy.iserviceimpl;

import java.time.LocalDateTime;
import java.util.Random;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.megaroy.dao.IUserDao;
import com.megaroy.iservice.IUserService;
import com.megaroy.mailingservice.MailingService;
import com.megaroy.model.User;
import com.megaroy.repo.UserRepository;



@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private MailingService mailsend;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@SuppressWarnings("unchecked")
	@Override
	public String saveUser(User user) {
		User isUserExixts=userRepository.findByUserEmail(user.getUserEmail());
		if(isUserExixts==null) {
			String enpwd=bcrypt.encode(user.getUserPwd());
			String otp=genarateOTP();
			user.setUserPwd(enpwd);
			user.setProfileIsVerified("NO");
			user.setOTP(otp);
			user.setSignUPDate(LocalDateTime.now());
			JSONObject obj=new JSONObject();
			obj.put("userEmail",user.getUserEmail());
			obj.put("OTP",otp);
			obj.put("userName",user.getUserName());
			//mailsend.sendProfileVerificationMail(obj);
			userRepository.save(user);
			return "Please check your mail ,An OTP has been sent for Profile Verification";
		}else {
			return "Email is Already Existed!";
		}
	}

	private String genarateOTP() {
		String otp=null;
		String str=null;
		while(true) {
			int r= new Random().nextInt(9999);
			str=""+Math.abs(r);
			if(str.length()==4){
				otp=str;
				break;
			}
		}
		return otp;
	}

	@Override
	public String profileVerivucation(JSONObject otp) {
		return userDao.profileVerivucation(otp);

	}

	@Override
	public String forgotPassword(String emailOrPhno) {
		return userDao.forgotPassword(emailOrPhno);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String verifyForgotPassword(JSONObject verifyForgotOTPpwd) {
		User user=userRepository.findByUserEmail((String) verifyForgotOTPpwd.get("userEmail"));
		if(user.getForgotPasswordIsVerified()!=null && user.getForgotPasswordIsVerified().equals("Verified")) {
			JSONObject obj=new JSONObject();
			obj.put("message","OTP is Already verified");
			return obj.toJSONString();
		}else {
			return userDao.verifyForgotPassword(verifyForgotOTPpwd);	
		}
	}

	@Override
	public User findByUserEmail(String userEmail) {

		return userRepository.findByUserEmail(userEmail);
	}

	@Override
	public User findByUserEmailOrUserPhono(String emailOrPhno, String emailorPhno) {
		User user=userRepository.findByUserEmailOrUserPhono(emailOrPhno, emailorPhno);
		String pwd=bcrypt.encode(user.getUserPwd());
		user.setUserPwd(pwd);

		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject resetUserPassword(JSONObject userDetails) {
		String email=(String) userDetails.get("userEmail");
		String forgotReqOtp=(String) userDetails.get("OTP");
		String userPwd=null;
		if(forgotReqOtp!=null) {
			userPwd=email+forgotReqOtp;
		}else {
			userPwd=(String) userDetails.get("userPwd");
		}
		String encodedPwd=bcrypt.encode(userPwd);
		JSONObject obj=new JSONObject();
		obj.put("userPwd", encodedPwd);
		obj.put("userEmail", email);
		return userDao.resetUserPassword(obj);

	}

}
