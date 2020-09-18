package id.org.test.restful.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

@Configuration
public class GatewayFallbackConfig {

	@Bean
	public FallbackProvider enigmaRouteFallback() {
		return new FallbackProvider() {

			@Override
			public String getRoute() {
				return "*";
			}

			@Override
			public ClientHttpResponse fallbackResponse() {
				return new ClientHttpResponse() {

					@Override
					public HttpHeaders getHeaders() {
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						return headers;
					}

					@Override
					public InputStream getBody() throws IOException {
						String responseBody = "{\"message\":\"Service Unavailable, Please try again later.\"}";
						return new ByteArrayInputStream(responseBody.getBytes());
					}

					@Override
					public String getStatusText() throws IOException {
						return HttpStatus.SERVICE_UNAVAILABLE.toString();
					}

					@Override
					public HttpStatus getStatusCode() throws IOException {
						return HttpStatus.SERVICE_UNAVAILABLE;
					}

					@Override
					public int getRawStatusCode() throws IOException {
						return HttpStatus.SERVICE_UNAVAILABLE.value();
					}

					@Override
					public void close() {

					}
				};
			}

			@Override
			public ClientHttpResponse fallbackResponse(Throwable cause) {
				return fallbackResponse();
			}
		};
	}

}
