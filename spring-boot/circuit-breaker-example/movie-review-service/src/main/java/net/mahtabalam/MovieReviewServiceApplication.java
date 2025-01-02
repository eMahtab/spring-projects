package net.mahtabalam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MovieReviewServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieReviewServiceApplication.class, args);
	}

}
