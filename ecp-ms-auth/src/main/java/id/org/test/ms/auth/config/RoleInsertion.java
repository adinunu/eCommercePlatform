package id.org.test.ms.auth.config;

import java.util.EnumSet;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import id.org.test.ms.auth.domain.Role;
import id.org.test.ms.auth.repository.RoleRepository;
import id.org.test.ms.shared.auth.UserRole;

@Component
public class RoleInsertion implements ApplicationListener<ApplicationReadyEvent> {

	private static final Logger log = LoggerFactory.getLogger(RoleInsertion.class);
	
	private final RoleRepository roleRepo;
	
	public RoleInsertion(RoleRepository roleRepo) {
		this.roleRepo = roleRepo;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		try {
			
			EnumSet.allOf(UserRole.class)
				.forEach(role -> addOrUpdateRole(role));
			
		} catch (Exception e) {
			log.error("Fail RoleInsertion", e);
			System.exit(-1);
		}
		
	}
	
	private void addOrUpdateRole(UserRole role) {
		Optional<Role> oRole = roleRepo.findByName(role);
		if(!oRole.isPresent()) {
			Role newRole = new Role();
			newRole.setName(role);
			roleRepo.save(newRole);
			log.debug("UserRole.{} added", role.getRoleName());
		}
	}
	
}
