package giraffe.clients.client1.controllers;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/oa")
public class AuthorizationController {
    @Resource
    private OAuth2RestTemplate oAuth2RestTemplate;

    @PostMapping(value = "/authorize", params = "grant_type=password")
    public String password_grant() {
        ResourceOwnerPasswordResourceDetails passwordResourceDetails =
                (ResourceOwnerPasswordResourceDetails) this.oAuth2RestTemplate.getResource();
        passwordResourceDetails.setUsername("tqb");
        passwordResourceDetails.setPassword("tqb");
        String p = passwordResourceDetails.getAccessTokenUri();

        String messages = this.oAuth2RestTemplate.getForObject("http://localhost:8803/message/m2", String.class);

        // Never store the user's credentials
        passwordResourceDetails.setUsername(null);
        passwordResourceDetails.setPassword(null);

        return messages;
    }
}
