package id.org.test.data.service.inventory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;

import id.org.test.data.model.Category;
import id.org.test.data.model.QCategory;
import id.org.test.data.repository.MemberRepository;
import id.org.test.data.repository.CategoryRepository;
import id.org.test.data.service.inventory.CategoryService;
import id.org.test.data.service.inventory.wrapper.CategoryWrapper;

@Service
public class CategoryServiceImpl implements CategoryService {

	private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

	private final CategoryRepository categoryRepository;
	private final MemberRepository accountRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository, 
			MemberRepository accountRepository) {
		this.categoryRepository = categoryRepository;
		this.accountRepository = accountRepository;
	}
	
	@Override
	public Optional<CategoryWrapper> getByIdAndAccountId(Long id, Long accountId) throws Exception {
		
		CategoryWrapper category = null;
		Optional<Category> octg = categoryRepository.findByIdAndAccountId(id, accountId);
		if(octg.isPresent())
			category = toWrapper(octg.get());
			
		return Optional.ofNullable(category);
	}

	private Category toEntity(CategoryWrapper wrapper) throws Exception {
		Category model = new Category();
		
		if (wrapper.getId() != null) {
			model = categoryRepository.findOne(wrapper.getId()); // edit mode
		}
		model.setCategoryName(wrapper.getCategoryName());
		model.setDescription(wrapper.getDescription());
		if (wrapper.getAccountId() != null) {
			model.setAccount(accountRepository.findOne(wrapper.getAccountId()));
		} else {
			model.setAccount(null);
		}
		return model;
	}

	private CategoryWrapper toWrapper(Category entity) {
		CategoryWrapper wrapper = new CategoryWrapper();
		if (entity != null) {
			wrapper.setId(entity.getId());
			wrapper.setDescription(entity.getDescription());
			wrapper.setCreatedBy(entity.getCreatedBy());
			wrapper.setCreatedDate(entity.getCreatedDate());
			wrapper.setUpdatedBy(entity.getUpdatedBy());
			wrapper.setUpdatedDate(entity.getUpdatedDate());
			wrapper.setDeleted(entity.getDeleted());

			wrapper.setCategoryName(entity.getCategoryName());

			if (entity.getAccount() != null) {
				wrapper.setAccountId(entity.getAccount().getId());
			} else {
				wrapper.setAccountId(null);
			}
		}
		return wrapper;
	}

	private List<CategoryWrapper> toWrapperList(List<Category> entityList) {
		List<CategoryWrapper> wrapperList = new ArrayList<>();
		if (entityList != null && entityList.size() > 0) {
			for (Category model : entityList) {
				wrapperList.add(toWrapper(model));
			}
		}
		return wrapperList;
	}

	@Override
	public Long getNum() {
		return categoryRepository.count();
	}

	@Override
	public CategoryWrapper save(CategoryWrapper wrapper) throws Exception {
		return toWrapper(categoryRepository.save(toEntity(wrapper)));
	}

	@Override
	public CategoryWrapper getById(Long aLong) throws Exception {
		return toWrapper(categoryRepository.findOne(aLong));
	}
	
	@Override
	public boolean delete(Long id, Long accountId) throws Exception {
		
		boolean deleted = false;
		
		try {

			Optional<Category> model = categoryRepository.findByIdAndAccountId(id, accountId);
			if(model.isPresent()) {
				Category ctg = model.get();
				ctg.setDeleted(1);
				categoryRepository.save(ctg);
				deleted = true;
			} else {
				throw new Exception("Catagegory id not found for current Account");
			}
				
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return deleted;
	}

	@Override
	public Boolean delete(Long aLong) throws Exception {
		try {

			// categoryRepository.delete(aLong); //should be soft-delete
			Category model = categoryRepository.findOne(aLong);
			model.setVersion(model.getVersion() + 1);
			model.setDeleted(1);
			categoryRepository.save(model);
			return true;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<CategoryWrapper> getAll() throws Exception {
		return toWrapperList((List<Category>) categoryRepository.findAll());
	}


	@Override
	public Long getNumFilteredByAccount(Long accountId) {
		return (long) categoryRepository.getNumFilteredByAccount(accountId).size();
	}

	@Override
	public List<CategoryWrapper> getListByAccount(Long aLong) {
		return toWrapperList(categoryRepository.getNumFilteredByAccount(aLong));
	}


	@Override
	public boolean createDefaultCategory(Long accountId) throws Exception{
		try {
			Category category = new Category();
			category.setDescription("default category");
			category.setCategoryName("");
			category.setAccount(accountRepository.findOne(accountId));
			categoryRepository.save(category);
			return true;
		}catch (Exception e){
			log.error("Fail createDefaultCategory", e);
			throw new Exception(e);
		}
	}

	@Override
	public void deleteAll() {
		categoryRepository.deleteAll();
	}
	
	@Override
	public Optional<CategoryWrapper> getCategoryByNameAndAccount(String categoryName, Long accountId) throws Exception {
		
		CategoryWrapper category = null;
		BooleanExpression predicate = QCategory.category.categoryName.equalsIgnoreCase(categoryName)
									.and(QCategory.category.account.id.eq(accountId));
		
		Category ctg = categoryRepository.findOne(predicate);
		if(null != ctg)
			category = toWrapper(ctg);
		
		return Optional.ofNullable(category);
	}
	
	@Override
	public Optional<CategoryWrapper> getDeletedCategoryByNameAndAccount(String categoryName, Long accountId) throws Exception {
		
		CategoryWrapper category = null;
		BooleanExpression predicate = QCategory.category.deleted.eq(1)
									.and(QCategory.category.categoryName.equalsIgnoreCase(categoryName))
									.and(QCategory.category.account.id.eq(accountId));
		
		Category ctg = categoryRepository.findOne(predicate);
		if(null != ctg)
			category = toWrapper(ctg);
		
		return Optional.ofNullable(category);
	}
	
	@Override
	public boolean isExist(String categoryName, Long accountId) throws Exception {
		
		BooleanExpression predicate = QCategory.category.deleted.eq(0)
									.and(QCategory.category.categoryName.equalsIgnoreCase(categoryName))
									.and(QCategory.category.account.id.eq(accountId));
		
		return categoryRepository.exists(predicate);
	}

	@Override
	public boolean checkExistingByName(String categoryName, Long accountId) throws Exception {
		try {
			Category category = categoryRepository.getByCategoryNameAndAccountId(categoryName, accountId);
			if(category==null){
				return false;
			} else{
				return true;
			}
		}catch (Exception e){

			log.error("Fail categoryIsExist", e);

			throw new Exception(e);
		}
	}

}
