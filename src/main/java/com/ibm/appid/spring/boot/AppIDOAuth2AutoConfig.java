package com.ibm.appid.spring.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import javax.servlet.Filter;

@Configuration
@EnableConfigurationProperties(AppIDOAuth2ConfigurationProperties.class)
public class AppIDOAuth2AutoConfig {

    @Autowired
    private AppIDOAuth2ConfigurationProperties config;

	@Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
    }

    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("appid")
            .clientId(config.getClientId())
            .clientSecret(config.getClientSecret())
            .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUriTemplate("http://localhost:8080/login")
            .scope("openid", "profile", "email", "address", "phone")
            .authorizationUri("https://au-syd.appid.cloud.ibm.com/oauth/v4/7b1d65f8-8283-47d2-96f4-998569e75433/authorization")
            .tokenUri("https://au-syd.appid.cloud.ibm.com/oauth/v4/7b1d65f8-8283-47d2-96f4-998569e75433/token")
            .userInfoUri("https://au-syd.appid.cloud.ibm.com/oauth/v4/7b1d65f8-8283-47d2-96f4-998569e75433/userinfo")
//            .userNameAttributeName(IdTokenClaimNames.SUB)
//            .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
            .clientName("appid")
            .build();
    }
}
