package id.org.test.data.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import id.org.test.data.model.Member;
import id.org.test.data.projection.MemberProj;

public interface MemberRepository extends JpaRepository<Member, Long>, QueryDslPredicateExecutor<Member>{
	
	MemberProj findByUserUsername(String username);
	
	@Query(value = "select a from Member a where a.deleted = 0 and (a.mobile like %:text% )")
    Page<Member> getPageable(@Param("text") String text, Pageable pageable);

    @Query(value = "select  a from Member a where a.user.username=:username")
    Member getByUser(@Param("username") String username);

    @Query(value = "select a from Member a where a.email=:email")
    Member getByEmail(@Param("email") String email);

    @Query(value = "select count(*) from Member a where date(a.createdDate) = CURRENT_DATE")
    Integer getMemberToday();
    
    @Query(value = "select count(*) from Member a where createdDate BETWEEN :startDate AND :endDate")
    Integer getMemberByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    @Query(value = "select a from Member a where a.deleted = 0 and (date(a.createdDate) BETWEEN :startDate AND :endDate) and (a.mobile like %:text% )")
    Page<Member> getPageableByDate(@Param("text") String text, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
    
    @Query(value = "select a from Member a where a.deleted = 0 and (date(a.createdDate) BETWEEN :startDate AND :endDate) and (a.mobile like %:text% )")
    List<Member> getMemberListByDate(@Param("text") String text, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
}
