package com.aws.sqs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AwsSqsSubscriberApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsSqsSubscriberApplication.class, args);
	}
}
