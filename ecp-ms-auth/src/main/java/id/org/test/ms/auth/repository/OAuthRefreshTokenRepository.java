package id.org.test.ms.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import id.org.test.ms.auth.domain.OAuthRefreshToken;

public interface OAuthRefreshTokenRepository
		extends JpaRepository<OAuthRefreshToken, String>, QueryDslPredicateExecutor<OAuthRefreshToken> {

}
