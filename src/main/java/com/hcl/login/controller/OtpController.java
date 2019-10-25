package com.hcl.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.login.dto.OptRequestDto;
import com.hcl.login.dto.OtpResponseDto;
import com.hcl.login.exception.InvalidCredentialsException;
import com.hcl.login.service.UserOtpService;
import com.hcl.login.util.ApplicationConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * This controller generates and sends otp to the registered mail of user
 * 
 * @author sharath vemperala
 *
 */

@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@Slf4j
public class OtpController {

	@Autowired
	UserOtpService otpService;

	/**
	 * 
	 * @param login credentials
	 * @return LoginResponse
	 * @throws InvalidCredentialsException
	 */

	@PostMapping("/otpnotification")
	public ResponseEntity<OtpResponseDto> generateAndsendOtp(@RequestBody OptRequestDto optRequestDto)
			throws InvalidCredentialsException {
		log.info(":: Enter into otpController--------::sendOtp()");
		OtpResponseDto otpResponseDto = new OtpResponseDto();
		int otp = otpService.generateOTP(optRequestDto.getEmail());
		if (otp != 0) {
			otpResponseDto.setMessage(ApplicationConstants.SUCCESS);
			otpResponseDto.setStatusCode(HttpStatus.OK.value());
		}
		return new ResponseEntity<>(otpResponseDto, HttpStatus.OK);

	}

}