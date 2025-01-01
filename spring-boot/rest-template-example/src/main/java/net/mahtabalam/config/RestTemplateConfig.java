package net.mahtabalam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        int timeout = 4000; // 4 seconds in milliseconds
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        // sets the connection timeout (time to establish the connection)
        factory.setConnectTimeout(timeout);
        // sets the read timeout (time to wait for data after connection)
        factory.setReadTimeout(timeout);
        return new RestTemplate(factory);
    }
}
