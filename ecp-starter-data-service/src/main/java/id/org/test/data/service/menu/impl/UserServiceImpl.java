package id.org.test.data.service.menu.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import id.org.test.data.model.User;
import id.org.test.data.repository.AuthorityRepository;
import id.org.test.data.repository.UsersRepository;
import id.org.test.data.service.menu.UserService;
import id.org.test.data.service.menu.wrapper.LoggedUserWrapper;
import id.org.test.data.service.menu.wrapper.UserSecurityWrapper;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UsersRepository usersRepository;
	private final AuthorityRepository authorityRepository;
	private PasswordEncoder encoder;

	@Autowired
	public UserServiceImpl(UsersRepository usersRepository, AuthorityRepository authorityRepository) {
		this.usersRepository = usersRepository;
		this.authorityRepository = authorityRepository;
		encoder = new BCryptPasswordEncoder();
	}

	private User toEntity(UserSecurityWrapper wrapper) {
		User model = new User();
		User temp = usersRepository.findOne(wrapper.getUsername());
		if (temp != null) {
			model = temp;
		}
		if (wrapper.getPassword() != null) {
			model.setPassword(encoder.encode(wrapper.getPassword()));
		}
		model.setEnabled(wrapper.getEnabled());
		return model;
	}

	private UserSecurityWrapper toWrapper(User user) {
		UserSecurityWrapper wrapper = new UserSecurityWrapper();
		wrapper.setUsername(user.getUsername());
//        wrapper.setUsername(user.getUsername());
		wrapper.setEnabled(user.isEnabled());
		return wrapper;
	}

	private LoggedUserWrapper toLoggedUserWrapper(User model) {
		LoggedUserWrapper wrapper = new LoggedUserWrapper();
		if (model != null) {
			wrapper.setUsername(model.getUsername());
			wrapper.setRole(model.getRole());
		}
		return wrapper;
	}

	private List<UserSecurityWrapper> toWrapperList(List<User> modelList) {
		List<UserSecurityWrapper> rList = new ArrayList<>();
		if (modelList != null && modelList.size() > 0) {
			for (User temp : modelList) {
				rList.add(toWrapper(temp));
			}
		}
		return rList;
	}

	@Override
	public Long getNum() {
		return usersRepository.count();
	}

	@Override
	public UserSecurityWrapper save(UserSecurityWrapper wrapper) throws Exception {
		return toWrapper(usersRepository.save(toEntity(wrapper)));
	}

	@Override
	public UserSecurityWrapper getById(String aLong) throws Exception {
		logger.info("Object user have string as id");
		return toWrapper(usersRepository.findOne(aLong));
	}

	@Override
	public Boolean delete(String PK) throws Exception {
		try {
			logger.info("do Nothing");
			return true;
		} catch (Exception e) {
			logger.error("Fail delete", e);
			return false;
		}
	}

	@Override
	public List<UserSecurityWrapper> getAll() throws Exception {
		return toWrapperList((List<User>) usersRepository.findAll());
	}

	@Override
	public String getRoleByUsername(String username) {
		return usersRepository.getRoleByUser(username);
	}

	@Override
	public LoggedUserWrapper getByUsername(String username) {
		return toLoggedUserWrapper(usersRepository.getByUserName(username));
	}
}
