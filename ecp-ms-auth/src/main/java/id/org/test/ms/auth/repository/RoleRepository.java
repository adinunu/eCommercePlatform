package id.org.test.ms.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import id.org.test.ms.auth.domain.Role;
import id.org.test.ms.shared.auth.UserRole;

public interface RoleRepository extends JpaRepository<Role, Long>, QueryDslPredicateExecutor<Role> {

	Optional<Role> findByName(UserRole roleName);
	
}
