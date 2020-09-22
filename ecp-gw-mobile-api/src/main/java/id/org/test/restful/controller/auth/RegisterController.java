package id.org.test.restful.controller.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.org.test.common.web.BaseController;
import id.org.test.common.web.ResponseStatus;
import id.org.test.data.service.dto.RegisterDTO;
import id.org.test.data.service.organization.MemberService;
import id.org.test.data.service.organization.wrapper.MemberWrapper;
import id.org.test.ms.shared.auth.UserAddDTO;
import id.org.test.restful.feign.OAuthFeignClient;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/auth/registers")
@Slf4j
public class RegisterController extends BaseController {
	@Autowired
	private OAuthFeignClient oauthFC;
	@Autowired
	private MemberService memberService;

	@PostMapping("")
	public Object register(@RequestBody @Valid RegisterDTO registerDto) {
		try {

			MemberWrapper memberWrapper = new MemberWrapper();
			memberWrapper.setFirstName(registerDto.getFirstName());
			memberWrapper.setLastName(registerDto.getLastName());
			memberWrapper.setGender(registerDto.getGender());
			memberWrapper.setMobile(registerDto.getMobile());
			memberWrapper.setEmail(registerDto.getEmail());
			memberWrapper.setUsername(registerDto.getUsername());
			memberWrapper.setPassword(registerDto.getPassword());
			memberWrapper.setBirthDate(registerDto.getBirthDate());
			memberWrapper.setEnabled(true);

			MemberWrapper memberSave = memberService.save(memberWrapper);

			UserAddDTO userAddDTO = new UserAddDTO();
			userAddDTO.setMemberId(memberSave.getId());
			userAddDTO.setEmail(registerDto.getEmail());
			userAddDTO.setMobile(registerDto.getMobile());
			userAddDTO.setPassword(registerDto.getPassword());
			userAddDTO.setUsername(registerDto.getUsername());

			Object createUser = oauthFC.addUserMember(userAddDTO, true);
			if (createUser != null) {
				try {
						return buildResponse(ResponseStatus.USER_CREATED, HttpStatus.OK);
				} catch (Exception e) {
					log.error("Fail /register ", e.getMessage());
				}
			}
		} catch (Exception e) {
			return buildResponseGeneralError(e.getMessage());
		}
		return buildResponse(ResponseStatus.AUTHORIZATION_FAIL, HttpStatus.UNAUTHORIZED);
	}

}
