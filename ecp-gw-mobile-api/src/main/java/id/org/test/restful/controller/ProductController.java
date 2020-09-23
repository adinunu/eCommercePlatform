package id.org.test.restful.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.org.test.common.web.BaseController;
import id.org.test.data.service.MemberService;
import id.org.test.data.service.ProductMobileService;
import id.org.test.data.service.wrapper.ProductMobileWrapper;
import id.org.test.restful.config.UserContext;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	private final ProductMobileService productMobileService;
	private final TokenStore tokenStore;

	@Autowired
	public ProductController(ProductMobileService productMobileService, MemberService memberService,
			TokenStore tokenStore) {
		this.productMobileService = productMobileService;
		this.tokenStore = tokenStore;
	}

	@GetMapping(value = { "/{productId}" })
	public Object getById(@PathVariable("productId") Long productId) {
		ProductMobileWrapper wrapper = new ProductMobileWrapper();
		try {
			wrapper = productMobileService.getById(productId);
		} catch (Exception e) {
			log.error("Fail /getById", e);
			return buildResponseGeneralError(e.getMessage());
		}
		return wrapper;
	}

	@GetMapping(value = { "", "/list" })
	public Object get() {
		List<ProductMobileWrapper> productMobileWrapperList = null;

		try {
			productMobileWrapperList = productMobileService.findAll();
		} catch (Exception e) {
			log.error("Fail /list", e);
			return buildResponseGeneralError(e.getMessage());
		}
		return productMobileWrapperList;
	}

	@PostMapping("/add")
	public Object transaction(@RequestBody ProductMobileWrapper wrapper, Locale locale, OAuth2Authentication auth) {
		UserContext user = UserContext.build(auth, tokenStore);
		try {
			for (GrantedAuthority role : user.getAuthorities()) {
				if (role.toString().equals(MEMBER)) {
					wrapper.setMemberId(user.getMemberId());
				}
				break;
			}
			productMobileService.save(wrapper);
		} catch (Exception e) {
			log.error("Fail /add", e);
			return buildResponseGeneralError(e.getMessage());
		}
		return buildResponseGeneralSuccess(wrapper);
	}

	@PostMapping("/delete")
	public Object delete(@RequestParam Long id, Locale locale, OAuth2Authentication auth) {
		try {
			productMobileService.delete(id);
		} catch (Exception e) {
			log.error("Fail /delete", e);
			return buildResponseGeneralError(e.getMessage());
		}
		return buildResponseGeneralSuccess();
	}

	@PostMapping("/update")
	public Object update(@RequestBody ProductMobileWrapper wrapper, Locale locale, OAuth2Authentication auth) {
		UserContext user = UserContext.build(auth, tokenStore);
		try {
			ProductMobileWrapper oldData = productMobileService.getById(wrapper.getId());
			for (GrantedAuthority role : user.getAuthorities()) {
				if (role.toString().equals(MEMBER)) {
					wrapper.setMemberId(user.getMemberId());
				}
				break;
			}
			if (oldData != null) {
				oldData.setMemberId(wrapper.getMemberId());
				oldData.setStatus(wrapper.getStatus());
				oldData.setProductName(wrapper.getProductName());
				oldData.setProductCode(wrapper.getProductCode());
				oldData.setCategoryId(wrapper.getCategoryId());
				oldData.setProductPrice(wrapper.getProductPrice());

				ProductMobileWrapper saveAction = productMobileService.save(oldData);
				if (saveAction != null) {
					return buildResponseGeneralSuccess(saveAction);
				} else {
					return buildResponseGeneralError();
				}
			}
		} catch (Exception e) {
			log.error("Fail /update", e);
			return buildResponseGeneralError(e.getMessage());
		}
		return buildResponseGeneralSuccess();
	}
}
