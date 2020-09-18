package id.org.test.data.service.mobile.impl;

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
import id.org.test.data.service.mobile.ProductMobileService;
import id.org.test.data.service.mobile.wrapper.ProductMobileWrapper;

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
	private final MemberRepository accountRepository;

	@Autowired
	public ProductMobileServiceImpl(ProductRepository productRepository,
			CategoryRepository categoryRepository,
			MemberRepository accountRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.accountRepository = accountRepository;
	}

	private ProductMobileWrapper toWrapper(Product model) throws Exception {
		ProductMobileWrapper wrapper = new ProductMobileWrapper();
		wrapper.setId(model.getId());

		if (model.getProductCategory() != null) {
			wrapper.setCategoryId(model.getProductCategory().getId());
			wrapper.setCategoryName(model.getProductCategory().getCategoryName());
		}

		wrapper.setProductName(model.getProductName());
		wrapper.setProductHandle(model.getProductHandle());
		wrapper.setSupplyPrice(model.getSupplyPrice());
		wrapper.setRetailPrice(model.getRetailPrice());
		wrapper.setStatus(String.valueOf(model.getGeneralStatus()));
		if(null == model.getImage() ||  model.getImage().isEmpty()) {
			wrapper.setImage("");
		}else {
//			wrapper.setImage("http://"+model.getAccount().getBusinessSubDomain()+".dev-pst.com:9090"+"/uploaded-files/product-images/"+model.getImage());
			wrapper.setImage(cdnUrl.concat("/" + productBucketPath)
					.concat("/" + String.valueOf(model.getAccount().getId()) + "/").concat(model.getImage()));
		}
		
		if (model.getProductName() != "" || model.getProductName() != null) {
			int number = countWords(model.getProductName().trim());
			String productInitials = printInitials(model.getProductName(), number);
			wrapper.setProductInitials(productInitials.toUpperCase().substring(0, 2));
		}
		return wrapper;
	}
	
	private List<ProductMobileWrapper> toWrapperList(List<Product> entityList)throws Exception {
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
	public List<ProductMobileWrapper> getListByAccount(Long accountId) throws Exception{
		return toWrapperList(productRepository.getActiveListByAccount(accountId));
	}
	
	@Override
    public List<ProductMobileWrapper> getPageableListByAccount(Long accountId,Long categoryId,String status, String param, int startPage, int pageSize, Sort sort) throws Exception {
        logger.info("Account Id : " + accountId);
        Page<Product> productPage = null;
        int page = DataTableObject.getPageFromStartAndLength(startPage, pageSize);
        if (accountId != null)	 {
        	if(status != "") {
        		if(categoryId != null) {
        			productPage = productRepository.getPageableListByAccountActiveAndCategoryAndStatus(accountId,categoryId,GeneralStatus.valueOf(status), param, new PageRequest(page, pageSize, sort));
        		}else {
        			productPage = productRepository.getPageableListByAccountAndStatus(accountId,GeneralStatus.valueOf(status), param, new PageRequest(page, pageSize, sort));
        		}
        	}else {
        		if(categoryId != null) {
        			productPage = productRepository.getPageableListByAccountAndCategory(accountId,categoryId, param, new PageRequest(page, pageSize, sort));
        		}else {
        			productPage = productRepository.getPageableListByAccountMob(accountId, param, new PageRequest(page, pageSize, sort));
        		}
        	}
        } else {
            if(param != null && !param.equalsIgnoreCase("")) {
                productPage = productRepository.getPageable(param, new PageRequest(page, pageSize, sort));
            }else{
                productPage = productRepository.getPageableNoSearch(new PageRequest(page, pageSize, sort));
            }
        }
        List<ProductMobileWrapper> wrapperList = toWrapperList(productPage.getContent());
        return wrapperList;
    }
	
	private ProductMobileWrapper toWrapperProductInventory(Product model, Long storeId) throws Exception {
		ProductMobileWrapper wrapper = new ProductMobileWrapper();
		wrapper.setId(model.getId());
		wrapper.setProductId(model.getId());

		if (model.getProductCategory() != null) {
			wrapper.setCategoryId(model.getProductCategory().getId());
			wrapper.setCategoryName(model.getProductCategory().getCategoryName());
		}

		wrapper.setProductName(model.getProductName());
		wrapper.setProductHandle(model.getProductHandle());
		if (model.getProductName() != "" || model.getProductName() != null) {
			int number = countWords(model.getProductName().trim());
			String productInitials = printInitials(model.getProductName(), number);
			wrapper.setProductInitials(productInitials.toUpperCase().substring(0, 2));
		}
		wrapper.setSupplyPrice(model.getSupplyPrice());
		wrapper.setRetailPrice(model.getRetailPrice());
		wrapper.setDescription(model.getDescription());
		wrapper.setStatus(String.valueOf(model.getGeneralStatus()));
		wrapper.setAccountId(model.getAccount().getId());
		if(null == model.getImage() ||  model.getImage().isEmpty()) {
			wrapper.setImage("");
		}else {
//			wrapper.setImage("http://"+model.getAccount().getBusinessSubDomain()+".dev-pst.com:9090"+"/uploaded-files/product-images/"+model.getImage());
			wrapper.setImage(cdnUrl.concat("/" + productBucketPath)
					.concat("/" + String.valueOf(model.getAccount().getId()) + "/").concat(model.getImage()));
		}
		return wrapper;
	}
	
	private List<ProductMobileWrapper> toWrapperProductInventoryList(List<Product> entityList, Long storeId) throws Exception {
		List<ProductMobileWrapper> wrapperList = new ArrayList<>();
		if (entityList != null && entityList.size() > 0) {
			// wrapperList = new ArrayList<>();
			for (Product temp : entityList) {
				wrapperList.add(toWrapperProductInventory(temp, storeId));
			}
		}
		return wrapperList;
	}
	
	@Override
    public List<ProductMobileWrapper> getPageableListByAccountAndStore(Long accountId, Long storeId, Long categoryId, String status, String param, int startPage, int pageSize, Sort sort) throws Exception {
		logger.info("Account Id : " + accountId);
		Page<Product> productPage = null;
		
		int page = DataTableObject.getPageFromStartAndLength(startPage, pageSize);
		if (accountId != null)	 {
        	if(status != "") {
        		if(categoryId != null) {
        			productPage = productRepository.getPageableListByAccountActiveAndCategoryAndStatus(accountId,categoryId,GeneralStatus.valueOf(status), param, new PageRequest(page, pageSize, sort));
        		}else {
        			productPage = productRepository.getPageableListByAccountAndStatus(accountId,GeneralStatus.valueOf(status), param, new PageRequest(page, pageSize, sort));
        		}
        	}else {
        		if(categoryId != null) {
        			productPage = productRepository.getPageableListByAccountAndCategory(accountId,categoryId, param, new PageRequest(page, pageSize, sort));
        		}else {
        			productPage = productRepository.getPageableListByAccountMob(accountId, param, new PageRequest(page, pageSize, sort));
        		}
        	}
        } else {
            if(param != null && !param.equalsIgnoreCase("")) {
                productPage = productRepository.getPageable(param, new PageRequest(page, pageSize, sort));
            }else{
                productPage = productRepository.getPageableNoSearch(new PageRequest(page, pageSize, sort));
            }
        }
		return toWrapperProductInventoryList(productPage.getContent(), storeId);
	}
	
	private Product toEntity(ProductMobileWrapper wrapper) throws Exception {
        Product entity = new Product();
        if (wrapper.getId() != null) {
            entity = productRepository.findOne(wrapper.getId());
            entity.setVersion(entity.getVersion() + 1L);
        }else{
            entity.setVersion(1L);
            entity.setDeleted(0);
        }
        if (wrapper.getCategoryId() != null) {
            entity.setProductCategory(categoryRepository.findOne(wrapper.getCategoryId()));
        }

        entity.setProductName(wrapper.getProductName());
        entity.setProductHandle(wrapper.getProductHandle());
        entity.setSupplyPrice(wrapper.getSupplyPrice());
        entity.setRetailPrice(wrapper.getRetailPrice());
        entity.setGeneralStatus(GeneralStatus.valueOf(wrapper.getStatus()));
        entity.setAccount(accountRepository.findOne(wrapper.getAccountId()));
//        entity.setImage(wrapper.getImage()); commented. Mobile doesn't change image
        entity.setTrackInventoryFlag(wrapper.getTrackInventoryFlag());
        entity.setHasVariant(false);
        entity.setDisplayFlag(true);
        entity.setComposite(false);
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
	
	public static String printInitials(String name, int number) {
		String result = "";
		if (name.length() == 0)
			return result;
		if (number < 2) {
			result = name.substring(0, 2);
		} else {
			result = Character.toString(name.charAt(0));
			for (int i = 1; i < name.length() - 1; i++)
				if (name.charAt(i) == ' ')
					result += Character.toString(name.charAt(i + 1));
		}
		return result;
	}

	public static int countWords(String str) {
		int count = 0;
		if (!(" ".equals(str.substring(0, 1))) || !(" ".equals(str.substring(str.length() - 1)))) {
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == ' ') {
					count++;
				}
			}
			count = count + 1;
		}
		return count;
	}

	@Override
	public Boolean updateStatusBoolean(Long id, String status) throws Exception {
		try {
			Product model = productRepository.findOne(id);
			if(status.equals("ACTIVE")) {
				model.setGeneralStatus(GeneralStatus.ACTIVE);
			}else {
				model.setGeneralStatus(GeneralStatus.INACTIVE);
			}
			productRepository.save(model);
			return true;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
}
