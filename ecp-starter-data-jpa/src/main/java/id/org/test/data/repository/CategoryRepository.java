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
	
	Optional<Category> findByIdAndAccountId(Long id, Long accountId);

	@Query(value = "select mc  from Category mc where mc.deleted = 0 and mc.categoryName like  %:text%")
	Page<Category> getPageable(@Param("text") String text, Pageable pageable);

	@Query(value = "select mc  from Category mc where mc.deleted = 0 and mc.account.id=:accountId and (mc.categoryName like  %:text%)")
	Page<Category> getPageableByAccount(@Param("accountId") Long accountId, @Param("text") String text,Pageable pageable);

	@Query(value = "select mc from Category mc where mc.deleted = 0 and mc.account.id=:accountId")
	List<Category> getNumFilteredByAccount(@Param("accountId") Long accountId);
	
	@Query(value = "select mc from Category mc where mc.deleted = 0 and mc.account.id =:accountId and mc.categoryName=:categoryName")
    Category getByCategoryNameAndAccountId(@Param("categoryName") String categoryName,@Param("accountId") Long accountId);
	
	@Query(value = "select mc from Category mc where mc.deleted = 1 and mc.account.id =:accountId and mc.categoryName=:categoryName")
    Category getByCategoryNameAndAccountIdDelete(@Param("categoryName") String categoryName,@Param("accountId") Long accountId);

	@Query(value = "select c from Category c where c.description='default category' and c.account.id=:accountId")
    Category getDefaultCategoryByAccount(@Param("accountId") Long accountId);
}
