package id.org.test.restful.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AppAuditorAware implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() throws AuthenticationException {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication == null || !authentication.isAuthenticated()) {
				String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
				return null != loggedUser ? loggedUser : "sys";
			}
			return ((UserContext) authentication.getPrincipal()).getUsername();
		} catch (Exception e) {
			String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
			return null != loggedUser ? loggedUser : "sys";
		}
	}

}
