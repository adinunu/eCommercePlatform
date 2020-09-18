package id.org.test.common.constant;

import java.util.Base64;

public class AppConstant {

	public static final Long ADMIN_ACCOUNT_ID = -19l;

	public static final String SWAGGER_URL_WHITELIST[] = { "/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs",
			"/webjars/**" };
	public static final String ACTUATOR_V1_URL_WHITELIST[] = { "/health", "/info", "/metrics", "/trace" };

	public static final String SECURITY_HTTP_BASIC_REALM_NAME = "WheeHQ";
	public static final String SECURITY_JWT_SIGN_KEY = "WheeAr3Th3Ch4mPi0ns19";

	public static final class OAuthClientDetails {

		public static final class MobileApi {
			public static final String ID = "WheeMobileApi19";
			public static final String SECRET = "Wh3eAr3Th3Ch4mPi0ns77";
			public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 99 * 86400; // 99 days
			public static final int REFRESH_TOKEN_VALIDITY_SECONDS = 99 * 86400; // 99 days

			public static String buildBasicAuthorization() {
				StringBuilder basicAuth = new StringBuilder("Basic ");
				String basicAuthEncoded = Base64.getEncoder().encodeToString(ID.concat(":").concat(SECRET).getBytes());
				basicAuth.append(new String(basicAuthEncoded));
				return basicAuth.toString();
			}
		}

		public static String getClientId(String headerBasicAuthorization) {
			if (headerBasicAuthorization.contentEquals(MobileApi.buildBasicAuthorization()))
				return MobileApi.ID;
			return null;
		}

		public static boolean isValidBasicAuthorization(String headerBasicAuthorization) {

			if (headerBasicAuthorization.contentEquals(MobileApi.buildBasicAuthorization()))
				return true;

			return false;
		}

	}

	public static final class HttpHeader {
		public static final String CONTENT_TYPE_JSON = "Content-Type:application/json;charset=utf-8";
		public static final String CONTENT_TYPE_URLENCODED = "Content-Type:application/x-www-form-urlencoded";
	}

	public static final class UserRole {

		// Administrator
		public static final String SYSTEM = "ROLE_SYSTEM";
		public static final String ADMIN = "ROLE_ADMIN";

		// Account
		public static final String MEMBER = "ROLE_MEMBER";
	}

	public static String[] ALLOWED_SYSTEM_PATH = new String[] { UserRole.ADMIN.replaceAll("ROLE_", ""),
			UserRole.SYSTEM.replaceAll("ROLE_", "") };
	public static String[] ALLOWED_FORWARD_SUCCESS_LOGIN = new String[] { UserRole.ADMIN.replaceAll("ROLE_", ""),
			UserRole.SYSTEM.replaceAll("ROLE_", ""), UserRole.MEMBER.replaceAll("ROLE_", "") };

	public static final class TokenInfo {
		public static final String USER_NO = "userNo";
		public static final String USERNAME = "userName";
		public static final String USER_EMAIL = "userEmail";
		public static final String USER_MOBILE = "userMobile";
		public static final String AUTHORITIES = "authorities";
		public static final String MEMBER = "member";
		public static final String MEMBER_ID = "memberId";
		public static final String ROLE = "role";

	}

}
