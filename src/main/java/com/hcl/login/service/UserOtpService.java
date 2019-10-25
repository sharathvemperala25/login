package com.hcl.login.service;

/**
 * The Interface UserOtpService.
 */
public interface UserOtpService {

	/**
	 * Generate OTP which is valid only for a specific tolerance time.
	 *
	 * @param email the email
	 * @return the int
	 */
	public int generateOTP(String email);

	/**
	 * Validate otp code and its time of validity.
	 *
	 * @param email            the email of user
	 * @param verificationCode the verification code
	 * @return true, if successful
	 */
	public boolean validateOtp(String email, int verificationCode);

	/**
	 * Send email to the specified email address.
	 *
	 * @param email   the email
	 * @param message the message
	 * @param subject the subject
	 */
	public void sendEmail(String email, String message, String subject);

}
