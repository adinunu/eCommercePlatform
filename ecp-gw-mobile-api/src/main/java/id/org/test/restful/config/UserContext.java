package id.org.test.restful.config;

import static id.org.test.common.constant.AppConstant.TokenInfo.MEMBER_ID;
import static id.org.test.common.constant.AppConstant.TokenInfo.USER_EMAIL;
import static id.org.test.common.constant.AppConstant.TokenInfo.USER_MOBILE;
import static id.org.test.common.constant.AppConstant.TokenInfo.USER_NO;

import java.util.ArrayList;
import java.util.Collection;
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

import id.org.test.common.constant.AppConstant;

/**
 * 
 * @author vladimir.stankovic
 *
 * Aug 4, 2016
 */
public class UserContext {
	
	private static final Logger log = LoggerFactory.getLogger(UserContext.class);
	
    private final String username;
    private final List<GrantedAuthority> authorities;
    private final Long userNo;
    private final String userEmail;
    private final String userMobile;
    private final Long memberId;
	
	private boolean member;
	private boolean administrator;
	
    private UserContext(String username, List<GrantedAuthority> authorities, Long userNo,
    		String userEmail, String userMobile, Long memberId) {
        this.username = username;
        this.authorities = authorities;
        this.userNo = userNo;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
        this.memberId = memberId;
    }
    
    private UserContext(String username, Collection<GrantedAuthority> authorities, Long userNo,
    		String userEmail, String userMobile, Long memberId) {
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
    
    @Deprecated
    public static UserContext create(String username, List<GrantedAuthority> authorities) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("Username is blank: " + username);
        return new UserContext(username, authorities, null, null, null, null);
    }
    
    @Deprecated
    public static UserContext build(String username, Collection<GrantedAuthority> authorities) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("Username is blank: " + username);
        return new UserContext(username, authorities, null, null, null, null);
    }
    
    public static UserContext build(OAuth2Authentication auth, TokenStore tokenStore) {
    	
    	Long userNo = null, memberId = null;
    	String userEmail = null, userMobile = null;
    	
    	OAuth2AuthenticationDetails oauthDtls = (OAuth2AuthenticationDetails) auth.getDetails();
		String accessTokenRaw = oauthDtls.getTokenValue();
		
		if(StringUtils.isNotEmpty(accessTokenRaw)) {
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(accessTokenRaw);
			Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
			
			String userNoStr = ((Integer) null == additionalInformation.get(USER_NO) ? 0 : additionalInformation.get(USER_NO)).toString();
			userNo = Long.valueOf("null".equalsIgnoreCase(userNoStr) ? "0" : userNoStr);
			
			userEmail = (String) additionalInformation.get(USER_EMAIL);
			userMobile = (String) additionalInformation.get(USER_MOBILE);
			
			String memberIdStr = ((Integer) null == additionalInformation.get(MEMBER_ID) ? 0 : additionalInformation.get(MEMBER_ID)).toString();
			memberId = Long.valueOf("null".equalsIgnoreCase(memberIdStr) ? "0" : memberIdStr);
			
		}
		
        return new UserContext((String) auth.getPrincipal(), auth.getAuthorities(), userNo, userEmail, userMobile, memberId);
    }

    public String getUsername() {
        return username;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

	public String getUserMobile() {
		return userMobile;
	}

	public Long getUserNo() {
		return userNo;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public Long getMemberId() {
		return memberId;
	}

	public boolean isAdministrator() {
		return getMemberId() == AppConstant.ADMIN_ACCOUNT_ID;
	}
    
}
