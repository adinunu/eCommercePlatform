package id.org.test.ms.auth.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.org.test.common.web.BaseController;
import id.org.test.ms.auth.domain.Role;
import id.org.test.ms.auth.repository.RoleRepository;
import id.org.test.ms.shared.auth.feign.RoleFeign;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController implements RoleFeign {

	private static final Logger log = LoggerFactory.getLogger(RoleController.class);

	private final RoleRepository roleRepo;

	public RoleController(RoleRepository roleRepo) {
		this.roleRepo = roleRepo;
	}


	@GetMapping("/list")
	public Object getRoleList() {
		Sort sort = new Sort("name");
		return roleRepo.findAll(sort);
	}

	@GetMapping("/list-admin")
	public Object getRoleListAdmin() {
		List<Role> admin = roleRepo.findAll();
		List<Role> listAdmin = new ArrayList<>();
		for (Role role : admin) {
			if (role.getName().toString().startsWith("ADMIN")) {
				listAdmin.add(role);
			}
		}
		return listAdmin;
	}

}
