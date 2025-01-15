package org.sergheimorari.letterprocessor.repository;

import org.sergheimorari.letterprocessor.model.Letter;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRepository
    extends DynamoDBCrudRepository<Letter, String>,
        DynamoDBPagingAndSortingRepository<Letter, String> {
  Page<Letter> findLettersByEmail(String email, Pageable pageable);
}
