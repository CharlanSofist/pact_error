package com.example.demo.pacts.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "dummy-provider-java11oK", pactVersion = PactSpecVersion.V3)
public class ExampleContractTest {
    @Pact(provider = "dummy-provider-java11oK", consumer = "dummy-consumer-java11oK")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        DslPart body = new PactDslJsonBody()
                .stringValue("eventType", "CREATED");

        return builder
                .given("retrieving a pact")
                .uponReceiving("a pact")
                .path("/v1/categories")
                .method("GET")
                .headers(headers)
                .willRespondWith()
                .body(body)
                .status(200)
                .toPact();
    }

    // Metodo que executa os testes do pacto apontando a qual pacto está sendo testado no caso pactMethod = "createPact"
    @Test
    @PactTestFor(pactMethod = "createPact")
    void test1(MockServer mockServer) throws IOException {
        String url = mockServer.getUrl() + "/v1/categories";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-correlation-id", "123");
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println(response);
    }

    @Pact(provider = "dummy-provider-java11oK", consumer = "dummy-consumer-java11oK")
    public RequestResponsePact createPact2(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        DslPart body = new PactDslJsonBody()
                .stringValue("eventType", "CREATED");

        return builder
                .given("retrieving a pact two")
                .uponReceiving("a pact")
                .path("/v1/categories")
                .method("GET")
                .headers(headers)
                .willRespondWith()
                .body(body)
                .status(200)
                .toPact();
    }

    // Metodo que executa os testes do pacto apontando a qual pacto está sendo testado no caso pactMethod = "createPact"
    @Test
    @PactTestFor(pactMethod = "createPact2")
    void test2(MockServer mockServer) throws IOException {
        String url = mockServer.getUrl() + "/v1/categories";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-correlation-id", "123");
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println(response);
    }
}