package com.example.awssns.pojo;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class SubscriptionData {

    @NonNull private String protocol;
    @NonNull private String topicArn;
    @NonNull private String endpoint;

    public SubscriptionData(String protocol, String topicArn, String endpoint) {
        this.protocol = protocol;
        this.topicArn = topicArn;
        this.endpoint = endpoint;
    }


}
