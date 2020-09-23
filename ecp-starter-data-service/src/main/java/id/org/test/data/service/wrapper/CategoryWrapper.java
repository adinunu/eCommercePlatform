package id.org.test.data.service.wrapper;

import id.org.test.common.wrapper.ReferenceBaseWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CategoryWrapper extends ReferenceBaseWrapper {

	private static final long serialVersionUID = 4471571736750280349L;
	
	private String categoryName;
	private Long memberId;

}
