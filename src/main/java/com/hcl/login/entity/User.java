package com.hcl.login.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is an database entity which describes the user
 * 
 * @author sharath vemperala
 *
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	private String userName;
	@Column(unique = true)
	private String email;
	private String address;
	private String password;
	@Column(name = "failure")
	/**
	 * This describes the number of failure attempts to login
	 */
	private Integer failure;
	/**
	 * This is set to true when the user attempts to login with wrong password
	 */
	private boolean locker;

}
