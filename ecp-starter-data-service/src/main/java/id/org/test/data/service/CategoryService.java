package id.org.test.data.service;

import java.util.List;
import java.util.Optional;

import id.org.test.common.service.CommonService;
import id.org.test.data.service.wrapper.CategoryWrapper;

public interface CategoryService extends CommonService<CategoryWrapper, Long> {
	
	boolean delete(Long id, Long memberId) throws Exception;
	
	Optional<CategoryWrapper> getCategoryByNameAndMember(String categoryName, Long memberId) throws Exception;
	
	Optional<CategoryWrapper> getDeletedCategoryByNameAndMember(String categoryName, Long memberId) throws Exception;
	
	boolean isExist(String categoryName, Long memberId) throws Exception;
	
	Optional<CategoryWrapper> getByIdAndMemberId(Long id, Long memberId) throws Exception;

	Long getNumFilteredByMember(Long memberId);

	List<CategoryWrapper> getListByMember(Long aLong);

	boolean createDefaultCategory(Long memberId) throws Exception;

    void deleteAll();
    
    boolean checkExistingByName(String categoryName, Long memberId) throws Exception;

}
