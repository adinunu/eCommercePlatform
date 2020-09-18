package id.org.test.data.service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import id.org.test.common.web.EntityToDTOMapper;
import id.org.test.data.model.PublicRegistrationActivation;
import id.org.test.data.repository.PublicRegistrationActivationRepository;
import id.org.test.ms.shared.mobile.SignUpCDTO;
import id.org.test.ms.shared.mobile.SignUpVDTO;

@Component
public class SignUpMapper implements EntityToDTOMapper<PublicRegistrationActivation, SignUpVDTO> {

	private ModelMapper mapper;
	private final PublicRegistrationActivationRepository publicRegistRepo;

	public SignUpMapper(PublicRegistrationActivationRepository publicRegistRepo) {
		this.publicRegistRepo = publicRegistRepo;
	}

	public PublicRegistrationActivation createEntity(SignUpCDTO dto) throws Exception{
		PublicRegistrationActivation publicRegistAct = new PublicRegistrationActivation();
//		dto.setBusinessType(BussinessType.UNREGIST);
//		dto.setPassword("password");
		publicRegistAct.setId(dto.getId());
		publicRegistAct.setPassword(dto.getPassword());
		publicRegistAct.setMailRegistrant(dto.getMailRegistrant());
		publicRegistAct.setBusinessName(dto.getBusinessName());
		publicRegistAct.setBusinessSubDomain(dto.getBusinessSubDomain());
		publicRegistAct.setActivationCode(dto.getActivationCode());
		publicRegistAct.setActivated(dto.getActivated());
		publicRegistAct.setNumberOfStore(dto.getNumberOfStore());
		publicRegistAct.setFirstName(dto.getFirstName());
		publicRegistAct.setCity(dto.getCity());
		publicRegistAct.setPhoneNum(dto.getPhoneNum());
		PublicRegistrationActivation publicRegist = publicRegistRepo.save(publicRegistAct);
		return publicRegist;
//		PublicRegistrationActivation map = mapper.map(dto, PublicRegistrationActivation.class);
//		return map;
	}

	@Override
	public PublicRegistrationActivation updateEntity(SignUpVDTO dto) {
		return mapper.map(dto, PublicRegistrationActivation.class);
	}

	@Override
	public SignUpVDTO convertToDto(PublicRegistrationActivation entity) {
		return mapper.map(entity, SignUpVDTO.class);
	}

	@Override
	public List<SignUpVDTO> convertToDto(Iterable<PublicRegistrationActivation> entities) {
		List<SignUpVDTO> dtos = new ArrayList<SignUpVDTO>();
		for (PublicRegistrationActivation entity : entities) {
			dtos.add(convertToDto(entity));
		}
		return dtos;
	}

	@Override
	public PublicRegistrationActivation convertToEntity(SignUpVDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PublicRegistrationActivation> convertToEntity(Iterable<SignUpVDTO> dtos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PublicRegistrationActivation createEntity(SignUpVDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
