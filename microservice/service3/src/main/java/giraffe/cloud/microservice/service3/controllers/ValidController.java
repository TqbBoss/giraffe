package giraffe.cloud.microservice.service3.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/valid")
public class ValidController {
    @RequestMapping("/value")
    public String getValue(){
        return "service value";
    }
}
