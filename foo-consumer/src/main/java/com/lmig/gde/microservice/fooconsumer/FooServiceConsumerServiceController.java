package com.lmig.gde.microservice.fooconsumer;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.naming.ServiceUnavailableException;
import java.nio.charset.Charset;
import java.util.Optional;

@RestController
public class FooServiceConsumerServiceController {
    private static final String SERVICE_NAME = "zuul-api-gateway-server";
    private static final String OPEATION_NAME = "getServerLocalTime";

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/getFooServerTime")
    public ResponseEntity<String> getFooServerTime() throws RestClientException, ServiceUnavailableException {
        return checkIfServiceAvailable()
                .map(service -> restTemplate.exchange("http://" + service + "/" + OPEATION_NAME,
                        HttpMethod.GET,
                        new HttpEntity<>(createHeaders("abc", "abc")),
                        String.class))
                .orElseThrow(ServiceUnavailableException::new);
    }

    HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

    private Optional<String> checkIfServiceAvailable() {
        return discoveryClient.getInstances(SERVICE_NAME)
                .stream()
                .map(si -> si.getServiceId())
                .findFirst();
    }
}