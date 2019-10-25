package com.hcl.login.exception;



/**
 * The Class OtpVerificationFailed throw when otp is failed.
 */
public class OtpVerificationFailed extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new otp verification failed.
	 *
	 * @param message the message
	 */
	public OtpVerificationFailed(String message) {
		super(message);
	}

}