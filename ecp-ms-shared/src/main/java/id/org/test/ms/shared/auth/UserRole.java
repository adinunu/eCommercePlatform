package id.org.test.ms.shared.auth;

public enum UserRole {
	
	SYSTEM("ROLE_SYSTEM"),
	ADMIN("ROLE_ADMIN"),
	MEMBER("ROLE_MEMBER");

	
	private String roleName;
	
	private UserRole(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

}
