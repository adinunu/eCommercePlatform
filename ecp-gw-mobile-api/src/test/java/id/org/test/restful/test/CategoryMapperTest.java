package id.org.test.restful.test;

import org.modelmapper.ModelMapper;

import id.org.test.data.model.Category;
import id.org.test.data.model.Member;
import id.org.test.ms.shared.mobile.CategoryCDTO;

public class CategoryMapperTest {
	
	public static void main(String[] args) {
		
		
		ModelMapper mapper = new ModelMapper();
		
//		CategoryCDTO cdto = new CategoryCDTO();
//		cdto.setCategoryName("Alat Tulis");
//		cdto.setDescription("ATK");
		
//		Category category = mapper.map(cdto, Category.class);
		
		Member account = new Member();
		account.setId(100l);
		
		Category category = new Category();
		category.setAccount(account);
//		category.setCategoryName(cdto.getCategoryName());
//		category.setDescription(cdto.getDescription());
		System.out.println(category.toString());
		
		
		
		CategoryCDTO dto = mapper.map(category, CategoryCDTO.class);
		
		System.out.println("XXXX");
		
		
		
//		categoryrepo.save(category);
		
//		mapper.map(source, destinationType);
		
	}

}
