package com.example.awssns.service;

import software.amazon.awssdk.services.sns.model.Subscription;
import software.amazon.awssdk.services.sns.model.Topic;

import java.util.List;
import java.util.Map;

public interface SnsClientService {

    List<Topic> getTopics();
    List<Subscription> getSubscriptions();
    Map<String, String> getTopicAttributes(String topicArn);
}
