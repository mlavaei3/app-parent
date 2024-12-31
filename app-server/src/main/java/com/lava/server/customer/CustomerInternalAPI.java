package com.lava.server.customer;


import com.lava.server.openapi.model.CustomerDTO;

public interface CustomerInternalAPI {
    CustomerDTO getCustomerById(String id);
}
