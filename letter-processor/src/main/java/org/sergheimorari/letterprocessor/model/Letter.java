package org.sergheimorari.letterprocessor.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson;
import lombok.Builder;

@Builder
@DynamoDBTable(tableName = "ChristmasLetters")
public class Letter {
  @DynamoDBHashKey(attributeName = "Email")
  private String email;

  @DynamoDBAttribute(attributeName = "Name")
  private String name;

  @DynamoDBAttribute(attributeName = "Wishes")
  private String wishes;

  @DynamoDBTypeConvertedJson
  @DynamoDBAttribute(attributeName = "Address")
  private Address address;
}
