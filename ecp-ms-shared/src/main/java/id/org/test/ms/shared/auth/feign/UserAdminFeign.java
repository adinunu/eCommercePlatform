package id.org.test.ms.shared.auth.feign;

import static id.org.test.common.constant.AppConstant.HttpHeader.CONTENT_TYPE_JSON;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import feign.Headers;
import id.org.test.ms.shared.auth.UserAdminCDTO;
import id.org.test.ms.shared.auth.UserAdminDTO;

public interface UserAdminFeign {

	@PostMapping("/user-admin/update-status")
	@Headers({ CONTENT_TYPE_JSON })
	Object updateStatus(@RequestParam("no") Long no, @RequestParam("status") Boolean status);

	@PostMapping("/user-admin/add")
	@Headers({ CONTENT_TYPE_JSON })
	Object add(@RequestBody UserAdminCDTO dto);

	@GetMapping("/user-admin/getById/{no}")
	@Headers({ CONTENT_TYPE_JSON })
	UserAdminDTO findByUserNo(@PathVariable("no") Long no);

	@PostMapping("/user-admin/update")
	@Headers({ CONTENT_TYPE_JSON })
	Object update(@RequestBody UserAdminDTO dto, @RequestParam(name = "encpass", required = false) boolean encpass);
	
	@PostMapping("/user-admin/updateProfile")
	@Headers({ CONTENT_TYPE_JSON })
	Object updateProfile(@RequestBody UserAdminDTO dto, @RequestParam(name = "encpass", required = false) boolean encpass);
	
	@PostMapping("/user-admin/checkEmail")
	@Headers({ CONTENT_TYPE_JSON })
	Boolean checkEmail(@RequestParam("email") String email);

}
