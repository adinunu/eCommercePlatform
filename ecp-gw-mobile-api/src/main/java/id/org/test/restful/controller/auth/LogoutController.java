package id.org.test.restful.controller.auth;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.org.test.common.web.BaseController;
import id.org.test.restful.feign.OAuthFeignClient;

@RestController
public class LogoutController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(LogoutController.class);

	private final OAuthFeignClient oauthFc;

	public LogoutController(OAuthFeignClient oauthFc) {
		this.oauthFc = oauthFc;
	}

	@PostMapping("/api/auth/logout")
	public Object logout(@RequestParam("token") String token) {
		
		if(StringUtils.isNotEmpty(token)) {
			int tlen = token.length();
			int stalen = 7;
			int endlen = tlen - 7;
			String logToken = token.substring(0, stalen);
			logToken = logToken.concat("...").concat(token.substring(endlen));
			log.debug("MobileAPI-logout({})", logToken);
		}
		
		return oauthFc.logout(token);
	}

}
