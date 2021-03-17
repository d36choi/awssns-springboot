package com.example.awssns;

import com.example.awssns.entity.PublishMessageRequest;
import com.example.awssns.service.MongodbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Slf4j
@SpringBootApplication
public class AwssnsApplication implements CommandLineRunner {

	@Autowired
	MongodbService mongodbService;

	public static void main(String[] args) {
		SpringApplication.run(AwssnsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		mongodbService.getAllMessages().forEach(c ->
					log.info(c.toString())
				);
	}
}
