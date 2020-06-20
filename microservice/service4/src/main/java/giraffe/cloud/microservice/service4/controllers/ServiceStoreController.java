package giraffe.cloud.microservice.service4.controllers;

import giraffe.cloud.microservice.service4.clients.MessageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceStoreController {
    @Autowired
    private MessageClient messageClient;

    @GetMapping("/service4")
    public String getClientRes(){
        return this.messageClient.readMsg();
    }
}
