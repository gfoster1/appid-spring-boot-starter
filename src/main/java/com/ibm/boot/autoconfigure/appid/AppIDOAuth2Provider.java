package com.ibm.boot.autoconfigure.appid;

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
			return getBuilder(registrationId, APPID_OAUTH_URL_DALLAS, properties);
		}
	},

	SYDNEY {

		@Override
		public Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties) {
			return getBuilder(registrationId, APPID_OAUTH_URL_SYDNEY, properties);
		}
	},

	FRANKFURT {

		@Override
		public Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties) {
			return getBuilder(registrationId, APPID_OAUTH_URL_FRANKFURT, properties);
		}
	},

	LONDON {

		@Override
		public Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties) {
			return getBuilder(registrationId, APPID_OAUTH_URL_LONDON, properties);
		}
	},
	
	TOKYO {

		@Override
		public Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties) {
			return getBuilder(registrationId, APPID_OAUTH_URL_TOKYO, properties);
		}
	};
	
	private static final String APPID_OAUTH_URL_DALLAS = "https://us-south.appid.cloud.ibm.com/oauth/";
	private static final String APPID_OAUTH_URL_SYDNEY = "https://au-syd.appid.cloud.ibm.com/oauth/";
	private static final String APPID_OAUTH_URL_FRANKFURT = "https://eu-de.appid.cloud.ibm.com/oauth/";
	private static final String APPID_OAUTH_URL_LONDON = "https://eu-gb.appid.cloud.ibm.com/oauth/";
	private static final String APPID_OAUTH_URL_TOKYO = "https://jp-tok.appid.cloud.ibm.com/oauth/";
	
	
	public Builder getBuilder(String registrationId, String appIDURL, AppIDOAuth2ConfigurationProperties.Registration properties) {
		Set<String> scope = new HashSet<String>();
		scope.add("openid");
		String oAuthServerUri = getOAuthServerUri(appIDURL, properties.getOAuthServerUri(),
				properties.getVersion(), properties.getTenantID());
		
		ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
		builder.clientId(properties.getClientId());
		builder.clientSecret(properties.getClientSecret());
		builder.clientAuthenticationMethod(ClientAuthenticationMethod.BASIC);
		builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
		builder.redirectUriTemplate(properties.getRedirectUri());
		builder.scope(properties.getScope() != null ? properties.getScope() : scope);
		builder.authorizationUri(oAuthServerUri + "/authorization");
		builder.tokenUri(oAuthServerUri + "/token");
		builder.userInfoUri(oAuthServerUri + "/userinfo");
		builder.jwkSetUri(oAuthServerUri + "/publickeys");
		builder.userNameAttributeName(IdTokenClaimNames.SUB);
		builder.clientName("AppID");
		return builder;
	}
	
	public String getOAuthServerUri(String appIDURL, String oAuthServerUri, String version, String tenantID) {
		if(oAuthServerUri != null) {
			return oAuthServerUri;
		}
		return new StringBuilder(appIDURL)
				.append("v" + version + "/")
				.append(tenantID).toString();
	}

	/**
	 * Create a new pre-configured with provider defaults.
	 */
	public abstract ClientRegistration.Builder getBuilder(String registrationId, AppIDOAuth2ConfigurationProperties.Registration properties);

}
