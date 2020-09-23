package id.org.test.data.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import id.org.test.common.constant.GeneralStatus;
import id.org.test.common.web.DataTableObject;
import id.org.test.data.model.Product;
import id.org.test.data.repository.MemberRepository;
import id.org.test.data.repository.CategoryRepository;
import id.org.test.data.repository.ProductRepository;
import id.org.test.data.service.ProductMobileService;
import id.org.test.data.service.wrapper.ProductMobileWrapper;

@Service
@Transactional
public class ProductMobileServiceImpl implements ProductMobileService {

	private static final Logger logger = LoggerFactory.getLogger(ProductMobileServiceImpl.class);

	@Value("${aliyun.oss.cdn-url:http://static.whee.tech}")
	private String cdnUrl;

	@Value("${aliyun.oss.product-bucket-path:images/product}")
	private String productBucketPath;

	@Value("${aliyun.oss.bucket-name:whee-tech}")
	private String bucketName;

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final MemberRepository memberRepository;

	@Autowired
	public ProductMobileServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
			MemberRepository memberRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.memberRepository = memberRepository;
	}

	private ProductMobileWrapper toWrapper(Product model) throws Exception {
		ProductMobileWrapper wrapper = new ProductMobileWrapper();
		wrapper.setId(model.getId());

		if (model.getProductCategory() != null) {
			wrapper.setCategoryId(model.getProductCategory().getId());
			wrapper.setCategoryName(model.getProductCategory().getCategoryName());
		}

		wrapper.setProductName(model.getProductName());
		wrapper.setProductCode(model.getProductCode());
		wrapper.setProductDescription(model.getDescription());
		wrapper.setProductPrice(model.getProductPrice());
		wrapper.setStoreName(model.getStoreName());
		wrapper.setBrandName(model.getBrandName());
		wrapper.setStatus(String.valueOf(model.getGeneralStatus()));
		wrapper.setMemberId(model.getMember().getId());

		return wrapper;
	}

	private List<ProductMobileWrapper> toWrapperList(List<Product> entityList) throws Exception {
		List<ProductMobileWrapper> wrapperList = new ArrayList<>();
		if (entityList != null && entityList.size() > 0) {
			// wrapperList = new ArrayList<>();
			for (Product temp : entityList) {
				wrapperList.add(toWrapper(temp));
			}
		}
		return wrapperList;
	}

	@Override
	public List<ProductMobileWrapper> getListByMember(Long memberId) throws Exception {
		return toWrapperList(productRepository.getActiveListByMember(memberId));
	}

	@Override
	public List<ProductMobileWrapper> getPageableListByMember(Long memberId, Long categoryId, String status,
			String param, int startPage, int pageSize, Sort sort) throws Exception {
		logger.info("Member Id : " + memberId);
		Page<Product> productPage = null;
		int page = DataTableObject.getPageFromStartAndLength(startPage, pageSize);
		if (memberId != null) {
			if (categoryId != null) {
				productPage = productRepository.getPageableListByMemberAndCategory(memberId, categoryId, param,
						new PageRequest(page, pageSize, sort));
			} else {
				productPage = productRepository.getPageableListByMemberMob(memberId, param,
						new PageRequest(page, pageSize, sort));
			}
		} else {
			if (param != null && !param.equalsIgnoreCase("")) {
				productPage = productRepository.getPageable(param, new PageRequest(page, pageSize, sort));
			} else {
				productPage = productRepository.getPageableNoSearch(new PageRequest(page, pageSize, sort));
			}
		}
		List<ProductMobileWrapper> wrapperList = toWrapperList(productPage.getContent());
		return wrapperList;
	}

	private Product toEntity(ProductMobileWrapper wrapper) throws Exception {
		Product entity = new Product();
		if (wrapper.getId() != null) {
			entity = productRepository.findOne(wrapper.getId());
			entity.setVersion(entity.getVersion() + 1L);
		} else {
			entity.setVersion(1L);
			entity.setDeleted(0);
		}
		if (wrapper.getCategoryId() != null) {
			entity.setProductCategory(categoryRepository.findOne(wrapper.getCategoryId()));
		}

		entity.setProductName(wrapper.getProductName());
		entity.setProductCode(wrapper.getProductCode());
		entity.setProductPrice(wrapper.getProductPrice());
		entity.setDescription(wrapper.getProductDescription());
		entity.setGeneralStatus(GeneralStatus.valueOf(wrapper.getStatus()));
		entity.setBrandName(wrapper.getBrandName());
		entity.setStoreName(wrapper.getStoreName());
		entity.setMember(memberRepository.findOne(wrapper.getMemberId()));
		return entity;
	}

	@Override
	public Long getNum() {
		return productRepository.count();
	}

	@Override
	public ProductMobileWrapper save(ProductMobileWrapper wrapper) throws Exception {
		ProductMobileWrapper savedProduct = toWrapper(productRepository.save(toEntity(wrapper)));
		return savedProduct;
	}

	@Override
	public ProductMobileWrapper getById(Long id) throws Exception {
		return toWrapper(productRepository.findOne(id));
	}

	@Override
	public Boolean delete(Long id) throws Exception {
		try {
			// customerRepository.delete(aLong); //should be soft-delete
			Product model = productRepository.findOne(id);
			model.setVersion(model.getVersion() + 1);
			model.setDeleted(1);
			productRepository.save(model);
			return true;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<ProductMobileWrapper> getAll() throws Exception {
		return toWrapperList((List<Product>) productRepository.findAll());
	}

	@Override
	public List<ProductMobileWrapper> findAll() {
		try {
			return toWrapperList(productRepository.findAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
