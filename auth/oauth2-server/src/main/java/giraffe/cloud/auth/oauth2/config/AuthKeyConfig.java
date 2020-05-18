package giraffe.cloud.auth.oauth2.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class AuthKeyConfig {
    private static final String KEY_STORE_FILE = "auth-store.jks";
    private static final String KEY_STORE_PASSWORD = "authstore";
    private static final String KEY_ALIAS = "auth";
    private static KeyStoreKeyFactory KEY_STORE_KEY_FACTORY = new KeyStoreKeyFactory(
            new ClassPathResource(KEY_STORE_FILE), KEY_STORE_PASSWORD.toCharArray());
    static final String VERIFIER_KEY_ID = Base64.getEncoder()
            .encodeToString(KeyGenerators.secureRandom(32).generateKey());

    static RSAPublicKey getVerifierKey() {
        return (RSAPublicKey) getKeyPair().getPublic();
    }

    static RSAPrivateKey getSignerKey() {
        return (RSAPrivateKey) getKeyPair().getPrivate();
    }

    static KeyPair getKeyPair() {
        return KEY_STORE_KEY_FACTORY.getKeyPair(KEY_ALIAS);
    }
}
