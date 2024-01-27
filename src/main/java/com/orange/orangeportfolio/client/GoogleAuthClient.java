package com.orange.orangeportfolio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="googleAuthClient", url="https://www.googleapis.com/oauth2/v1/")
public interface GoogleAuthClient {
	
	@GetMapping("userinfo")
    String getUserInfo();
	
}
