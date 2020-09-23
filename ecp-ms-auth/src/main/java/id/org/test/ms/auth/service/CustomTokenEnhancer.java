package id.org.test.ms.auth.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import id.org.test.common.constant.AppConstant;
import id.org.test.common.web.ResponseStatus;
import id.org.test.ms.auth.repository.UserRepository;
import id.org.test.ms.shared.auth.UserRole;

@Component("CustomTokenEnhancer")
public class CustomTokenEnhancer implements TokenEnhancer {

	private static final Logger log = LoggerFactory.getLogger(CustomTokenEnhancer.class);

	private final UserRepository userRepository;

	public CustomTokenEnhancer(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		User user = (User) authentication.getPrincipal();

		final Map<String, Object> tokenInfo = new HashMap<>();
		Optional<id.org.test.ms.auth.domain.User> ouser = userRepository.findByUsername(user.getUsername());
		if (ouser.isPresent()) {
			id.org.test.ms.auth.domain.User wuser = ouser.get();
			tokenInfo.put(AppConstant.TokenInfo.USER_NO, wuser.getNo());
			tokenInfo.put(AppConstant.TokenInfo.USERNAME, wuser.getUsername());
			tokenInfo.put(AppConstant.TokenInfo.USER_EMAIL, wuser.getEmail());
			tokenInfo.put(AppConstant.TokenInfo.USER_MOBILE, wuser.getMobile());
			tokenInfo.put(AppConstant.TokenInfo.AUTHORITIES, wuser.getAuthorities());
			tokenInfo.put(AppConstant.TokenInfo.MEMBER_ID, wuser.getMemberId());

			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(authentication.getAuthorities());
			if (authorities.size() > 0) {
				String role = authorities.get(0).getAuthority();
				tokenInfo.put(AppConstant.TokenInfo.ROLE, role);

				if (AppConstant.OAuthClientDetails.MobileApi.ID
						.contentEquals(authentication.getOAuth2Request().getClientId())) {

					if (role.equalsIgnoreCase(AppConstant.UserRole.MEMBER)) {

						tokenInfo.put(AppConstant.TokenInfo.ROLE, AppConstant.UserRole.MEMBER);

						List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
						roles.add(new SimpleGrantedAuthority(AppConstant.UserRole.MEMBER));
						tokenInfo.put(AppConstant.TokenInfo.AUTHORITIES, roles);
					}

				}
			}

		}

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(tokenInfo);
		return accessToken;
	}

}
