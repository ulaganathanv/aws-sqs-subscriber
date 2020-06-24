# AWS SQS Subscriber

This spring boot application helps to receive the messages continously from SQS.

If there are no messages then it will poll the queue with the particular interval based on the configuration to retrieve the new messages in future.

## Configurations:

Default Visibility Timeout: 30 seconds
Receive Message Wait Time: 10 seconds
