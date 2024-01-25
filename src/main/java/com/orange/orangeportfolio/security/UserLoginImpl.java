//package com.orange.orangeportfolio.security;
//
//import java.util.Collection;
//import java.util.List;
//
//import com.orange.orangeportfolio.model.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public class UserLoginImpl implements UserDetails {
//	
//	private String userName;
//	private String password;
//	
//	private List<GrantedAuthority> authorities;
//
//	public UserLoginImpl (User user) {
//		this.userName = user.getEmail();
//		this.password = user.getPassword();
//	}
//	
//	public UserLoginImpl() {
//		
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return authorities;
//	}
//
//	@Override
//	public String getPassword() {
//		return password;
//	}
//
//	@Override
//	public String getUsername() {
//		return userName;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
//}
