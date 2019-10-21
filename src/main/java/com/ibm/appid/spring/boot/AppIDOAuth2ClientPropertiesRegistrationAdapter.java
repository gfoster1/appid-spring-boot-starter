package com.ibm.appid.spring.boot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.ConversionException;

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
				getClientRegistration(key, value)));
		return clientRegistrations;
	}

	private static ClientRegistration getClientRegistration(String registrationId,
			AppIDOAuth2ConfigurationProperties.Registration properties) {
		Builder builder = getBuilder(registrationId, properties.getProvider(), properties);
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
