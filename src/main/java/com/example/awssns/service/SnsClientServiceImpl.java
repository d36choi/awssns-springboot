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

        SnsClient snsClient = credentialService.getSnsClient();
        List<Topic> topics = snsClient.listTopics().topics();
        snsClient.close();
        return topics;
    }

    @Override
    public List<Subscription> getSubscriptions() {

        SnsClient snsClient = credentialService.getSnsClient();
        List<Subscription> subscriptions = snsClient.listSubscriptions().subscriptions();
        snsClient.close();
        return subscriptions;
    }

    @Override
    public Map<String, String> getTopicAttributes(String topicArn) {

        final GetTopicAttributesRequest request = GetTopicAttributesRequest.builder()
                .topicArn(topicArn)
                .build();

        SnsClient snsClient = credentialService.getSnsClient();
        Map<String, String> attributes = snsClient.getTopicAttributes(request).attributes();
        snsClient.close();
        return attributes;
    }
}
