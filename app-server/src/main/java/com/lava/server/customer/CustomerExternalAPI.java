package com.lava.server.customer;


import com.lava.server.openapi.model.CustomerDTO;

public interface CustomerExternalAPI {

    CustomerDTO add(CustomerDTO customer);

}
