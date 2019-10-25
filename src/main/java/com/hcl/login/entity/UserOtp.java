package com.hcl.login.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is a database entity which stores the validation code for the user which
 * has a tolerance time
 * 
 * @author sharath vemperala
 *
 */
@Entity
@Table(name = "user_otp")
@Getter
@Setter
@NoArgsConstructor
public class UserOtp {

	@Id
	@Column(name = "email")
	private String email;

	/**
	 * This is the actual code validated at the time of verification
	 */
	private int validationcode;

	/**
	 * This is the validity of the validation code which has a tolerance from the
	 * time code is generated
	 */
	private long validity;

}
