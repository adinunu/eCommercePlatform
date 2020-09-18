package id.org.test.common.web;

import static id.org.test.common.constant.AppConstant.TokenInfo.MEMBER_ID;
import static id.org.test.common.constant.AppConstant.TokenInfo.USER_EMAIL;
import static id.org.test.common.constant.AppConstant.TokenInfo.USER_MOBILE;
import static id.org.test.common.constant.AppConstant.TokenInfo.USER_NO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;

import lombok.Getter;
import lombok.Setter;

public class UserContext implements Serializable {

	private static final long serialVersionUID = 7173516933346271212L;

	private static final Logger log = LoggerFactory.getLogger(UserContext.class);

	@Getter
	private final String username;
	@Getter
	private final List<GrantedAuthority> authorities;
	@Getter
	private final Long userNo;
	@Getter
	private final String userEmail;
	@Getter
	private final String userMobile;
	@Getter
	private final Long memberId;

	@Getter
	@Setter
	private Object authentication;
	@Setter
	private boolean hasAccessForMenuUri; // has access to specified Menu URI (applicable only for SmartDashboard)

	private boolean member;
	private String role;

	private UserContext(String username, List<GrantedAuthority> authorities, Long userNo, String userEmail,
			String userMobile, Long memberId) {
		this.username = username;
		this.authorities = authorities;
		this.userNo = userNo;
		this.userEmail = userEmail;
		this.userMobile = userMobile;
		this.memberId = memberId;
	}

	private UserContext(String username, Collection<GrantedAuthority> authorities, Long userNo, String userEmail,
			String userMobile, Long memberId) {
		this.username = username;
		this.authorities = new ArrayList<GrantedAuthority>();
		for (GrantedAuthority ga : authorities) {
			this.authorities.add(new SimpleGrantedAuthority((ga.getAuthority().split("=")[1]).replaceAll("}", "")));
		}
		this.userNo = userNo;
		this.userEmail = userEmail;
		this.userMobile = userMobile;
		this.memberId = memberId;
	}

	/**
	 * applicable for Mobile API
	 * 
	 * @param auth
	 * @param tokenStore
	 * @return
	 */
	public static UserContext build(OAuth2Authentication auth, TokenStore tokenStore) {

		Long userNo = null, memberId = null;
		String userEmail = null, userMobile = null;

		OAuth2AuthenticationDetails oauthDtls = (OAuth2AuthenticationDetails) auth.getDetails();
		String accessTokenRaw = oauthDtls.getTokenValue();

		if (StringUtils.isNotEmpty(accessTokenRaw)) {
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(accessTokenRaw);
			Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();

			String userNoStr = ((Integer) null == additionalInformation.get(USER_NO) ? 0
					: additionalInformation.get(USER_NO)).toString();
			userNo = Long.valueOf("null".equalsIgnoreCase(userNoStr) ? "0" : userNoStr);

			userEmail = (String) additionalInformation.get(USER_EMAIL);
			userMobile = (String) additionalInformation.get(USER_MOBILE);

			String memberIdStr = ((Integer) null == additionalInformation.get(MEMBER_ID) ? 0
					: additionalInformation.get(MEMBER_ID)).toString();
			memberId = Long.valueOf("null".equalsIgnoreCase(memberIdStr) ? "0" : memberIdStr);

		}

		return new UserContext((String) auth.getPrincipal(), auth.getAuthorities(), userNo, userEmail, userMobile,
				memberId);
	}

	public boolean hasAccess() {
		return hasAccessForMenuUri;
	}

	public String getRole() {
		List<LinkedHashMap<String, String>> authoritiesMap = (ArrayList) authorities;
		LinkedHashMap<String, String> map = authoritiesMap.get(0);
		for (String key : map.keySet()) {
			role = map.get(key);
		}
		return role;
	}

}
