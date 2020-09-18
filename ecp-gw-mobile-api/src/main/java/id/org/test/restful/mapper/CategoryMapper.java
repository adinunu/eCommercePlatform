package id.org.test.restful.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import id.org.test.common.web.EntityToDTOMapper;
import id.org.test.data.model.Category;
import id.org.test.ms.shared.mobile.CategoryCDTO;
import id.org.test.ms.shared.mobile.CategoryVDTO;

@Component
public class CategoryMapper implements EntityToDTOMapper<Category, CategoryVDTO> {
	
	private ModelMapper mapper;
	
	public CategoryMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

	public Category createEntity(CategoryCDTO dto) {
		return mapper.map(dto, Category.class);
	}
	
	@Override
	public Category createEntity(CategoryVDTO dto) {
		return null;
	}

	@Override
	public Category updateEntity(CategoryVDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoryVDTO convertToDto(Category entity) {
		return mapper.map(entity, CategoryVDTO.class);
	}

	@Override
	public List<CategoryVDTO> convertToDto(Iterable<Category> entities) {
		List<CategoryVDTO> dtos = new ArrayList<CategoryVDTO>();
		for (Category entity : entities) {
			dtos.add(convertToDto(entity));
		}
		return dtos;
	}

	@Override
	public Category convertToEntity(CategoryVDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> convertToEntity(Iterable<CategoryVDTO> dtos) {
		// TODO Auto-generated method stub
		return null;
	}

}
