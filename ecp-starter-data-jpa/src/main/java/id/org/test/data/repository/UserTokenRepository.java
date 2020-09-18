package id.org.test.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import id.org.test.data.model.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, Long>, QueryDslPredicateExecutor<UserToken>{

	@Query(value = "select mc from UserToken mc where mc.token=:token")
	UserToken getUserByToken(@Param("token") String token);
}
