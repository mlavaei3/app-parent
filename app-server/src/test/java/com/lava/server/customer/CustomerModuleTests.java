package com.lava.server.customer;


import com.lava.server.customer.repository.CustomerRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;

@ApplicationModuleTest(ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerModuleTests {

    private static final String TEST_ID = "100";

    @Autowired
    CustomerRepository repository;

    @Test
    @Order(1)
    void shouldAddDepartmentsOnEvent(Scenario scenario) {
        scenario.publish(new CustomerAddedEvent(TEST_ID))
                .andWaitForStateChange(() -> repository.findById(TEST_ID))
                .andVerify(result -> {assert !result.isEmpty();});
    }
}
