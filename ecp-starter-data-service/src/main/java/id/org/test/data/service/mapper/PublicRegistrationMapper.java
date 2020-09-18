package id.org.test.data.service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import id.org.test.common.web.EntityToDTOMapper;
import id.org.test.data.model.PublicRegistrationActivation;
import id.org.test.ms.shared.mobile.PublicRegistrationDTO;

@Component
public class PublicRegistrationMapper implements EntityToDTOMapper<PublicRegistrationActivation, PublicRegistrationDTO>{
	private ModelMapper mapper;
	
	public PublicRegistrationMapper(ModelMapper mapper) {
		this.mapper=mapper;
	}

	@Override
	public PublicRegistrationActivation createEntity(PublicRegistrationDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PublicRegistrationActivation updateEntity(PublicRegistrationDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PublicRegistrationDTO convertToDto(PublicRegistrationActivation entity) {
		return mapper.map(entity, PublicRegistrationDTO.class);
	}

	@Override
	public List<PublicRegistrationDTO> convertToDto(Iterable<PublicRegistrationActivation> entities) {
		List<PublicRegistrationDTO> dtos=new ArrayList<PublicRegistrationDTO>();
		for (PublicRegistrationActivation entity:entities) {
			dtos.add(convertToDto(entity));
		}
		return dtos;
	}

	@Override
	public PublicRegistrationActivation convertToEntity(PublicRegistrationDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PublicRegistrationActivation> convertToEntity(Iterable<PublicRegistrationDTO> dtos) {
		// TODO Auto-generated method stub
		return null;
	}



}
