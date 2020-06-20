package giraffe.cloud.microservice.service4.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("service2")
public interface MessageClient {
    @GetMapping("/message2")
    String readMsg();
}
