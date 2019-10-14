package com.ibm.appid.spring.boot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.util.StringUtils;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties.Provider;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.ConversionException;

/**
 * Adapter class to convert {@link OAuth2ClientProperties} to a
 * {@link ClientRegistration}.
 */

public class AppIDOAuth2ClientPropertiesRegistrationAdapter {

	private AppIDOAuth2ClientPropertiesRegistrationAdapter() {
	}
	
	public static Map<String, ClientRegistration> getClientRegistrations(AppIDOAuth2ConfigurationProperties properties) {
		Map<String, ClientRegistration> clientRegistrations = new HashMap<>();
		properties.getRegistration().forEach((key, value) -> clientRegistrations.put(key,
				getClientRegistration(key, value)));
		return clientRegistrations;
	}

	private static ClientRegistration getClientRegistration(String registrationId,
			AppIDOAuth2ConfigurationProperties.Registration properties) {
		Builder builder = getBuilder(registrationId, properties.getProvider(), properties);
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
		return builder.build();
	}

	private static Builder getBuilder(String registrationId, String configuredProviderId,
			AppIDOAuth2ConfigurationProperties.Registration properties) {
		String providerId = (configuredProviderId != null) ? configuredProviderId : registrationId;
		CommonOAuth2Provider provider = getCommonProvider(providerId);
		AppIDOAuth2Provider appIDProvider = getAppIDProvider(properties.getRegion());
		Builder builder = (provider != null) ? provider.getBuilder(registrationId)
				: appIDProvider.getBuilder(registrationId, properties);
		return builder;
	}

	private static CommonOAuth2Provider getCommonProvider(String providerId) {
		try {
			return ApplicationConversionService.getSharedInstance().convert(providerId, CommonOAuth2Provider.class);
		}
		catch (ConversionException ex) {
			return null;
		}
	}
	
	private static AppIDOAuth2Provider getAppIDProvider(String region) {
		try {
			return ApplicationConversionService.getSharedInstance().convert(region, AppIDOAuth2Provider.class);
		}
		catch (ConversionException ex) {
			return null;
		}
	}

}
