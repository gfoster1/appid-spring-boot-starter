package com.ibm.boot.autoconfigure.appid;

import com.ibm.boot.autoconfigure.appid.AppIDOAuth2ConfigurationProperties.Provider;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.ConversionException;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Adapter class to convert {@link AppIDOAuth2ConfigurationProperties} to a
 * {@link ClientRegistration}.
 */

public class AppIDOAuth2ClientPropertiesRegistrationAdapter {

    private AppIDOAuth2ClientPropertiesRegistrationAdapter() {
    }

    public static Map<String, ClientRegistration> getClientRegistrations(AppIDOAuth2ConfigurationProperties properties) {
        Map<String, ClientRegistration> clientRegistrations = new HashMap<>();
        properties.getRegistration().forEach((key, value) -> clientRegistrations.put(key,
                getClientRegistration(key, value, properties.getProvider())));
        return clientRegistrations;
    }

    private static ClientRegistration getClientRegistration(String registrationId,
                                                            AppIDOAuth2ConfigurationProperties.Registration properties, Map<String, Provider> providers) {
        Builder builder = getBuilderFromIssuerIfPossible(registrationId, properties.getProvider(), providers, properties);
        if (builder == null) {
            builder = getBuilder(registrationId, properties.getProvider(), properties, providers);
        }
        return builder.build();
    }

    private static Builder getBuilderFromIssuerIfPossible(String registrationId, String configuredProviderId,
                                                          Map<String, Provider> providers, AppIDOAuth2ConfigurationProperties.Registration properties) {
        String providerId = (configuredProviderId != null) ? configuredProviderId : registrationId;
        if (providers.containsKey(providerId)) {
            Provider provider = providers.get(providerId);
            String issuer = provider.getIssuerUri();
            if (issuer != null) {
                Builder builder = ClientRegistrations.fromOidcIssuerLocation(issuer).registrationId(registrationId);
                return getBuilder(builder, provider, properties);
            }
        }
        return null;
    }


    private static Builder getBuilder(String registrationId, String configuredProviderId,
                                      AppIDOAuth2ConfigurationProperties.Registration properties, Map<String, Provider> providers) {
        String providerId = (configuredProviderId != null) ? configuredProviderId : registrationId;
        CommonOAuth2Provider provider = getCommonProvider(providerId);
        AppIDOAuth2Provider appIDProvider = getAppIDProvider(properties.getRegion());
        Builder builder = (provider != null) ? getCommonProviderBuilder(provider, registrationId, properties)
                : appIDProvider.getBuilder(registrationId, properties);
        if (providers.containsKey(providerId)) {
            return getBuilder(builder, providers.get(providerId), properties);
        }
		return builder;
    }

    private static Builder getBuilder(Builder builder, Provider provider, AppIDOAuth2ConfigurationProperties.Registration properties) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(properties::getClientId).to(builder::clientId);
        map.from(properties::getClientSecret).to(builder::clientSecret);
        map.from(properties::getClientAuthenticationMethod).as(ClientAuthenticationMethod::new)
                .to(builder::clientAuthenticationMethod);
        map.from(properties::getAuthorizationGrantType).as(AuthorizationGrantType::new)
                .to(builder::authorizationGrantType);
        map.from(properties::getRedirectUri).to(builder::redirectUriTemplate);
        map.from(properties::getScope).as(StringUtils::toStringArray).to(builder::scope);
        map.from(properties::getClientName).to(builder::clientName);

        map.from(provider::getAuthorizationUri).to(builder::authorizationUri);
        map.from(provider::getTokenUri).to(builder::tokenUri);
        map.from(provider::getUserInfoUri).to(builder::userInfoUri);
        map.from(provider::getUserInfoAuthenticationMethod).as(AuthenticationMethod::new)
                .to(builder::userInfoAuthenticationMethod);
        map.from(provider::getJwkSetUri).to(builder::jwkSetUri);
        map.from(provider::getUserNameAttribute).to(builder::userNameAttributeName);
        return builder;
    }

    private static Builder getCommonProviderBuilder(CommonOAuth2Provider provider, String registrationId,
                                                    AppIDOAuth2ConfigurationProperties.Registration properties) {
        Builder builder = provider.getBuilder(registrationId);
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from(properties::getClientId).to(builder::clientId);
        map.from(properties::getClientSecret).to(builder::clientSecret);
        map.from(properties::getClientAuthenticationMethod).as(ClientAuthenticationMethod::new)
                .to(builder::clientAuthenticationMethod);
        map.from(properties::getAuthorizationGrantType).as(AuthorizationGrantType::new)
                .to(builder::authorizationGrantType);
        map.from(properties::getRedirectUri).to(builder::redirectUriTemplate);
        map.from(properties::getScope).as(StringUtils::toStringArray).to(builder::scope);
        map.from(properties::getClientName).to(builder::clientName);
        return builder;
    }

    private static CommonOAuth2Provider getCommonProvider(String providerId) {
        try {
            return ApplicationConversionService.getSharedInstance().convert(providerId, CommonOAuth2Provider.class);
        } catch (ConversionException ex) {
            // TODO why catch the exception and return a null value?
            return null;
        }
    }

    private static AppIDOAuth2Provider getAppIDProvider(String region) {
        try {
            return ApplicationConversionService.getSharedInstance().convert(region, AppIDOAuth2Provider.class);
        } catch (ConversionException ex) {
            // TODO why catch the exception and return a null value?
            return null;
        }
    }

}
