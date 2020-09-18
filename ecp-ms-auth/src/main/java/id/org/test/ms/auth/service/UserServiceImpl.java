package id.org.test.ms.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.dsl.BooleanExpression;

import id.org.test.common.constant.AppConstant;
import id.org.test.common.web.DataTableObject;
import id.org.test.ms.auth.domain.QUser;
import id.org.test.ms.auth.domain.User;
import id.org.test.ms.auth.repository.UserRepository;
import id.org.test.ms.shared.auth.LoggedUserWrapper;
import id.org.test.ms.shared.auth.UserAdminDTO;
import id.org.test.ms.shared.auth.UserSecurityWrapper;
import id.org.test.ms.shared.auth.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository usersRepository;
	private ModelMapper mapper;
	private PasswordEncoder encoder;

	@Autowired
	public UserServiceImpl(UserRepository usersRepository, ModelMapper mapper) {
		this.usersRepository = usersRepository;
		encoder = new BCryptPasswordEncoder();
		this.mapper = mapper;
	}

	private User toEntity(UserSecurityWrapper wrapper) {
		User model = new User();
		User temp = usersRepository.findByUsername(wrapper.getUsername()).get();
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
	public UserSecurityWrapper getById(Long aLong) throws Exception {
		return toWrapper(usersRepository.findOne(aLong));
	}

	@Override
	public Boolean delete(Long PK) throws Exception {
		try {
			LOG.info("do Nothing");
			return true;
		} catch (Exception e) {
			LOG.error("Fail delete", e);
			return false;
		}
	}

	@Override
	public List<UserSecurityWrapper> getAll() throws Exception {
		return toWrapperList((List<User>) usersRepository.findAll());
	}

	@Override
	public Page<UserSecurityWrapper> getPageableList(String param, int startPage, int pageSize, Sort sort)
			throws Exception {
		int page = DataTableObject.getPageFromStartAndLength(startPage, pageSize);
		BooleanExpression predicate = QUser.user.username.likeIgnoreCase("%" + param + "%");
		Page<User> customerPage = usersRepository.findAll(predicate, new PageRequest(page, pageSize, sort));
		List<UserSecurityWrapper> wrapperList = toWrapperList(customerPage.getContent());
		return new PageImpl<>(wrapperList, new PageRequest(page, pageSize, sort), customerPage.getTotalElements());
	}

	@Override
	public String getRoleByUsername(String username) {
		User user = usersRepository.findByUsername(username).get();
		if (null != user)
			return user.getRole();
		return null;
	}

	@Override
	public LoggedUserWrapper getByUsername(String username) {
		return toLoggedUserWrapper(usersRepository.findByUsername(username).get());
	}

}
