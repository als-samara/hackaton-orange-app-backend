package com.orange.orangeportfolio.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GoogleLoginUserRepository extends JpaRepository<GoogleLoginUser, Long> {
	
	public Optional<GoogleLoginUser> findByEmail(String email);
}
