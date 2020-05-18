package giraffe.clients.client1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfig {
    @Bean
    @ConfigurationProperties(prefix = "giraffe.oauth2.client.giraffe-password")
    public OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails() {
        return new ResourceOwnerPasswordResourceDetails();
    }

    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(){
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(oAuth2ProtectedResourceDetails());
        oAuth2RestTemplate.setAccessTokenProvider(new ResourceOwnerPasswordAccessTokenProvider());
        return oAuth2RestTemplate;
    }
}
