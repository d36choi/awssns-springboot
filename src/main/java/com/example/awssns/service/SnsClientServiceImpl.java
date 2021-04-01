package com.example.awssns.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.GetTopicAttributesRequest;
import software.amazon.awssdk.services.sns.model.Subscription;
import software.amazon.awssdk.services.sns.model.Topic;

import java.util.List;
import java.util.Map;

@Service
public class SnsClientServiceImpl implements SnsClientService{

    private final CredentialService credentialService;

    public SnsClientServiceImpl(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @Override
    public List<Topic> getTopics() {
        try (SnsClient snsClient = credentialService.getSnsClient()) {
            return snsClient.listTopics().topics();
        }

    }

    @Override
    public List<Subscription> getSubscriptions() {

        try (SnsClient snsClient = credentialService.getSnsClient()) {
            return snsClient.listSubscriptions().subscriptions();
        }
    }

    @Override
    public Map<String, String> getTopicAttributes(String topicArn) {



        try (SnsClient snsClient = credentialService.getSnsClient()) {
            final GetTopicAttributesRequest request = GetTopicAttributesRequest.builder()
                    .topicArn(topicArn)
                    .build();
            return snsClient.getTopicAttributes(request).attributes();
        }
    }
}
