package id.org.test.restful.controller.auth;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.org.test.common.constant.AppConstant;
import id.org.test.common.web.BaseController;
import id.org.test.common.web.ResponseStatus;
import id.org.test.data.model.Member;
import id.org.test.data.repository.MemberRepository;
import id.org.test.ms.shared.auth.LoginDTO;
import id.org.test.restful.feign.OAuthFeignClient;

@RestController
public class LoginController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	private final OAuthFeignClient oauthFc;
	private final MemberRepository accountRepository;
	private ObjectMapper mapper;

	public LoginController(OAuthFeignClient oauthFc, MemberRepository accountRepository) {
		this.oauthFc = oauthFc;
		this.accountRepository = accountRepository;
		this.mapper = new ObjectMapper();
	}

	@PostMapping("/auth/login")
	public Object login(@RequestBody LoginDTO login, HttpServletResponse response) {
		try {

			Object kickTokenObj = oauthFc.kickToken(AppConstant.OAuthClientDetails.MobileApi.buildBasicAuthorization(),
					login.getUsername());
			Map<String, Object> kickTokenMap = mapper.convertValue(kickTokenObj, Map.class);
			String code = (String) kickTokenMap.get("code");
			log.debug("MobileAPI-login({})-kickTokenResCode({})", login.getUsername(), code);

			if (code.contentEquals(ResponseStatus.GENERAL_SUCCESS.getCodeString())) {

				Object oauthObj = oauthFc.login(AppConstant.OAuthClientDetails.MobileApi.buildBasicAuthorization(),
						login.getUsername(), login.getPassword(), "password");
				Map<String, Object> oauthMap = mapper.convertValue(oauthObj, Map.class);
				Object accToken = oauthMap.get("access_token");
				Object refToken = oauthMap.get("refresh_token");
				Object accountId = oauthMap.get(AppConstant.TokenInfo.MEMBER_ID);
				boolean isAccount = Boolean.valueOf(String.valueOf(oauthMap.get(AppConstant.TokenInfo.MEMBER)));

				Member oldData = accountRepository.findOne(Long.valueOf(String.valueOf(accountId)));
				if (oldData != null) {

				}

				oauthMap.put("token", accToken);
				oauthMap.put("refreshToken", refToken);
				return oauthMap;

			}

		} catch (Exception e) {
			log.error("Fail /login ", e.getMessage());
		}

		return buildResponse(ResponseStatus.AUTHORIZATION_FAIL, HttpStatus.BAD_REQUEST);
	}
}
