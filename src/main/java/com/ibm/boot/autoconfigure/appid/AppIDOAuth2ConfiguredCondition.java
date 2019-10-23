package com.ibm.boot.autoconfigure.appid;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Condition that matches if any {@code spring.security.oauth2.client.registration.appid}
 * properties are defined.
 */

public class AppIDOAuth2ConfiguredCondition extends SpringBootCondition {

    private static final Bindable<Map<String, OAuth2ClientProperties.Registration>> STRING_REGISTRATION_MAP = Bindable
            .mapOf(String.class, OAuth2ClientProperties.Registration.class);

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder message = ConditionMessage.forCondition("OAuth2 Clients Configured Condition");
        ConditionOutcome result = ConditionOutcome.noMatch(message.notAvailable("registered clients"));
        Map<String, OAuth2ClientProperties.Registration> registrations = getRegistrations(context.getEnvironment());
        // TODO rename this variable to something descriptive
        String beMoreDescriptiveOfWhatThisKeyIs = "appid";
        if (!registrations.isEmpty() && registrations.containsKey(beMoreDescriptiveOfWhatThisKeyIs)) {
            String registeredClients = registrations.values().stream()
                    .map(OAuth2ClientProperties.Registration::getClientId)
                    .collect(Collectors.joining(", "));
            result = ConditionOutcome.match(message.foundExactly("registered clients " + registeredClients));
        }
        return result;
    }

    private Map<String, OAuth2ClientProperties.Registration> getRegistrations(Environment environment) {
        return Binder.get(environment)
                .bind("spring.security.oauth2.client.registration", STRING_REGISTRATION_MAP)
                .orElse(Collections.emptyMap());
    }
}
