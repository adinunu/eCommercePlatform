package id.org.test.ms.shared.auth.feign;

import static id.org.test.common.constant.AppConstant.HttpHeader.CONTENT_TYPE_JSON;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import feign.Headers;
import id.org.test.ms.shared.auth.UserAddDTO;
import id.org.test.ms.shared.auth.UserDTO;
import id.org.test.ms.shared.auth.UserRole;

public interface UserFeign {

	@PostMapping("/user/add/member")
	@Headers({ CONTENT_TYPE_JSON })
	Object addUserMember(@RequestBody UserAddDTO udto,
			@RequestParam(name = "encpass", required = false) boolean encpass);

	@PostMapping("/user/update/role")
	@Headers({ CONTENT_TYPE_JSON })
	Object updateRole(@RequestParam("username") String username, @RequestParam("role") UserRole role);

	@PostMapping("/user/findByUsername")
	@Headers({ CONTENT_TYPE_JSON })
	UserDTO findUserByUsername(@RequestParam("username") String username);
}
