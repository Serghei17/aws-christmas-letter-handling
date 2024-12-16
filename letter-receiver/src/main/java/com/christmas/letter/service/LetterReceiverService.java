package com.christmas.letter.service;

import org.springframework.messaging.Message;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Slf4j
@Service
public class LetterReceiverService {

    @SqsListener()
    public void listen(Message<?> message) {
        log.info("Received message: {}, at: {}.", message.getPayload(), OffsetDateTime.now());
    }
}
