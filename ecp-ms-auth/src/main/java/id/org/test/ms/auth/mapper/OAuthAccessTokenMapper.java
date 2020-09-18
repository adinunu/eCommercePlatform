package id.org.test.ms.auth.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import id.org.test.common.web.EntityToDTOMapper;
import id.org.test.ms.auth.domain.OAuthAcccessToken;
import id.org.test.ms.shared.auth.OAuthAccessTokenDTO;

@Component
public class OAuthAccessTokenMapper implements EntityToDTOMapper<OAuthAcccessToken, OAuthAccessTokenDTO>{

	private static final Logger log = LoggerFactory.getLogger(OAuthAccessTokenMapper.class);

	private ModelMapper mapper;

	public OAuthAccessTokenMapper(ModelMapper mapper) {
		this.mapper=mapper;
	}

	public OAuthAcccessToken createNewEntity(OAuthAccessTokenDTO dto) {
		return mapper.map(dto, OAuthAcccessToken.class);
	}
	
	@Override
	public OAuthAcccessToken createEntity(OAuthAccessTokenDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OAuthAcccessToken updateEntity(OAuthAccessTokenDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OAuthAccessTokenDTO convertToDto(OAuthAcccessToken entity) {
		return mapper.map(entity, OAuthAccessTokenDTO.class);
	}

	@Override
	public List<OAuthAccessTokenDTO> convertToDto(Iterable<OAuthAcccessToken> entities) {
		List<OAuthAccessTokenDTO> dtos = new ArrayList<OAuthAccessTokenDTO>();
		for (OAuthAcccessToken entity : entities) {
			dtos.add(convertToDto(entity));
		}
		return dtos;
	}

	@Override
	public OAuthAcccessToken convertToEntity(OAuthAccessTokenDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OAuthAcccessToken> convertToEntity(Iterable<OAuthAccessTokenDTO> dtos) {
		// TODO Auto-generated method stub
		return null;
	}

}
