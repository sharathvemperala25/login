package com.hcl.login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ResetPasswordResponse {

	private String message;
	private Integer statusCode;
	private Integer userId;
}
