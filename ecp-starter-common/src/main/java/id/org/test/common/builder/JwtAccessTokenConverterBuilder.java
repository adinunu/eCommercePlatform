package id.org.test.common.builder;

import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import id.org.test.common.constant.AppConstant;

public class JwtAccessTokenConverterBuilder {

	public static JwtAccessTokenConverter build() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(AppConstant.SECURITY_JWT_SIGN_KEY);
		return converter;
	}

}
