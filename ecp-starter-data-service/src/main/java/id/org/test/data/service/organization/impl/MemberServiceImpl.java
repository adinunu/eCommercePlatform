package id.org.test.data.service.organization.impl;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;

import id.org.test.common.constant.Gender;
import id.org.test.common.util.RandomString;
import id.org.test.common.web.DataTableObject;
import id.org.test.data.model.Member;
import id.org.test.data.model.PublicRegistrationActivation;
import id.org.test.data.model.QMember;
import id.org.test.data.model.User;
import id.org.test.data.projection.MemberProj;
import id.org.test.data.repository.MemberRepository;
import id.org.test.data.repository.UsersRepository;
import id.org.test.data.service.inventory.CategoryService;
import id.org.test.data.service.organization.MemberService;
import id.org.test.data.service.organization.wrapper.MemberWrapper;
import id.org.test.data.service.registration.PublicRegistrationActivationService;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

	private final UsersRepository usersRepository;
	private final MemberRepository accountRepository;
	private final CategoryService categoryService;
	private final PublicRegistrationActivationService registrationActivationService;
	private JdbcUserDetailsManager jdbcUserDetailsManager;
	private DataSource dataSource;

	@Autowired
	public MemberServiceImpl(DataSource dataSource, UsersRepository usersRepository, MemberRepository accountRepository,
			CategoryService categoryService, PublicRegistrationActivationService registrationActivationService) {
		this.dataSource = dataSource;
		this.usersRepository = usersRepository;
		this.accountRepository = accountRepository;
		this.categoryService = categoryService;
		this.registrationActivationService = registrationActivationService;
		if (jdbcUserDetailsManager == null) {
			jdbcUserDetailsManager = new JdbcUserDetailsManager();
		}
		this.jdbcUserDetailsManager.setDataSource(this.dataSource);
	}

	@Override
	public Long getAccountId(String username) {
		logger.debug("getAccountId({})", username);
		MemberProj account = accountRepository.findByUserUsername(username);
		return null != account.getId() ? account.getId() : null;
	}

	private Member toEntity(MemberWrapper wrapper) throws Exception {
		Member entity = new Member();
		if (wrapper.getId() != null) {
			entity = accountRepository.findOne(wrapper.getId());
			entity.setVersion(entity.getVersion() + 1);
		}
		entity.setFirstName(wrapper.getFirstName());
		entity.setLastName(wrapper.getLastName());
		if (wrapper.getBirthDate() != "" && wrapper.getBirthDate() != null) {
			entity.setBirthDate(new SimpleDateFormat("dd-MM-yyyy").parse(wrapper.getBirthDate()));
		}

		if (wrapper.getGender() != "" && wrapper.getGender() != null) {
			entity.setGender(Gender.valueOf(wrapper.getGender()));
		}
		entity.setMobile(wrapper.getMobile());
		entity.setEmail(wrapper.getEmail());

		if (wrapper.getUsername() != null) {
			entity.setUser(usersRepository.findOne(wrapper.getUsername()));
		}
		entity.setDescription(wrapper.getDescription());
		return entity;
	}

	private MemberWrapper toWrapper(Member model) {
		MemberWrapper wrapper = new MemberWrapper();
		if (model != null) {
			wrapper.setFirstName(model.getFirstName());
			wrapper.setLastName(model.getLastName());
			if (model.getBirthDate() != null) {
				wrapper.setBirthDate(new SimpleDateFormat("dd-MM-yyyy").format(model.getBirthDate()));
			}
			if (null != model.getGender()) {
				wrapper.setGender(String.valueOf(model.getGender()));
			} else {
				wrapper.setGender(Gender.MALE.name());
			}

			wrapper.setMobile(model.getMobile());
			wrapper.setEmail(model.getEmail());
			wrapper.setUsername(model.getUser().getUsername());
			wrapper.setEnabled(model.getUser().isEnabled());
			wrapper.setPassword(""); // always set empty for password

			// ReferenceBase
			wrapper.setId(model.getId());
			wrapper.setDescription(model.getDescription());
			wrapper.setCreatedBy(model.getCreatedBy());
			wrapper.setCreatedDate(model.getCreatedDate());
			wrapper.setUpdatedBy(model.getUpdatedBy());
			wrapper.setUpdatedDate(model.getUpdatedDate());
			wrapper.setDeleted(model.getDeleted());

		}
		return wrapper;
	}

	private MemberWrapper toWrapperUpdate(Member model) {
		MemberWrapper wrapper = new MemberWrapper();
		if (model != null) {
			wrapper.setFirstName(model.getFirstName());
			wrapper.setLastName(model.getLastName());
			if (model.getBirthDate() != null) {
				wrapper.setBirthDate(new SimpleDateFormat("dd-MM-yyyy").format(model.getBirthDate()));
			}

			wrapper.setGender(String.valueOf(model.getGender()));
			wrapper.setMobile(model.getMobile());
			wrapper.setEmail(model.getEmail());
			wrapper.setUsername(model.getUser().getUsername());
			wrapper.setEnabled(model.getUser().isEnabled());
			wrapper.setPassword(model.getUser().getPassword());

			// ReferenceBase
			wrapper.setId(model.getId());
			wrapper.setDescription(model.getDescription());
			wrapper.setCreatedBy(model.getCreatedBy());
			wrapper.setCreatedDate(model.getCreatedDate());
			wrapper.setUpdatedBy(model.getUpdatedBy());
			wrapper.setUpdatedDate(model.getUpdatedDate());
			wrapper.setDeleted(model.getDeleted());

		}
		return wrapper;
	}

	private List<MemberWrapper> toWrapperList(List<Member> modelList) {
		List<MemberWrapper> rList = new ArrayList<>();
		if (modelList != null && modelList.size() > 0) {
			for (Member temp : modelList) {
				rList.add(toWrapper(temp));
			}
		}
		return rList;
	}

	@Override
	public Long getNum() {
		return accountRepository.count();
	}

	@Override
	public MemberWrapper save(MemberWrapper accountWrapper) throws Exception {
		logger.info("in save service : " + accountWrapper);
		org.springframework.security.crypto.password.PasswordEncoder encoder = new BCryptPasswordEncoder();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		User secUser = new User();
		try {
			secUser.setUsername(accountWrapper.getUsername());
			secUser.setEnabled(accountWrapper.isEnabled());
			secUser.setRole("ROLE_MEMBER");

			User toSave = usersRepository.findOne(secUser.getUsername());
			if (toSave == null) { // if new sec-user use jdbcUsersDetailManager to create user
				authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER")); // user store is always ROLE_USER
				if (accountWrapper.getPassword() != null && !Objects.equals(accountWrapper.getPassword(), "")) {
					secUser.setPassword(encoder.encode(accountWrapper.getPassword()));
				}
				org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
						secUser.getUsername(), secUser.getPassword(), authorities);
				jdbcUserDetailsManager.createUser(user);
				
				usersRepository.save(secUser);
			} else {
				toSave.setUsername(secUser.getUsername());
				if (accountWrapper.getPassword() != null || !accountWrapper.getPassword().equals("")) {
					toSave.setPassword(encoder.encode(accountWrapper.getPassword()));
				}
				toSave.setRole(secUser.getRole());
				toSave.setEnabled(secUser.isEnabled());
				usersRepository.save(toSave); // procedure to save user (if existing it's automatically update)
			}

			accountWrapper = toWrapper(accountRepository.save(toEntity(accountWrapper)));

			return accountWrapper;
		} catch (Exception e) {
			logger.error("Fail save", e);
			throw new Exception(e);
		}
	}

	public MemberWrapper updateProfile(MemberWrapper accountWrapper) throws Exception {
		try {
			User toSave = usersRepository.findOne(accountWrapper.getUsername());
			toSave.setUsername(accountWrapper.getUsername());
			usersRepository.save(toSave); // procedure to save user (if existing it's automatically update)
			accountWrapper = toWrapperUpdate(accountRepository.save(toEntity(accountWrapper)));
			return accountWrapper;
		} catch (Exception e) {
			logger.error("Fail updateProfile", e);
			throw new Exception(e);
		}
	}

	@Override
	public MemberWrapper getById(Long aLong) throws Exception {
		return toWrapper(accountRepository.findOne(aLong));
	}

	@Override
	public MemberWrapper getByIdUpdate(Long aLong) throws Exception {
		return toWrapperUpdate(accountRepository.findOne(aLong));
	}

	@Override
	public Boolean delete(Long aLong) throws Exception {
		try {
			Member model = accountRepository.findOne(aLong);
			if (model != null) {
				model.setVersion(model.getVersion() + 1);
				model.setDeleted(1);
				accountRepository.save(model);
			}
			return true;
		} catch (Exception e) {
			logger.error("Fail delete", e);
			throw new Exception(e);
		}
	}

	@Override
	public List<MemberWrapper> getAll() throws Exception {
		return toWrapperList((List<Member>) accountRepository.findAll());
	}

	@Override
	public Page<MemberWrapper> getPageableList(String param, int startPage, int pageSize, Sort sort) throws Exception {
		int page = DataTableObject.getPageFromStartAndLength(startPage, pageSize);
		Page<Member> accountPage = accountRepository.getPageable(param, new PageRequest(page, pageSize, sort));
		List<MemberWrapper> wrapperList = toWrapperList(accountPage.getContent());
		return new PageImpl<>(wrapperList, new PageRequest(page, pageSize, sort), accountPage.getTotalElements());
	}

	@Override
	public MemberWrapper getByUser(String username) throws Exception {
		return toWrapper(accountRepository.getByUser(username));
	}

	@Override
	public void deleteAll() {
		accountRepository.deleteAll();
	}

	@Override
	public Map<String, Object> registerAccount(MemberWrapper accountWrapper) throws Exception {
		Map<String, Object> rMap = new HashMap<>();
		try {
			org.springframework.security.crypto.password.PasswordEncoder encoder = new BCryptPasswordEncoder();
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			User secUser = new User();
			secUser.setUsername(accountWrapper.getUsername());
			if (accountWrapper.getPassword() != null && !Objects.equals(accountWrapper.getPassword(), "")) {
				secUser.setPassword(encoder.encode(accountWrapper.getPassword()));
			}
			secUser.setEnabled(accountWrapper.isEnabled());
			secUser.setRole("ROLE_ACCOUNT");

			if (usersRepository.findOne(secUser.getUsername()) == null) { // if new sec-user use jdbcUsersDetailManager
																			// to create user
				authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // user is always ROLE_USER
				org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
						secUser.getUsername(), secUser.getPassword(), authorities);
				jdbcUserDetailsManager.createUser(user);
				usersRepository.save(secUser);
			} else {
				usersRepository.save(secUser); // procedure to save user (if existing it's automatically update)
			}

			MemberWrapper rWrapper = toWrapper(accountRepository.save(toEntity(accountWrapper)));

			rMap.put("model", rWrapper);
			rMap.put("activationKey", generateActivationKey(rWrapper));
			return rMap;
		} catch (Exception e) {
			logger.error("Fail registerAccount", e);
			throw new Exception(e);
		}
	}

	@Override
	public MemberWrapper getByEmailAddress(String emailAddress) throws Exception {
		return toWrapper(accountRepository.getByEmail(emailAddress));
	}

	private String generateActivationKey(MemberWrapper wrapper) throws Exception {
		String randomKey = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx123456789";
		RandomString activationKey = new RandomString(64, new SecureRandom(), randomKey);
		try {
			PublicRegistrationActivation pra = new PublicRegistrationActivation();
			pra.setMailRegistrant(wrapper.getEmail());
			pra.setActivationCode(activationKey.nextString());
			pra.setActivated(false);
			registrationActivationService.save(pra);
			if (pra.getId() != null) {
				return pra.getActivationCode();
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Fail generateActivationKey", e);
			throw new Exception(e);
		}
	}

	@Override
	public Member findByUsername(String username) throws Exception {
		try {
			if (username != null) {

				BooleanExpression predicate = QMember.member.id.isNotNull();

				predicate = predicate.and(QMember.member.user.username.eq(username));

				return accountRepository.findOne(predicate);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}