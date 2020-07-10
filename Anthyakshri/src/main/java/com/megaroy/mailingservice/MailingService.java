package com.megaroy.mailingservice;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailingService {	
	
	@Autowired
    private JavaMailSender mailSender;
	
	public String sendProfileVerificationMail(JSONObject obj) {
		SimpleMailMessage msg=new SimpleMailMessage();
        msg.setTo((String) obj.get("userEmail"));
        msg.setSubject("Mail from Anthyakshri");
        msg.setText(" Hai! "+obj.get("userName")+" \n            Your four digit verification code for anthyakshri is  "+obj.get("OTP"));
        mailSender.send(msg);
		return "Mail sended successfully";
	}
	public String sendforgotPasswordMail(JSONObject obj) {
		SimpleMailMessage msg=new SimpleMailMessage();
		msg.setTo((String) obj.get("userEmailOruserPhno"));
		msg.setSubject("ForgotPassword Mail From Anthyakshri");
		msg.setText("Hai! "+obj.get("userName")+ "\n           Your four digit code for forgot password is "+obj.get("OTP"));
		mailSender.send(msg);
		return "mail sended Successfully";
	}
}
