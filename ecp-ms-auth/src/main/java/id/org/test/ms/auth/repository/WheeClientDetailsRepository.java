package id.org.test.ms.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import id.org.test.ms.auth.domain.CustomClientDetails;

public interface WheeClientDetailsRepository
		extends JpaRepository<CustomClientDetails, Long>, QueryDslPredicateExecutor<CustomClientDetails> {
	
	CustomClientDetails findByClientId(String clientId);

}
