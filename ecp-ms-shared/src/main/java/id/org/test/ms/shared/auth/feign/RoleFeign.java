package id.org.test.ms.shared.auth.feign;

import static id.org.test.common.constant.AppConstant.HttpHeader.CONTENT_TYPE_JSON;

import org.springframework.web.bind.annotation.GetMapping;

import feign.Headers;

public interface RoleFeign {

	@GetMapping("/role/list")
	@Headers({ CONTENT_TYPE_JSON })
	Object getRoleList();

	@GetMapping("/role/list-admin")
	@Headers({ CONTENT_TYPE_JSON })
	Object getRoleListAdmin();

}
