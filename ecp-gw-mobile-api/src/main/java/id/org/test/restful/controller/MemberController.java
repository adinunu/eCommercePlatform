package id.org.test.restful.controller;

import java.text.SimpleDateFormat;

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

import id.org.test.common.constant.Gender;
import id.org.test.common.web.BaseController;
import id.org.test.data.model.Member;
import id.org.test.data.repository.MemberRepository;
import id.org.test.data.service.MemberService;
import id.org.test.data.service.wrapper.MemberWrapper;
import id.org.test.ms.shared.mobile.MemberVDTO;
import id.org.test.restful.config.UserContext;

@RestController
@RequestMapping(value = "/member")
public class MemberController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(MemberController.class);

	private final MemberService memberService;
	private final MemberRepository memberRepository;
	private TokenStore tokenStore;

	@Autowired
	public MemberController(MemberService memberService, MemberRepository memberRepository, TokenStore tokenStore) {

		this.memberService = memberService;
		this.tokenStore = tokenStore;
		this.memberRepository = memberRepository;
	}

	@PostMapping("/update")
	public Object updateMember(@RequestBody MemberVDTO vdto, OAuth2Authentication auth) {
		UserContext user = UserContext.build(auth, tokenStore);
		Member member = null;
		try {
			member = memberRepository.findOne(user.getMemberId());
			if (null == member) {
				return buildResponseNotFound("Member not found");
			}
			member.setFirstName(vdto.getFirstName());
			member.setLastName(vdto.getLastName());
			member.setBirthDate(new SimpleDateFormat("dd-MM-yyyy").parse(vdto.getBirthDate()));
			if (vdto.getGender() != "" && vdto.getGender() != null) {
				member.setGender(Gender.valueOf(vdto.getGender()));
			}
			memberRepository.save(member);
			return ok(vdto);
		} catch (Exception e) {
			log.error("member/update " + "  ({})", vdto.getId());
			return buildResponseGeneralError(e.getMessage());
		}
	}

	@GetMapping("/info")
	public Object getMemberInfo(OAuth2Authentication auth) {
		UserContext user = UserContext.build(auth, tokenStore);
		try {
			MemberWrapper memberWrapper = memberService.getByUser(user.getUsername());
			if (null == memberWrapper) {
				return buildResponseNotFound("Member not found");
			}
			return ok(memberWrapper);
		} catch (Exception e) {
			log.error("Fail member/info  ({})", user.getMemberId());
			return buildResponseGeneralError(e.getMessage());
		}
	}

}
