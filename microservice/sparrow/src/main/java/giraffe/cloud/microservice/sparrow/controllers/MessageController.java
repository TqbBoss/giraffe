package giraffe.cloud.microservice.sparrow.controllers;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class MessageController {
    @GetMapping("/message")
    public String getMessage(){
        return "hello message";
    }
}
