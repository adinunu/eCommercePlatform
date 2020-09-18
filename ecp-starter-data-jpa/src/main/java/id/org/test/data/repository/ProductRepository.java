package id.org.test.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import id.org.test.common.constant.GeneralStatus;
import id.org.test.data.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, QueryDslPredicateExecutor<Product>{

    @Query(value = "select p from Product p where p.deleted=0 and p.composite=0 and (p.productName like  %:text% or p.productHandle like  %:text% or p.productCategory.categoryName like  %:text%  or p.sku like  %:text%)")
    Page<Product> getPageable(@Param("text") String text, Pageable pageable);

    @Query(value = "select p from Product p where p.account.id=:accountId and p.deleted=0  and (p.productName like  %:text% or p.productHandle like  %:text% or p.productCategory.categoryName like  %:text% or p.sku like  %:text% )")
    List<Product> getNumFilteredByAccount(@Param("accountId") Long accountId, @Param("text") String text);

    @Query(value = "select p from Product p where p.account.id=:accountId and p.deleted=0 and p.generalStatus='0' and (p.productName like  %:text% or p.productHandle like  %:text% or p.productCategory.categoryName like  %:text%  or p.sku like  %:text% )")
    List<Product> getNumFilteredByAccountActive(@Param("accountId") Long accountId, @Param("text") String text);
    
    @Query(value = "select p from Product p where p.account.id=:accountId and p.deleted=0 and p.generalStatus='1' and (p.productName like  %:text% or p.productHandle like  %:text% or p.productCategory.categoryName like  %:text%  or p.sku like  %:text% )")
    List<Product> getNumFilteredByAccountInactive(@Param("accountId") Long accountId, @Param("text") String text);

    @Query(value = "select p from Product p where p.account.id=:accountId and p.deleted=0  and (p.productName like  %:text% or p.productHandle like  %:text% or p.productCategory.categoryName like  %:text%  or p.sku like  %:text% )")
    Page<Product> getPageableByAccountId(@Param("accountId") Long accountId, @Param("text") String text, Pageable pageable);
    
    @Query(value = "select p from Product p where p.account.id=:accountId and p.deleted=0 and p.composite=0 and ( p.productName like  %:text% or p.productHandle like  %:text% or p.productCategory.categoryName like  %:text% or p.sku like  %:text%)")
    Page<Product> getPageableListByAccount(@Param("accountId") Long accountId, @Param("text") String text, Pageable pageable);
    
    @Query(value = "select p from Product p where p.account.id=:accountId and p.deleted=0  and ( p.productName like  %:text% )")
    Page<Product> getPageableListByAccountMob(@Param("accountId") Long accountId, @Param("text") String text, Pageable pageable);
    
    @Query(value = "select p from Product p where p.account.id=:accountId and p.deleted=0 and p.generalStatus='0' and p.composite=0 and ( p.productName like  %:text% or p.productHandle like  %:text% or p.productCategory.categoryName like  %:text%  or p.sku like  %:text%)")
    Page<Product> getPageableListByAccountActive(@Param("accountId") Long accountId, @Param("text") String text, Pageable pageable);
    
    @Query(value = "select p from Product p where p.account.id=:accountId and p.deleted=0 and p.generalStatus=:status and ( p.productName like  %:text% )")
    Page<Product> getPageableListByAccountAndStatus(@Param("accountId") Long accountId,@Param("status") GeneralStatus status, @Param("text") String text, Pageable pageable);
    
    @Query(value = "select p from Product p where p.account.id=:accountId and p.productCategory.id=:categoryId and p.deleted=0 and p.generalStatus='0' and p.composite=0 and ( p.productName like  %:text% or p.productHandle like  %:text% or p.productCategory.categoryName like  %:text%  or p.sku like  %:text%)")
    Page<Product> getPageableListByAccountActiveAndCategory(@Param("accountId") Long accountId,@Param("categoryId") Long categoryId, @Param("text") String text, Pageable pageable);
    
    @Query(value = "select p from Product p where p.account.id=:accountId and p.productCategory.id=:categoryId and p.deleted=0 and p.composite=0 and ( p.productName like  %:text% )")
    Page<Product> getPageableListByAccountAndCategory(@Param("accountId") Long accountId,@Param("categoryId") Long categoryId, @Param("text") String text, Pageable pageable);
    
    @Query(value = "select p from Product p where p.account.id=:accountId and p.productCategory.id=:categoryId and p.deleted=0 and p.generalStatus=:status and ( p.productName like  %:text% )")
    Page<Product> getPageableListByAccountActiveAndCategoryAndStatus(@Param("accountId") Long accountId,@Param("categoryId") Long categoryId,@Param("status") GeneralStatus status,@Param("text") String text, Pageable pageable);
    
    @Query(value = "select p from Product p where p.account.id=:accountId and p.productCategory.id=:categoryId and p.deleted=0 and p.generalStatus='1' and ( p.productName like  %:text% or p.productHandle like  %:text% or p.productCategory.categoryName like  %:text%  or p.sku like  %:text%)")
    Page<Product> getPageableListByAccountInactiveAndCategory(@Param("accountId") Long accountId,@Param("categoryId") Long categoryId, @Param("text") String text, Pageable pageable);
    
    @Query(value = "select p from Product p where p.account.id=:accountId and p.deleted=0 and p.generalStatus='1' and ( p.productName like  %:text% or p.productHandle like  %:text% or p.productCategory.categoryName like  %:text%  or p.sku like  %:text%)")
    Page<Product> getPageableListByAccountInactive(@Param("accountId") Long accountId, @Param("text") String text, Pageable pageable);
   
    @Query(value = "select p from Product p where p.account.id=:accountId")
    List<Product> getListByAccount(@Param("accountId") Long accountId);

    @Query(value = "select p from Product p where p.account.id=:accountId and p.generalStatus='0' and p.deleted=0" )
    List<Product> getActiveListByAccount(@Param("accountId") Long accountId);

    @Query(value = "select p from Product p where p.account.id=:accountId and p.generalStatus='0' and p.deleted=0" )
    List<Product> getActiveListByAccountPageable(@Param("accountId") Long accountId,Pageable pageable);

    @Query(value = "select p from Product p where p.account.id=:accountId")
    Page<Product> getPageableListByAccountNoSearch(@Param("accountId") Long accountId, Pageable pageable);

    @Query(value = "select p from Product p where p.composite=0")
    Page<Product> getPageableNoSearch(Pageable pageable);

    @Query(value = "select p from Product p where p.deleted=0 and (p.productName like  %:text% or p.productHandle like  %:text% or (p.productCategory is null or p.productCategory is not null)    or p.sku like  %:text%)")
    Page<Product> getPageableCustom(@Param("text") String text, Pageable pageable);
    
    @Query(value = "select s from Product s where s.account.id=:accountId")
    List<Product> getProductByAccount(@Param("accountId")  Long accountId);
	
	@Query(value = "select s from Product s where s.account.id=:accountId")
    List<Product> getProductByAccountAndStore(@Param("accountId")  Long accountId);

	@Query(value = "select p from Product p where p.account.id=:accId and p.productCategory.id=:catId and p.deleted=0")
    List<Product> getProductActiveListByCategoryAndAccount(@Param("accId") Long accId, @Param("catId") Long catId);

	@Query(value = "select p from Product p where p.sku=:skuCode and p.account.id=:accountId and p.deleted=0")
    List<Product> getProductBySku(@Param("skuCode") String skuCode, @Param("accountId")Long accountId);

	@Query(value = "select p from Product p where p.deleted=0 and p.productCategory.id=:catId and (p.productName like  %:text% or p.productHandle like  %:text% or p.productCategory.categoryName like  %:text%  or p.sku like  %:text%)")
    Page<Product> getPageableListByCategory(@Param("catId") Long catId,@Param("text") String text, Pageable pageable);
	
	@Query(value = "select p from Product p where p.account.id=:accountId and p.trackInventoryFlag = 1 and p.deleted=0")
	List<Product> getListProductWithInventory(@Param("accountId") Long accountId);


	//composite product
	@Query(value = "select p from Product p where p.composite=true ")
    Long countCompositeProduct();

    @Query(value = "select p from Product p where p.deleted=0 and p.composite=true and ( p.productName like  %:text% or p.productHandle like  %:text% or p.sku like  %:text% or p.productCategory.categoryName like  %:text%)")
    Page<Product> getPageableComposite(@Param("text") String text, Pageable pageable);

    @Query(value = "select p from Product p where p.account.id=:accountId and p.composite=true and p.deleted=0  and ( p.productName like  %:text% or p.productHandle like  %:text% or p.sku like  %:text% or p.productCategory.categoryName like  %:text%)")
    Page<Product> getPageableCompositeListByAccount(@Param("accountId") Long accountId, @Param("text") String text, Pageable pageable);

    @Query(value = "select p from Product p where p.composite=true")
    Page<Product> getPageableCompositeNoSearch(Pageable pageable);

    @Query(value = "select p from Product p where p.account.id=:accountId and p.barcode=:barcode and p.generalStatus='0' and p.deleted=0" )
    Product getProductByAccountAndProductId(@Param("accountId") Long accountId,@Param("barcode") String barcode);

    @Query(value = "select p from Product p where p.account.id=:accountId and p.deleted=0  and p.productCategory.id=:categoryId and (p.productName like  %:text% or p.productHandle like  %:text% or p.productCategory.categoryName like  %:text% or p.sku like  %:text% )")
	List<Product> getNumFilteredByAccountAndCategory(@Param("accountId") Long accountId, @Param("categoryId") Long categoryId, @Param("text") String text);

    @Query(value = "select p from Product p where p.account.id=:accountId and p.deleted=0  and p.productCategory.id=:categoryId and (p.productName like  %:text% or p.productHandle like  %:text% or p.productCategory.categoryName like  %:text% or p.sku like  %:text% )")
	Page<Product> getPageableListByAccountCategory(@Param("accountId") Long accountId, @Param("categoryId") Long categoryId, @Param("text") String text, Pageable pageable);

}
