package id.org.test.data.service.menu;

import id.org.test.common.service.CommonService;
import id.org.test.data.service.menu.wrapper.LoggedUserWrapper;
import id.org.test.data.service.menu.wrapper.UserSecurityWrapper;

public interface UserService extends CommonService<UserSecurityWrapper, String> {

	String getRoleByUsername(String username);

	LoggedUserWrapper getByUsername(String username);
}
