package com.megaroy.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="User_Table")
public class User {
	@Id
	@GeneratedValue( generator = "userIDGenerator")
	@GenericGenerator(name="userIDGenerator",strategy = "com.megaroy.customgenerators.UserIdGenerator")
	private String userId;
	private String userName;
	private String userEmail;
	private String userPwd;
	private String userPhono;
	private String userGender;
	private String userLanguage;
	private LocalDate userDob;
	private String profileIsVerified;
	private String forgotPasswordIsVerified;
	private String OTP;
	private LocalDateTime signUPDate;
	private LocalDateTime lastModified;
	private String forgotPasswordOTP;


	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name="User_Roles_Table",joinColumns = @JoinColumn(name="userId"),inverseJoinColumns = @JoinColumn(name="roleId"))
	private Set<Role> roles;


	public User() {
		super();
	}


	public User(String userId, String userName, String userEmail, String userPwd, String userPhono, String userGender,
			String userLanguage, LocalDate userDob, String profileIsVerified, String forgotPasswordIsVerified,
			String oTP, LocalDateTime signUPDate, LocalDateTime lastModified, String forgotPasswordOTP,
			Set<Role> roles) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPwd = userPwd;
		this.userPhono = userPhono;
		this.userGender = userGender;
		this.userLanguage = userLanguage;
		this.userDob = userDob;
		this.profileIsVerified = profileIsVerified;
		this.forgotPasswordIsVerified = forgotPasswordIsVerified;
		OTP = oTP;
		this.signUPDate = signUPDate;
		this.lastModified = lastModified;
		this.forgotPasswordOTP = forgotPasswordOTP;
		this.roles = roles;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserPhono() {
		return userPhono;
	}

	public void setUserPhono(String userPhono) {
		this.userPhono = userPhono;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserLanguage() {
		return userLanguage;
	}

	public void setUserLanguage(String userLanguage) {
		this.userLanguage = userLanguage;
	}

	public LocalDate getUserDob() {
		return userDob;
	}

	public void setUserDob(LocalDate userDob) {
		this.userDob = userDob;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getProfileIsVerified() {
		return profileIsVerified;
	}

	public void setProfileIsVerified(String profileIsVerified) {
		this.profileIsVerified = profileIsVerified;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}

	public LocalDateTime getSignUPDate() {
		return signUPDate;
	}

	public void setSignUPDate(LocalDateTime signUPDate) {
		this.signUPDate = signUPDate;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}


	public String getForgotPasswordOTP() {
		return forgotPasswordOTP;
	}


	public void setForgotPasswordOTP(String forgotPasswordOTP) {
		this.forgotPasswordOTP = forgotPasswordOTP;
	}


	public String getForgotPasswordIsVerified() {
		return forgotPasswordIsVerified;
	}


	public void setForgotPasswordIsVerified(String forgotPasswordIsVerified) {
		this.forgotPasswordIsVerified = forgotPasswordIsVerified;
	}

}
