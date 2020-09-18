package id.org.test.data.service.mobile;

import java.util.List;

import org.springframework.data.domain.Sort;

import id.org.test.common.service.CommonService;
import id.org.test.data.service.mobile.wrapper.ProductMobileWrapper;

public interface ProductMobileService extends CommonService<ProductMobileWrapper, Long>{
	
	List<ProductMobileWrapper> getListByAccount(Long aLong)throws Exception;
	
	List<ProductMobileWrapper> getPageableListByAccount(Long accountId, Long categoryId,String status, String param, int startPage, int pageSize, Sort sort) throws Exception;
	
	List<ProductMobileWrapper> getPageableListByAccountAndStore(Long accountId,Long storeId, Long categoryId,String status, String param, int startPage, int pageSize, Sort sort) throws Exception;
	
	Boolean updateStatusBoolean (Long id, String status)throws Exception;
}
