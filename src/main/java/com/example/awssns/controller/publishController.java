package com.example.awssns.controller;

import com.example.awssns.configuration.AwsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Slf4j
@RestController
public class publishController {

    private AwsConfig awsConfig;
    private SnsClient snsClient;

    public publishController(AwsConfig awsConfig, SnsClient snsClient) {
        this.awsConfig = awsConfig;
        this.snsClient = snsClient;
    }

    @GetMapping("/publish")
    public String publish() {
        final PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(awsConfig.getSnsTopicARN())
                .subject("HTTP ENDPOINT TEST MESSAGE")
                .message("11st choi sang hyun test")
                .build();
        PublishResponse publishResponse = snsClient.publish(publishRequest);
        if (!publishResponse.sdkHttpResponse().isSuccessful()) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, publishResponse.sdkHttpResponse().statusText().get()
            );
        }
        log.info("message status:" + publishResponse.sdkHttpResponse().statusCode());
        snsClient.close();

        return "sent MSG ID = " + publishResponse.messageId();

    }

}
