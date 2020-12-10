package io.pcf.plantserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
public class PlantServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlantServerApplication.class, args);
	}


	@LoadBalanced
	@Bean
	public RestOperations restOperations() {
		return new RestTemplate();
	}

}
