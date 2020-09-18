package id.org.test.ms.shared.auth.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import id.org.test.common.service.CommonService;
import id.org.test.ms.shared.auth.UserAccountWrapper;

public interface UserAccountService extends CommonService<UserAccountWrapper, Long> {

    Page<UserAccountWrapper> getPageableList(String param, int startPage, int pageSize, Sort sort) throws Exception;

    UserAccountWrapper getByUsername(String username) throws Exception;
}
