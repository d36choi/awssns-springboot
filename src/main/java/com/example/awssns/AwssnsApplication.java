package com.example.awssns;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class AwssnsApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(AwssnsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
