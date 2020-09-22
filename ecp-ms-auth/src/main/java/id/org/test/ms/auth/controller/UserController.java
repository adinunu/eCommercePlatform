package id.org.test.ms.auth.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.org.test.common.web.BaseController;
import id.org.test.common.web.ResponseStatus;
import id.org.test.ms.auth.domain.User;
import id.org.test.ms.auth.mapper.UserMapper;
import id.org.test.ms.auth.repository.UserRepository;
import id.org.test.ms.auth.service.CustomUserDetailsManager;
import id.org.test.ms.shared.auth.UserAddDTO;
import id.org.test.ms.shared.auth.UserDTO;
import id.org.test.ms.shared.auth.UserRole;
import id.org.test.ms.shared.auth.feign.UserFeign;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController implements UserFeign {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	private CustomUserDetailsManager wheeUdm;
	private ModelMapper mapper;
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepo;
	private UserMapper userMap;

	@Autowired
	public UserController(CustomUserDetailsManager wheeUdm, ModelMapper mapper, PasswordEncoder passwordEncoder,
			UserRepository userRepo, UserMapper userMap) {
		this.wheeUdm = wheeUdm;
		this.mapper = mapper;
		this.passwordEncoder = passwordEncoder;
		this.userRepo = userRepo;
		this.userMap = userMap;
	}

	@PostMapping("/findByUsername")
	public UserDTO findUserByUsername(@RequestParam("username") String username) {
		Optional<User> byUsername = userRepo.findByUsername(username);
		UserDTO convertToDto = userMap.convertToDto(byUsername.get());
		return convertToDto;
	}

	private Map<String, Object> isUserExist(User u) {
		log.debug("isUserExist(username={}, email={}, mobile={})", u.getUsername(), u.getEmail(), u.getMobile());

		Map<String, Object> result = new HashMap<String, Object>();

		Optional<User> byUsername = userRepo.findByUsername(u.getUsername());
		if (byUsername.isPresent()) {
			result.put("exist", true);
			result.put("msg", "Username ".concat(u.getUsername()).concat(" is exist"));
			return result;
		}

		Optional<User> byEmail = userRepo.findByEmail(u.getEmail());
		if (byEmail.isPresent()) {
			result.put("exist", true);
			result.put("msg", "Email ".concat(u.getEmail()).concat(" is exist"));
			return result;
		}

		Optional<User> byMobile = userRepo.findByMobile(u.getMobile());
		if (byMobile.isPresent()) {
			result.put("exist", true);
			result.put("msg", "Mobile ".concat(u.getMobile()).concat(" is exist"));
			return result;
		}

		return result;
	}

	@PostMapping("/add/member")
	@Override
	public Object addUserMember(@RequestBody UserAddDTO udto,
			@RequestParam(name = "encpass", required = false) boolean encpass) {
		log.info("addUserMember({})", udto.toString());
		User u = mapper.map(udto, User.class);

		if (null == u.getMemberId() || 0l >= u.getMemberId())
			return buildResponseGeneralError("memberId is not defined");

		Map<String, Object> m = isUserExist(u);
		boolean exist = (boolean) (null != m.get("exist") ? m.get("exist") : false);
		if (!exist) {

			if (encpass) {
				u.setPassword(passwordEncoder.encode(udto.getPassword()));
			} else {
				u.setPassword("{bcrypt}".concat(udto.getPassword()));
			}
			u.addRole(MEMBER);
			wheeUdm.createUser(u);
			return buildResponse(ResponseStatus.USER_CREATED, HttpStatus.CREATED);
		}

		return buildResponseGeneralError((String) m.get("msg"));
	}

	@PostMapping("/update/role")
	public Object updateRole(@RequestParam("username") String username, @RequestParam("role") UserRole role) {
		log.info("updateRole({})", username.toString());
		try {
			Optional<User> byUsername = userRepo.findByUsername(username);
			if (byUsername.isPresent()) {
				User user = byUsername.get();
				user.setRoles(new HashSet<String>(Arrays.asList(role.getRoleName())));

				userRepo.save(user);
			}
		} catch (Exception e) {
			log.error("/update/role", e);
			return internalServerError(e.getMessage());
		}
		return ok("Role updated");
	}

	@GetMapping("/getByRole")
	public List<UserDTO> getByRole(@RequestParam("role") String role) {
		List<User> roles = null;
		try {
			roles = userRepo.findByRoles(new HashSet<String>(Arrays.asList(role)));
			List<UserDTO> result = userMap.convertToDto(roles);
			return result;
		} catch (Exception e) {
			log.error("/getByRole", e);
			return null;
		}

	}

}
