package com.example.demo.pacts.provider;

import au.com.dius.pact.provider.junitsupport.State;
import com.github.tomakehurst.wiremock.client.WireMock;

import java.io.IOException;


class ControllerOneContractTest extends AbstractContractTest {

    @State("retrieving a pact")
    void calculateClubBonuses() throws IOException {
        wireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/v1/categories"))
                .withHeader("Content-Type", WireMock.equalTo("application/json"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=UTF-8")
                        .withBody("{\"eventType\": \"CREATED\"}")));
    }
}

