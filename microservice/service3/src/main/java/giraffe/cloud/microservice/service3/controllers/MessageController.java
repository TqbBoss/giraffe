package giraffe.cloud.microservice.service3.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {
    @GetMapping("/m2")
    public String getMessage2(){
        return "hello service3";
    }
}
