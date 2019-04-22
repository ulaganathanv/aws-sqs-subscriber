package com.aws.sqs.client;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import lombok.Getter;

@Component
@Scope("singleton")
public final class AwsDemoSqsClient {

	@Value("${cloud.aws.region.static}")
	private String awsRegion;

	@Value("${cloud.aws.sqs.url}")
	private String awsSqsUrl;

	private AmazonSQS basicSqsClient;

	public String getAwsRegion() {
		return awsRegion;
	}

	public void setAwsRegion(String awsRegion) {
		this.awsRegion = awsRegion;
	}

	public String getAwsSqsUrl() {
		return awsSqsUrl;
	}

	public void setAwsSqsUrl(String awsSqsUrl) {
		this.awsSqsUrl = awsSqsUrl;
	}

	public AmazonSQS getBasicSqsClient() {
		return basicSqsClient;
	}

	public void setBasicSqsClient(AmazonSQS basicSqsClient) {
		this.basicSqsClient = basicSqsClient;
	}

	@PostConstruct
	private void init() {
		basicSqsClient =  AmazonSQSClientBuilder.standard().withRegion(awsRegion).build();
	}
}
