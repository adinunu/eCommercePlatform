package id.org.test.data.service.inventory;

import java.util.List;

import id.org.test.common.service.CommonService;
import id.org.test.data.model.ProductDetail;
import id.org.test.data.service.wrapper.ProductDetailWrapper;

public interface ProductDetailService extends CommonService<ProductDetailWrapper,Long>{

	ProductDetail toEntity(ProductDetailWrapper wrapper) throws Exception;

	ProductDetailWrapper toWrapper(ProductDetail entity);
	
	List<ProductDetailWrapper> toWrapperList (List<ProductDetail> entity);
	
	List<ProductDetailWrapper> getListByProductId (Long productId);
	
	Boolean deleteDetail(Long id) throws Exception;
}
