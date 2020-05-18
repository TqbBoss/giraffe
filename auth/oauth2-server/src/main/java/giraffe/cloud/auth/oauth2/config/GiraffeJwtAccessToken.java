package giraffe.cloud.auth.oauth2.config;

import com.alibaba.fastjson.JSON;
import giraffe.auth.db.domains.CloudUsers;
import giraffe.cloud.auth.oauth2.common.ConstantVars;
import giraffe.cloud.auth.oauth2.common.GiraffeUserDetails;
import giraffe.cloud.auth.oauth2.config.AuthKeyConfig;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

public class GiraffeJwtAccessToken extends JwtAccessTokenConverter {
    private JsonParser objectMapper = JsonParserFactory.create();

    final RsaSigner signer = new RsaSigner(AuthKeyConfig.getSignerKey());

    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String content;
        try {
            content = this.objectMapper.formatMap(getAccessTokenConverter().convertAccessToken(accessToken, authentication));
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot convert access token to JSON", ex);
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("kid", AuthKeyConfig.VERIFIER_KEY_ID);
        return JwtHelper.encode(content, this.signer, headers).getEncoded();
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        // 设置额外用户信息
        CloudUsers cloudUser = ((GiraffeUserDetails) authentication.getPrincipal()).getCloudUser();
        cloudUser.setPassword(null);
        // 将用户信息添加到token额外信息中
        defaultOAuth2AccessToken.getAdditionalInformation().put(ConstantVars.USER_INFO, cloudUser);
        return super.enhance(defaultOAuth2AccessToken, authentication);
    }

    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map){
        OAuth2AccessToken oauth2AccessToken = super.extractAccessToken(value, map);
        convertData(oauth2AccessToken, oauth2AccessToken.getAdditionalInformation());
        return oauth2AccessToken;
    }

    private void convertData(OAuth2AccessToken accessToken,  Map<String, ?> map) {
        accessToken.getAdditionalInformation().put(ConstantVars.USER_INFO,convertUserData(map.get(ConstantVars.USER_INFO)));

    }

    private CloudUsers convertUserData(Object map) {
        String json = JSON.toJSONString(map);
        return JSON.parseObject(json, CloudUsers.class);
    }
}
