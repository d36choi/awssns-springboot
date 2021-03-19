package com.example.awssns.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Getter @Setter @ToString
@NoArgsConstructor
@Document
public class PublishMessageRequest {

    @Id
    private String id;
    private String request;

    public PublishMessageRequest(PublishRequest request) {
        this.request = request.toString();
    }

    public static PublishMessageRequest of(PublishRequest snsRequest) {
        return new PublishMessageRequest(snsRequest);
    }
}
