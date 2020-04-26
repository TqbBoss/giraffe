package giraffe.cloud.microservice.service2.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class MessageConfiguration {
    private final Logger logger = LoggerFactory.getLogger(MessageConfiguration.class);
    @Bean
    public Function<String, String> source1(){
        return input -> {
            logger.warn("source1-> " + input);
            return input.toUpperCase();
        };
    }

    @Bean
    public Function<String, String> source2(){
        return input -> {
            logger.warn("source2-> " + input);
            return input.concat("xiao sha zi");
        };
    }

    @Bean
    public Consumer<String> sink1(){
        return msg-> {
            logger.warn("sink1-> 收到：" + msg);
        };
    }
}
