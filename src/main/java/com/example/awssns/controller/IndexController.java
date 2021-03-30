package com.example.awssns.controller;

import com.example.awssns.service.SnsClientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {

    private final SnsClientService snsClientService;

    public IndexController(SnsClientService snsClientService) {
        this.snsClientService = snsClientService;
    }

    @ApiOperation(value = "home page", notes = "see topic, subscription list")
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("topics", snsClientService.getTopics());
        model.addAttribute("subscriptions", snsClientService.getSubscriptions());
        return "index";
    }

    @ApiOperation(value = "topic detail page", notes = "see selected topic attributes")
    @GetMapping("/detail")
    public String topicDetail(Model model,
                              @ApiParam(value = "topic name that you want to see attributes")
                              @RequestParam String topicArn) {
        model.addAttribute("attributes",snsClientService.getTopicAttributes(topicArn));
        model.addAttribute("topicArn",topicArn);
        return "topicDetail";
    }
}
