package com.lava.server;

import com.lava.server.openapi.model.CustomerDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.annotation.Order;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppRestControllerTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void shouldAddNewEmployee() {
        CustomerDTO emp = new CustomerDTO(null, "ali", "fam2", null);
        emp = restTemplate.postForObject("/api/customer", emp, CustomerDTO.class);
        assertNotNull(emp.getId());
    }


}
