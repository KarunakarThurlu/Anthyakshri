package com.megaroy.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.megaroy.dao.IUserDao;
import com.megaroy.mailingservice.MailingService;

@Repository
public class UserDaoImpl implements IUserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private MailingService mailSender;

	@Override
	public String profileVerivucation(JSONObject otp) {
		String query="select OTP,profile_is_verified from user_table where user_email=?";
		String userEmail=(String) otp.get("userEmail");
		@SuppressWarnings("unchecked")
		List<JSONObject> user= (List<JSONObject>) jdbcTemplate.query(query,new Object[] {userEmail},new RowMapper<JSONObject>() {

			@Override
			public JSONObject mapRow(ResultSet rs, int rowNum) throws SQLException {
				JSONObject obj=new JSONObject();
				obj.put("OTP",rs.getString("otp"));
				obj.put("verified",rs.getString("profile_is_verified"));
				return obj;
			}

		});
		JSONObject userobj=user.get(0);
		if(userobj.get("verified").equals("YES")) {
			return "Your Profile is already verified";
		}
		if(userobj.get("OTP").toString().equals(otp.get("OTP").toString())) {
			String update=" update user_table set profile_is_verified = ? where user_Email = ?";
			int c=jdbcTemplate.update(update,"YES",userEmail);

			return "Your Profile is Verified";
		}else {
			return "Invalid OTP ";
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public String forgotPassword(String emailOrPhno) {
		JSONObject object=new JSONObject();
		String 	forgotpassword_query="select count(*) from user_table where user_phono=? or user_email=?";
		int count=jdbcTemplate.queryForObject(forgotpassword_query,new Object[] {emailOrPhno,emailOrPhno}, Integer.class);
		if(count>0) {
			String otp=genarateOTP();
			String forgotPasswordOTP="update user_table set OTP=? where user_phono=? or user_email=?";
			String get_user_email="select user_email from user_table where user_email=? or user_Phono=?";
			String userEmail=jdbcTemplate.queryForObject(get_user_email, new Object[] {emailOrPhno,emailOrPhno}, String.class);
			jdbcTemplate.update(forgotPasswordOTP, otp,emailOrPhno,emailOrPhno);
			JSONObject obj=new JSONObject();
			obj.put("OTP", otp);
			obj.put("userEmailOruserPhno", emailOrPhno);
			mailSender.sendforgotPasswordMail(obj);
			String msg="OTP has been sended to "+emailOrPhno+ " please check it";
			object.put("message", msg);
			object.put("userEmail",userEmail);
			object.put("OTP",otp);
			return object.toJSONString();
		}else {
			String msg=emailOrPhno +" is not found in our database";
			object.put("message", msg);
			return object.toJSONString();
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

	@SuppressWarnings("unchecked")
	public String verifyForgotPassword(JSONObject verifyForgotOTPpwd) {
		JSONObject responseobj=new JSONObject();
		String userEmail=(String) verifyForgotOTPpwd.get("userEmail");
		String verifyForgotPasswordQuery="select otp from user_table where user_email=?";
		String otpfromdb=  jdbcTemplate.queryForObject(verifyForgotPasswordQuery,new Object[] {userEmail},String.class);
		String forgotPasswordOTPFromui=(String) verifyForgotOTPpwd.get(("OTP"));
		if(otpfromdb.equals(forgotPasswordOTPFromui)) {
			String otp_verified="update user_table set forgot_Password_Is_Verified=?  where user_email=?";	
			jdbcTemplate.update(otp_verified, "Verified",userEmail);
			responseobj.put("message","verified");
			return responseobj.toJSONString();
		}else {
			responseobj.put("message", "Invalid OTP Please enter currect OTP");
			return responseobj.toJSONString();
		}
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public JSONObject resetUserPassword(JSONObject obj) {
		String userPwd=(String) obj.get("userPwd");
		String emailOrPhono=(String) obj.get("userEmail");
		String password_Updata_Query="update user_table set user_pwd=? where user_email=? or user_phono=?";
		int result=jdbcTemplate.update(password_Updata_Query, userPwd,emailOrPhono,emailOrPhono);
		JSONObject object=new JSONObject();
		if(result>0) {
			object.put("message", "Password reset is Done successfully");
		}
		return object;
	}
}
