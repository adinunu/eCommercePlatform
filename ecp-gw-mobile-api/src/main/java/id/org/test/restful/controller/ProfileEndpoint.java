package id.org.test.restful.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import id.org.test.common.web.BaseController;
import id.org.test.restful.config.UserContext;

@RestController
public class ProfileEndpoint extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(ProfileEndpoint.class);

	private TokenStore tokenStore;

	@Autowired
	public ProfileEndpoint(TokenStore tokenStore) {
		this.tokenStore = tokenStore;
	}

	@GetMapping(value = "/api/me")
	public @ResponseBody UserContext get(OAuth2Authentication auth) {
		UserContext user = UserContext.build(auth, tokenStore);
		return user;
	}
}
