package com.ibm.appid.spring.boot;

import java.util.Collections;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;


/**
 * Condition that matches if any {@code spring.security.oauth2.client.registration.appid}
 * properties are defined.
 */

public class AppIDOAuth2ConfiguredCondition extends SpringBootCondition {

	private static final Bindable<Map<String, OAuth2ClientProperties.Registration>> STRING_REGISTRATION_MAP = Bindable
			.mapOf(String.class, OAuth2ClientProperties.Registration.class);

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Map<String, OAuth2ClientProperties.Registration> registrations = getRegistrations(context.getEnvironment());
		if (!registrations.isEmpty() && registrations.containsKey("appid")) {
			return ConditionOutcome.match("AppID registration found");
		}
		return ConditionOutcome.noMatch("No matching AppID registration found");
	}
	
	private Map<String, OAuth2ClientProperties.Registration> getRegistrations(Environment environment) {
		return Binder.get(environment).bind("spring.security.oauth2.client.registration", STRING_REGISTRATION_MAP)
				.orElse(Collections.emptyMap());
	}
}
