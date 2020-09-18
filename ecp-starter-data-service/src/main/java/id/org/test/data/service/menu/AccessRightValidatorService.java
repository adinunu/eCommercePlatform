package id.org.test.data.service.menu;

import id.org.test.data.service.menu.wrapper.LoggedUserWrapper;

public interface AccessRightValidatorService {

	LoggedUserWrapper getByUsername(String username);

	String getRoleByUser(String username);

}
