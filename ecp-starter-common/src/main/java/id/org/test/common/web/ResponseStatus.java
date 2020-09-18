package id.org.test.common.web;

// @formatter:off
public enum ResponseStatus {

	// SUCCESS
	GENERAL_SUCCESS(1000, "General Success"),
	LOGOUT_SUCCESS(1100, "Logout success"),

	// FAIL
	GENERAL_FAIL(2000, "General Failure"),
	USER_VERIFICATION_FAIL(2100, "User Verification fail"),
	AUTHORIZATION_FAIL(2222, "Authorization Failed"),
	AUTHORIZATION_TOKEN_INVALID(2223, "Authorization Token has invalidated"),
	
	// INFO
	GENERAL_INFO(3000, "General Information"),
	USER_IS_LOGGED_IN(3002,"User is Logged In"),
	USER_IS_LOGGED_OUT(3003,"User is Logged Out"),
	USER_CREATED(3004,"User Created"),
	USER_NOT_EXIST(3100, "User Not Exist"),
	
	// WARN
	GENERAL_WARN(4000, "General Warning"),
	USER_EMAIL_TAKEN(4101, "User's email taken"),
	LOGOUT_ALREADY(4102, "User already logged out"),

	// ERROR
	GENERAL_ERROR(5000, "General Error"),
	TOKEN_IS_EMPTY(5100, "Token is empty"),
	
	// COMMON Response Status

	GENERAL_NOT_FOUND(9000, "Data Not Found");
	

	ResponseStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}

	private final int code;
	private final String message;

	public int getCode() {
		return code;
	}

	public String getCodeString() {
		return String.valueOf(this.code);
	}

	public String getMessage() {
		return message;
	}

	public Type type() {
		return Type.valueOf(this);
	}

	public enum Type {

		SUCCESS(1), FAIL(2), INFO(3), WARN(4), ERROR(5);

		Type(int code) {
			this.code = code;
		}

		private final int code;

		public int getCode() {
			return code;
		}

		public static Type valueOf(int code) {
			int typeCode = code / 1000;
			for (Type type : values()) {
				if (type.code == typeCode) {
					return type;
				}
			}
			throw new IllegalArgumentException("No matching ResponseStatus.Type for [" + code + "]");
		}

		public static Type valueOf(ResponseStatus status) {
			return valueOf(status.code);
		}

	}
	
	public String toJsonString() {
		StringBuilder json = new StringBuilder();
		json.append("{");
			json.append("\"code\"").append(":").append(this.code).append(",");
			json.append("\"message\"").append(":").append("\""+ this.message +"\"");
		json.append("}");
		return json.toString();
	}

}
//@formatter:on
