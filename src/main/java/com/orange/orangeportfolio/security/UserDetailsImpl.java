package com.orange.orangeportfolio.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.orange.orangeportfolio.model.User;

import lombok.Data;

@Data
public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private String userEmail;
	private String passWord;
	private List<GrantedAuthority> authorities;
	
	public UserDetailsImpl(User user) {
		this.userEmail = user.getEmail();
		this.passWord = user.getPassword();
	}
	
	public UserDetailsImpl() { }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	@Override
	public String getPassword() {

		return passWord;
	}

	@Override
	public String getUsername() {

		return userEmail;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}