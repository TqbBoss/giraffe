package giraffe.cloud.microservice.service2.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

@RestController
@RefreshScope
public class MessageController {
    private final EmitterProcessor<Message<?>> processor = EmitterProcessor.create();

    @Autowired
    private ObjectMapper jsonMapper;

    @GetMapping("/message2")
    public String getMessage2(){
        return "hello message2";
    }

    @Bean
    public Supplier<Flux<Message<?>>> supplier(){
        return () -> processor;
    }

    @RequestMapping("/send")
    @SuppressWarnings("unchecked")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void handleRequest(@RequestBody String body) throws Exception{
        Map<String, String> payload = jsonMapper.readValue(body, Map.class);
        String destinationName = payload.get("id");
        Message<?> message =  MessageBuilder.withPayload(payload)
                .setHeader("spring.cloud.stream.sendto.destination", destinationName)
                .build();
        processor.onNext(message);
    }

    static class TestSink {
        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Bean
        public Consumer<String> receive1() {
            return data -> logger.info("Data received from customer-1..." + data);
        }

        @Bean
        public Consumer<String> receive2() {
            return data -> logger.info("Data received from customer-2..." + data);
        }
    }
}
