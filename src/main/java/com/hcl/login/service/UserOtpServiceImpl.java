package com.hcl.login.service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hcl.login.entity.UserOtp;
import com.hcl.login.repository.UserOtpRepository;
import com.hcl.login.util.ApplicationConstants;
import com.hcl.login.util.BOASendMail;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class UserOtpServiceImpl which will be used for OTP operations.
 * 
 * @author sharath vemperala
 */
@Service

/** The Constant log. */
@Slf4j
public class UserOtpServiceImpl implements UserOtpService {

	/** The random number. */
	Random random = new Random();

	/** The user otp repository. */
	@Autowired
	UserOtpRepository userOtpRepository;

	/** The boa send email. */
	@Autowired
	private BOASendMail boaSendEmail;

	/**
	 * Generates OTP.
	 *
	 * @param email the email
	 * @return the int
	 */
	@Override
	public int generateOTP(String email) {
		log.info(":: Enter into UserOtpServiceImpl--------::generateOTP()");
		int otp = random.nextInt((9999 - 100) + 1) + 10;
		UserOtp userOtp = new UserOtp();
		userOtp.setEmail(email);
		userOtp.setValidationcode(otp);
		userOtp.setValidity(new Date().getTime() + ApplicationConstants.TOLERENCE_OF_OTP);
		userOtpRepository.save(userOtp);
		sendEmail(email, ApplicationConstants.EMAIL_MESSAGE + otp, ApplicationConstants.EMAIL_SUBJECT);
		return otp;
	}

	/**
	 * Validates otp.
	 *
	 * @param email            the email
	 * @param verificationCode the verification code
	 * @return true, if OTP given is correct and it have not expired
	 */
	@Override
	public boolean validateOtp(String email, int verificationCode) {
		log.info(":: Enter into UserOtpServiceImpl--------::validateOtp()");
		Optional<UserOtp> userOtpO = userOtpRepository.findById(email);
		boolean isCodeValid = false;

		if (userOtpO.isPresent()) {
			UserOtp userOtp = userOtpO.get();
			isCodeValid = (userOtp.getValidationcode() == verificationCode)
					&& (new Date().getTime() <= userOtp.getValidity()) ? true : false;
		}
		return isCodeValid;
	}

	/**
	 * Sends email to specified email.
	 *
	 * @param email   the email
	 * @param message the message
	 * @param subject the subject
	 */
	@Override
	public void sendEmail(String email, String message, String subject) {
		log.info(":: Enter into UserOtpServiceImpl--------::sendEmail()");
		boaSendEmail.SendMailToCustomer(email, subject, message);
	}

}
