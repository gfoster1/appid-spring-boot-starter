package com.ibm.appid.spring.boot;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.util.StringUtils;

/**
 * AppID OAuth2 Providers that can be used to create pre-configured with sensible defaults.
 *
 */
public enum AppIDOAuth2Provider {

	DALLAS {

		@Override
		public Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties) {
			ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
			builder.clientId(properties.getClientId());
			builder.clientSecret(properties.getClientSecret());
			builder.clientAuthenticationMethod(ClientAuthenticationMethod.BASIC);
			builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
			builder.redirectUriTemplate(properties.getRedirectUri());
			Set<String> scope = new HashSet<String>();
			scope.add("openid");
			builder.scope(properties.getScope().size() > 0 ? properties.getScope() : scope);
			builder.authorizationUri(APPID_OAUTH_URL_DALLAS + properties.getTenantID() + "/authorization");
			builder.tokenUri(APPID_OAUTH_URL_DALLAS + properties.getTenantID() + "/token");
			builder.userInfoUri(APPID_OAUTH_URL_DALLAS + properties.getTenantID() + "/userinfo");
			builder.jwkSetUri(APPID_OAUTH_URL_DALLAS + properties.getTenantID() + "/publickeys");
			builder.userNameAttributeName(IdTokenClaimNames.SUB);
			builder.clientName("AppID");
			return builder;
		}
	},

	SYDNEY {

		@Override
		public Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties) {
			ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
			builder.clientId(properties.getClientId());
			builder.clientSecret(properties.getClientSecret());
			builder.clientAuthenticationMethod(ClientAuthenticationMethod.BASIC);
			builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
			builder.redirectUriTemplate(properties.getRedirectUri());
			Set<String> scope = new HashSet<String>();
			scope.add("openid");
			builder.scope(properties.getScope().size() > 0 ? properties.getScope() : scope);
			builder.authorizationUri(APPID_OAUTH_URL_SYDNEY + properties.getTenantID() + "/authorization");
			builder.tokenUri(APPID_OAUTH_URL_SYDNEY + properties.getTenantID() + "/token");
			builder.userInfoUri(APPID_OAUTH_URL_SYDNEY + properties.getTenantID() + "/userinfo");
			builder.jwkSetUri(APPID_OAUTH_URL_SYDNEY + properties.getTenantID() + "/publickeys");
			builder.userNameAttributeName(IdTokenClaimNames.SUB);
			builder.clientName("AppID");
			return builder;
		}
	},

	FRANKFURT {

		@Override
		public Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties) {
			ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
			builder.clientId(properties.getClientId());
			builder.clientSecret(properties.getClientSecret());
			builder.clientAuthenticationMethod(ClientAuthenticationMethod.BASIC);
			builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
			builder.redirectUriTemplate(properties.getRedirectUri());
			Set<String> scope = new HashSet<String>();
			scope.add("openid");
			builder.scope(properties.getScope().size() > 0 ? properties.getScope() : scope);
			builder.authorizationUri(APPID_OAUTH_URL_FRANKFURT + properties.getTenantID() + "/authorization");
			builder.tokenUri(APPID_OAUTH_URL_FRANKFURT + properties.getTenantID() + "/token");
			builder.userInfoUri(APPID_OAUTH_URL_FRANKFURT + properties.getTenantID() + "/userinfo");
			builder.jwkSetUri(APPID_OAUTH_URL_FRANKFURT + properties.getTenantID() + "/publickeys");
			builder.userNameAttributeName(IdTokenClaimNames.SUB);
			builder.clientName("AppID");
			return builder;
		}
	},

	LONDON {

		@Override
		public Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties) {
			ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
			builder.clientId(properties.getClientId());
			builder.clientSecret(properties.getClientSecret());
			builder.clientAuthenticationMethod(ClientAuthenticationMethod.BASIC);
			builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
			builder.redirectUriTemplate(properties.getRedirectUri());
			Set<String> scope = new HashSet<String>();
			scope.add("openid");
			builder.scope(properties.getScope() != null && properties.getScope().size() > 0 ? properties.getScope() : scope);
			builder.authorizationUri(APPID_OAUTH_URL_LONDON + properties.getTenantID() + "/authorization");
			builder.tokenUri(APPID_OAUTH_URL_LONDON + properties.getTenantID() + "/token");
			builder.userInfoUri(APPID_OAUTH_URL_LONDON + properties.getTenantID() + "/userinfo");
			builder.jwkSetUri(APPID_OAUTH_URL_LONDON + properties.getTenantID() + "/publickeys");
			builder.userNameAttributeName(IdTokenClaimNames.SUB);
			builder.clientName("AppID");
			return builder;
		}
	},
	
	TOKYO {

		@Override
		public Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties) {
			ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
			builder.clientId(properties.getClientId());
			builder.clientSecret(properties.getClientSecret());
			builder.clientAuthenticationMethod(ClientAuthenticationMethod.BASIC);
			builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
			builder.redirectUriTemplate(properties.getRedirectUri());
			Set<String> scope = new HashSet<String>();
			scope.add("openid");
			builder.scope(properties.getScope().size() > 0 ? properties.getScope() : scope);
			builder.authorizationUri(APPID_OAUTH_URL_TOKYO + properties.getTenantID() + "/authorization");
			builder.tokenUri(APPID_OAUTH_URL_TOKYO + properties.getTenantID() + "/token");
			builder.userInfoUri(APPID_OAUTH_URL_TOKYO + properties.getTenantID() + "/userinfo");
			builder.jwkSetUri(APPID_OAUTH_URL_TOKYO + properties.getTenantID() + "/publickeys");
			builder.userNameAttributeName(IdTokenClaimNames.SUB);
			builder.clientName("AppID");
			return builder;
		}
	};
	
	private static final String APPID_OAUTH_URL_DALLAS = "https://us-south.appid.cloud.ibm.com/oauth/v4/";
	private static final String APPID_OAUTH_URL_SYDNEY = "https://au-syd.appid.cloud.ibm.com/oauth/v4/";
	private static final String APPID_OAUTH_URL_FRANKFURT = "https://eu-de.appid.cloud.ibm.com/oauth/v4/";
	private static final String APPID_OAUTH_URL_LONDON = "https://eu-gb.appid.cloud.ibm.com/oauth/v4/";
	private static final String APPID_OAUTH_URL_TOKYO = "https://jp-tok.appid.cloud.ibm.com/oauth/v4/";

	/**
	 * Create a new pre-configured with provider defaults.
	 */
	public abstract ClientRegistration.Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties);

}
