package com.lava.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class AppServerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void writeDocumentationSnippets() {

        var modules = ApplicationModules.of(AppServerApplication.class).verify();

        modules.forEach(System.out::println);

        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }

}
