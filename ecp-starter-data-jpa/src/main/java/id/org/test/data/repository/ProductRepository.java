package id.org.test.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import id.org.test.data.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, QueryDslPredicateExecutor<Product>{

    @Query(value = "select p from Product p where p.deleted=0 and (p.productName like  %:text% or p.productCode like  %:text% or p.productCategory.categoryName like  %:text%)")
    Page<Product> getPageable(@Param("text") String text, Pageable pageable);

    @Query(value = "select p from Product p where p.member.id=:memberId and p.deleted=0  and ( p.productName like  %:text% )")
    Page<Product> getPageableListByMemberMob(@Param("memberId") Long memberId, @Param("text") String text, Pageable pageable);
    
    @Query(value = "select p from Product p where p.member.id=:memberId and p.productCategory.id=:categoryId and p.deleted=0 and ( p.productName like  %:text% )")
    Page<Product> getPageableListByMemberAndCategory(@Param("memberId") Long memberId,@Param("categoryId") Long categoryId, @Param("text") String text, Pageable pageable);
   
    @Query(value = "select p from Product p where p.member.id=:memberId and p.generalStatus='0' and p.deleted=0" )
    List<Product> getActiveListByMember(@Param("memberId") Long memberId);

    @Query(value = "select p from Product p where p.generalStatus=0")
	Page<Product> getPageableNoSearch(Pageable pageable);

    List<Product> findAll();

}
