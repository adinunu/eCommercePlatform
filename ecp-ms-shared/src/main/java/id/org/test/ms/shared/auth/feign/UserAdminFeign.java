package id.org.test.ms.shared.auth.feign;

import static id.org.test.common.constant.AppConstant.HttpHeader.CONTENT_TYPE_JSON;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import feign.Headers;
import id.org.test.ms.shared.auth.UserAdminCDTO;

public interface UserAdminFeign {

	@PostMapping("/user-admin/add")
	@Headers({ CONTENT_TYPE_JSON })
	Object add(@RequestBody UserAdminCDTO dto);


}
