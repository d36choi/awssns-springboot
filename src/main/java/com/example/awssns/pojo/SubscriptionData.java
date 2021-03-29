package com.example.awssns.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubscriptionData {

    private String protocol;
    private String topicArn;
    private String endpoint;

}
