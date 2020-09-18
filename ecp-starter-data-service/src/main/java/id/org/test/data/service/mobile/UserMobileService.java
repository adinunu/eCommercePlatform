package id.org.test.data.service.mobile;

import id.org.test.data.service.mobile.wrapper.UserMobileWrapper;

public interface UserMobileService {

	UserMobileWrapper getByUsername(String username);

}
