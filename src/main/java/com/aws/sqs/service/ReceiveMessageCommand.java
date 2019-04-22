package com.aws.sqs.service;

import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.aws.sqs.client.AwsSqsClient;

public class ReceiveMessageCommand implements Runnable {

	private AwsSqsClient basicSqsClient;

	public ReceiveMessageCommand(AwsSqsClient basicSqsClient) {

		this.basicSqsClient = basicSqsClient;
	}

	@Override
	public void run() {
		ReceiveMessageResult result = basicSqsClient.getBasicSqsClient().receiveMessage(basicSqsClient.getAwsSqsUrl());
		if (null != result) {
			System.out.println("Message Received {}" + result);
			if (null != result.getMessages() && !result.getMessages().isEmpty()) {
				System.out.println("Deleting message {} " + result.getMessages().get(0).getReceiptHandle());
				basicSqsClient.getBasicSqsClient().deleteMessage(basicSqsClient.getAwsSqsUrl(),
						result.getMessages().get(0).getReceiptHandle());
			}
		}
	}
}
