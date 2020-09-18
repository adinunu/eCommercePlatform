package id.org.test.ms.auth.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Collection;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import id.org.test.ms.auth.domain.User;
import id.org.test.ms.auth.repository.UserRepository;
import id.org.test.ms.shared.auth.LoginID;

@Service
public class CustomUserDetailsManager implements UserDetailsManager {

	private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsManager.class);
	
	public static final String[] ALLOWED_ROLES = { "ROLE_ADMIN", "ROLE_MEMBER"};
	
	private UserRepository userRepository;
	private SecurityContextService securityContextService;
	private AuthenticationManager authenticationManager;
	private EmailValidator ev = EmailValidator.getInstance();
	
	public CustomUserDetailsManager(final UserRepository userRepository,
									final SecurityContextService securityContextService,
									final AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.securityContextService = securityContextService;
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("WheeUserDetailsManager.loadUserByUsername({})", username);
		org.springframework.security.core.userdetails.User springUser = null;
		
		switch (loginId(username)) {
		
		case USERNAME: //login using username
			
			springUser = login(username, LoginID.USERNAME);
			
	        if (null == springUser){ // try to login with email instead
	        	springUser = login(username, LoginID.EMAIL);
	        }
			
			break;
			
		case EMAIL: //login using email
			
			springUser = login(username, LoginID.EMAIL);
			
	        if (null == springUser){ // try to login with username instead
	        	springUser = login(username, LoginID.USERNAME);
	        }
			
			break;

		case MOBILE: //login using mobile
			
			springUser = login(username, LoginID.MOBILE);
			
			break;
			
		default:
			break;
		}
        
        if(null != springUser) {
        	if(loginAllowed(springUser.getAuthorities())) {
        		return springUser;
        	}
        }
        
        throw new UsernameNotFoundException("user does not exists.");
	}
	
	private boolean loginAllowed(Collection<GrantedAuthority> authorities) {
		log.debug("WheeUserDetailsManager.loginAllowed({})", authorities);
		for(GrantedAuthority ga : authorities) {
			String role = ga.getAuthority();
			for (String arole : ALLOWED_ROLES) {
				if (role.equalsIgnoreCase(arole)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private org.springframework.security.core.userdetails.User login(String username, LoginID loginId){
		org.springframework.security.core.userdetails.User springUser = null;
		
		switch (loginId) {
		
		case USERNAME: //login using username
			
			final Optional<User> byUsername = userRepository.findByUsername(username);
	        if (byUsername.isPresent()) {
	    		log.debug("{} is username", username);
	    		User user = byUsername.get();
	    		if(user.isEnabled())
	    			springUser = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
	        }
			
			break;
			
		case EMAIL: //login using email
			
			final Optional<User> byEmail = userRepository.findByEmail(username);
	        if (byEmail.isPresent()){
	    		log.debug("{} is email", username);
	    		User user = byEmail.get();
	    		if(user.isEnabled())
	    			springUser = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
	        }
			
			break;

		case MOBILE: //login using mobile
			
			final Optional<User> byMobile = userRepository.findByMobile(username);
	        if (byMobile.isPresent()) {
	    		log.debug("{} is mobile", username);
	    		User user = byMobile.get();
	    		if(user.isEnabled())
	    			springUser = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
	        }
			
			break;
			
		default:
			break;
		}
		
		
		return springUser;
	}
	
	private LoginID loginId(String username) {
		
		if(StringUtils.isEmpty(username)) {
			return null;
		}
		
		if(ev.isValid(username)) {
			return LoginID.EMAIL;
		} else if(username.matches("\\d+")) {
			return LoginID.MOBILE;
		} else {
			return LoginID.USERNAME;
		}
	}
	
	public void createUser(User user) {
		user.setEnabled(true);
		user.setAccountNonExpired(false);
		user.setAccountNonLocked(false);
		user.setCredentialsNonExpired(false);
		userRepository.save(user);
	}
	
	public void createUserPIC(User user) {
		user.setEnabled(false);
		user.setAccountNonExpired(false);
		user.setAccountNonLocked(false);
		user.setCredentialsNonExpired(false);
		userRepository.save(user);
	}

	@Override
	public void createUser(UserDetails user) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateUser(UserDetails user) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		final Authentication currentUser = securityContextService.getAuthentication();
		if (isNull(currentUser)) {
	        // This would indicate bad coding somewhere
			throw new AccessDeniedException("Can't change password as no Authentication object found in context for current user.");
		}
		
		final String username = currentUser.getName();
		// If an authentication manager has been set, re-authenticate the user with the supplied password.
        if (nonNull(authenticationManager)) {
            log.debug("Reauthenticating user '{}' for password change request.", username);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        } else {
            log.debug("No authentication manager set. Password won't be re-checked.");
        }

        log.debug("Changing password for user '{}'", username);
        
        Optional<User> ouser = userRepository.findByUsernameAndPasswordAndEnabledTrue(username, oldPassword);
        if(ouser.isPresent()) {
    		User user = ouser.get();
    		user.setPassword(newPassword);
    		userRepository.saveAndFlush(user);
    		
    		final UserDetails userDetails = loadUserByUsername(username);
    		UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    		newAuth.setDetails(currentUser.getDetails());
    		securityContextService.setAuthentication(newAuth);
        }
	}

	@Override
	public boolean userExists(String username) {
		return Optional.of(userRepository.findByUsername(username)).isPresent();
	}

}
