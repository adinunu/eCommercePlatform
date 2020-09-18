package id.org.test.ms.shared.cloud;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.AbstractClientHttpResponse;
import org.springframework.http.client.ClientHttpResponse;

import lombok.Data;

@Data
public class GatewayFallbackProvider implements FallbackProvider {
	
	private static final Logger log = LoggerFactory.getLogger(GatewayFallbackProvider.class);

	private String route = null;
	private int rawStatusCode = HttpStatus.SERVICE_UNAVAILABLE.value();
	private String statusText = HttpStatus.SERVICE_UNAVAILABLE.toString();
	private HttpHeaders headers = null;
	private String responseBody = null;

	@Override
	public String getRoute() {
		if (route == null)
			route = "route";
		return route;
	}

	@Override
	public ClientHttpResponse fallbackResponse() {
		ClientHttpResponse cr = new AbstractClientHttpResponse() {

			@Override
			public HttpHeaders getHeaders() {
				if (headers == null) {
					headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);
				}
				return headers;
			}

			@Override
			public InputStream getBody() throws IOException {
				if (responseBody == null)
					responseBody = "{\"message\":\"Service Unavailable, Please try again later.\"}";
				return new ByteArrayInputStream(responseBody.getBytes());
			}

			@Override
			public String getStatusText() throws IOException {
				return statusText;
			}

			@Override
			public int getRawStatusCode() throws IOException {
				return rawStatusCode;
			}

			@Override
			public void close() {

			}
		};
		return cr;
	}

	@Override
	public ClientHttpResponse fallbackResponse(Throwable cause) {
		log.info("fallbackResponse({})", cause.getMessage());
		return fallbackResponse();
	}

}
