package id.org.test.data.service.registration.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import id.org.test.common.web.DataTableObject;
import id.org.test.data.model.Category;
import id.org.test.data.model.Member;
import id.org.test.data.model.PublicRegistrationActivation;
import id.org.test.data.model.User;
import id.org.test.data.repository.MemberRepository;
import id.org.test.data.repository.CategoryRepository;
import id.org.test.data.repository.PublicRegistrationActivationRepository;
import id.org.test.data.repository.UsersRepository;
import id.org.test.data.service.mapper.PublicRegistrationMapper;
import id.org.test.data.service.registration.PublicRegistrationActivationService;

@Service
public class PublicRegistrationActivationServiceImpl implements PublicRegistrationActivationService {
	private static final Logger logger = LoggerFactory.getLogger(PublicRegistrationActivationServiceImpl.class);

	private final PublicRegistrationActivationRepository activationRepository;
	private final UsersRepository usersRepository;
	private final MemberRepository accountRepository;
	private final CategoryRepository categoryRepository;
	private JdbcUserDetailsManager jdbcUserDetailsManager;

	// using this can cause circular dependencies..
	// private final AccountService accountService;

	@Value("${expired.store.num-days}")
	private String expiredStoreNumDays;

	private DataSource dataSource;

	@Autowired
	public PublicRegistrationActivationServiceImpl(// DatabaseConfig databaseConfig,
			DataSource dataSource, PublicRegistrationActivationRepository activationRepository,
			UsersRepository usersRepository, MemberRepository accountRepository, CategoryRepository categoryRepository,
			PublicRegistrationMapper publicMap) {
		this.dataSource = dataSource;
		this.activationRepository = activationRepository;
		this.usersRepository = usersRepository;
		this.accountRepository = accountRepository;
		this.categoryRepository = categoryRepository;
		if (jdbcUserDetailsManager == null) {
			jdbcUserDetailsManager = new JdbcUserDetailsManager();
		}
		this.jdbcUserDetailsManager.setDataSource(this.dataSource);
	}

	@Override
	public Long getNum() {
		return activationRepository.count();
	}

	@Override
	public PublicRegistrationActivation save(PublicRegistrationActivation publicRegistrationActivation)
			throws Exception {
		return activationRepository.save(publicRegistrationActivation);
	}

	@Override
	public PublicRegistrationActivation getById(Long aLong) throws Exception {
		return activationRepository.findOne(aLong);
	}

	@Override
	public Boolean delete(Long aLong) throws Exception {
		try {
			activationRepository.delete(aLong);
			return true;
		} catch (Exception e) {
			logger.error("Fail Delete", e);
			throw new Exception(e);
		}
	}

	@Override
	public boolean deleteAll(List<PublicRegistrationActivation> objList) throws Exception {
		try {
			activationRepository.delete(objList);
			return true;
		} catch (Exception e) {
			logger.error("Fail deleteAll", e);
			throw new Exception(e);
		}
	}

	@Override
	public List<PublicRegistrationActivation> getListByDateRange(Date dStartDate, Date dEndDate) throws Exception {
		return activationRepository.findListByDateRange(dStartDate, dEndDate);
	}

	@Override
	public List<PublicRegistrationActivation> getAll() throws Exception {
		return (List<PublicRegistrationActivation>) activationRepository.findAll();
	}

	@Override
	public Page<PublicRegistrationActivation> getPageableList(String param, int startPage, int pageSize, Sort sort)
			throws Exception {
		int page = DataTableObject.getPageFromStartAndLength(startPage, pageSize);
		return activationRepository.getPageable(param, new PageRequest(page, pageSize, sort));
	}

	@Override
	public Map<String, Object> activateAccount(String activationCode) throws Exception {
		try {
			Map<String, Object> rMap = new HashMap<>();
			PublicRegistrationActivation par = activationRepository.getByActivationCode(activationCode);
			if (par != null && par.getId() != null) {
				// 1. check if account already activated
				if (par.getActivated()) {
					rMap.put("result", "FAILED");
					rMap.put("message", "Account already registered");
				} else {
					// 2. activate public registration
					par.setActivated(true);
					par.setUpdatedDate(new Date());
					par.setUpdatedBy(par.getMailRegistrant());
					save(par);

					// 3. create account and set menu
					Member account = new Member();
					User user = new User();
					user.setUsername(par.getBusinessSubDomain() + "." + par.getMailRegistrant());
					user.setPassword(par.getPassword());

//                    Account acId = accountRepository.getByEmail(par.getMailRegistrant());

					account.setUser(user);
					account.setFirstName(par.getFirstName());
					account.setLastName(par.getLastName());
					account.setEmail(par.getMailRegistrant());
					account.setMobile(par.getPhoneNum());
					registerActivatedAccount(account);
					// method to generate menu should be listed here
					if (account.getId() != null) {
						generateDefaultAttributeAndMenu(account);
						rMap.put("account", account);
//                        rMap.put("idAccount",acId.getId());
						rMap.put("result", "SUCCESS");
						rMap.put("message", "Account activated");
					}
				}
			} else {
				rMap.put("result", "FAILED");
				rMap.put("message", "No such email and activation code");
			}
			return rMap;
		} catch (Exception e) {
			logger.error("Fail activateAccount", e);
			throw new Exception(e);
		}
	}

