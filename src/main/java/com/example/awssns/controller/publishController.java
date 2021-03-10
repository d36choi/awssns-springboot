package com.example.awssns.controller;

import com.example.awssns.configuration.AWSConfig;
import com.example.awssns.service.CredentialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import java.net.URISyntaxException;

@Slf4j
@RestController
public class publishController {

    AWSConfig awsConfig;
    CredentialService credentialService;

    public publishController(AWSConfig awsConfig, CredentialService credentialService) {
        this.awsConfig = awsConfig;
        this.credentialService = credentialService;
    }

    @GetMapping("/publish")
    public String publish() throws URISyntaxException {
        SnsClient snsClient = credentialService.getSnsClient();
        final PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(awsConfig.getSnsTopicARN())
                .subject("HTTP ENDPOINT TEST MESSAGE")
                .message("11st choi sang hyun test")
                .build();
        PublishResponse publishResponse = snsClient.publish(publishRequest);
        log.info("message status:" + publishResponse.sdkHttpResponse().statusCode());
        snsClient.close();

        return "sent MSG ID = " + publishResponse.messageId();

    }



}
