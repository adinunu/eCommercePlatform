package id.org.test.data.service.inventory.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import id.org.test.data.model.ProductDetail;
import id.org.test.data.repository.ProductDetailRepository;
import id.org.test.data.repository.ProductRepository;
import id.org.test.data.service.inventory.ProductDetailService;
import id.org.test.data.service.wrapper.ProductDetailWrapper;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

	private final ProductDetailRepository productDetailRepository;
	private final ProductRepository productRepository;

	public ProductDetailServiceImpl(ProductDetailRepository productDetailRepository,
			ProductRepository productRepository) {
		super();
		this.productDetailRepository = productDetailRepository;
		this.productRepository = productRepository;
	}

	@Override
	public Boolean delete(Long arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDetailWrapper> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductDetailWrapper getById(Long id) throws Exception {
		return toWrapper(productDetailRepository.findOne(id));
	}

	@Override
	public Long getNum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductDetailWrapper save(ProductDetailWrapper wrapper) throws Exception {
		return toWrapper(productDetailRepository.save(toEntity(wrapper)));
	}

	@Override
	public ProductDetail toEntity(ProductDetailWrapper wrapper) throws Exception {
		ProductDetail model = new ProductDetail();
		if (wrapper.getId() != null) {
			model = productDetailRepository.findOne(wrapper.getId()); // edit mode
		}
		model.setDescription(wrapper.getDescription());
		model.setProductReference(productRepository.findOne(wrapper.getProductReferenceId()));
		model.setProduct(productRepository.findOne(wrapper.getProductId()));
		model.setQuantity(wrapper.getQuantity());

		return model;
	}

	@Override
	public ProductDetailWrapper toWrapper(ProductDetail entity) {
		ProductDetailWrapper wrapper = new ProductDetailWrapper();
		if (entity != null) {
			wrapper.setId(entity.getId());
			wrapper.setDescription(entity.getDescription());
			wrapper.setProductId(entity.getProduct().getId());
			wrapper.setProductReferenceId(entity.getProductReference().getId());
			wrapper.setProductName(entity.getProductReference().getProductName());
			wrapper.setSupplyPrice(entity.getProductReference().getSupplyPrice());
			wrapper.setQuantity(entity.getQuantity());

		}
		return wrapper;
	}

	@Override
	public List<ProductDetailWrapper> toWrapperList(List<ProductDetail> entityList) {
		List<ProductDetailWrapper> wrapperList = new ArrayList<>();
		if (entityList != null && entityList.size() > 0) {
			for (ProductDetail model : entityList) {
				wrapperList.add(toWrapper(model));
			}
		}
		return wrapperList;
	}

	@Override
	public List<ProductDetailWrapper> getListByProductId(Long productId) {
		return toWrapperList(productDetailRepository.getByProductId(productId));
	}

	@Override
	public Boolean deleteDetail(Long id) throws Exception {
		try {
			productDetailRepository.deleteDetail(id);
			return true;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
