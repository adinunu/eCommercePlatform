package id.org.test.ms.shared.auth.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import feign.Headers;
import id.org.test.ms.shared.auth.UserAddDTO;

public interface AuthFeign {

	@PostMapping("/oauth/token/kick")
	@Headers({ "Content-Type:application/x-www-form-urlencoded" })
	Object kickToken(@RequestHeader("Authorization") String authorizationBasic,
			@RequestParam("username") String username);

	@PostMapping("/user/add/admin")
	@Headers({ "Content-Type:application/json;charset=utf-8" })
	Object addUserAdmin(@RequestBody UserAddDTO udto);

	@PostMapping("/user/add/member")
	@Headers({ "Content-Type:application/json;charset=utf-8" })
	Object addUserMember(@RequestBody UserAddDTO udto,
			@RequestParam(name = "encpass", required = false) boolean encpass);

	@PostMapping("/oauth/token/revoke")
	@Headers({ "Content-Type:application/x-www-form-urlencoded" })
	Object logout(@RequestParam("token") String token);

	@PostMapping("/oauth/token")
	@Headers({ "Content-Type:application/x-www-form-urlencoded" })
	Object login(@RequestHeader("Authorization") String authorizationBasic, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("grant_type") String grant_type);

	@PostMapping("/oauth/check_token")
	@Headers({ "Content-Type:application/x-www-form-urlencoded" })
	Object checkToken(@RequestHeader("Authorization") String authorizationBasic, @RequestParam("token") String token);

	@PostMapping("/oauth/token")
	@Headers({ "Content-Type:application/x-www-form-urlencoded" })
	Object refreshToken(@RequestHeader("Authorization") String authorizationBasic,
			@RequestParam("refresh_token") String refresh_token, @RequestParam("grant_type") String grant_type);

	@PostMapping("/oauth/is-login")
	@Headers({ "Content-Type:application/x-www-form-urlencoded" })
	Object isLoggedIn(@RequestHeader("Authorization") String authorizationBasic,
			@RequestParam("username") String username);

	@GetMapping("/oauth/ping")
	Object ping();

}
