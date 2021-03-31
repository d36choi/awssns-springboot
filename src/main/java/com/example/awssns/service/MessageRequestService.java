package com.example.awssns.service;

import com.example.awssns.entity.MessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MessageRequestService {
    void addRequestToDocument(PublishRequest request, Map<String, String> message);

    MessageRequest getMessageRequest(String id);

    void removeMessageRequest(String id);

    List<MessageRequest> getAllMessageRequests();

    ResponseEntity<String> publishMessage(String topicArn, Map<String, String> message) throws JsonProcessingException;
}
