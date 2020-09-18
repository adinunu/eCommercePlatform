package id.org.test.ms.auth.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import id.org.test.common.web.EntityToDTOMapper;
import id.org.test.ms.auth.domain.User;
import id.org.test.ms.shared.auth.UserDTO;

@Component
public class UserMapper implements EntityToDTOMapper<User, UserDTO>{

	private ModelMapper mapper;
	
	public UserMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}
	
	@Override
	public User createEntity(UserDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateEntity(UserDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO convertToDto(User entity) {
		return mapper.map(entity, UserDTO.class);
	}

	@Override
	public List<UserDTO> convertToDto(Iterable<User> entities) {
		List<UserDTO>dtos=new ArrayList<UserDTO>();
		for(User entity : entities) {
			dtos.add(convertToDto(entity));
		}
		return dtos;
	}

	@Override
	public User convertToEntity(UserDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> convertToEntity(Iterable<UserDTO> dtos) {
		// TODO Auto-generated method stub
		return null;
	}

}
