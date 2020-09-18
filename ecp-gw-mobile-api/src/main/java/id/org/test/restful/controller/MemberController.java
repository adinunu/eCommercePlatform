package id.org.test.restful.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.org.test.common.web.BaseController;
import id.org.test.data.model.Member;
import id.org.test.data.model.PublicRegistrationActivation;
import id.org.test.data.repository.MemberRepository;
import id.org.test.data.service.organization.MemberService;
import id.org.test.data.service.organization.wrapper.MemberWrapper;
import id.org.test.data.service.registration.PublicRegistrationActivationService;
import id.org.test.ms.shared.mobile.AccountVDTO;
import id.org.test.restful.config.UserContext;
import id.org.test.restful.feign.OAuthFeignClient;

@RestController
@RequestMapping(value = "/member")
public class MemberController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(MemberController.class);

	private final MemberService memberService;
	private final OAuthFeignClient oauthFc;
	private final PublicRegistrationActivationService parService;
	private final MemberRepository memberRepository;
	private TokenStore tokenStore;

	@Autowired
	public MemberController(MemberService memberService, OAuthFeignClient oauthFc,
			PublicRegistrationActivationService parService, MemberRepository memberRepository,
			TokenStore tokenStore) {

		this.memberService = memberService;
		this.oauthFc = oauthFc;
		this.tokenStore = tokenStore;
		this.parService = parService;
		this.memberRepository = memberRepository;
	}

	@PostMapping("/update")
	public Object updateAccount(@RequestBody AccountVDTO vdto, OAuth2Authentication auth) {
		UserContext user = UserContext.build(auth, tokenStore);
		Member account = null;
		String lastName = "";
		try {
			account = memberRepository.findOne(user.getMemberId());
			if (null == account) {
				return buildResponseNotFound("Account not found");
			}
			String fullName = vdto.getFullName();
			String[] splitName = fullName.split("\\s+");
			lastName = fullName.replace(splitName[0] + " ", "");

			if (fullName.equals(splitName[0]))
				lastName = "";

			account.setFirstName(splitName[0]);
			account.setLastName(lastName);
			memberRepository.save(account);
			return ok(vdto);
		} catch (Exception e) {
			log.error("account/update " + "  ({})", vdto.getId());
			return buildResponseGeneralError(e.getMessage());
		}
	}

	@GetMapping("/info")
	public Object getAccountInfo(OAuth2Authentication auth) {
		UserContext user = UserContext.build(auth, tokenStore);
		AccountVDTO vdto = new AccountVDTO();
		try {
			Member account = memberRepository.findOne(user.getMemberId());
			if (null == account) {
				return buildResponseNotFound("Account not found");
			}
			vdto.setId(account.getId());
			vdto.setFullName(account.getFirstName() + " " + account.getLastName());
			vdto.setEmail(account.getEmail());
			return ok(vdto);
		} catch (Exception e) {
			log.error("Fail account/info  ({})", user.getMemberId());
			return buildResponseGeneralError(e.getMessage());
		}
	}

	@Deprecated
	@GetMapping("/ping")
	public Object ping() {
		return oauthFc.ping();
	}

	@PostMapping("/validateBusinessName")
	public Map<String, Object> validateBusinessName(@RequestBody Map<String, Object> mapRequest) throws Exception {

		String businessName = (String) mapRequest.get("businessName");
		log.info("validateBusinessName({})", businessName);
		Map<String, Object> mapResult = new HashMap<>();
		if (businessName != null && !businessName.trim().equals("")) {
			PublicRegistrationActivation par = parService.getByBusinessSubDomain(businessName);
			if (par != null && par.getId() != null) {
				MemberWrapper accountWrapper = new MemberWrapper();
				mapResult.put("result", true);
				mapResult.put("businessName", par.getBusinessName());
				mapResult.put("subDomain", par.getBusinessSubDomain());
				mapResult.put("model", accountWrapper);
			} else {
				mapResult.put("result", false);
				mapResult.put("reason", "No Such Business Name");
			}
		} else {
			mapResult.put("result", false);
			mapResult.put("reason", "Business Name is empty or null");
		}
		return mapResult;
	}

	@Deprecated
	@PostMapping("/validateEmail")
	public Map<String, Object> validateEmail(@RequestBody Map<String, Object> mapRequest) throws Exception {
		String email = (String) mapRequest.get("email");
		log.info("validateEmail({})", email);
		Map<String, Object> mapResult = new HashMap<>();
		if (email != null && !email.trim().equals("")) {
			MemberWrapper accountWrapper = memberService.getByEmailAddress(email);
			if (accountWrapper != null && accountWrapper.getId() != null) {
				if (accountWrapper.isEnabled()) {
					mapResult.put("result", true);
					mapResult.put("reason", "Email sudah terdaftar");
				} else {
					mapResult.put("result", false);
					mapResult.put("reason", "Email belum aktivasi");
				}
//            }else{
//            	PublicRegistrationActivation par = parService.getByMailRegistrant(email);
//            	if(par!=null){
//            		parService.delete(par.getId());
//            	}
//                mapResult.put("result",false);
//                mapResult.put("reason","Email belum terdaftar");
			}
		} else {
			mapResult.put("result", false);
			mapResult.put("reason", "Email is empty or null");
		}
		return mapResult;
	}
}
