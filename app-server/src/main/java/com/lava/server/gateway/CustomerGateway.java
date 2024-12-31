package com.lava.server.gateway;

import com.lava.server.customer.CustomerAddingEvent;
import com.lava.server.customer.CustomerExternalAPI;
import com.lava.server.customer.repository.CustomerRepository;
import com.lava.server.integration.IntegrationExternalAPI;
import com.lava.server.openapi.api.CustomerApi;
import com.lava.server.openapi.model.CustomerDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomerGateway implements CustomerApi {

    private CustomerExternalAPI customerExternalAPI;
    private IntegrationExternalAPI integrationExternalAPI;

    public CustomerGateway(CustomerExternalAPI customerExternalAPI, IntegrationExternalAPI integrationExternalAPI) {
        this.customerExternalAPI = customerExternalAPI;
        this.integrationExternalAPI = integrationExternalAPI;
    }

    @Override
    public ResponseEntity<CustomerDTO> addCustomer(CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerExternalAPI.add(customerDTO));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/upperCase",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<String> convertorCustomerName(@Valid @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(integrationExternalAPI.toUpperCase(customerDTO.getName()));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/callTest"
    )
    public ResponseEntity<String> callTest() {
        return ResponseEntity.ok(integrationExternalAPI.callTest());
    }


    @RequestMapping(value = "/test",
            method = RequestMethod.GET
    )
    public ResponseEntity<String> convertorCustomerName() {
        return ResponseEntity.ok("Test Is OK!");
    }


    @RequestMapping(
            method = RequestMethod.POST,
            value = "/callScript",
            produces = { "application/json" })
    public ResponseEntity<String> convertorCustomerName(@RequestBody String script) {
        return ResponseEntity.ok(integrationExternalAPI.callScript(script));
    }

    /* @PostMapping(value = "/customer")
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerExternalAPI.add(customerDTO);
    }*/

}
