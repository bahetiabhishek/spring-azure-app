package com.daimlertruck.sample;

import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.stereotype.Service;

@Service
public class AzureOAuth2Service {

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public AzureOAuth2Service(ClientRegistrationRepository clientRegistrationRepository,
                              OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, (OAuth2AuthorizedClientRepository) authorizedClientService);
    }

    public String getAccessToken() {
        OAuth2AuthorizedClient client = authorizedClientManager.authorize(
                OAuth2AuthorizeRequest.withClientRegistrationId("azure").principal("oauth2-client").build());

        return client != null ? client.getAccessToken().getTokenValue() : null;
    }
}

