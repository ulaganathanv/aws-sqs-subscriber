package com.aws.sqs.service;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aws.sqs.client.AwsDemoSqsClient;

@Component
public class AwsDemoMessageSubscriberService {

	@Autowired
	private AwsDemoSqsClient basicSqsClient;

	@Value("${cloud.aws.sqs.pollingThreads}")
	private int pollingThreads;
	
	@Value("${cloud.aws.sqs.pollingRate}")
	private int pollingRate;
	
	private ScheduledThreadPoolExecutor executor;
	
	@PostConstruct
	private void startSqsPoll() {
		executor = new ScheduledThreadPoolExecutor(pollingThreads);
		
		for(int worker = 0; worker < pollingThreads; worker++) {
			executor.scheduleAtFixedRate(new ReceiveMessageCommand(basicSqsClient), 0, pollingRate, TimeUnit.MILLISECONDS);
		}
	}
}
