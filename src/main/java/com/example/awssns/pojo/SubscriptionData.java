package com.example.awssns.pojo;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class SubscriptionData {

    @NonNull private final String protocol;
    @NonNull private final String topicArn;
    @NonNull private final String endpoint;

    public SubscriptionData(String protocol, String topicArn, String endpoint) {
        this.protocol = protocol;
        this.topicArn = topicArn;
        this.endpoint = endpoint;
    }


}
