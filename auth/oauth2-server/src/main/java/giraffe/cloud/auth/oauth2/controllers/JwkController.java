package giraffe.cloud.auth.oauth2.controllers;

import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/oauth2")
public class JwkController {
    @Resource
    JWKSet jwkSet;

    @GetMapping("/pub_key")
    public String getPublicKey(){
        return jwkSet.toString();
    }

    @GetMapping("/encrypt")
    public String encrypt(String p){
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return passwordEncoder.encode(p);
    }
}
