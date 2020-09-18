package id.org.test.ms.auth.config.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import id.org.test.ms.auth.repository.OAuthAccessTokenRepository;

//@Component
public class OAuthLoginFilter extends OncePerRequestFilter implements Ordered {
	
	private static final Logger log = LoggerFactory.getLogger(OAuthLoginFilter.class);
	
	private int order = Ordered.LOWEST_PRECEDENCE - 8;
	private String username, grantType, clientId = "";
	
	private final JdbcTokenStore tokenStore;
	private final OAuthAccessTokenRepository oauthAccTokenRepo;
	
	public OAuthLoginFilter(JdbcTokenStore tokenStore, OAuthAccessTokenRepository oauthAccTokenRepo) {
		this.tokenStore = tokenStore;
		this.oauthAccTokenRepo = oauthAccTokenRepo;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String url = getFullURL(request);
		
		if(url.contains("/oauth/token")) {
			
			ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
			username = requestWrapper.getParameter("username");
			grantType = requestWrapper.getParameter("grant_type");
			
			if(StringUtils.isNotEmpty(grantType)) {
				if(grantType.equalsIgnoreCase("password")) {
					
					try {
						
						StringBuilder hlogmsg = new StringBuilder("{");
						Enumeration<String> headerNames = requestWrapper.getHeaderNames();
						while(headerNames.hasMoreElements()) {
							String hname = headerNames.nextElement();
							String hvalue = requestWrapper.getHeader(hname);
							hlogmsg.append("\"").append(hname).append("\" : \"")
									.append(hvalue).append("\"");
							if(headerNames.hasMoreElements())
								hlogmsg.append(", ");
							else 
								hlogmsg.append(" }");
						}
						log.debug("Headers[{}]", hlogmsg);
						
						String basicAuthorization = requestWrapper.getHeader("Authorization");
						clientId = getClientId(basicAuthorization);
						log.debug("Authorization[clientId={}, username={}, grant_type={}]", clientId, username, grantType);
						
						if(StringUtils.isNotEmpty(clientId)) {
								
							Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByUserName(username);
//							Collection<OAuthAcccessToken> storedTokens = oauthAccTokenRepo.findByUserNameAndClientId(username, clientId);
//							log.debug("TOKENS AVAILABLE >> {} x STORED {}", tokens.size(), storedTokens.size());
//							Thread.currentThread().sleep(5000);
//							for (OAuth2AccessToken accToken : tokens) {
//								boolean expired = accToken.isExpired();
//								if(expired) {
//									tokenStore.removeAccessToken(accToken);
//									tokenStore.removeRefreshToken(accToken.getRefreshToken());
//								} else {
//									response.setContentType("application/json");
//									response.sendError(HttpStatus.UNAUTHORIZED.value(), ResponseStatus.USER_IS_LOGGED_IN.getMessage());
//									return;
//								}
//							}
								
						}
						
					} catch (Exception e) {
						String errorMsg = e.getMessage();
						log.error(errorMsg, e);
						response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMsg);
					}
					
				}
			}
			
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	private String getClientId(String basicAuthorization) {
		String clientId = "";
		if(StringUtils.isNotEmpty(basicAuthorization)) {
			String[] basic = basicAuthorization.split(" ");
			if(basic.length > 1) {
				String auth = basic[1];
				if(StringUtils.isNotEmpty(auth)) {
					byte[] decode = Base64.getDecoder().decode(auth.getBytes());
					String fullDecodedAuthorization = new String(decode);
					if(StringUtils.isNotEmpty(fullDecodedAuthorization)) {
						String[] fda = fullDecodedAuthorization.split(":");
						if(fda.length > 1)
							clientId = fda[0];
					}
				}
			}
		}
		return clientId;
	}
	
	private String getFullURL(HttpServletRequest request) {
	    StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
	    String queryString = request.getQueryString();
	    if (queryString == null) {
	        return requestURL.toString();
	    } else {
	        return requestURL.append('?').append(queryString).toString();
	    }
	}

	@Override
	public int getOrder() {
		return order;
	}

}
