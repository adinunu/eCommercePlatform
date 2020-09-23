package id.org.test.ms.shared.auth.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import id.org.test.common.service.CommonService;
import id.org.test.ms.shared.auth.UserSecurityWrapper;

public interface UserService extends CommonService<UserSecurityWrapper, Long> {

	Page<UserSecurityWrapper> getPageableList(String param, int startPage, int pageSize, Sort sort) throws Exception;

	String getRoleByUsername(String username);

}
