package id.org.test.ms.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import id.org.test.ms.auth.domain.UserAccount;

public interface UserAccountRepository extends PagingAndSortingRepository<UserAccount, Long>, QueryDslPredicateExecutor<UserAccount> {

    @Query(value = "select a from UserAccount a where a.firstName like %:sSearch% or a.lastName like %:sSearch% or a.nickName like %:sSearch% or a.accountAddress like %:sSearch% or a.accountContactNo like %:sSearch% or a.accountEmail like %:sSearch% or a.user.username like %:sSearch% ")
    Page<UserAccount> getPageable(@Param("sSearch") String sSearch, Pageable pageable);

    @Query(value = "select ua from UserAccount ua where ua.user.username=:username")
    UserAccount getByUsername(@Param("username") String username);

}
