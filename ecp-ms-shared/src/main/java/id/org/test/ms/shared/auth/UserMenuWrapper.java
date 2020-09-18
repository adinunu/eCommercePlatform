package id.org.test.ms.shared.auth;

import id.org.test.common.wrapper.ReferenceBaseWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserMenuWrapper extends ReferenceBaseWrapper {

	private static final long serialVersionUID = -2543607186447856126L;

	private String username;
	private Long menuId;
	private String menuName;
	private String menuUrl;
	private Boolean active = false;
}
