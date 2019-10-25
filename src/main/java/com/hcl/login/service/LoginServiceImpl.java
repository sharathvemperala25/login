package com.hcl.login.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hcl.login.dto.LoginRequest;
import com.hcl.login.dto.ResetPasswordRequest;
import com.hcl.login.entity.User;
import com.hcl.login.exception.InvalidCredentialsException;
import com.hcl.login.exception.InvalidUserException;
import com.hcl.login.exception.OtpVerificationFailed;
import com.hcl.login.repository.UserRepository;
import com.hcl.login.util.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class LoginServiceImpl.
 * 
 * @author sharath vemperala
 */
@Service

/** The Constant log. */
@Slf4j
public class LoginServiceImpl implements LoginService {

	/** The users Repository. */
	@Autowired
	private UserRepository usersDao;

	@Autowired
	private UserOtpService otpService;

	/**
	 * Authenticates the user credentials.This method is the service for logging
	 * into the application After 3 unsuccessful attempts of logging into account,
	 * password will be reset and an email will be sent to the user to reset his
	 * password.
	 * 
	 * @param loginRequest the login request which contains the email and password
	 * @return Optional<Integer> the user id of user for an authorized request
	 * @throws InvalidCredentialsException the invalid credentials exception for a
	 *                                     authorized request
	 */

	@Override
	public Optional<Integer> authenticate(LoginRequest loginRequest) throws InvalidCredentialsException {

		log.info(":: Enter into LoginService--------::authenticate()");

		String message = "";
		Optional<User> user = usersDao.findByEmailAndPasswordAndLocker(loginRequest.getEmail(),
				loginRequest.getPassword(), false);

		if (!user.isPresent()) {
			Optional<User> failedUser = usersDao.findByEmail(loginRequest.getEmail());

			message = ApplicationConstants.INVALID_CREDENTIALS;

			if (!(failedUser.isPresent())) {
				message = ApplicationConstants.USER_DOESNT_EXIST;
				throw new InvalidCredentialsException(message);
			}
			log.info("Failure Attempts of user : " + failedUser.get().getFailure());

			if ((failedUser.get().getFailure() < 3)) {
				User failedUserO = failedUser.get();
				failedUserO.setFailure(failedUserO.getFailure() + 1);
				usersDao.save(failedUserO);
				message = ApplicationConstants.PASSWOR_MISSMATCH;
				throw new InvalidCredentialsException(message);
			}

			/*
			 * After 3 unsuccessful attempts of logging into account, account will be locked
			 * by setting the locker to true
			 */

			if (failedUser.get().getFailure() == 3) {

				Optional<User> updateuserO = usersDao.findByEmail(failedUser.get().getEmail());
				if (!(updateuserO.isPresent())) {
					throw new InvalidCredentialsException(message);
				}

				User updateuser = updateuserO.get();

				updateuser.setFailure(0);
				updateuser.setLocker(true);
				usersDao.save(updateuser);
				message = ApplicationConstants.FAILED_ATTEMPTS_EXCEEDED;
				throw new InvalidCredentialsException(message);
			}
		}
		if (!user.isPresent()) {
			throw new InvalidCredentialsException(message);
		}

		return Optional.of(user.get().getUserId());
	}

	/**
	 * Will reset the user password only when the user gives the OTP code correctly
	 * within the tolerance time which is sent to the users registered mail.
	 *
	 * @param resetPasswordRequest the reset password request
	 * @return the optional
	 * @throws OtpVerificationFailed the otp verification failed
	 */

	@Override
	public Optional<Integer> forgetPassword(ResetPasswordRequest resetPasswordRequest) throws OtpVerificationFailed,InvalidUserException {
		Optional<User> userO = usersDao.findByEmail(resetPasswordRequest.getEmail());
		boolean isCodeValid = otpService.validateOtp(resetPasswordRequest.getEmail(), resetPasswordRequest.getOtp());
		User user = null;
		if (!userO.isPresent()) {
			throw new InvalidUserException(ApplicationConstants.INVALID_CREDENTIALS);
		}
		if (userO.isPresent() && !isCodeValid) {
			throw new OtpVerificationFailed(ApplicationConstants.OTP_VERIFICATION_FAILED);
		}

		user = userO.get();
		user.setPassword(resetPasswordRequest.getNewPassword());
		user.setLocker(false);
		usersDao.save(user);
		return Optional.of(user.getUserId());
	}

}
