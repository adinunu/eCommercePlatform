package id.org.test.data.service.impl;

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
import id.org.test.data.service.CategoryService;
import id.org.test.data.service.wrapper.CategoryWrapper;

@Service
public class CategoryServiceImpl implements CategoryService {

	private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

	private final CategoryRepository categoryRepository;
	private final MemberRepository memberRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository, 
			MemberRepository memberRepository) {
		this.categoryRepository = categoryRepository;
		this.memberRepository = memberRepository;
	}
	
	@Override
	public Optional<CategoryWrapper> getByIdAndMemberId(Long id, Long memberId) throws Exception {
		
		CategoryWrapper category = null;
		Optional<Category> octg = categoryRepository.findByIdAndMemberId(id, memberId);
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
		if (wrapper.getMemberId() != null) {
			model.setMember(memberRepository.findOne(wrapper.getMemberId()));
		} else {
			model.setMember(null);
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

			if (entity.getMember() != null) {
				wrapper.setMemberId(entity.getMember().getId());
			} else {
				wrapper.setMemberId(null);
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
	public boolean delete(Long id, Long memberId) throws Exception {
		
		boolean deleted = false;
		
		try {

			Optional<Category> model = categoryRepository.findByIdAndMemberId(id, memberId);
			if(model.isPresent()) {
				Category ctg = model.get();
				ctg.setDeleted(1);
				categoryRepository.save(ctg);
				deleted = true;
			} else {
				throw new Exception("Catagegory id not found for current Member");
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
	public Long getNumFilteredByMember(Long memberId) {
		return (long) categoryRepository.getNumFilteredByMember(memberId).size();
	}

	@Override
	public List<CategoryWrapper> getListByMember(Long aLong) {
		return toWrapperList(categoryRepository.getNumFilteredByMember(aLong));
	}


	@Override
	public boolean createDefaultCategory(Long memberId) throws Exception{
		try {
			Category category = new Category();
			category.setDescription("default category");
			category.setCategoryName("");
			category.setMember(memberRepository.findOne(memberId));
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
	public Optional<CategoryWrapper> getCategoryByNameAndMember(String categoryName, Long memberId) throws Exception {
		
		CategoryWrapper category = null;
		BooleanExpression predicate = QCategory.category.categoryName.equalsIgnoreCase(categoryName)
									.and(QCategory.category.member.id.eq(memberId));
		
		Category ctg = categoryRepository.findOne(predicate);
		if(null != ctg)
			category = toWrapper(ctg);
		
		return Optional.ofNullable(category);
	}
	
	@Override
	public Optional<CategoryWrapper> getDeletedCategoryByNameAndMember(String categoryName, Long memberId) throws Exception {
		
		CategoryWrapper category = null;
		BooleanExpression predicate = QCategory.category.deleted.eq(1)
									.and(QCategory.category.categoryName.equalsIgnoreCase(categoryName))
									.and(QCategory.category.member.id.eq(memberId));
		
		Category ctg = categoryRepository.findOne(predicate);
		if(null != ctg)
			category = toWrapper(ctg);
		
		return Optional.ofNullable(category);
	}
	
	@Override
	public boolean isExist(String categoryName, Long memberId) throws Exception {
		
		BooleanExpression predicate = QCategory.category.deleted.eq(0)
									.and(QCategory.category.categoryName.equalsIgnoreCase(categoryName))
									.and(QCategory.category.member.id.eq(memberId));
		
		return categoryRepository.exists(predicate);
	}

	@Override
	public boolean checkExistingByName(String categoryName, Long memberId) throws Exception {
		try {
			Category category = categoryRepository.getByCategoryNameAndMemberId(categoryName, memberId);
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
