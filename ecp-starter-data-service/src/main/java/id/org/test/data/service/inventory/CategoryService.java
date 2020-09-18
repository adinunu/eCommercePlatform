package id.org.test.data.service.inventory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import id.org.test.common.service.CommonService;
import id.org.test.data.service.inventory.wrapper.CategoryWrapper;

public interface CategoryService extends CommonService<CategoryWrapper, Long> {
	
	boolean delete(Long id, Long accountId) throws Exception;
	
	Optional<CategoryWrapper> getCategoryByNameAndAccount(String categoryName, Long accountId) throws Exception;
	
	Optional<CategoryWrapper> getDeletedCategoryByNameAndAccount(String categoryName, Long accountId) throws Exception;
	
	boolean isExist(String categoryName, Long accountId) throws Exception;
	
	Optional<CategoryWrapper> getByIdAndAccountId(Long id, Long accountId) throws Exception;

	Long getNumFilteredByAccount(Long accountId);

	List<CategoryWrapper> getListByAccount(Long aLong);

	boolean createDefaultCategory(Long accountId) throws Exception;

    void deleteAll();
    
    boolean checkExistingByName(String categoryName, Long accountId) throws Exception;

}
