package giraffe.cloud.microservice.service1.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RefreshScope
public class MessageController {

    @Value("${dynamicMessage}")
    private String dynamicValue;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/message1")
    @HystrixCommand(fallbackMethod = "defaultMessage")
    public String getMessage1(){
        return this.restTemplate.getForObject("http://service2/message2", String.class);
    }

    @GetMapping("/dynamic")
    public String getConfigInfo(){
        return this.dynamicValue;
    }

    public String defaultMessage(){
        return "default message";
    }
}
