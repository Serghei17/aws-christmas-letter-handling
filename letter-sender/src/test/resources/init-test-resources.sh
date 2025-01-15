#!/bin/bash

echo "Test resource initialization started..."

# Create SNS topic
topic_name="test-topic"
awslocal sns create-topic --name $topic_name
echo "SNS topic '$topic_name' created successfully"

echo "Test resource initialization complete!"