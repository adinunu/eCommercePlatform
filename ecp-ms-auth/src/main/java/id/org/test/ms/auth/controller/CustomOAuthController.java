package id.org.test.ms.auth.controller;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.org.test.common.constant.AppConstant;
import id.org.test.common.web.BaseController;
import id.org.test.common.web.ResponseStatus;

@RestController
@RequestMapping("/oauth")
public class CustomOAuthController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(CustomOAuthController.class);

	private final ConsumerTokenServices tokenService;
	private final JdbcTokenStore tokenStore;

	public CustomOAuthController(ConsumerTokenServices tokenService, JdbcTokenStore tokenStore) {
		this.tokenService = tokenService;
		this.tokenStore = tokenStore;
	}

	@PostMapping("/token/kick")
	public Object kickToken(@RequestHeader("Authorization") String basicAuthorization,
			@RequestParam("username") String username) {
		log.debug("kickToken({})", username);
		if (StringUtils.isNotEmpty(basicAuthorization)) {
			boolean validAuthorization = AppConstant.OAuthClientDetails.isValidBasicAuthorization(basicAuthorization);
			if (!validAuthorization)
				return unauthorized("Invalid Header Authorization");
		} else {
			return unauthorized("Header Authorization not present");
		}

		if (StringUtils.isEmpty(username))
			return buildResponseNotFound("username is empty");

		String clientId = AppConstant.OAuthClientDetails.getClientId(basicAuthorization);

		// expired access_token removal
		Collection<OAuth2AccessToken> acctokens = tokenStore.findTokensByUserName(username);
		int accTokenSize = acctokens.size();
		int accTokenRemoved = 0;
		for (OAuth2AccessToken accToken : acctokens) {
			tokenStore.removeAccessToken(accToken);
			tokenStore.removeRefreshToken(accToken.getRefreshToken());
			accTokenRemoved++;
		}
		log.debug("removing {} access_token(s) of {} total", accTokenRemoved, accTokenSize);

		return ok("All available tokens removed");
	}

	@PostMapping("/token/revoke")
	public Object revokeToken(@RequestParam("token") String token) {

		StringBuilder logMsg = new StringBuilder();
		if (StringUtils.isNotEmpty(token)) {
			int tlen = token.length();
			int stalen = 7;
			int endlen = tlen - 7;
			String logToken = token.substring(0, stalen);
			logToken = logToken.concat("...").concat(token.substring(endlen, tlen));
			logMsg.append("revokeToken(").append(logToken).append(")");
		}

		boolean loggedOut = tokenService.revokeToken(token);
		logMsg.append(" >> loggedOut(").append(loggedOut).append(")");
		log.debug(logMsg.toString());

		if (loggedOut) {
			return buildResponse(ResponseStatus.LOGOUT_SUCCESS, HttpStatus.OK);
		}
		return buildResponse(ResponseStatus.LOGOUT_ALREADY, HttpStatus.OK);
	}

	/**
	 * 
	 * kindly handle following methods with care. Its been used to normalize
	 * access_token for all of our applications
	 * 
	 * @param basicAuthorization
	 * @param username
	 * @return
	 */
	@PostMapping("/is-login")
	public Object isLoggedIn(@RequestHeader("Authorization") String basicAuthorization,
			@RequestParam("username") String username) {

		if (StringUtils.isNotEmpty(basicAuthorization)) {
			boolean validAuthorization = AppConstant.OAuthClientDetails.isValidBasicAuthorization(basicAuthorization);
			if (!validAuthorization)
				return unauthorized("Invalid Header Authorization");
		} else {
			return unauthorized("Header Authorization not present");
		}

		if (StringUtils.isEmpty(username))
			return buildResponseNotFound("username is empty");

		// expired access_token removal
		Collection<OAuth2AccessToken> acctokens = tokenStore.findTokensByUserName(username);
		int accTokenSize = acctokens.size();
		int accTokenRemoved = 0;
		for (OAuth2AccessToken accToken : acctokens) {
			if (accToken.isExpired()) {
				tokenStore.removeAccessToken(accToken);
				tokenStore.removeRefreshToken(accToken.getRefreshToken());
				accTokenRemoved++;
			}
		}
		log.debug("found {} access_token(s) for user={}, removing {} expired access_token(s) of {} total", accTokenSize,
				username, accTokenRemoved, accTokenSize);

		// all tokens after this line are not yet expired
		String clientId = AppConstant.OAuthClientDetails.getClientId(basicAuthorization);
		if (clientId.equalsIgnoreCase(AppConstant.OAuthClientDetails.MobileApi.ID)) { // user checking via mobileapi

			Collection<OAuth2AccessToken> mapitokens = tokenStore.findTokensByClientIdAndUserName(clientId, username);
			if (mapitokens.size() > 1) { // if token found more than 1, its abnormal behavior, we should removed all
											// those tokens
				for (OAuth2AccessToken mapitoken : mapitokens) {
					tokenStore.removeAccessToken(mapitoken);
					tokenStore.removeRefreshToken(mapitoken.getRefreshToken());
				}
//				return ok("User is not login in SmartDashboard");
			} else if (mapitokens.size() == 1) { // this expected behavior, 1 token per user & clientId at a time
				OAuth2AccessToken mapitoken = (OAuth2AccessToken) mapitokens.toArray()[0];
				return mapitoken;
			}

		}

		return buildResponse(ResponseStatus.LOGOUT_ALREADY, HttpStatus.OK);
	}

//	private boolean isLoginFromMobileAPI(String username) {
//		int validToken = 0;
//		Collection<OAuth2AccessToken> mapitokens = tokenStore.findTokensByClientIdAndUserName(AppConstant.OAuthClientDetails.MobileApi.ID, username);
//		for (OAuth2AccessToken mapitoken : mapitokens) {
//			if(mapitoken.isExpired()) {
//				tokenStore.removeAccessToken(mapitoken);
//				tokenStore.removeRefreshToken(mapitoken.getRefreshToken());
//			} else {
//				validToken++;
//			}
//		}
//		return validToken > 0;
//	}

	@GetMapping("/ping")
	public Object ping() {
		log.debug("Someone ping, lets pong back!");
		return buildResponseGeneralSuccess("pong");
	}

}
