package com.hcl.login.service;

import java.util.Optional;


import com.hcl.login.dto.LoginRequest;
import com.hcl.login.dto.ResetPasswordRequest;
import com.hcl.login.exception.InvalidCredentialsException;
import com.hcl.login.exception.InvalidUserException;
import com.hcl.login.exception.OtpVerificationFailed;

/**
 * The Interface LoginService.
 * 
 * @author sharath vemperala
 */
public interface LoginService {

	/**
	 * Authenticates the user credentials. This method is the service for logging
	 * into the application After 3 unsuccessful attempts of logging into account,
	 * password will be reset and an email will be sent to the user to reset his
	 * password.
	 *
	 * @param loginRequest the login request which contains the email and password
	 * @return Optional<Integer> the user id of user for an authorized request
	 * @throws InvalidCredentialsException the invalid credentials exception for an
	 *                                     authorized request
	 */
	public Optional<Integer> authenticate(LoginRequest loginRequest) throws InvalidCredentialsException;

	/**
	 * Will reset the user password only when the user gives the OTP code correctly
	 * within the tolerance time which is sent to the users registered mail.
	 *
	 * @param resetPasswordRequest the reset password request
	 * @return the optional
	 * @throws OtpVerificationFailed the otp verification failed
	 */
	public Optional<Integer> forgetPassword(ResetPasswordRequest resetPasswordRequest) throws OtpVerificationFailed,InvalidUserException;

}