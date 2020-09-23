package id.org.test.data.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import id.org.test.common.service.CommonService;
import id.org.test.data.service.wrapper.ProductMobileWrapper;

public interface ProductMobileService extends CommonService<ProductMobileWrapper, Long>{
	
	List<ProductMobileWrapper> getListByMember(Long aLong)throws Exception;
	
	List<ProductMobileWrapper> getPageableListByMember(Long memberId, Long categoryId,String status, String param, int startPage, int pageSize, Sort sort) throws Exception;
	
	List<ProductMobileWrapper> findAll();
}
