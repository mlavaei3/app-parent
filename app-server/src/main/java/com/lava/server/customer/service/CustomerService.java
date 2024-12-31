package com.lava.server.customer.service;

import com.lava.server.customer.CustomerAddedEvent;
import com.lava.server.customer.CustomerAddingEvent;
import com.lava.server.customer.mapper.CustomerMapper;
import com.lava.server.customer.model.entity.Customer;
import com.lava.server.customer.repository.CustomerRepository;
import com.lava.server.customer.CustomerExternalAPI;
import com.lava.server.customer.CustomerInternalAPI;
import com.lava.server.openapi.model.CustomerDTO;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements CustomerInternalAPI,
        CustomerExternalAPI {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    private CustomerRepository repository;
    private CustomerMapper mapper;
    private ApplicationEventPublisher events;

    public CustomerService(CustomerRepository repository,
                           CustomerMapper mapper, ApplicationEventPublisher events) {
        this.repository = repository;
        this.mapper = mapper;
        this.events = events;
    }

    @Override
    public CustomerDTO add(CustomerDTO customer) {
        //publish
        CustomerAddingEvent customerAddingEvent = new CustomerAddingEvent(customer, null);
        events.publishEvent(customerAddingEvent);


        Customer newEmp = null;
        if (customerAddingEvent.getError() == null) {
            //repo
            newEmp = repository.save(mapper.customerDTOToCustomer(customer));

            //publish
            events.publishEvent(new CustomerAddedEvent(newEmp.getId()));
        } else {
            System.out.println("Error: " + customerAddingEvent.getError());
        }
        System.out.println("end............");
        return mapper.customerToCustomerDTO(newEmp);
    }

    @Override
    @Transactional
    public CustomerDTO getCustomerById(String customerId) {
        return repository.findDTOById(customerId);
    }

    @ApplicationModuleListener
    void onRemovedOrganizationEvent(CustomerAddedEvent event) {
        LOG.info("onCustomerAddEvent(cusId={})", event.id());
        var a = repository.findDTOById(event.id());
    }

    /*@EventListener
    void onCustomerAddingEvent(CustomerAddingEvent event) {
        LOG.info("onCustomerAddingEvent(cusId={})", event.getCustomerDTO().getId());
        event.setError("ERRRRRRR");
    }*/
}
