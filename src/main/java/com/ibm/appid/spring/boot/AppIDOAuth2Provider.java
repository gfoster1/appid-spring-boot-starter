package com.ibm.appid.spring.boot;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

/**
 * AppID OAuth2 Providers that can be used to create pre-configured with sensible defaults.
 *
 */
public enum AppIDOAuth2Provider {

	DALLAS {

		@Override
		public Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties) {
			ClientRegistration.Builder builder = getBuilder(registrationId, properties.getClientId(),
					properties.getClientSecret(), properties.getRedirectUri());
			Set<String> scope = new HashSet<String>();
			scope.add("openid");
			builder.scope(properties.getScope().size() > 0 ? properties.getScope() : scope);
			builder.authorizationUri(APPID_URL_DALLAS + properties.getTenantID() + "/authorization");
			builder.tokenUri(APPID_URL_DALLAS + properties.getTenantID() + "/token");
			builder.userInfoUri(APPID_URL_DALLAS + properties.getTenantID() + "/userinfo");
			builder.userNameAttributeName(IdTokenClaimNames.SUB);
			builder.clientName("AppID");
			return builder;
		}
	},

	SYDNEY {

		@Override
		public Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties) {
			ClientRegistration.Builder builder = getBuilder(registrationId, properties.getClientId(),
					properties.getClientSecret(), properties.getRedirectUri());
			Set<String> scope = new HashSet<String>();
			scope.add("openid");
			builder.scope(properties.getScope().size() > 0 ? properties.getScope() : scope);
			builder.authorizationUri(APPID_URL_SYDNEY + properties.getTenantID() + "/authorization");
			builder.tokenUri(APPID_URL_SYDNEY + properties.getTenantID() + "/token");
			builder.userInfoUri(APPID_URL_SYDNEY + properties.getTenantID() + "/userinfo");
			builder.userNameAttributeName(IdTokenClaimNames.SUB);
			builder.clientName("AppID");
			return builder;
		}
	},

	FRANKFURT {

		@Override
		public Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties) {
			ClientRegistration.Builder builder = getBuilder(registrationId, properties.getClientId(),
					properties.getClientSecret(), properties.getRedirectUri());
			Set<String> scope = new HashSet<String>();
			scope.add("openid");
			builder.scope(properties.getScope().size() > 0 ? properties.getScope() : scope);
			builder.authorizationUri(APPID_URL_FRANKFURT + properties.getTenantID() + "/authorization");
			builder.tokenUri(APPID_URL_FRANKFURT + properties.getTenantID() + "/token");
			builder.userInfoUri(APPID_URL_FRANKFURT + properties.getTenantID() + "/userinfo");
			builder.userNameAttributeName(IdTokenClaimNames.SUB);
			builder.clientName("AppID");
			return builder;
		}
	},

	LONDON {

		@Override
		public Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties) {
			ClientRegistration.Builder builder = getBuilder(registrationId, properties.getClientId(),
					properties.getClientSecret(), properties.getRedirectUri());
			Set<String> scope = new HashSet<String>();
			scope.add("openid");
			builder.scope(properties.getScope().size() > 0 ? properties.getScope() : scope);
			builder.authorizationUri(APPID_URL_LONDON + properties.getTenantID() + "/authorization");
			builder.tokenUri(APPID_URL_LONDON + properties.getTenantID() + "/token");
			builder.userInfoUri(APPID_URL_LONDON + properties.getTenantID() + "/userinfo");
			builder.userNameAttributeName(IdTokenClaimNames.SUB);
			builder.clientName("AppID");
			return builder;
		}
	},
	
	TOKYO {

		@Override
		public Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties) {
			ClientRegistration.Builder builder = getBuilder(registrationId, properties.getClientId(),
					properties.getClientSecret(), properties.getRedirectUri());
			Set<String> scope = new HashSet<String>();
			scope.add("openid");
			builder.scope(properties.getScope().size() > 0 ? properties.getScope() : scope);
			builder.authorizationUri(APPID_URL_TOKYO + properties.getTenantID() + "/authorization");
			builder.tokenUri(APPID_URL_TOKYO + properties.getTenantID() + "/token");
			builder.userInfoUri(APPID_URL_TOKYO + properties.getTenantID() + "/userinfo");
			builder.userNameAttributeName(IdTokenClaimNames.SUB);
			builder.clientName("AppID");
			return builder;
		}
	};
	
	private static final String APPID_URL_DALLAS = "{baseUrl}/{action}/oauth2/code/{registrationId}";
	private static final String APPID_URL_SYDNEY = "{baseUrl}/{action}/oauth2/code/{registrationId}";
	private static final String APPID_URL_FRANKFURT = "{baseUrl}/{action}/oauth2/code/{registrationId}";
	private static final String APPID_URL_LONDON = "{baseUrl}/{action}/oauth2/code/{registrationId}";
	private static final String APPID_URL_TOKYO = "{baseUrl}/{action}/oauth2/code/{registrationId}";


	protected final ClientRegistration.Builder getBuilder(String registrationId,
			String clientId, String clientSecret, String redirectUri) {
		ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
		builder.clientAuthenticationMethod(ClientAuthenticationMethod.BASIC);
		builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
		builder.redirectUriTemplate(redirectUri);
		return builder;
	}

	/**
	 * Create a new pre-configured with provider defaults.
	 */
	public abstract ClientRegistration.Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties);

}
