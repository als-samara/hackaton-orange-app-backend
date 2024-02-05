package com.orange.orangeportfolio.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class OAuth2LoginSecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.oauth2Login(oauth2 -> oauth2
			    .clientRegistrationRepository(this.clientRegistrationRepository())
			    .authorizedClientRepository(this.authorizedClientRepository())
			    .authorizedClientService(this.authorizedClientService())
			    .loginPage("/login/oauth2")
			    .authorizationEndpoint(authorization -> authorization
			        .baseUri(this.authorizationRequestBaseUri())
			        .authorizationRequestRepository(this.authorizationRequestRepository())
			        .authorizationRequestResolver(this.authorizationRequestResolver())
			    )
			    .redirectionEndpoint(redirection -> redirection
			        .baseUri(this.authorizationResponseBaseUri())
			    )
			    .tokenEndpoint(token -> token
			        .accessTokenResponseClient(this.accessTokenResponseClient())
			    )
			    .userInfoEndpoint(userInfo -> userInfo
			        .userAuthoritiesMapper(this.userAuthoritiesMapper())
			        .userService(this.oauth2UserService())
			        .oidcUserService(this.oidcUserService())
			    )
			);
		return http.build();
	}

	private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
		// TODO Auto-generated method stub
		return null;
	}

	private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
		// TODO Auto-generated method stub
		return null;
	}

	private GrantedAuthoritiesMapper userAuthoritiesMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
		// TODO Auto-generated method stub
		return null;
	}

	private String authorizationResponseBaseUri() {
		// TODO Auto-generated method stub
		return null;
	}

	private OAuth2AuthorizationRequestResolver authorizationRequestResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	private AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
		// TODO Auto-generated method stub
		return null;
	}

	private String authorizationRequestBaseUri() {
		// TODO Auto-generated method stub
		return null;
	}

	private OAuth2AuthorizedClientService authorizedClientService() {
		// TODO Auto-generated method stub
		return null;
	}

	private OAuth2AuthorizedClientRepository authorizedClientRepository() {
		// TODO Auto-generated method stub
		return null;
	}

	private ClientRegistrationRepository clientRegistrationRepository() {
		// TODO Auto-generated method stub
		return null;
	}
}