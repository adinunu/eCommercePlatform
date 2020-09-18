package id.org.test.data.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import id.org.test.data.model.ProductDetail;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long>, QueryDslPredicateExecutor<ProductDetail> {

    @Query(value = "select pd from ProductDetail pd where pd.product.id=:productId")
    List<ProductDetail> getByProductId(@Param("productId") Long productId);
    
    @Transactional
    @Modifying
    @Query(value = "delete from ProductDetail pd where pd.product.id=:productId")
    void deleteDetail(@Param("productId") Long productId);
    
    @Query(value = "select pd from ProductDetail pd where pd.productReference.id=:id")
    List<ProductDetail> getByProductrefId(@Param("id") Long id);
    
}
