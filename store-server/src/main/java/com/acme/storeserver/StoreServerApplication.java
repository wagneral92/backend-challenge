package com.acme.storeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class StoreServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreServerApplication.class, args);
	}

}
