package id.org.test.restful.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

import id.org.test.ms.shared.auth.feign.AuthFeign;

@FeignClient(name = "${ecp-ms-auth.name}", path = "${ecp-ms-auth.path}")
public interface OAuthFeignClient extends AuthFeign {

}
