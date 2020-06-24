package com.aws.sqs.service;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.aws.sqs.client.AwsSqsClient;

import java.util.Date;
import java.util.List;
import java.util.Map;

import java.text.SimpleDateFormat;

public class ReceiveMessageCommand implements Runnable {

	private AwsSqsClient basicSqsClient;

	Date date;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");

	public ReceiveMessageCommand(AwsSqsClient basicSqsClient) {

		this.basicSqsClient = basicSqsClient;
	}

	@Override
	public void run() {
		// This method will be invoked for the fixed interval. But it will wait for the previous process to complete.
		// Load sharing can be done by increasing multiple threads.
		date = new Date();

		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(basicSqsClient.getAwsSqsUrl());
		receiveMessageRequest.setMaxNumberOfMessages(10);

		// The below code snippet will process the next message immediately
		// while (null != result && null != result.getMessages() && !result.getMessages().isEmpty()) {
		while(true) {

			ReceiveMessageResult result = basicSqsClient.getBasicSqsClient().receiveMessage(receiveMessageRequest);

			printMessage(result.getMessages());

			if (result.getMessages().size() != 0) {
				for (final Message message : result.getMessages()) {
					basicSqsClient.getBasicSqsClient().deleteMessage(basicSqsClient.getAwsSqsUrl(),
							message.getReceiptHandle());
				}
			}
		}
	}

	private void printMessage(List<Message> messages) {
		date = new Date();

		if (messages.size() == 0)
			System.out.println(simpleDateFormat.format(date)
					+ " Thread Id: " + Thread.currentThread().getId()
					+ " Thread Name: " + Thread.currentThread().getName()
					+ " Body:           No message");
		else {
			for (final Message message : messages) {
				System.out.println("  MessageId:     " + message.getMessageId());
				System.out.println("  ReceiptHandle: " + message.getReceiptHandle());
				System.out.println("  MD5OfBody:     " + message.getMD5OfBody());

				System.out.println(simpleDateFormat.format(date)
						+ " Thread Id: " + Thread.currentThread().getId()
						+ " Thread Name: " + Thread.currentThread().getName()
						+ " Body:          " + message.getBody());

				for (final Map.Entry<String, String> entry : message.getAttributes().entrySet()) {
					System.out.println("Attribute");
					System.out.println("  Name:  " + entry.getKey());
					System.out.println("  Value: " + entry.getValue());
				}
			}
		}
	}
}
