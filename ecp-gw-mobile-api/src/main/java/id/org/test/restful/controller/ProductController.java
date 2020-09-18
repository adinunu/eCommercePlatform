package id.org.test.restful.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.org.test.common.util.ExceptionCustomMessageUtil;
import id.org.test.common.web.BaseController;
import id.org.test.common.web.HttpRequestParam;
import id.org.test.common.web.JsonMessage;
import id.org.test.data.service.mobile.ProductMobileService;
import id.org.test.data.service.mobile.wrapper.ProductMobileWrapper;
import id.org.test.data.service.organization.MemberService;
import id.org.test.restful.config.UserContext;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);
	
	private final ProductMobileService productMobileService;
	private final MessageSource messageSource;
    private final ExceptionCustomMessageUtil exceptionCustomMessageUtil;
    private final TokenStore tokenStore;
	
	@Autowired
	public ProductController(
			ProductMobileService productMobileService, 
			MemberService accountService,
			MessageSource messageSource,
			ExceptionCustomMessageUtil exceptionCustomMessageUtil,
			TokenStore tokenStore) {
		this.productMobileService = productMobileService;
		this.messageSource = messageSource;
		this.exceptionCustomMessageUtil = exceptionCustomMessageUtil;
		this.tokenStore = tokenStore;
	}
	
	@GetMapping(value = {"", "/list"})
	public Object get(OAuth2Authentication auth,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer startPage,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) String sortCol,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long storeId,
            @RequestParam(required = false) String status) {
		
		UserContext user = UserContext.build(auth, tokenStore);
		List<ProductMobileWrapper> productMobileWrapperList = null;
		HttpRequestParam param = new HttpRequestParam(startPage, pageSize, sortCol, sortDir).getRequestParam();
		
		try {
			for (GrantedAuthority role : user.getAuthorities()) {
				if(role.toString().equals(MEMBER)){
//					Long accountId = accountService.getAccountId(user.getUsername());
					Long accountId = user.getMemberId();
					if(storeId != null) {
						productMobileWrapperList = productMobileService.getPageableListByAccountAndStore(accountId,storeId, categoryId,status, search, param.getDisplayStart(), param.getPageSize(), param.getSort());
					}else {
						productMobileWrapperList = productMobileService.getPageableListByAccount(accountId,categoryId,status, search, param.getDisplayStart(), param.getPageSize(), param.getSort());
					}
				}
				break;
			}
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
//					Long accountId = accountService.getAccountId(user.getUsername());
					wrapper.setAccountId(user.getMemberId());
				}
				break;
			}
            productMobileService.save(wrapper);
        } catch (Exception e) {
            log.error("Fail /add", e);
            return JsonMessage.showErrorMessage(messageSource.getMessage("message.save.failed", new Object[]{}, locale), exceptionCustomMessageUtil.replaceMessage(e, locale));
        }
		return JsonMessage.showInfoMessage(messageSource.getMessage("message.save.succeed", new Object[]{}, locale));
	}
	
	@PostMapping("/delete")
	public Object delete(@RequestParam Long id, Locale locale, OAuth2Authentication auth) {
		try {
            productMobileService.delete(id);
        } catch (Exception e) {
            log.error("Fail /delete", e);
            return JsonMessage.showErrorMessage(messageSource.getMessage("message.save.failed", new Object[]{}, locale), exceptionCustomMessageUtil.replaceMessage(e, locale));
        }
		return JsonMessage.showInfoMessage(messageSource.getMessage("message.save.succeed", new Object[]{}, locale));
	}
	
	@PostMapping("/update_status")
	public Object updateStatus(@RequestParam(required = true) Long id,@RequestParam(required = true) String status, Locale locale, OAuth2Authentication auth) {
		try {
            productMobileService.updateStatusBoolean(id, status);
        } catch (Exception e) {
            log.error("Fail /update_status", e);
            return JsonMessage.showErrorMessage(messageSource.getMessage("message.save.failed", new Object[]{}, locale), exceptionCustomMessageUtil.replaceMessage(e, locale));
        }
		return JsonMessage.showInfoMessage(messageSource.getMessage("message.save.succeed", new Object[]{}, locale));
	}
	
	@PostMapping("/update")
	public Object update(@RequestBody ProductMobileWrapper wrapper, Locale locale, OAuth2Authentication auth) {
		UserContext user = UserContext.build(auth, tokenStore);
		try {
			ProductMobileWrapper oldData = productMobileService.getById(wrapper.getId());
			 for (GrantedAuthority role : user.getAuthorities()) {
 				if (role.toString().equals(MEMBER)) {
// 					Long accountId = accountService.getAccountId(user.getUsername());
 					wrapper.setAccountId(user.getMemberId());
 				}
 				break;
 			}
            if (oldData != null) {
            		oldData.setAccountId(wrapper.getAccountId());
                    oldData.setStatus(wrapper.getStatus());
                    oldData.setProductName(wrapper.getProductName());
                    oldData.setProductHandle(wrapper.getProductHandle());
                    oldData.setCategoryId(wrapper.getCategoryId());
//                    oldData.setImage(wrapper.getImage()); commented. mobile doesn't change image
                    oldData.setSupplyPrice(wrapper.getSupplyPrice());
                    oldData.setRetailPrice(wrapper.getRetailPrice());
                    oldData.setTrackInventoryFlag(wrapper.getTrackInventoryFlag());
                    
                    ProductMobileWrapper saveAction = productMobileService.save(oldData);
                    if (saveAction != null) {
                        return JsonMessage.showInfoMessage(messageSource.getMessage("message.save.succeed", new Object[]{}, locale));
                    } else {
                        return JsonMessage.showErrorMessage(messageSource.getMessage("message.save.failed", new Object[]{}, locale), "Error");
                    }
                }
        } catch (Exception e) {
            log.error("Fail /update", e);
            return JsonMessage.showErrorMessage(messageSource.getMessage("message.save.failed", new Object[]{}, locale), exceptionCustomMessageUtil.replaceMessage(e, locale));
        }
		return JsonMessage.showInfoMessage(messageSource.getMessage("message.save.succeed", new Object[]{}, locale));
	}
}
