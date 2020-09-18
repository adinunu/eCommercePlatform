package id.org.test.ms.shared.auth.feign;

import static id.org.test.common.constant.AppConstant.HttpHeader.CONTENT_TYPE_JSON;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import feign.Headers;
import id.org.test.ms.shared.auth.UserAddDTO;
import id.org.test.ms.shared.auth.UserDTO;
import id.org.test.ms.shared.auth.UserRole;
import id.org.test.ms.shared.auth.UserUpdPassDTO;

public interface UserFeign {

	@PostMapping("/user/add/account")
	@Headers({ CONTENT_TYPE_JSON })
	Object addUserAccount(@RequestBody UserAddDTO udto,
			@RequestParam(name = "encpass", required = false) boolean encpass);

	@PostMapping("/user/update/paswd")
	@Headers({ CONTENT_TYPE_JSON })
	Object updateUserPassword(@RequestBody UserUpdPassDTO udto,
			@RequestParam(name = "encpass", required = false) boolean encpass);

	@PostMapping("/user/update/role")
	@Headers({ CONTENT_TYPE_JSON })
	Object updateRole(@RequestParam("username") String username, @RequestParam("role") UserRole role);

	@PostMapping("/user/findByUsername")
	@Headers({ CONTENT_TYPE_JSON })
	UserDTO findUserByUsername(@RequestParam("username") String username);
}
