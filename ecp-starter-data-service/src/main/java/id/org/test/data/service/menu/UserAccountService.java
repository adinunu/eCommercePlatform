package id.org.test.data.service.menu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import id.org.test.common.service.CommonService;
import id.org.test.data.service.menu.wrapper.UserAccountWrapper;

public interface UserAccountService extends CommonService<UserAccountWrapper, Long> {

    UserAccountWrapper getByUsername(String username) throws Exception;
}
