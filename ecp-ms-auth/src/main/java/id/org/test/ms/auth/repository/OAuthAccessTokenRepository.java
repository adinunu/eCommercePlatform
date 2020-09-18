package id.org.test.ms.auth.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import id.org.test.ms.auth.domain.OAuthAcccessToken;

public interface OAuthAccessTokenRepository
		extends JpaRepository<OAuthAcccessToken, String>, QueryDslPredicateExecutor<OAuthAcccessToken> {

	Collection<OAuthAcccessToken> findByUserName(String userName);
	
	Collection<OAuthAcccessToken> findByUserNameAndClientId(String userName, String clientId);
	
}
