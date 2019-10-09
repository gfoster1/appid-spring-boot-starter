package com.ibm.appid.spring.boot;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties.Provider;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties.Registration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

/**
 * OAuth 2.0 client properties.
 */

@ConfigurationProperties(prefix="spring.security.oauth2.client")
public class AppIDOAuth2ConfigurationProperties {
	
	/**
	 * OAuth provider details.
	 */
	private final Map<String, Provider> provider = new HashMap<>();

	/**
	 * OAuth client registrations.
	 */
	private final Map<String, Registration> registration = new HashMap<>();

	public Map<String, Provider> getProvider() {
		return this.provider;
	}

	public Map<String, Registration> getRegistration() {
		return this.registration;
	}

	@PostConstruct
	public void validate() {
		this.getRegistration().values().forEach(this::validateRegistration);
	}

	private void validateRegistration(Registration registration) {
		if (!StringUtils.hasText(registration.getClientId())) {
			throw new IllegalStateException("Client id must not be empty.");
		}
	}

	/**
	 * A single client registration.
	 */
	public static class Registration {

		private String provider;

		/**
		 * Client ID for the registration.
		 */
		private String clientId;

		/**
		 * Client secret of the registration.
		 */
		private String clientSecret;

		/**
		 * Client authentication method.
		 */
		private String clientAuthenticationMethod;

		/**
		 * Authorization grant type.
		 */
		private String authorizationGrantType;

		/**
		 * Redirect URI.
		 */
		private String redirectUri;

		/**
		 * Authorization scopes.
		 */
		private Set<String> scope;

		/**
		 * Client name.
		 */
		private String clientName;
		
		/**
		 * AppID region.
		 */
		private String region;
		
		/**
		 * Tenant id of AppID
		 */
		private String tenantID;
		
		public String getProvider() {
			return this.provider;
		}

		public void setProvider(String provider) {
			this.provider = provider;
		}

		public String getClientId() {
			return this.clientId;
		}

		public void setClientId(String clientId) {
			this.clientId = clientId;
		}

		public String getClientSecret() {
			return this.clientSecret;
		}

		public void setClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
		}

		public String getClientAuthenticationMethod() {
			return this.clientAuthenticationMethod;
		}

		public void setClientAuthenticationMethod(String clientAuthenticationMethod) {
			this.clientAuthenticationMethod = clientAuthenticationMethod;
		}

		public String getAuthorizationGrantType() {
			return this.authorizationGrantType;
		}

		public void setAuthorizationGrantType(String authorizationGrantType) {
			this.authorizationGrantType = authorizationGrantType;
		}

		public String getRedirectUri() {
			return this.redirectUri;
		}

		public void setRedirectUri(String redirectUri) {
			this.redirectUri = redirectUri;
		}

		@Deprecated
		public String getRedirectUriTemplate() {
			return getRedirectUri();
		}

		@Deprecated
		public void setRedirectUriTemplate(String redirectUri) {
			setRedirectUri(redirectUri);
		}

		public Set<String> getScope() {
			return this.scope;
		}

		public void setScope(Set<String> scope) {
			this.scope = scope;
		}

		public String getClientName() {
			return this.clientName;
		}

		public void setClientName(String clientName) {
			this.clientName = clientName;
		}
		
		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getTenantID() {
			return tenantID;
		}

		public void setTenantID(String tenantID) {
			this.tenantID = tenantID;
		}
	}
}
