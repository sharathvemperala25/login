package com.hcl.login.controller;

import java.util.Optional;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.login.dto.LoginRequest;
import com.hcl.login.dto.LoginResponse;
import com.hcl.login.dto.ResetPasswordRequest;
import com.hcl.login.dto.ResetPasswordResponse;
import com.hcl.login.exception.InvalidCredentialsException;
import com.hcl.login.exception.OtpVerificationFailed;
import com.hcl.login.service.LoginService;
import com.hcl.login.util.ApplicationConstants;

/**
 * 
 * @author sharath vemperala
 *
 */

@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	LoginService loginService;

	/**
	 * Authenticates the user credentials.This method is the service for logging
	 * into the application After 3 unsuccessful attempts of logging into account,
	 * password will be reset and an email will be sent to the user to reset his
	 * password.
	 * 
	 * @param loginRequest the login request which contains the email and password
	 * @return LoginResponse
	 * @throws InvalidCredentialsException
	 */

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest)
			throws InvalidCredentialsException {

		/*
		 * This method is the service for logging into the application After 3
		 * unsuccessful attempts of logging into account, password will be reset and an
		 * email will be sent to the user to reset his password.
		 */
		logger.info(":: Enter into LoginController--------::login()");
		if (loginRequest.getEmail().isEmpty() || loginRequest.getPassword().isEmpty()) {
			String message = ApplicationConstants.INVALID_CREDENTIALS;
			throw new InvalidCredentialsException(message);
		}
		LoginResponse loginResponse = new LoginResponse();
		Optional<Integer> userIdo = loginService.authenticate(loginRequest);
		if (userIdo.isPresent()) {
			loginResponse.setMessage(ApplicationConstants.SUCCESS);
			loginResponse.setStatusCode(HttpStatus.OK.value());
			loginResponse.setUserId(userIdo.get());
		}

		return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);

	}

	@PutMapping("/forgottenpassword")
	public ResponseEntity<ResetPasswordResponse> forgottenPassword(
			@RequestBody ResetPasswordRequest resetPasswordRequest)
			throws InvalidCredentialsException, OtpVerificationFailed {
		logger.info(":: Enter into LoginController--------::forgottenPassword()");
		Optional<Integer> userIdo = loginService.forgetPassword(resetPasswordRequest);
		ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse();
		if (userIdo.isPresent()) {
			resetPasswordResponse.setMessage(ApplicationConstants.SUCCESS);
			resetPasswordResponse.setStatusCode(HttpStatus.OK.value());
			resetPasswordResponse.setUserId(userIdo.get());
		}
		return new ResponseEntity<>(resetPasswordResponse, HttpStatus.OK);
	}
}
