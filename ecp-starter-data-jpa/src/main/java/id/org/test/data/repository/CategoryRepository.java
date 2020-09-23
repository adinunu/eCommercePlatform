package id.org.test.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import id.org.test.data.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>, QueryDslPredicateExecutor<Category> {
	
	Optional<Category> findByIdAndMemberId(Long id, Long memberId);

	@Query(value = "select mc  from Category mc where mc.deleted = 0 and mc.categoryName like  %:text%")
	Page<Category> getPageable(@Param("text") String text, Pageable pageable);

	@Query(value = "select mc  from Category mc where mc.deleted = 0 and mc.member.id=:memberId and (mc.categoryName like  %:text%)")
	Page<Category> getPageableByMember(@Param("memberId") Long memberId, @Param("text") String text,Pageable pageable);

	@Query(value = "select mc from Category mc where mc.deleted = 0 and mc.member.id=:memberId")
	List<Category> getNumFilteredByMember(@Param("memberId") Long memberId);
	
	@Query(value = "select mc from Category mc where mc.deleted = 0 and mc.member.id =:memberId and mc.categoryName=:categoryName")
    Category getByCategoryNameAndMemberId(@Param("categoryName") String categoryName,@Param("memberId") Long memberId);
	
	@Query(value = "select mc from Category mc where mc.deleted = 1 and mc.member.id =:memberId and mc.categoryName=:categoryName")
    Category getByCategoryNameAndMemberIdDelete(@Param("categoryName") String categoryName,@Param("memberId") Long memberId);

	@Query(value = "select c from Category c where c.description='default category' and c.member.id=:memberId")
    Category getDefaultCategoryByMember(@Param("memberId") Long memberId);
}
