package com.orange.orangeportfolio.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.orange.orangeportfolio.model.User;
import com.orange.orangeportfolio.repository.UserRepository;
import com.orange.orangeportfolio.service.exception.UserNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByEmail(userEmail);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException();		
		}
		
		return new UserDetailsImpl(user.get());
	}
}
