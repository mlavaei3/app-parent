package com.lava.server.shell.spel;

import org.springframework.data.spel.spi.EvaluationContextExtension;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class MyExtension  implements EvaluationContextExtension  {

    public static String extensionMethod() {
        return "Hello World";
    }

    @Override
    public String getExtensionId() {
        return "extension";
    }

    @Override
    public MyCustomFunctions getRootObject() {
        // Return the object you want to be available in SpEL expressions
        return new MyCustomFunctions();
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>(EvaluationContextExtension.super.getProperties());
        properties.put("key", "result");
        return properties;
    }
}