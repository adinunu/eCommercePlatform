package id.org.test.data.service.mobile.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import id.org.test.data.model.User;
import id.org.test.data.repository.UserRepository;
import id.org.test.data.service.mobile.UserMobileService;
import id.org.test.data.service.mobile.wrapper.UserMobileWrapper;

@Service
@Transactional
public class UserMobileServiceImpl implements UserMobileService {

	private static final Logger logger = LoggerFactory.getLogger(UserMobileServiceImpl.class);
	private static final String ACCOUNT = "ROLE_ACCOUNT";
	private static final String EMPLOYEE = "ROLE_EMPLOYEE";
	private static final String CUSTOMER = "ROLE_CUSTOMER";
	private static final String CASHIER = "ROLE_CASHIER";
	private static final String PIC = "ROLE_PIC";

	private final UserRepository userRepository;

	@Autowired
	public UserMobileServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private UserMobileWrapper toWrapper(User model) {
		UserMobileWrapper wrapper = new UserMobileWrapper();
		if(model !=null){
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			if(model.getRole().equals(ACCOUNT)){
				authorities.add(new SimpleGrantedAuthority(ACCOUNT));
			}else if(model.getRole().equals(EMPLOYEE)){
				authorities.add(new SimpleGrantedAuthority(EMPLOYEE));
			}else if(model.getRole().equals(CASHIER)){
				authorities.add(new SimpleGrantedAuthority(CASHIER));
			}else if(model.getRole().equals(PIC)){
				authorities.add(new SimpleGrantedAuthority(PIC));
			}else{
				authorities.add(new SimpleGrantedAuthority(CUSTOMER));
			}
			
			wrapper.setUsername(model.getUsername());
			wrapper.setPassword(model.getPassword());
			wrapper.setRoles(authorities);
		}
		return wrapper;
	}

	private List<UserMobileWrapper> toWrapperList(List<User> entityList) {
		List<UserMobileWrapper> wrapperList = new ArrayList<>();
		if (entityList != null && entityList.size() > 0) {
			// wrapperList = new ArrayList<>();
			for (User temp : entityList) {
				wrapperList.add(toWrapper(temp));
			}
		}
		return wrapperList;
	}
	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public UserMobileWrapper getByUsername(String username) {
		return toWrapper(userRepository.getByUsername(username));
	}

}
