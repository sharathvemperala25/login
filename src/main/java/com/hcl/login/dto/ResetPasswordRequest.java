package com.hcl.login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ResetPasswordRequest {

	private String email;
	private int otp;
	private String newPassword;

}
