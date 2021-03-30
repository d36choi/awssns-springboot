package com.example.awssns.service;

import com.example.awssns.pojo.SubscriptionData;
import org.springframework.http.ResponseEntity;

public interface TopicRequestService {

    ResponseEntity<String> createTopic(String topicName);

    ResponseEntity<String> deleteTopic(String topicArn);

    ResponseEntity<String> subscribe(SubscriptionData payload);

    ResponseEntity<String> unsubscribe(String subscriptionArn);
}
