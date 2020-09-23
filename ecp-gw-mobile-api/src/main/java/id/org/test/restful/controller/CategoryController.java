package id.org.test.restful.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.dsl.BooleanExpression;

import id.org.test.common.web.BaseController;
import id.org.test.data.model.Category;
import id.org.test.data.model.Product;
import id.org.test.data.model.QCategory;
import id.org.test.data.model.QProduct;
import id.org.test.data.repository.CategoryRepository;
import id.org.test.data.repository.ProductRepository;
import id.org.test.data.service.CategoryService;
import id.org.test.data.service.MemberService;
import id.org.test.data.service.wrapper.CategoryWrapper;
import id.org.test.data.service.wrapper.MemberWrapper;
import id.org.test.ms.shared.mobile.CategoryCDTO;
import id.org.test.ms.shared.mobile.CategoryVDTO;
import id.org.test.restful.config.UserContext;
import id.org.test.restful.mapper.CategoryMapper;

@RestController
@RequestMapping(value = "/api/category")
public class CategoryController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

	private MemberService memberService;
	private CategoryMapper categoryMapper;
	private CategoryRepository categoryRepository;
	private CategoryService categoryService;
	private ProductRepository productRepository;
	private TokenStore tokenStore;
	
	
	public CategoryController(
			MemberService memberService, 
			CategoryMapper categoryMapper, 
			CategoryRepository categoryRepository,
			CategoryService categoryService, 
			ProductRepository productRepository, 
			TokenStore tokenStore) {

		this.memberService = memberService;
		this.categoryMapper = categoryMapper;
		this.categoryRepository = categoryRepository;
		this.categoryService = categoryService;
		this.productRepository = productRepository;
		this.tokenStore = tokenStore;
		
	}

	@PostMapping("/add")
	public Object transaction(OAuth2Authentication auth, Locale locale, @RequestBody CategoryCDTO dto) {
		UserContext user = UserContext.build(auth, tokenStore);
		CategoryVDTO category = new CategoryVDTO();
		Category saveAction = new Category();
		try {
			for (GrantedAuthority role : user.getAuthorities()) {
				if (role.toString().equals(MEMBER)) {
					MemberWrapper memberWrapper = memberService.getByUser(user.getUsername());
					dto.setMemberId(memberWrapper.getId());
				} 
				break;
			}
			Category categoryDelete = categoryRepository.getByCategoryNameAndMemberIdDelete(dto.getCategoryName().trim(),
					dto.getMemberId());
			if (categoryDelete != null) {
				Category oldData = categoryRepository.findOne(categoryDelete.getId());
				oldData.setCategoryName(dto.getCategoryName());
				oldData.setDescription(dto.getDescription());
				oldData.setDeleted(0);
				Category categoryUpdate = categoryRepository.save(oldData);
				category = categoryMapper.convertToDto(categoryUpdate);
				return buildResponseGeneralSuccess(category);
			}
			Category categoryNotDelete = categoryRepository.getByCategoryNameAndMemberId(dto.getCategoryName().trim(),
					dto.getMemberId());
			if (categoryNotDelete == null) {
				saveAction = categoryRepository.save(categoryMapper.createEntity(dto));
				category = categoryMapper.convertToDto(saveAction);
				return buildResponseGeneralSuccess(category);
			} else {
				return badRequest("Kategori ini sudah ada");
			}
		} catch (Exception e) {
			log.error("Fail /api/category/bank/add", e);
			return buildResponseGeneralError(e.getMessage());
		}
	}

	@GetMapping(value = { "", "/list" })
	public Object get(OAuth2Authentication auth) {
		UserContext userCtx = UserContext.build(auth, tokenStore);
		
		List<CategoryWrapper> categoryWrapperList = new ArrayList<>();
		try {
			for (GrantedAuthority role : userCtx.getAuthorities()) {
				if (role.toString().equals(MEMBER)) {
					categoryWrapperList = categoryService.getListByMember(userCtx.getMemberId());
				}
				break;
			}
		} catch (Exception e) {
			log.error("Fail /list", e);
			return buildResponseGeneralError(e.getMessage());
		}
		return categoryWrapperList;
	}

	@PostMapping("/list/paging/{page}/{size}")
	public Object listPaging(OAuth2Authentication auth, @PathVariable("page") int page,
			@PathVariable("size") int size, @RequestBody(required = false) CategoryWrapper search) {

		UserContext user = UserContext.build(auth, tokenStore);

		Pageable pr = new PageRequest(page, size, new Sort(Direction.DESC, "createdDate"));

		BooleanExpression predicate = QCategory.category.id.isNotNull();
		if (null != search) {
			String categoryName = search.getCategoryName();
			if (StringUtils.isNotEmpty(categoryName)) {
				predicate = predicate.and(QCategory.category.categoryName.likeIgnoreCase("%" + categoryName + "%"));
			}
		}

		try {
			for (GrantedAuthority role : user.getAuthorities()) {
				if (role.toString().equals(MEMBER)) {
					Long memberId = memberService.getMemberId(user.getUsername());

					predicate = predicate.and(QCategory.category.member.id.eq(memberId));

				}
			}
		} catch (Exception e) {
			log.error("Fail /api/category/list/paging/{page}/{size}", e);
			return buildResponseGeneralError(e.getMessage());
		}
		predicate = predicate.and(QCategory.category.deleted.eq(0));
		Page<Category> findAll = categoryRepository.findAll(predicate, pr);
		List<CategoryVDTO> content = categoryMapper.convertToDto(findAll.getContent());
		Page<CategoryVDTO> result = new PageImpl<>(content, pr, findAll.getTotalElements());
		return result;
	}

	@DeleteMapping("/delete/{id}")
	public Object delete(@PathVariable("id") Long id, Locale locale, OAuth2Authentication auth) {
		UserContext user = UserContext.build(auth, tokenStore);

		BooleanExpression predicateProduct = QProduct.product.productCategory.id.eq(id);
		predicateProduct = predicateProduct.and(QProduct.product.deleted.eq(BOOLEAN_FALSE));
		Iterable<Product> products = productRepository.findAll(predicateProduct);

		if (products.iterator().hasNext()) {
			return badRequest("Kategori ini sedang digunakan di salah satu produk");
		}
		BooleanExpression predicate = QCategory.category.id.isNotNull();
		try {
			for (GrantedAuthority role : user.getAuthorities()) {
				if (role.toString().equals(MEMBER)) {
				}
				break;
			}
			Category category = categoryRepository.findOne(id);
			if (category == null) {
				return buildResponseNotFound("Category not found");
			}
			Long memberId = memberService.getMemberId(user.getUsername());
			predicate = predicate.and(QCategory.category.member.id.eq(memberId));
			predicate = predicate.and(QCategory.category.deleted.eq(BOOLEAN_FALSE));
			category.setVersion(category.getVersion() + 1);
			category.setDeleted(1);
			categoryRepository.save(category);
		} catch (Exception e) {
			log.error("Fail /api/category/delete/{id}", e);
			return buildResponseGeneralError(e.getMessage());
		}
		Iterable<Category> findAll = categoryRepository.findAll(predicate);
		List<CategoryVDTO> content = categoryMapper.convertToDto(findAll);
		return buildResponseGeneralSuccess(content);
	}

	@PostMapping("/update/{id}")
	public Object update(@PathVariable("id") Long id, @RequestBody CategoryCDTO dto, Locale locale, OAuth2Authentication auth) {
		UserContext user = UserContext.build(auth, tokenStore);
		Category saveAction = new Category();
		CategoryVDTO category = new CategoryVDTO();
		BooleanExpression predicate = QCategory.category.id.isNotNull();

		try {
			for (GrantedAuthority role : user.getAuthorities()) {
				if (role.toString().equals(MEMBER)) {
					MemberWrapper memberWrapper = memberService.getByUser(user.getUsername());
					dto.setMemberId(memberWrapper.getId());
				}
				break;
			}

			Category oldData = categoryRepository.findOne(id);
			if (oldData == null) {
				return buildResponseNotFound("Category not found");
			}
			
			String categoryName = oldData.getCategoryName();

			oldData.setCategoryName(dto.getCategoryName().trim());
			oldData.setDescription(dto.getDescription());
			
			predicate = predicate.and(QCategory.category.categoryName.equalsIgnoreCase(dto.getCategoryName().trim()));
			predicate = predicate.and(QCategory.category.member.id.eq(dto.getMemberId()));
			Iterable<Category> categories = categoryRepository.findAll(predicate);
			Category categoriExist = new Category();
			for (Category categori : categories) {
				categoriExist = categori;
			}
			if (categoriExist.getMember() == null) {
				saveAction = categoryRepository.save(oldData);
				category = categoryMapper.convertToDto(saveAction);
			} else {
				if (categoriExist.getCategoryName().equalsIgnoreCase(categoryName)) {
					saveAction = categoryRepository.save(oldData);
					category = categoryMapper.convertToDto(saveAction);
				} else {
					return badRequest("Kategori Sudah ada");
				}
			}

		} catch (Exception e) {
			log.error("Fail /api/category/update/{id}", e);
			return buildResponseGeneralError(e.getMessage());
		}
		return buildResponseGeneralSuccess(category);
	}

}
