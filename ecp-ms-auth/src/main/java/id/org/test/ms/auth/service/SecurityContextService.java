package id.org.test.ms.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService {
	
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public void setAuthentication(final Authentication authentication) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
