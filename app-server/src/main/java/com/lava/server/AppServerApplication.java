package com.lava.server;

import com.lava.server.multipleauthapi.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.modulith.ApplicationModule;
import org.springframework.shell.command.annotation.CommandScan;


@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@ApplicationModule(allowedDependencies = {"multipleauthapi :: config", "openapi :: api", "openapi :: model"})
@CommandScan
public class AppServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(AppServerApplication.class, args);


    }

}
