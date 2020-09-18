package id.org.test.data.service.menu.wrapper;

import id.org.test.common.wrapper.ReferenceBaseWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserMenuWrapper extends ReferenceBaseWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1876596072696303912L;

	private String username;
	private Long menuId;
	private String menuName;
	private String menuUrl;
	private Boolean active = false;

}
