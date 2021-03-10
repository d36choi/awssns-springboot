package com.example.awssns.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;


@Getter
@Configuration
public class AwsConfig {

    @Value("${sns.topic.arn}")
    private String snsTopicARN;

    @Value("${aws.accessKey}")
    private String awsAccessKey;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public SnsClient snsClient() {
        return SnsClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(
                        StaticCredentialsProvider.create(AwsBasicCredentials.create(
                                awsAccessKey,awsSecretKey
                        ))
                )
                .build();
    }
}