	private Map<String, Object> registerActivatedAccount(Member account) throws Exception {
		Map<String, Object> rMap = new HashMap<>();
		try {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			User secUser = account.getUser();
			secUser.setRole("ROLE_ACCOUNT");
			secUser.setEnabled(true);

			if (usersRepository.getByUserName(secUser.getUsername()) == null) { // if new sec-user use
																				// jdbcUsersDetailManager to create user
				authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // user is always ROLE_USER
				org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
						secUser.getUsername(), secUser.getPassword(), authorities);
				jdbcUserDetailsManager.createUser(user);
				usersRepository.save(secUser);
			} else {
				usersRepository.save(secUser); // procedure to save user (if existing it's automatically update)
			}

			account = accountRepository.save(account);
			if (account.getId() != null) {
				generateDefaultAttributeAndMenu(account);
			}
			rMap.put("model", account);
			return rMap;
		} catch (Exception e) {
			logger.error("Fail registerActivatedAccount", e);
			throw new Exception(e);
		}
	}

	private void generateDefaultAttributeAndMenu(Member account) throws Exception {
		Category defaultCategory = new Category();
		/**
		 * if save succeed, proceed to create 1. DEFAULT CATEGORY,SUPPLIER, BRAND FOR
		 * PRODUCT 2. DEFAULT USER_MENU NAVIGATION
		 *
		 */
		if (account.getId() != null) {
			// 1. check if all default is exist, if not then create one
			if (categoryRepository.getNumFilteredByAccount(account.getId()).size() == 0) {
				defaultCategory.setDescription("");
				defaultCategory.setCategoryName("No Category");
				defaultCategory.setAccount(account);
				categoryRepository.save(defaultCategory);
			}
		}
	}

	@Override
	public Map<String, Object> checkActivationCode(String activationCode) throws Exception {
		try {
			Map<String, Object> rMap = new HashMap<>();
			PublicRegistrationActivation par = activationRepository.getByActivationCode(activationCode);
			if (par != null && par.getId() != null) {
				if (par.getActivated()) {
					rMap.put("result", "FAILED");
					rMap.put("message", "Account already registered");
				} else {
					rMap.put("result", "VALID");
					rMap.put("businessName", par.getBusinessName());
					rMap.put("message", "Account ready to activate");
				}
			} else {
				rMap.put("result", "FAILED");
				rMap.put("message", "No such email and activation code");
			}
			return rMap;
		} catch (Exception e) {
			logger.error("Fail checkActivationCode", e);
			throw new Exception(e);
		}
	}

	@Override
	public PublicRegistrationActivation getByBusinessName(String businessName) throws Exception {
		return activationRepository.getByBusinessName(businessName);
	}

	@Override
	public PublicRegistrationActivation getByBusinessSubDomain(String businessSubDomain) throws Exception {
		return activationRepository.getByBusinessSubDomain(businessSubDomain);
	}

	@Override
	public PublicRegistrationActivation getByMailRegistrant(String mailRegistrant) throws Exception {
		return activationRepository.getByMailRegistrant(mailRegistrant);
	}

	@Override
	public List<PublicRegistrationActivation> findInActiveListLowerThanToday(Date yesterday) throws Exception {
		return activationRepository.getInactiveListUntilEndDate(yesterday);
	}

	@Override
	public List<PublicRegistrationActivation> getRestrationExpiredList(Date today) throws Exception {
		try {
//			List<PublicRegistrationActivation> expiredList = activationRepository.listRegistrationExpired(today);
			List<PublicRegistrationActivation> expiredList = activationRepository.listRegistrationExpiredPerHour(today);
			return expiredList;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public boolean findByBusinessSubDomain(String wheeAccount) {

		if (activationRepository.findByBusinessSubDomain(wheeAccount).isPresent())
			return true;

		return false;
	}

	@Override
	public boolean findByMailRegistrant(String mailRegistrant) {

		if (activationRepository.findByMailRegistrant(mailRegistrant).isPresent())
			return true;

		return false;
	}

	@Override
	public Member addAccount(PublicRegistrationActivation par) {
		try {
			String lastName = "";
			Member account = new Member();
			String fullName = par.getFirstName();
			String[] splitName = fullName.split("\\s+");
			lastName = fullName.replace(splitName[0] + " ", "");

			if (fullName.equals(splitName[0]))
				lastName = "";

			User user = new User();
			user.setUsername(par.getBusinessSubDomain() + "." + par.getMailRegistrant());
			user.setPassword(par.getPassword());

			account.setUser(user);
			account.setFirstName(splitName[0]);
			account.setLastName(lastName);
			account.setEmail(par.getMailRegistrant());
			account.setMobile(par.getPhoneNum());
			registerActivatedAccount(account);
			// method to generate menu should be listed here
			if (account.getId() != null) {
				generateDefaultAttributeAndMenu(account);
			}
			return account;
		} catch (Exception e) {
			logger.error("Fail add new account", e);
			return null;
		}
	}

}