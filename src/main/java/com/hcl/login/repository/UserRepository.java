package com.hcl.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.login.entity.User;

/**
 * The Interface UserRepository.
 * 
 * @author sharath vemperala
 */
public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * This abstract method will give the user object specified for the userId .
	 *
	 * @param userId the user id
	 * @return Optional<User>
	 */
	Optional<User> findByUserId(Integer userId);

	/**
	 * This method will generate a database query find by email and password.
	 *
	 * @param email    the email
	 * @param password the password
	 * @return the optional
	 */
	Optional<User> findByEmailAndPassword(String email, String password);

	/**
	 * This method will generate a database query to find the user based on the
	 * email password and locker attributes.
	 *
	 * @param email    the email
	 * @param password the password
	 * @param locker   the locker
	 * @return the optional
	 */

	Optional<User> findByEmailAndPasswordAndLocker(String email, String password, boolean locker);

	/**
	 * This method will generate a database query to find the user based on the
	 * email.
	 *
	 * @param email the email
	 * @return the optional
	 */

	Optional<User> findByEmail(String email);

}
