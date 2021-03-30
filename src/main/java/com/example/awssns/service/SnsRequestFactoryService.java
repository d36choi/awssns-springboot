package com.example.awssns.service;

import com.example.awssns.pojo.SubscriptionData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.model.*;

import java.util.Map;

@Service
public class SnsRequestFactoryService {

    private static final String DEFAULT_SUBJECT = "11st CX team";
    private final ObjectMapper mapper;

    public SnsRequestFactoryService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    protected DeleteTopicRequest getDeleteTopicRequest(String topicArn) {
        return DeleteTopicRequest.builder()
                .topicArn(topicArn)
                .build();
    }

    protected CreateTopicRequest getCreateTopicRequest(String topicName) {
        return CreateTopicRequest.builder()
                .name(topicName)
                .build();
    }

    protected SubscribeRequest getSubscribeRequest(SubscriptionData payload) {
        return SubscribeRequest.builder()
                .protocol(payload.getProtocol())
                .topicArn(payload.getTopicArn())
                .endpoint(payload.getEndpoint())
                .build();
    }

    protected UnsubscribeRequest getUnsubscribeRequest(String subscriptionArn) {
        return UnsubscribeRequest.builder()
                .subscriptionArn(subscriptionArn)
                .build();
    }

    protected PublishRequest getPublishRequest(String topicArn, Map<String, String> message) throws JsonProcessingException {
        return PublishRequest.builder()
                .topicArn(topicArn)
                .subject(DEFAULT_SUBJECT)
                .message(mapper.writeValueAsString(message))
                .build();
    }
}
