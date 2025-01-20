package org.sergheimorari.letterprocessor.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@DynamoDBDocument
public class Address {
  @DynamoDBAttribute(attributeName = "Street")
  private String street;

  @DynamoDBAttribute(attributeName = "City")
  private String city;

  @DynamoDBAttribute(attributeName = "State")
  private String state;

  @DynamoDBAttribute(attributeName = "Zip code")
  private String zip;
}
