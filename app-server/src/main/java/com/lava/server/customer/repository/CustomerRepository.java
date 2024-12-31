package com.lava.server.customer.repository;

import com.lava.server.customer.model.entity.Customer;
import com.lava.server.openapi.model.CustomerDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface  CustomerRepository  extends  CrudRepository<Customer, String> {
    @Query("""
           SELECT new com.lava.server.openapi.model.CustomerDTO(c.id,  c.name,c.family,c.birthDate)
           FROM Customer c
           WHERE c.id = :id
           """)
    CustomerDTO findDTOById(String id);


}