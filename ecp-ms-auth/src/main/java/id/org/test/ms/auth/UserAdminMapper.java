package id.org.test.ms.auth;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import id.org.test.common.web.EntityToDTOMapper;
import id.org.test.ms.auth.domain.User;
import id.org.test.ms.shared.auth.UserAdminCDTO;
import id.org.test.ms.shared.auth.UserAdminDTO;

@Component
public class UserAdminMapper implements EntityToDTOMapper<User, UserAdminDTO> {

	private static final Logger log = LoggerFactory.getLogger(UserAdminMapper.class);

	private ModelMapper mapper;

	public UserAdminMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

	public User createNewEntity(UserAdminCDTO dto) {
		return mapper.map(dto, User.class);
	}

	@Override
	public User createEntity(UserAdminDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateEntity(UserAdminDTO dto) {
		return mapper.map(dto, User.class);
	}

	@Override
	public UserAdminDTO convertToDto(User entity) {
		return mapper.map(entity, UserAdminDTO.class);
	}

	@Override
	public List<UserAdminDTO> convertToDto(Iterable<User> entities) {
		List<UserAdminDTO> dtos = new ArrayList<UserAdminDTO>();
		for (User entity : entities) {
			dtos.add(convertToDto(entity));
		}
		return dtos;
	}

	@Override
	public User convertToEntity(UserAdminDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> convertToEntity(Iterable<UserAdminDTO> dtos) {
		// TODO Auto-generated method stub
		return null;
	}

}
