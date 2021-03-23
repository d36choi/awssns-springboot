package com.example.awssns.controller;

import com.example.awssns.configuration.AWSConfig;
import com.example.awssns.service.CredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.Subscription;
import software.amazon.awssdk.services.sns.model.Topic;

import java.util.List;


@Controller
public class IndexController {

    AWSConfig awsConfig;
    CredentialService credentialService;

    public IndexController(AWSConfig awsConfig, CredentialService credentialService) {
        this.awsConfig = awsConfig;
        this.credentialService = credentialService;
    }

    @GetMapping("/")
    public String index(Model model) {
        SnsClient snsClient = credentialService.getSnsClient();
        List<Topic> topics = snsClient.listTopics().topics();
        List<Subscription> subscriptions = snsClient.listSubscriptions().subscriptions();
        snsClient.close();
        model.addAttribute("topics",topics);
        model.addAttribute("subscriptions",subscriptions);
        return "index";
    }
}
