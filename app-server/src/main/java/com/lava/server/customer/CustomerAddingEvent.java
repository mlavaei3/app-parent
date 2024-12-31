package com.lava.server.customer;

import com.lava.server.openapi.model.CustomerDTO;

public class CustomerAddingEvent {
    CustomerDTO customerDTO;
    String error;

    public CustomerAddingEvent(CustomerDTO customerDTO, String error) {
        this.customerDTO = customerDTO;
        this.error = error;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
