package com.lava.server.integration;

public interface IntegrationExternalAPI {
    String toUpperCase(String term);
    String callTest();

    String callScript(String script);

}
