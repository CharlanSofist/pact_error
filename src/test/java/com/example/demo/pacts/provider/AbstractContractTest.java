package com.example.demo.pacts.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.IgnoreNoPactsToVerify;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Provider("dummy-provider-java11oK")
@IgnoreNoPactsToVerify(ignoreIoErrors = "true")
@PactFolder("src/test/resources/pacts")
@Slf4j
abstract class AbstractContractTest {

    protected static WireMockServer wireMock;

    @BeforeEach
    protected void setup(PactVerificationContext context) {
        wireMock = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8080));
        wireMock.start();
        if (context != null) {
            context.setTarget(new HttpTestTarget("127.0.0.1", 8080, "/"));
        } else {
            verifyPactContext();
        }
    }

    @AfterEach
    protected void stop() {
        wireMock.stop();
    }

    protected String objectToJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter objeto para JSON", e);
        }
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    protected void pacTestTemplate(PactVerificationContext context) {
        if (context != null) {
            context.verifyInteraction();
        } else {
            verifyPactContext();
        }
    }

    private static void verifyPactContext() {
        String errorMessage = "PactVerificationContext está nulo. Verificação se existem contratos para o Provedor.";
        log.error(errorMessage);
        Assumptions.assumeTrue(false, errorMessage);
    }

}