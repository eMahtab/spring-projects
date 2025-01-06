package net.mahtabalam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FeignClientExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignClientExampleApplication.class, args);
	}

}
