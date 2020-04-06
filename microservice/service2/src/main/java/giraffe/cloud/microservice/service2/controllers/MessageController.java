package giraffe.cloud.microservice.service2.controllers;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class MessageController {
    @GetMapping("/message2")
    public String getMessage2(){
        return "hello message2";
    }
}
