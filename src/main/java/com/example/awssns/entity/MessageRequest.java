package com.example.awssns.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@ToString
@Builder @Getter
@Document
public class MessageRequest {

    @Id
    String id;
    String topicArn;
    String targetArn;
    String subject;
    Map<String, String> message;

    public MessageRequest(String id, String topicArn, String targetArn, String subject, Map<String, String> message) {
        this.id = id;
        this.topicArn = topicArn;
        this.targetArn = targetArn;
        this.subject = subject;
        this.message = message;
    }


}
