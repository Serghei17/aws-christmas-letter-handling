package org.sergheimorari.letterprocessor.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Configurable
@EnableDynamoDBRepositories(basePackages = "org.sergheimorari.letterprocessor.repository")
public class DynamoDBConfiguration {

  @Value("${spring.cloud.aws.dynamodb.accessKey}")
  private String accessKey;

  @Value("${spring.cloud.aws.dynamodb.secretKey}")
  private String secretKey;

  @Value("${spring.cloud.aws.dynamodb.region}")
  private String region;

  @Value("${spring.cloud.aws.dynamodb.endpoint}")
  private String endpoint;

  @Bean
  public AmazonDynamoDB amazonDynamoDBClient() {
    return AmazonDynamoDBClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
        .withCredentials(awsDynamoCredentials())
        .build();
  }

  private com.amazonaws.auth.AWSCredentialsProvider awsDynamoCredentials() {
    return new AWSStaticCredentialsProvider(
        new com.amazonaws.auth.BasicAWSCredentials(accessKey, secretKey));
  }
}
