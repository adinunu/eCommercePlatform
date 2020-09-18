package id.org.test.common.exception;

import java.io.IOException;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import id.org.test.common.web.ResponseStatus;

public class AppAuthExceptionSerializer extends StdSerializer<AppAuthException> {

	private static final long serialVersionUID = -5874866948482845076L;

	protected AppAuthExceptionSerializer() {
		super(AppAuthException.class);
	}

	@Override
	public void serialize(AppAuthException e, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		if(e.getOAuth2ErrorCode().equals(OAuth2Exception.INVALID_REQUEST) && e.getMessage().equals("Bad credentials")) {
			gen.writeNumberField("code", ResponseStatus.AUTHORIZATION_FAIL.getCode());
			gen.writeStringField("message", ResponseStatus.AUTHORIZATION_FAIL.getMessage());
		}else if(e.getOAuth2ErrorCode().equals(OAuth2Exception.INVALID_TOKEN) && e.getMessage().equals("Token was not recognised")) {
			gen.writeNumberField("code", ResponseStatus.AUTHORIZATION_TOKEN_INVALID.getCode());
			gen.writeStringField("message", ResponseStatus.AUTHORIZATION_TOKEN_INVALID.getMessage());
		}
		gen.writeEndObject();
	}

}
