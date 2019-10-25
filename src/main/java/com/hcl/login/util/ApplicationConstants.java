package com.hcl.login.util;

public class ApplicationConstants {

	private ApplicationConstants() {

	}

	public static final String SUCCESS = "sucess";
	public static final String INVALID_CREDENTIALS = "Invalid username/password";
	public static final String FAILED_ATTEMPTS_EXCEEDED = "failed attempts exceeded. please contact admin.";
	public static final String PASSWOR_MISSMATCH = "password missmatch";
	public static final String USER_DOESNT_EXIST = "username doesnt exist";
	public static final String OTP_VERIFICATION_FAILED = "otp is not valid or expired";
	public static final String RESERVED = "Reserved";
	public static final String AVAILABLE = "Available";
	public static final String ISSUEDED = "Issued";
	public static final int TOLERENCE_OF_OTP = 5 * 60 * 1000;
	public static final String EMAIL_MESSAGE = "OTP GENERATED FOR YOUR YOUR ACCOUNT IS";
	public static final String EMAIL_SUBJECT = "REGARDING OTP";
	

}