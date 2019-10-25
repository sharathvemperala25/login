package com.hcl.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.login.entity.UserOtp;

public interface UserOtpRepository extends JpaRepository<UserOtp, String> {

}
